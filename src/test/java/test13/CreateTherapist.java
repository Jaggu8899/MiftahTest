package test13;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v144.network.Network;
import org.openqa.selenium.devtools.v144.network.model.Request;
import org.openqa.selenium.devtools.v144.network.model.Response;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateTherapist {

    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();

        int totalScenarios = 0;
        int passedCount = 0;
        int failedCount = 0;
        List<String> failedLogs = new ArrayList<>();
        List<String> passedLogs = new ArrayList<>();

        List<TherapistData.TherapistScenario> scenarios = TherapistData.getScenarios();
        totalScenarios = scenarios.size();

        for (TherapistData.TherapistScenario scenario : scenarios) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println(">>> Executing Scenario: " + scenario.scenarioName);
            System.out.println("=".repeat(50));

            ChromeOptions options = new ChromeOptions();
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            ChromeDriver driver = new ChromeDriver(options);

            DevTools devTools = driver.getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty()));

            Map<String, String> reqIdToMethod = new ConcurrentHashMap<>();
            Map<String, String> requestPayloads = new ConcurrentHashMap<>();
            List<String> currentScenarioBuffer = new ArrayList<>();
            StringBuilder scenarioLogs = new StringBuilder();
            currentScenarioBuffer.add("SCENARIO: " + scenario.scenarioName);

            devTools.addListener(Network.requestWillBeSent(), request -> {
                Request req = request.getRequest();
                String url = req.getUrl();
                if (!url.startsWith("data:") && !url.contains(".js") && !url.contains(".css") && !url.contains(".png")
                        && !url.contains(".jpg") && !url.contains(".svg") && !url.contains(".ico")) {
                    String reqId = request.getRequestId().toString();
                    reqIdToMethod.put(reqId, req.getMethod());
                    if (req.getPostData().isPresent()) {
                        requestPayloads.put(reqId, req.getPostData().get());
                    }
                }
            });

            devTools.addListener(Network.responseReceived(), response -> {
                Response res = response.getResponse();
                String url = res.getUrl();
                String reqId = response.getRequestId().toString();
                String method = reqIdToMethod.getOrDefault(reqId, "UNKNOWN");

                if (url.contains("/api/v1/master-users/therapist") && "POST".equals(method)) {
                    scenarioLogs.append("\n" + "=".repeat(40) + "\n");
                    scenarioLogs.append("[Header General Details]\n");
                    scenarioLogs.append("1. Request URL: ").append(url).append("\n");
                    scenarioLogs.append("2. Request Method: ").append(method).append("\n");
                    scenarioLogs.append("3. Status Code: ").append(res.getStatus()).append(" ")
                            .append(res.getStatusText()).append("\n");

                    String ip = res.getRemoteIPAddress().isPresent() ? res.getRemoteIPAddress().get() : "N/A";
                    String port = res.getRemotePort().isPresent() ? res.getRemotePort().get().toString() : "N/A";
                    scenarioLogs.append("4. Remote Address: ").append(ip).append(":").append(port).append("\n");

                    String refPol = "no-referrer-when-downgrade";
                    try {
                        Map<String, Object> headers = res.getHeaders().toJson();
                        for (String key : headers.keySet()) {
                            if (key.equalsIgnoreCase("referrer-policy")) {
                                refPol = headers.get(key).toString();
                                break;
                            }
                        }
                    } catch (Exception e) {
                    }
                    scenarioLogs.append("5. Referrer Policy: ").append(refPol).append("\n");

                    String payload = requestPayloads.get(reqId);
                    if (payload == null) {
                        try {
                            payload = devTools.send(Network.getRequestPostData(response.getRequestId()));
                        } catch (Exception e) {
                        }
                    }

                    if (payload != null) {
                        try {
                            if (payload.trim().startsWith("{")) {
                                JsonElement jsonElement = JsonParser.parseString(payload);
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                payload = gson.toJson(jsonElement);
                            } else if (payload.contains("Content-Disposition: form-data;")) {
                                StringBuilder formOutput = new StringBuilder();
                                String[] lines = payload.split("\\r?\\n");
                                String currentName = null;
                                boolean isFile = false;
                                StringBuilder currentValue = new StringBuilder();
                                boolean readingValue = false;

                                for (String line : lines) {
                                    if (line.startsWith("Content-Disposition: form-data;")) {
                                        int nameStart = line.indexOf("name=\"");
                                        if (nameStart != -1) {
                                            int nameEnd = line.indexOf("\"", nameStart + 6);
                                            currentName = line.substring(nameStart + 6, nameEnd);
                                        }
                                        isFile = line.contains("filename=\"");
                                        readingValue = false;
                                    } else if (line.isEmpty() && currentName != null && !readingValue) {
                                        readingValue = true;
                                    } else if (line.startsWith("--") && currentName != null) {
                                        if (isFile) {
                                            formOutput.append(currentName).append(": (binary)\n");
                                        } else {
                                            String val = currentValue.toString().trim();
                                            if (!val.isEmpty()) {
                                                try {
                                                    if (val.startsWith("{") || val.startsWith("[")) {
                                                        JsonElement jsonElement = JsonParser.parseString(val);
                                                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                                        val = gson.toJson(jsonElement);
                                                    }
                                                } catch (Exception e) {
                                                }
                                                formOutput.append(currentName).append(": ").append(val).append("\n");
                                            }
                                        }
                                        currentName = null;
                                        isFile = false;
                                        currentValue = new StringBuilder();
                                        readingValue = false;
                                    } else if (readingValue) {
                                        currentValue.append(line).append(" ");
                                    }
                                }
                                payload = formOutput.toString().trim();
                            }
                        } catch (Exception e) {
                        }
                        scenarioLogs.append("Payload Data:\n").append(payload).append("\n");
                    }

                    try {
                        String body = devTools.send(Network.getResponseBody(response.getRequestId())).getBody();
                        try {
                            JsonElement jsonElement = JsonParser.parseString(body);
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            body = gson.toJson(jsonElement);
                        } catch (Exception e) {
                            if (body.length() > 5000)
                                body = body.substring(0, 5000) + "... (truncated)";
                        }
                        scenarioLogs.append("\n[NETWORK RESPONSE]\n");
                        scenarioLogs.append(body).append("\n");
                    } catch (Exception e) {
                        scenarioLogs.append("\n[NETWORK RESPONSE]\n(No response body available)\n");
                    }
                }
            });

            devTools.send(org.openqa.selenium.devtools.v144.log.Log.enable());

            boolean isCreated = false;
            boolean errorObserved = false;

            try {
                driver.get("https://crmdev.miftah.ai/login/");
                driver.manage().window().maximize();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                // 1. Login
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                        .sendKeys("masters.therapist@gmail.com");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("Masters@1122");
                driver.findElement(By.xpath("//button[text()='Login']")).click();

                // 2. Navigation
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//span[contains(@class,'font-medium sidebar-text-transition')])[1]"))).click();
                wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//button[normalize-space(text())='Add Service Provider']")))
                        .click();

                // Clicking "Therapist" card
                try {
                    wait.until(ExpectedConditions
                            .elementToBeClickable(By.xpath("//h4[normalize-space(text())='Therapist']"))).click();
                } catch (Exception e) {
                    wait.until(ExpectedConditions
                            .elementToBeClickable(By.xpath("(//div[contains(@class,'flex items-start')])[2]"))).click();
                }

                // 3. Form Filling - Hybrid approach (Indices + Labels for safety)

                // --- Basic Info ---
                WebElement nameInput = wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")));
                fillField(driver, nameInput, scenario.name);

                driver.findElement(By.xpath("//button[.//span[normalize-space()='+971']]")).click();
                wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//*[contains(text(),'" + scenario.dialCode + "')]"))).click();
                WebElement phone1 = driver.findElement(By.xpath("(//input[contains(@class,'w-full h-10')])[1]"));
                fillField(driver, phone1, scenario.phone);

                driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//*[contains(text(),'" + scenario.secDialCode + "')])[last()]"))).click();
                WebElement phone2 = driver.findElement(By.xpath("(//input[contains(@class,'w-full h-10')])[2]"));
                fillField(driver, phone2, scenario.secPhone);

                driver.findElement(By.xpath("//span[@class='text-gray-400 text-sm']")).click();
                WebElement langOption = wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//*[text()='" + scenario.languages + "']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", langOption);

                WebElement descField = safeFind(driver, wait, "Description", "textarea",
                        "(//textarea[contains(@class,'w-full px-3')])[1]");
                fillField(driver, descField, scenario.description);

                WebElement countInput = driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]"));
                fillField(driver, countInput, scenario.count);

                WebElement currencySelect = driver
                        .findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]"));
                new Select(currencySelect).selectByVisibleText(scenario.currency);

                WebElement leadTimeInput = safeFind(driver, wait, "Lead Time", "input",
                        "(//input[contains(@class,'w-full px-3')])[3]");
                fillField(driver, leadTimeInput, scenario.leadTime);

                WebElement basePrice = safeFind(driver, wait, "Base Price", "input", "(//input[@min='0'])[3]");
                fillField(driver, basePrice, scenario.basePrice);

                WebElement priceSession = safeFind(driver, wait, "Price per Session", "input",
                        "(//input[@min='0'])[4]");
                fillField(driver, priceSession, scenario.pricePerSession);

                WebElement priceHour = safeFind(driver, wait, "Price per Hour", "input", "(//input[@min='0'])[5]");
                fillField(driver, priceHour, scenario.pricePerHour);

                WebElement bespokePrice = safeFind(driver, wait, "Bespoke Price", "input", "(//input[@min='0'])[6]");
                fillField(driver, bespokePrice, scenario.bespokePrice);

                // --- Professional Info ---
                WebElement licenseNum = safeFind(driver, wait, "License Number", "input",
                        "(//label[contains(.,'License Number')]/following::input)[1]");
                fillField(driver, licenseNum, scenario.licenseNumber);

                WebElement therapyMethods = driver
                        .findElement(By.xpath("//input[@placeholder='Enter therapy methods (comma separated)']"));
                fillField(driver, therapyMethods, scenario.therapyMethods + "\n");

                WebElement experience = safeFind(driver, wait, "Experience", "textarea",
                        "(//textarea[contains(@class,'w-full px-3')])[2]");
                fillField(driver, experience, scenario.experience);

                WebElement capacity = safeFind(driver, wait, "Capacity", "input", "//input[@min='1']");
                fillField(driver, capacity, scenario.capacity);

                if (scenario.homeVisits) {
                    try {
                        driver.findElement(By
                                .xpath("//label[normalize-space()='Home Visits?']/following::input[@type='radio'][1]"))
                                .click();
                    } catch (Exception e) {
                        driver.findElement(By.xpath("(//input[contains(@class,'w-4 h-4')])[2]")).click();
                    }
                }

                WebElement telehealth = safeFind(driver, wait, "Telehealth Platform", "input",
                        "(//label[contains(.,'Telehealth Platform')]/following::input)[1]");
                fillField(driver, telehealth, scenario.telehealthPlatform);

                WebElement locSelect = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[contains(text(),'Location Type')]/following::select[1]")));
                new Select(locSelect).selectByVisibleText(scenario.locationType);

                WebElement radiusInput = safeFind(driver, wait, "Service Radius", "input",
                        "(//label[contains(.,'Service Radius')]/following::input)[1]");
                fillField(driver, radiusInput, scenario.serviceRadius);

                WebElement citiesInput = driver
                        .findElement(By.xpath("//input[@placeholder='Enter cities (comma separated)']"));
                fillField(driver, citiesInput, scenario.cities + "\n");

                WebElement cancelPolicy = safeFind(driver, wait, "cancellation policy", "textarea",
                        "(//textarea[contains(@class,'w-full px-3')])[3]");
                fillField(driver, cancelPolicy, scenario.cancellationPolicy);

                WebElement termsPolicy = driver
                        .findElement(By.xpath("//textarea[@placeholder='Enter terms and conditions...']"));
                fillField(driver, termsPolicy, scenario.termsAndConditions);

                // 4. Images
                WebElement coverInput = wait
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
                File coverFile = new File("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");
                if (coverFile.exists())
                    coverInput.sendKeys(coverFile.getAbsolutePath());

                WebElement additionalInput = wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[last()]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);
                File folder = new File("C:\\Users\\NS\\Desktop\\images");
                if (folder.exists() && folder.listFiles() != null) {
                    StringBuilder allFiles = new StringBuilder();
                    for (File f : folder.listFiles()) {
                        if (f.isFile() && f.getName().contains("."))
                            allFiles.append(f.getAbsolutePath()).append("\n");
                    }
                    if (allFiles.length() > 0)
                        additionalInput.sendKeys(allFiles.toString().trim());
                }

                Thread.sleep(5000);

                // 5. Submit
                WebElement submitBtn = wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//button[contains(.,'Create Service Provider')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

                // 6. Verification
                try {
                    WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'successfully') or contains(text(),'Success')]")));
                    System.out.println("Result: SUCCESS - " + toast.getText());
                    isCreated = true;
                } catch (Exception e) {
                    List<WebElement> errors = driver.findElements(
                            By.xpath("//p[contains(@class,'text-red')] | //div[contains(@class,'error')]"));
                    if (!errors.isEmpty()) {
                        System.out.println("Result: VALIDATION ERROR OBSERVED");
                        errorObserved = true;
                        for (WebElement err : errors) {
                            if (err.isDisplayed() && !err.getText().isEmpty())
                                currentScenarioBuffer.add("VALIDATION ERROR: " + err.getText());
                        }
                    } else if (driver.findElements(By.xpath("//button[contains(.,'Create Service Provider')]"))
                            .isEmpty()) {
                        System.out.println("Result: SUCCESS (Form disappeared)");
                        isCreated = true;
                    } else {
                        System.out.println("Result: FAILED (No success, no clear error)");
                    }
                }

            } catch (Exception e) {
                System.err.println("Error in scenario: " + scenario.scenarioName + " | " + e.getMessage());
                currentScenarioBuffer.add("EXCEPTION: " + e.getMessage());
            } finally {
                try {
                    LogEntries browserLogs = driver.manage().logs().get(LogType.BROWSER);
                    for (LogEntry entry : browserLogs)
                        currentScenarioBuffer.add("CONSOLE [" + entry.getLevel() + "] : " + entry.getMessage());
                } catch (Exception e) {
                }

                try {
                    File logDir = new File("test-logs");
                    if (!logDir.exists())
                        logDir.mkdirs();

                    String fileName = "Therapist_" + scenario.scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_"
                            + (isCreated ? "PASS" : "FAIL") + ".txt";
                    File scenarioFile = new File(logDir, fileName);
                    try (PrintWriter writer = new PrintWriter(new FileWriter(scenarioFile))) {
                        writer.println(scenarioLogs.toString());
                    }
                    currentScenarioBuffer.add("Log saved to: " + scenarioFile.getAbsolutePath());
                    currentScenarioBuffer.add("\n" + scenarioLogs.toString());
                    System.out.println("Log saved to: " + scenarioFile.getAbsolutePath());
                } catch (Exception e) {
                    currentScenarioBuffer.add("Failed to save scenario log: " + e.getMessage());
                }

                try {
                    driver.quit();
                } catch (Exception e) {
                }
            }

            boolean isNegative = scenario.scenarioName.startsWith("Neg");
            String finalLog = String.join("\n", currentScenarioBuffer);

            if ((!isNegative && isCreated) || (isNegative && (errorObserved || !isCreated))) {
                passedCount++;
                passedLogs.add(finalLog + "\nRESULT: PASSED " + (isNegative ? "(Matched Negative Expectation)" : ""));
            } else {
                failedCount++;
                failedLogs.add(finalLog + "\nRESULT: FAILED");
            }
            Thread.sleep(2000);
        }

        System.out.println("\n===== SUMMARY =====");
        System.out.println("Passed: " + passedCount + "/" + totalScenarios);

        try {
            File resourceDir = new File("src/test/resource");
            if (!resourceDir.exists())
                resourceDir.mkdirs();
            File logFile = new File(resourceDir, "Therapist");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
                writer.println("Total Test Cases Executed: " + totalScenarios);
                writer.println("----------------------------------------");
                writer.println("Passed Test Cases: " + passedCount + "   Negative Test Cases: " + failedCount);
                writer.println("----------------------------------------");

                writer.println("\nDETAILED LOGS PER Console (FAILED CASES FIRST):");
                writer.println("\n--- FAILED TEST CASES ---");
                if (failedLogs.isEmpty()) {
                    writer.println("No failed test cases.");
                } else {
                    for (String log : failedLogs) {
                        writer.println(log);
                        writer.println("----------------------------------------");
                    }
                }

                writer.println("\n--- PASSED TEST CASES ---");
                if (passedLogs.isEmpty()) {
                    writer.println("No passed test cases.");
                } else {
                    for (String log : passedLogs) {
                        writer.println(log);
                        writer.println("----------------------------------------");
                    }
                }

                writer.println("\nReport Generated on: " + dtf.format(now));
            }
            System.out.println("Log file generated at: " + logFile.getAbsolutePath());
        } catch (Exception e) {
        }
    }

    private static void fillField(WebDriver driver, WebElement element, String text) {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(500);
            element.click();
        } catch (Exception e) {
        }
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(text);
    }

    private static WebElement safeFind(WebDriver driver, WebDriverWait wait, String label, String type,
            String fallbackXpath) {
        try {
            return findByLabel(driver, label, type);
        } catch (Exception e) {
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fallbackXpath)));
        }
    }

    private static WebElement findByLabel(WebDriver driver, String labelText, String elementType) {
        String xpath = String.format("//div[label[contains(.,'%s')]]//%s | //label[contains(.,'%s')]/following::%s[1]",
                labelText, elementType, labelText, elementType);
        return driver.findElement(By.xpath(xpath));
    }
}
