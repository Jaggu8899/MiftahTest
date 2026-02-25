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

public class creatReality {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();

        int totalScenarios = 0;
        int passedCount = 0;
        int failedCount = 0;
        java.util.List<String> passedScenariosNames = new java.util.ArrayList<>();
        java.util.List<String> failedScenariosNames = new java.util.ArrayList<>();
        java.util.List<String> detailedLogs = new java.util.ArrayList<>();
        List<RealityData.RealityScenario> scenarios = RealityData.getScenarios();
        totalScenarios = scenarios.size();

        for (RealityData.RealityScenario scenario : scenarios) {
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

                // Select Miftah Realty category
                System.out.println("  Selecting Realty category");
                try {
                    WebElement chooseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                            "//button[.//h3[contains(text(),'Realty')]]//span[contains(text(),'Choose')] | //button[contains(.,'Realty')]//span[text()='Choose']")));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", chooseBtn);
                    Thread.sleep(1000);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chooseBtn);
                } catch (Exception e) {
                    WebElement realtyCard = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(.,'Realty')]")));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", realtyCard);
                }
                Thread.sleep(3000);

                // --- Form Data Entry ---
                System.out.println("  Filling form fields");

                // === BASIC INFORMATION ===

                // 1. Title *
                System.out.println("  1. Title");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Title')]/following::input[1]")));
                    safeFill(driver, el, scenario.title);
                } catch (Exception e) {
                    System.err.println("  FAIL Title: " + e.getMessage());
                }
                Thread.sleep(200);

                // 2. City
                System.out.println("  2. City");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'City')]/following::input[1]")));
                    safeFill(driver, el, scenario.city);
                } catch (Exception e) {
                    System.err.println("  FAIL City: " + e.getMessage());
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

                // 4. Zone Name * — placeholder: "e.g., Dubai Marina, Downtown Dubai"
                System.out.println("  4. Zone Name");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'Dubai Marina')] | //label[contains(.,'Zone Name')]/following::input[1]")));
                    safeFill(driver, el, scenario.zoneName);
                } catch (Exception e) {
                    System.err.println("  FAIL Zone Name: " + e.getMessage());
                }
                Thread.sleep(200);

                // 5. Rating *
                System.out.println("  5. Rating");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Rating')]/following::input[1]")));
                    safeFill(driver, el, scenario.rating);
                } catch (Exception e) {
                    System.err.println("  FAIL Rating: " + e.getMessage());
                }
                Thread.sleep(200);

                // 6. Makani Number * — placeholder: "e.g. PROP-DXB-2024-001"
                System.out.println("  6. Makani Number");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'PROP-DXB')] | //label[contains(.,'Makani')]/following::input[1]")));
                    safeFill(driver, el, scenario.makaniNumber);
                } catch (Exception e) {
                    System.err.println("  FAIL Makani Number: " + e.getMessage());
                }
                Thread.sleep(200);

                // 7. Lead Time (Hours) * — placeholder: "e.g. 1.5"
                System.out.println("  7. Lead Time");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Lead Time')]/following::input[1]")));
                    safeFill(driver, el, scenario.leadTime);
                } catch (Exception e) {
                    System.err.println("  FAIL Lead Time: " + e.getMessage());
                }
                Thread.sleep(200);

                // 8. Currency * (select dropdown — already defaults to "AED - UAE Dirham")
                System.out.println("  8. Currency");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Currency')]/following::select[1]")));
                    Select sel = new Select(el);
                    // Default is AED, leave as-is or select explicitly
                    try {
                        sel.selectByVisibleText("AED - UAE Dirham");
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

                // 9. Description — textarea
                System.out.println("  9. Description");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[normalize-space()='Description']/following::textarea[1]")));
                    safeFill(driver, el, scenario.description);
                } catch (Exception e) {
                    System.err.println("  FAIL Description: " + e.getMessage());
                }
                Thread.sleep(200);

                // === PROPERTY DETAILS ===

                // 10. Property Type * (top-level select dropdown)
                System.out.println("  10. Property Type");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//*[contains(text(),'Property Details')]/following::select[1] | //label[contains(.,'Property Type')]/following::select[1]")));
                    Select sel = new Select(el);
                    try {
                        sel.selectByVisibleText(scenario.propertyType);
                    } catch (Exception e1) {
                        // Try selecting by index 1 if text doesn't match
                        if (sel.getOptions().size() > 1) {
                            sel.selectByIndex(1);
                            System.out.println("  Property Type: selected first available option");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Property Type: " + e.getMessage());
                }
                Thread.sleep(200);

                // 11. Realty Description — textarea
                System.out.println("  11. Realty Description");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Realty Description')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.realtyDescription);
                } catch (Exception e) {
                    System.err.println("  FAIL Realty Description: " + e.getMessage());
                }
                Thread.sleep(200);

                // === BUYING OPTIONS (Option 1) ===
                System.out.println("  12-20. Buying Options");

                // 12. Bedrooms *
                System.out.println("  12. Bedrooms");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Bedrooms')]/following::input[1]")));
                    safeFill(driver, el, scenario.bedrooms);
                } catch (Exception e) {
                    System.err.println("  FAIL Bedrooms: " + e.getMessage());
                }
                Thread.sleep(200);

                // 13. Bathrooms *
                System.out.println("  13. Bathrooms");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Bathrooms')]/following::input[1]")));
                    safeFill(driver, el, scenario.bathrooms);
                } catch (Exception e) {
                    System.err.println("  FAIL Bathrooms: " + e.getMessage());
                }
                Thread.sleep(200);

                // 14. Area (sq.ft) *
                System.out.println("  14. Area (sq.ft)");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Area')]/following::input[1]")));
                    safeFill(driver, el, scenario.areaSqFt);
                } catch (Exception e) {
                    System.err.println("  FAIL Area: " + e.getMessage());
                }
                Thread.sleep(200);

                // 15. DLD Number *
                System.out.println("  15. DLD Number");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//label[normalize-space()='DLD Number *' or normalize-space()='DLD Number']/following::input[1]")));
                    safeFill(driver, el, scenario.dldNumber);
                } catch (Exception e) {
                    System.err.println("  FAIL DLD Number: " + e.getMessage());
                }
                Thread.sleep(200);

                // 16. DLD Permit Number * — placeholder: "e.g. DLD-PERMIT-1234567"
                System.out.println("  16. DLD Permit Number");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'DLD-PERMIT')] | //label[contains(.,'DLD Permit')]/following::input[1]")));
                    safeFill(driver, el, scenario.dldPermitNumber);
                } catch (Exception e) {
                    System.err.println("  FAIL DLD Permit Number: " + e.getMessage());
                }
                Thread.sleep(200);

                // 17. Property Ref No *
                System.out.println("  17. Property Ref No");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Property Ref')]/following::input[1]")));
                    safeFill(driver, el, scenario.propertyRefNo);
                } catch (Exception e) {
                    System.err.println("  FAIL Property Ref No: " + e.getMessage());
                }
                Thread.sleep(200);

                // 18. Property Type * (inside buying option — second Property Type dropdown)
                System.out.println("  18. Option Property Type");
                try {
                    // This is the SECOND "Property Type" select on the page
                    List<WebElement> allPropTypeSelects = driver.findElements(
                            By.xpath("//label[contains(.,'Property Type')]/following::select[1]"));
                    // Find all selects that match — we want the second one (inside buying options)
                    List<WebElement> propSelects = driver.findElements(
                            By.xpath("//select[contains(.,'Select property type')]"));
                    if (propSelects.size() >= 2) {
                        Select sel = new Select(propSelects.get(1));
                        try {
                            sel.selectByVisibleText(scenario.optionPropertyType);
                        } catch (Exception e1) {
                            if (sel.getOptions().size() > 1) {
                                sel.selectByIndex(1);
                            }
                        }
                    } else if (!propSelects.isEmpty()) {
                        // If there's only one left or the first one IS the buying option one
                        Select sel = new Select(propSelects.get(propSelects.size() - 1));
                        try {
                            sel.selectByVisibleText(scenario.optionPropertyType);
                        } catch (Exception e1) {
                            if (sel.getOptions().size() > 1)
                                sel.selectByIndex(1);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Option Property Type: " + e.getMessage());
                }
                Thread.sleep(200);

                // 19. Furnished (toggle switch)
                System.out.println("  19. Furnished");
                try {
                    if (scenario.furnished) {
                        // Toggle switches are typically <input type="checkbox"> or a clickable
                        // div/button
                        WebElement toggle = driver.findElement(
                                By.xpath(
                                        "//*[contains(text(),'Furnished')]/preceding-sibling::*[contains(@class,'toggle') or contains(@class,'switch') or @type='checkbox'] | //*[contains(text(),'Furnished')]/parent::*//input[@type='checkbox'] | //*[contains(text(),'Furnished')]/preceding::input[@type='checkbox'][1]"));
                        if (!toggle.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggle);
                            System.out.println("  Furnished toggled ON");
                        }
                    }
                } catch (Exception e) {
                    // Try clicking the label/text itself
                    try {
                        if (scenario.furnished) {
                            WebElement furnLabel = driver.findElement(
                                    By.xpath("//*[contains(text(),'Furnished')]"));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", furnLabel);
                            System.out.println("  Furnished clicked via label");
                        }
                    } catch (Exception e2) {
                        System.err.println("  FAIL Furnished: " + e2.getMessage());
                    }
                }
                Thread.sleep(200);

                // 20. Views — placeholder: "e.g., sea, city skyline"
                System.out.println("  20. Views");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'sea, city')] | //label[contains(.,'Views')]/following::input[1]")));
                    safeFill(driver, el, scenario.views);
                } catch (Exception e) {
                    System.err.println("  FAIL Views: " + e.getMessage());
                }
                Thread.sleep(200);

                // 21. Room Image * (file upload)
                System.out.println("  21. Room Image");
                try {
                    WebElement roomImgInput = driver.findElement(
                            By.xpath("//*[contains(text(),'Room Image')]/following::input[@type='file'][1]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
                            roomImgInput);
                    Thread.sleep(300);
                    File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] imgs = imgDir.listFiles();
                    if (imgs != null && imgs.length > 0) {
                        roomImgInput.sendKeys(imgs[0].getAbsolutePath());
                        System.out.println("  Room image: " + imgs[0].getName());
                    }
                } catch (Exception e) {
                    System.err.println("  SKIP Room Image: " + e.getMessage());
                }
                Thread.sleep(1000);

                // 22. Layout Images (file upload — optional, multiple)
                System.out.println("  22. Layout Images");
                try {
                    WebElement layoutInput = driver.findElement(
                            By.xpath("//*[contains(text(),'Layout Images')]/following::input[@type='file'][1]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
                            layoutInput);
                    Thread.sleep(300);
                    File imgDir2 = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] imgs2 = imgDir2.listFiles();
                    if (imgs2 != null && imgs2.length > 1) {
                        layoutInput.sendKeys(imgs2[1].getAbsolutePath());
                        System.out.println("  Layout image: " + imgs2[1].getName());
                    }
                } catch (Exception e) {
                    System.err.println("  SKIP Layout Images: " + e.getMessage());
                }
                Thread.sleep(1000);

                // === FEATURES & METADATA ===

                // 23. Features — placeholder: "Add features (separated by commas)" + "+" button
                System.out.println("  23. Features");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[contains(@placeholder,'Add features')]")));
                    scrollAndFill(driver, el, scenario.features);
                    Thread.sleep(300);
                    try {
                        WebElement addBtn = driver.findElement(
                                By.xpath(
                                        "//input[contains(@placeholder,'Add features')]/following-sibling::button[1]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                        System.out.println("  Features + clicked");
                    } catch (Exception e2) {
                        System.err.println("  Could not click Features +: " + e2.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Features: " + e.getMessage());
                }
                Thread.sleep(300);

                // 24. Metadata — placeholder: "Add metadata (separated by commas, e.g.,
                // waterfront, parking available)" + "+" button
                System.out.println("  24. Metadata");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[contains(@placeholder,'Add metadata')]")));
                    scrollAndFill(driver, el, scenario.metadata);
                    Thread.sleep(300);
                    try {
                        WebElement addBtn = driver.findElement(
                                By.xpath(
                                        "//input[contains(@placeholder,'Add metadata')]/following-sibling::button[1]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                        System.out.println("  Metadata + clicked");
                    } catch (Exception e2) {
                        System.err.println("  Could not click Metadata +: " + e2.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Metadata: " + e.getMessage());
                }
                Thread.sleep(300);

                // 25. Realty Includes — placeholder: "Add included items (separated by commas,
                // e.g., Parking, Swimming Pool)" + "+" button
                System.out.println("  25. Realty Includes");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'included items')] | //input[contains(@placeholder,'Parking, Swimming')]")));
                    scrollAndFill(driver, el, scenario.realtyIncludes);
                    Thread.sleep(300);
                    try {
                        WebElement addBtn = driver.findElement(
                                By.xpath(
                                        "//input[contains(@placeholder,'included items') or contains(@placeholder,'Parking, Swimming')]/following-sibling::button[1]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                        System.out.println("  Realty Includes + clicked");
                    } catch (Exception e2) {
                        System.err.println("  Could not click Realty Includes +: " + e2.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("  FAIL Realty Includes: " + e.getMessage());
                }
                Thread.sleep(300);

                // === CONTACT INFORMATION ===

                // 26. Contact Email
                System.out.println("  26. Contact Email");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Contact Email')]/following::input[1]")));
                    safeFill(driver, el, scenario.contactEmail);
                } catch (Exception e) {
                    System.err.println("  FAIL Contact Email: " + e.getMessage());
                }
                Thread.sleep(200);

                // 27. Contact Phone — placeholder: "501234567"
                System.out.println("  27. Contact Phone");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[contains(@placeholder,'501234567')] | //label[contains(.,'Contact Phone')]/following::input[1]")));
                    safeFill(driver, el, scenario.contactPhone);
                } catch (Exception e) {
                    System.err.println("  FAIL Contact Phone: " + e.getMessage());
                }
                Thread.sleep(200);

                // 28. Contact Person
                System.out.println("  28. Contact Person");
                try {
                    WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//label[contains(.,'Contact Person')]/following::input[1]")));
                    safeFill(driver, el, scenario.contactPerson);
                } catch (Exception e) {
                    System.err.println("  FAIL Contact Person: " + e.getMessage());
                }
                Thread.sleep(200);

                // === LOCATION & DIRECTIONS ===

                // 29. Map Embed — textarea, placeholder: "Paste Google Maps embed code here"
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

                // 30. Directions — textarea
                System.out.println("  30. Directions");
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(.,'Directions')]/following::textarea[1]")));
                    safeFill(driver, el, scenario.directions);
                } catch (Exception e) {
                    System.err.println("  FAIL Directions: " + e.getMessage());
                }
                Thread.sleep(200);

                // 31. Video URL — field does NOT exist in Realty form (skipped)
                Thread.sleep(200);

                // === IMAGES ===

                // 31. Cover Image * (file upload)
                System.out.println("  31. Cover Image");
                try {
                    // Use label-based selector — the label text is "Cover Image * (Max 100 MB)"
                    WebElement coverInput = driver.findElement(
                            By.xpath("//label[contains(text(),'Cover Image')]/following::input[@type='file'][1]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
                            coverInput);
                    Thread.sleep(500);
                    File imgDir3 = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] imgs3 = imgDir3.listFiles();
                    if (imgs3 != null && imgs3.length > 0) {
                        coverInput.sendKeys(imgs3[0].getAbsolutePath());
                        System.out.println("  Cover image: " + imgs3[0].getName());
                    }
                } catch (Exception e) {
                    System.err.println("  SKIP Cover Image: " + e.getMessage());
                }
                Thread.sleep(2000);

                // 32. Additional Images
                System.out.println("  32. Additional Images");
                try {
                    WebElement addlInput = driver.findElement(
                            By.xpath("(//input[@type='file'])[last()]"));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1'; arguments[0].style.height='30px'; arguments[0].style.width='100px';",
                            addlInput);
                    Thread.sleep(500);
                    File imgDir4 = new File("C:\\Users\\NS\\Desktop\\images");
                    File[] imgs4 = imgDir4.listFiles();
                    if (imgs4 != null && imgs4.length > 0) {
                        StringBuilder paths = new StringBuilder();
                        for (File f : imgs4) {
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

                // === TERMS & CONDITIONS ===

                // 33. Terms and Conditions — textarea (label exactly: "Terms and Conditions")
                System.out.println("  33. Terms and Conditions");
                try {
                    // The section heading is "Terms & Conditions" but the label is "Terms and
                    // Conditions"
                    // Use a broad enough XPath to match either variant
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//label[contains(text(),'Terms and Conditions')]/following::textarea[1] | //label[contains(text(),'Terms')]/following::textarea[1] | //*[contains(text(),'Terms & Conditions')]/following::textarea[1]")));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", el);
                    Thread.sleep(300);
                    safeFill(driver, el, scenario.termsAndConditions);
                } catch (Exception e) {
                    System.err.println("  FAIL Terms and Conditions: " + e.getMessage());
                }
                Thread.sleep(200);

                // --- Submit ---
                System.out.println("  34. Submit");
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
        System.out.println("       REALTY TEST SUMMARY");
        System.out.println("Total Scenarios Run: " + totalScenarios);
        System.out.println("=".repeat(40));
        System.out.println("Total Passed: " + passedCount + " | Failed: " + failedCount);

        // Logging
        try {
            File logDir = new File("src/test/resource/logs");
            if (!logDir.exists())
                logDir.mkdirs();
            File logFile = new File(logDir, "realty_execution_"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log");
            try (PrintWriter pw = new PrintWriter(new FileWriter(logFile))) {
                pw.println("Realty Automation Report: " + LocalDateTime.now());
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
