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

public class CreateTrainer {

    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();

        int totalScenarios = 0;
        int passedCount = 0;
        int failedCount = 0;
        List<String> failedLogs = new ArrayList<>();
        List<String> passedLogs = new ArrayList<>();

        List<TrainerData.TrainerScenario> scenarios = TrainerData.getScenarios();
        totalScenarios = scenarios.size();

        for (TrainerData.TrainerScenario scenario : scenarios) {
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

                // STRICT FILTER: Only capture POST requests to the trainer endpoint
                if (url.contains("/api/v1/master-users/trainer") && "POST".equals(method)) {
                    scenarioLogs.append("\n" + "=".repeat(40) + "\n");
                    scenarioLogs.append("[Header General Details]\n");
                    scenarioLogs.append("1. Request URL: ").append(url).append("\n");
                    scenarioLogs.append("2. Request Method: ").append(method).append("\n");
                    scenarioLogs.append("3. Status Code: ").append(res.getStatus()).append(" ")
                            .append(res.getStatusText()).append("\n");

                    String ip = res.getRemoteIPAddress().isPresent() ? res.getRemoteIPAddress().get() : "N/A";
                    String port = res.getRemotePort().isPresent() ? res.getRemotePort().get().toString() : "N/A";
                    scenarioLogs.append("4. Remote Address: ").append(ip).append(":").append(port).append("\n");

                    String refPol = "no-referrer-when-downgrade"; // Default
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
                            // Payload might not be available or request method doesn't support it
                        }
                    }

