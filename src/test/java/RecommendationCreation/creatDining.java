package RecommendationCreation;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class creatDining {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();

        int totalScenarios = 0;
        int passedCount = 0;
        int failedCount = 0;
        java.util.List<String> passedScenariosNames = new java.util.ArrayList<>();
        java.util.List<String> failedScenariosNames = new java.util.ArrayList<>();
        java.util.List<String> detailedLogs = new java.util.ArrayList<>();
        List<DiningData.DiningScenario> scenarios = DiningData.getScenarios();
        totalScenarios = scenarios.size();

        for (DiningData.DiningScenario scenario : scenarios) {
            System.out.println("Executing Scenario: " + scenario.scenarioName);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            ChromeDriver driver = new ChromeDriver(options);
            try {
                driver.get("https://crmdev.miftah.ai/dashboard/");
                driver.manage().window().maximize();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                // Login
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")))
                        .sendKeys("jagadeeswara89@gmail.com");
                driver.findElement(By.id("password")).sendKeys("Jaggu@89");
                driver.findElement(By.xpath("//button[text()='Login']")).click();

                // Wait for Dashboard
                wait.until(ExpectedConditions.urlContains("dashboard"));
                Thread.sleep(1000);

                // Navigate to Recommendations
                By miftahBtn = By.xpath("//button[contains(.,'Miftah Recommendations')]");
                wait.until(ExpectedConditions.elementToBeClickable(miftahBtn)).click();
                Thread.sleep(1000);

                // Click Add Recommendation Service
                WebElement addBtnNav = wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//button[contains(.,'Add Recommendation Service')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtnNav);
                Thread.sleep(2000);

                // Select Dining category
                System.out.println("  Selecting Dining category");
                try {
                    WebElement chooseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                            "//button[.//h3[contains(text(),'Dining')]]//span[contains(text(),'Choose')] | //button[contains(.,'Dining')]//span[text()='Choose']")));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", chooseBtn);
                    Thread.sleep(1000);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chooseBtn);
                } catch (Exception e) {
                    WebElement diningCard = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(.,'Dining')]")));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", diningCard);
                }
                Thread.sleep(3000);

                // --- Form Data Entry ---
                System.out.println("  Filling form fields");

                // 1. Title
                System.out.println("  1. Title");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Title')]/following::input[1]")));
                    safeFill(driver, el, scenario.title);
                } catch (Exception e) {
                    System.err.println("  FAIL Title: " + e.getMessage());
                }
                Thread.sleep(200);

                // 2. Description — this is an INPUT field (not textarea)
                System.out.println("  2. Description");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[normalize-space()='Description']/following::input[1]")));
                    safeFill(driver, el, scenario.description);
                } catch (Exception e) {
                    System.err.println("  FAIL Description: " + e.getMessage());
                }
                Thread.sleep(200);

                // 3. Location
                System.out.println("  3. Location");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Location')]/following::input[1]")));
                    safeFill(driver, el, scenario.location);
                } catch (Exception e) {
                    System.err.println("  FAIL Location: " + e.getMessage());
                }
                Thread.sleep(200);

                // 4. City — placeholder: "e.g. Dubai, New York"
                System.out.println("  4. City");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'Dubai')] | //label[contains(.,'City')]/following::input[1]")));
                    safeFill(driver, el, scenario.city);
                } catch (Exception e) {
                    System.err.println("  FAIL City: " + e.getMessage());
                }
                Thread.sleep(200);

                // 5. Rating
                System.out.println("  5. Rating");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Rating')]/following::input[1]")));
                    safeFill(driver, el, scenario.rating);
                } catch (Exception e) {
                    System.err.println("  FAIL Rating: " + e.getMessage());
                }
                Thread.sleep(200);

                // 6. Lead Time — placeholder: "e.g. 1.5"
                System.out.println("  6. Lead Time");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'1.5')] | //label[contains(.,'Lead Time')]/following::input[1]")));
                    safeFill(driver, el, scenario.leadTime);
                } catch (Exception e) {
                    System.err.println("  FAIL Lead Time: " + e.getMessage());
                }
                Thread.sleep(200);

                // 7. Price — placeholder: "e.g., 150.00"
                System.out.println("  7. Price");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'150.00')] | //label[contains(.,'Price')]/following::input[1]")));
                    safeFill(driver, el, scenario.price);
                } catch (Exception e) {
                    System.err.println("  FAIL Price: " + e.getMessage());
                }
                Thread.sleep(200);

                // 8. Currency (select dropdown)
                System.out.println("  8. Currency");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Currency')]/following::select[1]")));
                    Select sel = new Select(el);
                    try {
                        sel.selectByVisibleText("AED");
                    } catch (Exception e1) {
                        try {
                            sel.selectByValue("AED");
                        } catch (Exception e2) {
                            System.out.println("  Currency: using default");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Currency: " + e.getMessage());
                }
                Thread.sleep(200);

                // 9. Contact Email — placeholder: "user@example.com"
                System.out.println("  9. Contact Email");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'user@example.com')] | //label[contains(.,'Contact Email')]/following::input[1]")));
                    safeFill(driver, el, scenario.contactEmail);
                } catch (Exception e) {
                    System.err.println("  FAIL Contact Email: " + e.getMessage());
                }
                Thread.sleep(200);

                // 10. Contact Phone — placeholder: "501234567"
                System.out.println("  10. Contact Phone");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'501234567')] | //label[contains(.,'Contact Phone')]/following::input[1]")));
                    safeFill(driver, el, scenario.contactPhone);
                } catch (Exception e) {
                    System.err.println("  FAIL Contact Phone: " + e.getMessage());
                }
                Thread.sleep(200);

                // 11. Contact Person
                System.out.println("  11. Contact Person");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Contact Person')]/following::input[1]")));
                    safeFill(driver, el, scenario.contactPerson);
                } catch (Exception e) {
                    System.err.println("  FAIL Contact Person: " + e.getMessage());
                }
                Thread.sleep(200);

                // 12. Dining Description — textarea, placeholder: "Enter a detailed
                // description"
                System.out.println("  12. Dining Description");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//textarea[contains(@placeholder,'detailed description')] | //label[contains(.,'Dining Description')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.diningDescription);
                } catch (Exception e) {
                    System.err.println("  FAIL Dining Description: " + e.getMessage());
                }
                Thread.sleep(200);

                // 13. Restaurant Group
                System.out.println("  13. Restaurant Group");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Restaurant Group')]/following::input[1]")));
                    safeFill(driver, el, scenario.restaurantGroup);
                } catch (Exception e) {
                    System.err.println("  FAIL Restaurant Group: " + e.getMessage());
                }
                Thread.sleep(200);

                // 14. Dining Includes — input with Add button
                // placeholder: "Add string items (separated by commas, e.g., wine pairing,
                // valet)"
                System.out.println("  14. Dining Includes");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'wine pairing') or contains(@placeholder,'string items')] | //label[contains(.,'Dining Includes')]/following::input[1]")));
                    scrollAndFill(driver, el, scenario.diningIncludes);
                    Thread.sleep(300);
                    try {
                        WebElement addBtn = driver.findElement(
                                By.xpath(
                                        "//input[contains(@placeholder,'wine pairing') or contains(@placeholder,'string items')]/following-sibling::button[normalize-space()='Add'] | //label[contains(.,'Dining Includes')]/following::button[normalize-space()='Add'][1]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                        System.out.println("  Dining Includes Add clicked");
                    } catch (Exception e2) {
                        System.err.println("  Could not click Dining Includes Add: " + e2.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Dining Includes: " + e.getMessage());
                }
                Thread.sleep(300);

                // 15. Features — input with Add button
                // placeholder: "Enter features (separated by commas, e.g., coffee, music, bar)"
                System.out.println("  15. Features");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'features') and contains(@placeholder,'coffee')] | //input[contains(@placeholder,'music, bar')] | //label[contains(.,'Features')]/following::input[1]")));
                    scrollAndFill(driver, el, scenario.features);
                    Thread.sleep(300);
                    try {
                        WebElement addBtn = driver.findElement(
                                By.xpath(
                                        "//input[contains(@placeholder,'features') or contains(@placeholder,'music, bar')]/following-sibling::button[normalize-space()='Add'] | //label[contains(.,'Features')]/following::button[normalize-space()='Add'][1]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                        System.out.println("  Features Add clicked");
                    } catch (Exception e2) {
                        System.err.println("  Could not click Features Add: " + e2.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Features: " + e.getMessage());
                }
                Thread.sleep(300);

                // 16. Meta Data — input with Add button
                // placeholder: "Enter metadata (separated by commas, e.g., outdoor seating,
                // live music)"
                System.out.println("  16. Meta Data");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'metadata')] | //label[contains(.,'Meta Data')]/following::input[1]")));
                    scrollAndFill(driver, el, scenario.metaData);
                    Thread.sleep(300);
                    try {
                        WebElement addBtn = driver.findElement(
                                By.xpath(
                                        "//input[contains(@placeholder,'metadata')]/following-sibling::button[normalize-space()='Add'] | //label[contains(.,'Meta Data')]/following::button[normalize-space()='Add'][1]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                        System.out.println("  Meta Data Add clicked");
                    } catch (Exception e2) {
                        System.err.println("  Could not click Meta Data Add: " + e2.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Meta Data: " + e.getMessage());
                }
                Thread.sleep(300);

                // 17. Dress Code — placeholder: "e.g., Smart casual"
                System.out.println("  17. Dress Code");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'Smart casual')] | //label[contains(.,'Dress Code')]/following::input[1]")));
                    safeFill(driver, el, scenario.dressCode);
                } catch (Exception e) {
                    System.err.println("  FAIL Dress Code: " + e.getMessage());
                }
                Thread.sleep(200);

                // 18. Cuisine Type — placeholder: "e.g., Mediterranean, Japanese, Italian"
                System.out.println("  18. Cuisine Type");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'Mediterranean')] | //label[contains(.,'Cuisine Type')]/following::input[1]")));
                    safeFill(driver, el, scenario.cuisineType);
                } catch (Exception e) {
                    System.err.println("  FAIL Cuisine Type: " + e.getMessage());
                }
                Thread.sleep(200);

                // 19. Opening Time — TIME PICKER input
                System.out.println("  19. Opening Time");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Opening Time')]/following::input[1]")));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", el);
                    Thread.sleep(200);
                    // Use nativeInputValueSetter for React compatibility
                    ((JavascriptExecutor) driver).executeScript(
                            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                                    + "nativeInputValueSetter.call(arguments[0], arguments[1]);"
                                    + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                                    + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                            el, scenario.openingTime);
                    System.out.println("  Opening Time set to: " + scenario.openingTime);
                } catch (Exception e) {
                    System.err.println("  FAIL Opening Time: " + e.getMessage());
                }
                Thread.sleep(200);

                // 20. Closing Time — TIME PICKER input
                System.out.println("  20. Closing Time");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Closing Time')]/following::input[1]")));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", el);
                    Thread.sleep(200);
                    // Use nativeInputValueSetter for React compatibility
                    ((JavascriptExecutor) driver).executeScript(
                            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                                    + "nativeInputValueSetter.call(arguments[0], arguments[1]);"
                                    + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                                    + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                            el, scenario.closingTime);
                    System.out.println("  Closing Time set to: " + scenario.closingTime);
                } catch (Exception e) {
                    System.err.println("  FAIL Closing Time: " + e.getMessage());
                }
                Thread.sleep(200);

                // 21. Available Time Slots — Loop through Breakfast, Lunch, Dinner
                System.out.println("  21. Time Slots");
                if (scenario.mealTypes != null && scenario.mealTypes.length > 0) {
                    for (int i = 0; i < scenario.mealTypes.length; i++) {
                        System.out.println("  21." + (i + 1) + ". Adding slot: " + scenario.mealTypes[i]);
                        try {
                            // Meal Type (select dropdown — breakfast/lunch/dinner)
                            WebElement mealSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.xpath(
                                            "//label[contains(.,'Meal Type')]/following::select[1] | //*[contains(text(),'Meal Type')]/following::select[1]")));
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", mealSelect);
                            Thread.sleep(200);
                            new Select(mealSelect).selectByValue(scenario.mealTypes[i]);
                            Thread.sleep(300);

                            // Time (HH:MM) — placeholder: "17:00"
                            WebElement slotTimeEl = driver.findElement(
                                    By.xpath(
                                            "//input[contains(@placeholder,'17:00')] | //label[contains(.,'Time')]/following::input[contains(@placeholder,'17:00')]"));
                            ((JavascriptExecutor) driver).executeScript(
                                    "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                                            + "nativeInputValueSetter.call(arguments[0], arguments[1]);"
                                            + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                                            + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                                    slotTimeEl, scenario.slotTimes[i]);
                            Thread.sleep(200);

                            // Guests — placeholder: "Total"
                            WebElement guestsEl = driver.findElement(
                                    By.xpath(
                                            "//input[contains(@placeholder,'Total')] | //label[contains(.,'Guests')]/following::input[1]"));
                            safeFill(driver, guestsEl, scenario.slotGuests[i]);
                            Thread.sleep(200);

                            // Add Slot button
                            WebElement addSlotBtn = driver.findElement(
                                    By.xpath("//button[contains(.,'Add Slot')]"));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addSlotBtn);
                            System.out
                                    .println("  Slot added: " + scenario.mealTypes[i] + " at " + scenario.slotTimes[i]);
                            Thread.sleep(500);
                        } catch (Exception e) {
                            System.err.println("  FAIL Time Slot [" + scenario.mealTypes[i] + "]: " + e.getMessage());
                        }
                    }
                }
                Thread.sleep(300);

                // 22. Reservation Policy — textarea, placeholder: "Describe reservation policy"
                System.out.println("  22. Reservation Policy");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//textarea[contains(@placeholder,'reservation policy')] | //label[contains(.,'Reservation Policy')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.reservationPolicy);
                } catch (Exception e) {
                    System.err.println("  FAIL Reservation Policy: " + e.getMessage());
                }
                Thread.sleep(200);

                // --- Cancellation Policy ---

                // 23. Policy Name — clear existing and enter new
                System.out.println("  23. Policy Name");
                try {
                    WebElement policyEl = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[@placeholder='e.g. Flexible Travel Policy'] | //label[contains(.,'Policy Name')]/following::input[1]")));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", policyEl);
                    Thread.sleep(300);
                    policyEl.click();
                    Thread.sleep(100);
                    policyEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    Thread.sleep(100);
                    policyEl.sendKeys(Keys.DELETE);
                    Thread.sleep(100);
                    ((JavascriptExecutor) driver).executeScript(
                            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                                    + "nativeInputValueSetter.call(arguments[0], '');"
                                    + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                                    + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                            policyEl);
                    Thread.sleep(200);
                    policyEl.sendKeys(scenario.policyName);
                    System.out.println("  Policy Name set to: " + scenario.policyName);
                } catch (Exception e) {
                    System.err.println("  FAIL Policy Name: " + e.getMessage());
                }
                Thread.sleep(300);

                // 24-27. Cancellation Rules — loop through Minutes, Hours, Days
                System.out.println("  24-27. Cancellation Rules");
                if (scenario.timeBefores != null && scenario.timeBefores.length > 0) {
                    for (int i = 0; i < scenario.timeBefores.length; i++) {
                        System.out.println("  Rule " + (i + 1) + ": " + scenario.timeBefores[i] + " "
                                + scenario.timeUnits[i] + " -> " + scenario.refundPercentages[i] + "% refund");
                        try {
                            // === TIME BEFORE ===
                            WebElement timeBefore = wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.xpath("//label[contains(text(),'Time Before')]/following-sibling::input")));
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", timeBefore);
                            Thread.sleep(200);
                            timeBefore.click();
                            Thread.sleep(100);
                            timeBefore.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                            Thread.sleep(100);
                            timeBefore.sendKeys(scenario.timeBefores[i]);
                            System.out.println("    Time Before: " + scenario.timeBefores[i]);
                            Thread.sleep(300);

                            // === UNIT (select dropdown) ===
                            WebElement unitSelect = driver.findElement(
                                    By.xpath("//label[contains(text(),'Unit')]/following-sibling::select"));
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", unitSelect);
                            Thread.sleep(200);
                            new Select(unitSelect).selectByVisibleText(scenario.timeUnits[i]);
                            System.out.println("    Unit: " + scenario.timeUnits[i]);
                            Thread.sleep(300);

                            // === REFUND % ===
                            WebElement refund = driver.findElement(By.xpath(
                                    "//div[label[contains(.,'Refund')]]//input | //label[contains(.,'Refund')]/following::input[1]"));
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", refund);
                            Thread.sleep(200);
                            refund.click();
                            Thread.sleep(100);
                            refund.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                            Thread.sleep(100);
                            refund.sendKeys(scenario.refundPercentages[i]);
                            System.out.println("    Refund: " + scenario.refundPercentages[i] + "%");
                            Thread.sleep(300);

                            // === ADD RULE BUTTON ===
                            WebElement addRuleBtn = driver.findElement(By.xpath("//button[contains(.,'Add Rule')]"));
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", addRuleBtn);
                            Thread.sleep(200);
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addRuleBtn);
                            System.out.println("  Rule " + (i + 1) + " added successfully");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            System.err.println("  FAIL Rule " + (i + 1) + ": " + e.getMessage());
                        }
                    }
                }
                Thread.sleep(300);

                // 28. Age Policy — textarea, placeholder: "Describe age policy or kids policy"
                System.out.println("  28. Age Policy");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//textarea[contains(@placeholder,'age policy')] | //textarea[contains(@placeholder,'kids policy')] | //label[contains(.,'Age Policy')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.agePolicy);
                } catch (Exception e) {
                    System.err.println("  FAIL Age Policy: " + e.getMessage());
                }
                Thread.sleep(200);

                // 29. Map Embed — textarea, placeholder: "Enter Google Maps iframe embed
                // URL..."
                System.out.println("  29. Map Embed");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//textarea[contains(@placeholder,'Google Maps')] | //label[contains(.,'Map Embed')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.mapEmbed);
                } catch (Exception e) {
                    System.err.println("  FAIL Map Embed: " + e.getMessage());
                }
                Thread.sleep(200);

                // 30. Direction — textarea, placeholder: "Enter directions"
                System.out.println("  30. Direction");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//textarea[contains(@placeholder,'directions')] | //label[contains(.,'Direction')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.direction);
                } catch (Exception e) {
                    System.err.println("  FAIL Direction: " + e.getMessage());
                }
                Thread.sleep(200);

                // 31. Terms & Conditions — textarea, placeholder: "Enter terms and conditions"
                System.out.println("  31. Terms & Conditions");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//textarea[contains(@placeholder,'terms and conditions')] | //label[contains(.,'Terms')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.termsAndConditions);
                } catch (Exception e) {
                    System.err.println("  FAIL Terms & Conditions: " + e.getMessage());
                }
                Thread.sleep(200);

                // 32. Kid Friendly — checkbox
                System.out.println("  32. Kid Friendly");
                try {
                    if (scenario.kidFriendly) {
                        WebElement el = driver.findElement(
                                By.xpath(
                                        "//*[contains(text(),'Kid Friendly')]/preceding::input[@type='checkbox'][1] | //*[contains(text(),'Kid Friendly')]/following::input[@type='checkbox'][1] | //label[contains(.,'Kid Friendly')]/input[@type='checkbox']"));
                        if (!el.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                            System.out.println("  Kid Friendly checked");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Kid Friendly: " + e.getMessage());
                }
                Thread.sleep(200);

                // 33. Pet Friendly — checkbox
                System.out.println("  33. Pet Friendly");
                try {
                    if (scenario.petFriendly) {
                        WebElement el = driver.findElement(
                                By.xpath(
                                        "//*[contains(text(),'Pet Friendly')]/preceding::input[@type='checkbox'][1] | //*[contains(text(),'Pet Friendly')]/following::input[@type='checkbox'][1] | //label[contains(.,'Pet Friendly')]/input[@type='checkbox']"));
                        if (!el.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                            System.out.println("  Pet Friendly checked");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Pet Friendly: " + e.getMessage());
                }
                Thread.sleep(200);

                // --- Images ---

                // 34. Cover Image
                System.out.println("  34. Cover Image");
                try {
                    WebElement coverInput = driver.findElement(
                            By.xpath("//*[contains(text(),'Cover Image')]/following::input[@type='file'][1]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
                            coverInput);
                    Thread.sleep(500);
                    File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] imgs = imgDir.listFiles();
                    if (imgs != null && imgs.length > 0) {
                        coverInput.sendKeys(imgs[0].getAbsolutePath());
                        System.out.println("  Cover image: " + imgs[0].getName());
                    }
                } catch (Exception e) {
                    System.err.println("  SKIP Cover Image: " + e.getMessage());
                }
                Thread.sleep(2000);

                // 35. Additional Images
                System.out.println("  35. Additional Images");
                try {
                    WebElement addlInput = driver.findElement(
                            By.xpath(
                                    "//label[contains(.,'Additional Images')]/following::input[@type='file'][1] | (//input[@type='file'])[2]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1'; arguments[0].style.height='30px'; arguments[0].style.width='100px';",
                            addlInput);
                    Thread.sleep(500);
                    File imgDir2 = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] imgs2 = imgDir2.listFiles();
                    if (imgs2 != null && imgs2.length > 0) {
                        StringBuilder paths = new StringBuilder();
                        for (File f : imgs2) {
                            if (f.isFile() && (f.getName().toLowerCase().endsWith(".jpg")
                                    || f.getName().toLowerCase().endsWith(".jpeg")
                                    || f.getName().toLowerCase().endsWith(".png"))) {
                                paths.append(f.getAbsolutePath()).append("\n");
                            }
                        }
                        if (paths.length() > 0) {
                            addlInput.sendKeys(paths.toString().trim());
                            System.out.println("  Additional images uploaded");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  SKIP Additional Images: " + e.getMessage());
                }
                Thread.sleep(2000);

                // 36. Menu Files (PDF)
                System.out.println("  36. Menu Files");
                try {
                    WebElement menuInput = driver.findElement(
                            By.xpath(
                                    "//*[contains(text(),'Menu Files') or contains(text(),'Menu')]/following::input[@type='file'][1]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
                            menuInput);
                    Thread.sleep(500);
                    // Look for PDF files in the images directory or a general directory
                    File pdfDir = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] pdfFiles = pdfDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
                    if (pdfFiles != null && pdfFiles.length > 0) {
                        menuInput.sendKeys(pdfFiles[0].getAbsolutePath());
                        System.out.println("  Menu file: " + pdfFiles[0].getName());
                    } else {
                        System.out.println("  No PDF files found for menu upload");
                    }
                } catch (Exception e) {
                    System.err.println("  SKIP Menu Files: " + e.getMessage());
                }
                Thread.sleep(1000);

                // --- Submit ---
                System.out.println("  37. Submit");
                WebElement createBtn = wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//button[normalize-space(text())='Create Recommendation']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createBtn);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createBtn);

                // Verification
                boolean isCreated = false;
                try {
                    WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'successfully') or contains(text(),'Success')]")));
                    System.out.println("  Result: SUCCESS - " + toast.getText());
                    isCreated = true;
                } catch (Exception e) {
                    if (driver.findElements(By.xpath("//button[normalize-space(text())='Create Recommendation']"))
                            .isEmpty()) {
                        isCreated = true;
                    }
                }

                if (isCreated) {
                    passedCount++;
                    passedScenariosNames.add(scenario.scenarioName);
                } else {
                    failedCount++;
                    failedScenariosNames.add(scenario.scenarioName);
                }
                detailedLogs.add("Scenario: " + scenario.scenarioName + " -> " + (isCreated ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                System.err.println("Exception in " + scenario.scenarioName + ": " + e.getMessage());
                failedCount++;
                failedScenariosNames.add(scenario.scenarioName);
                detailedLogs.add("Scenario: " + scenario.scenarioName + " -> FAILED (" + e.getMessage() + ")");
            } finally {
                driver.quit();
            }
            Thread.sleep(5000);
        }

        // Summary
        System.out.println("\n" + "=".repeat(40));
        System.out.println("       DINING TEST SUMMARY");
        System.out.println("Total Scenarios Run: " + totalScenarios);
        System.out.println("=".repeat(40));
        System.out.println("Total Passed: " + passedCount + " | Failed: " + failedCount);

        // Logging
        try {
            File logDir = new File("src/test/resource/logs");
            if (!logDir.exists())
                logDir.mkdirs();
            File logFile = new File(logDir, "dining_execution_"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log");
            try (PrintWriter pw = new PrintWriter(new FileWriter(logFile))) {
                pw.println("Dining Automation Report: " + LocalDateTime.now());
                for (String log : detailedLogs)
                    pw.println(log);
                pw.println("Summary: " + passedCount + " Passed, " + failedCount + " Failed");
            }
            System.out.println("Log: " + logFile.getAbsolutePath());
        } catch (Exception e) {
        }
    }

    /** Scroll to element, clear, and type text */
    private static void safeFill(org.openqa.selenium.WebDriver driver, WebElement element, String text) {
        if (element == null)
            return;
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", element);
            Thread.sleep(200);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("  safeFill error: " + e.getMessage());
        }
    }

    /**
     * Scroll to element, clear, type text — for fields inside Add-button sections
     */
    private static void scrollAndFill(org.openqa.selenium.WebDriver driver, WebElement element, String text) {
        if (element == null)
            return;
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", element);
            Thread.sleep(200);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("  scrollAndFill error: " + e.getMessage());
        }
    }
}