                    if (payload != null) {
                        try {
                            // Check if it's JSON first
                            if (payload.trim().startsWith("{")) {
                                JsonElement jsonElement = JsonParser.parseString(payload);
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                payload = gson.toJson(jsonElement);
                            } else if (payload.contains("Content-Disposition: form-data;")) {
                                // Manual Multipart Parsing
                                StringBuilder formOutput = new StringBuilder();
                                String[] lines = payload.split("\\r?\\n");
                                String currentName = null;
                                boolean isFile = false;
                                StringBuilder currentValue = new StringBuilder();
                                boolean readingValue = false;

                                for (String line : lines) {
                                    if (line.startsWith("Content-Disposition: form-data;")) {
                                        // Extract name
                                        int nameStart = line.indexOf("name=\"");
                                        if (nameStart != -1) {
                                            int nameEnd = line.indexOf("\"", nameStart + 6);
                                            currentName = line.substring(nameStart + 6, nameEnd);
                                        }
                                        isFile = line.contains("filename=\"");
                                        readingValue = false; // Reset until empty line
                                    } else if (line.isEmpty() && currentName != null && !readingValue) {
                                        readingValue = true; // Start reading value after empty line
                                    } else if (line.startsWith("--") && currentName != null) {
                                        // End of part
                                        if (isFile) {
                                            formOutput.append(currentName).append(": (binary)\n");
                                        } else {
                                            String val = currentValue.toString().trim();
                                            if (!val.isEmpty())
                                                formOutput.append(currentName).append(": ").append(val).append("\n");
                                        }
                                        // Reset for next part
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
                            // Keep original if parsing fails
                        }
                        scenarioLogs.append("Payload Data:\n").append(payload).append("\n");
                    }

                    try {
                        String body = devTools.send(Network.getResponseBody(response.getRequestId())).getBody();

                        // Parse and pretty print JSON
                        try {
                            JsonElement jsonElement = JsonParser.parseString(body);
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            body = gson.toJson(jsonElement);
                        } catch (Exception e) {
                            // Not a valid JSON, keep original body
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

            // Console logs disabled - only capturing specific POST request
            // devTools.addListener(org.openqa.selenium.devtools.v129.log.Log.entryAdded(),
            // entry -> {
            // scenarioLogs.append("\n[CONSOLE DATA] [").append(entry.getLevel()).append("]
            // ").append(entry.getText())
            // .append("\n");
            // });

            devTools.send(org.openqa.selenium.devtools.v144.log.Log.enable());

            boolean isCreated = false;
            boolean errorObserved = false;

            try {
                driver.get("https://crmdev.miftah.ai/login/");
                driver.manage().window().maximize();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                // 1. Login
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                        .sendKeys("masters.trainer@gmail.com");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("Masters@1122");
                driver.findElement(By.xpath("//button[text()='Login']")).click();

                // 2. Navigation
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//span[contains(@class,'font-medium sidebar-text-transition')])[1]"))).click();
                wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//button[normalize-space(text())='Add Service Provider']")))
                        .click();
                wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//h4[normalize-space(text())='Personal Trainer']"))).click();

                // 3. Form Filling - Sequential Flow

                // --- Basic Information ---
                WebElement nameInput = wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")));
                fillField(driver, nameInput, scenario.serviceTitle);

                driver.findElement(By.xpath("//button[.//span[normalize-space()='+971']]")).click();
                wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//*[contains(text(),'" + scenario.dialCode + "')]"))).click();
                WebElement phone1 = driver.findElement(By.xpath("(//input[contains(@class,'w-full h-10')])[1]"));
                fillField(driver, phone1, scenario.phoneNumber);

                driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//*[contains(text(),'" + scenario.secDialCode + "')])[last()]"))).click();
                WebElement phone2 = driver.findElement(By.xpath("(//input[contains(@class,'w-full h-10')])[2]"));
                fillField(driver, phone2, scenario.alternativePhoneNumber);

                driver.findElement(By.xpath("//span[@class='text-gray-400 text-sm']")).click();
                WebElement langOption = wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//*[text()='" + scenario.languages + "']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", langOption);

                WebElement descField = findByLabel(driver, "Description", "textarea");
                fillField(driver, descField, scenario.description);

                WebElement countInput = driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]"));
                fillField(driver, countInput, scenario.sessionsAvailable);

                WebElement currencySelect = driver
                        .findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]"));
                new Select(currencySelect).selectByVisibleText(scenario.currency);

                WebElement leadTimeInput = findByLabel(driver, "Lead Time", "input");
                fillField(driver, leadTimeInput, scenario.leadTime);

                WebElement basePrice = findByLabel(driver, "Base Price", "input");
                fillField(driver, basePrice, scenario.basePrice);

                WebElement priceSession = findByLabel(driver, "Price per Session", "input");
                fillField(driver, priceSession, scenario.pricePerSession);

                WebElement priceHour = findByLabel(driver, "Price per Hour", "input");
                fillField(driver, priceHour, scenario.pricePerHour);

                WebElement bespokePrice = findByLabel(driver, "Bespoke Price", "input");
                fillField(driver, bespokePrice, scenario.bespokePrice);

                // --- Professional Information ---
                WebElement trainingTypes = driver
                        .findElement(By.xpath("//input[@placeholder='Enter training types (comma separated)']"));
                fillField(driver, trainingTypes, scenario.trainingTypes + "\n");

                WebElement expDetails = findByLabel(driver, "Experience Details", "textarea");
                fillField(driver, expDetails, scenario.experienceDetails);

                WebElement certDetails = findByLabel(driver, "Certification Details", "textarea");
                fillField(driver, certDetails, scenario.certificationDetails);

                // License Number Removed (Not on form)

                WebElement sessDur = findByLabel(driver, "Session Duration", "input");
                fillField(driver, sessDur, scenario.sessionDuration);

                // Capacity Removed (Not on form)

                WebElement levelFocus = driver
                        .findElement(By.xpath("//input[@placeholder='Enter levels (comma separated)']"));
                fillField(driver, levelFocus, scenario.levelFocus + "\n");

                if (scenario.equipmentProvided) {
                    driver.findElement(By.xpath(
                            "//label[normalize-space()='Equipment Provided?']/following::input[@type='radio'][1]"))
                            .click();
                }

                WebElement locSelect = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[contains(text(),'Location Type')]/following::select[1]")));
                new Select(locSelect).selectByVisibleText(scenario.locationType);

                WebElement radiusInput = findByLabel(driver, "Service Radius", "input");
                fillField(driver, radiusInput, scenario.serviceRadius);

                WebElement citiesInput = driver
                        .findElement(By.xpath("//input[@placeholder='Enter cities (comma separated)']"));
                fillField(driver, citiesInput, scenario.cities + "\n");

                WebElement cancelPolicy = driver
                        .findElement(By.xpath("//textarea[@placeholder='Enter cancellation policy...']"));
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
                    WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'successfully') or contains(text(),'Success')]")));
                    System.out.println("Result: SUCCESS - " + toast.getText());
                    isCreated = true;
                } catch (Exception e) {
                    // Check for validation errors (usually red text or specific classes)
                    List<WebElement> errors = driver.findElements(
                            By.xpath("//p[contains(@class,'text-red')] | //div[contains(@class,'error')]"));
                    if (!errors.isEmpty()) {
                        System.out.println("Result: VALIDATION ERROR OBSERVED (Expected for Negative Tests)");
                        errorObserved = true;
                        for (WebElement err : errors) {
                            if (err.isDisplayed() && !err.getText().isEmpty()) {
                                currentScenarioBuffer.add("VALIDATION ERROR: " + err.getText());
                            }
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
                // Capture Browser Console Logs
                try {
                    LogEntries browserLogs = driver.manage().logs().get(LogType.BROWSER);
                    for (LogEntry entry : browserLogs) {
                        currentScenarioBuffer.add("CONSOLE [" + entry.getLevel() + "] : " + entry.getMessage());
                    }
                } catch (Exception e) {
                }

                // Add Network Logs from current scenario
                currentScenarioBuffer.add("\n" + scenarioLogs.toString());

                try {
                    driver.quit();
                } catch (Exception e) {
                    System.err.println("Driver quit failed: " + e.getMessage());
                }
            }

            // Determine if test matched expectation
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

        // Final Logging in requested format
        System.out.println("\n===== SUMMARY =====");
        System.out.println("Passed: " + passedCount + "/" + totalScenarios);

        try {
            File resourceDir = new File("src/test/resource/logs");
            if (!resourceDir.exists())
                resourceDir.mkdirs();
            File logFile = new File(resourceDir, "Trainer.log");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
                writer.println("Total Test Cases Executed: " + totalScenarios);
                writer.println("----------------------------------------");
                writer.println("Passed Test Cases: " + passedCount + "   Failed Test Cases: " + failedCount);
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

    private static WebElement findByLabel(WebDriver driver, String labelText, String elementType) {
        String xpath = String.format("//div[label[contains(.,'%s')]]//%s | //label[contains(.,'%s')]/following::%s[1]",
                labelText, elementType, labelText, elementType);
        return driver.findElement(By.xpath(xpath));
    }
}
