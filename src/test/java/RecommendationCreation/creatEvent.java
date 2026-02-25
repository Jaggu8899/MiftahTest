package RecommendationCreation;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

public class creatEvent {

	public static void main(String[] args) throws Exception {

		WebDriverManager.chromedriver().setup();

		int totalScenarios = 0;
		int passedCount = 0;
		int failedCount = 0;
		List<String> passedScenariosNames = new ArrayList<>();
		List<String> failedScenariosNames = new ArrayList<>();
		List<String> detailedLogs = new ArrayList<>();
		List<EventData.EventScenario> scenarios = EventData.getScenarios();
		totalScenarios = scenarios.size();

		for (EventData.EventScenario scenario : scenarios) {
			System.out.println("\n--- Executing Scenario: " + scenario.scenarioName + " ---");
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

				// Select Events category
				System.out.println("Step 7: Selecting Events category");
				By eventsChooseXPath = By.xpath(
						"//button[.//h3[contains(text(),'Events')]]//span[contains(text(),'Choose')] | //button[contains(.,'Events')]//span[text()='Choose']");
				try {
					WebElement chooseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(eventsChooseXPath));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", chooseBtn);
					Thread.sleep(1000);
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", chooseBtn);
					} catch (Exception clickEx) {
						chooseBtn.click();
					}
				} catch (Exception e) {
					// Fallback: click the button card directly
					WebElement eventCard = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//button[contains(.,'Events')]")));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", eventCard);
				}
				Thread.sleep(3000);

				// --- Sequential Data Entry: Title to Description (Exact UI Order) ---
				System.out.println("Step 8: Filling form fields sequentially");

				// 1. Title
				System.out.println("Step 8.1: Entering Title");
				WebElement titleField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Title')]]//input")));
				safeFill(driver, titleField, scenario.title);

				// 2. Rating
				System.out.println("Step 8.2: Entering Rating");
				WebElement ratingField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Rating')]]//input")));
				safeFill(driver, ratingField, scenario.stars);

				// 3. Lead Time (Hours)
				System.out.println("Step 8.3: Entering Lead Time (Hours)");
				WebElement leadTimeField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Lead Time')]]//input")));
				safeFill(driver, leadTimeField, scenario.leadTime);

				// 4. Currency
				System.out.println("Step 8.4: Selecting Currency");
				try {
					WebElement currencySelect = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[label[contains(.,'Currency')]]//select")));
					new Select(currencySelect).selectByVisibleText("AED");
				} catch (Exception e) {
				}

				// 5. Description (General)
				System.out.println("Step 8.5: Entering Description");
				WebElement descField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Description')]]//textarea")));
				safeFill(driver, descField, scenario.description);

				// 6. Location
				System.out.println("Step 8.6: Entering Location");
				WebElement locField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Location')]]//input")));
				safeFill(driver, locField, scenario.location);

				// 7. City
				System.out.println("Step 8.7: Entering City");
				WebElement cityField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'City')]]//input")));
				safeFill(driver, cityField, scenario.city);

				// 8. Contact Email
				System.out.println("Step 8.8: Entering Contact Email");
				WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Contact Email')]]//input")));
				safeFill(driver, emailField, scenario.contactEmail);

				// 9. Contact Phone
				System.out.println("Step 8.9: Entering Contact Phone");
				try {
					// The phone field has a country code dropdown + text input
					// Target the text input by looking for input with type='tel' or the last input
					// in the Contact Phone div
					WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//div[label[contains(.,'Contact Phone')]]//input[@type='tel'] | //div[label[contains(.,'Contact Phone')]]//input[contains(@placeholder,'501234567') or contains(@class,'phone')]")));
					safeFill(driver, phoneInput, scenario.contactPhone);
				} catch (Exception e) {
					// Fallback: try finding just the input with phone-like placeholder
					try {
						WebElement phoneInput2 = driver.findElement(
								By.xpath("//input[contains(@placeholder,'501234567') or @type='tel']"));
						safeFill(driver, phoneInput2, scenario.contactPhone);
					} catch (Exception e2) {
						System.err.println("Could not find phone input: " + e2.getMessage());
					}
				}

				// 10. Contact Person
				System.out.println("Step 8.10: Entering Contact Person");
				WebElement personField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Contact Person')]]//input")));
				safeFill(driver, personField, scenario.contactPerson);

				// 11. Cover Image
				System.out.println("Step 8.11: Uploading Cover Image");
				try {
					List<WebElement> fileInputs = driver.findElements(By.xpath("//input[@type='file']"));
					if (!fileInputs.isEmpty()) {
						WebElement coverInput = fileInputs.get(0);
						((JavascriptExecutor) driver).executeScript(
								"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='auto'; arguments[0].style.width='auto'; arguments[0].style.opacity='1';",
								coverInput);
						Thread.sleep(500);
						File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
						File[] images = imgDir.listFiles();
						if (images != null && images.length > 0) {
							coverInput.sendKeys(images[0].getAbsolutePath());
							System.out.println("Cover image uploaded: " + images[0].getName());
						}
					}
				} catch (Exception e) {
					System.err.println("Cover image upload skipped: " + e.getMessage());
				}
				Thread.sleep(2000);

				// 12. Additional Images
				System.out.println("Step 8.12: Uploading Additional Images");
				try {
					WebElement additionalInput = wait.until(
							ExpectedConditions.presenceOfElementLocated(By
									.xpath("//label[contains(.,'Additional Images')]/following::input[@type='file'][1] | (//input[@type='file'])[2]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1'; arguments[0].style.height='30px'; arguments[0].style.width='100px';",
							additionalInput);
					Thread.sleep(500);
					File imgDir2 = new File("C:\\Users\\NS\\Desktop\\images");
					File[] images2 = imgDir2.listFiles();
					if (images2 != null && images2.length > 0) {
						StringBuilder paths = new StringBuilder();
						for (File f : images2) {
							if (f.isFile() && (f.getName().toLowerCase().endsWith(".jpg")
									|| f.getName().toLowerCase().endsWith(".jpeg")
									|| f.getName().toLowerCase().endsWith(".png"))) {
								paths.append(f.getAbsolutePath()).append("\n");
							}
						}
						if (paths.length() > 0) {
							additionalInput.sendKeys(paths.toString().trim());
							System.out.println("  Additional images uploaded");
						}
					}
				} catch (Exception e) {
					System.err.println("Additional images upload skipped: " + e.getMessage());
				}
				Thread.sleep(2000);

				// 13. Map Embed URL
				System.out.println("Step 8.13: Entering Map Embed URL");
				WebElement mapField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Map Embed URL')]]//textarea")));
				safeFill(driver, mapField, scenario.mapEmbedUrl);

				// 14. Directions
				System.out.println("Step 8.14: Entering Directions");
				WebElement directionsField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Directions')]]//textarea")));
				safeFill(driver, directionsField, scenario.directions);

				// 15. Terms & Conditions
				System.out.println("Step 8.15: Entering Terms & Conditions");
				WebElement termsField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Terms & Conditions')]]//textarea")));
				safeFill(driver, termsField, scenario.termsAndConditions);

				// 16. Age Policy
				System.out.println("Step 8.16: Entering Age Policy");
				WebElement agePolicyField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[label[contains(.,'Age Policy')]]//input")));
				safeFill(driver, agePolicyField, scenario.agePolicy);

				// 17. Cancellation Policy (structured section)
				System.out.println("Step 8.17: Entering Cancellation Policy");

				// 17a. Policy Name — clear existing and enter new
				try {
					WebElement policyEl = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//input[@placeholder='e.g. Flexible Travel Policy'] | //div[label[contains(.,'Policy Name')]]//input")));
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

				// 17b-e. Cancellation Rules — loop through Minutes, Hours, Days
				System.out.println("Step 8.17b: Entering Cancellation Rules");
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

				// 18. Meta Data
				System.out.println("Step 8.18: Entering Meta Data");
				try {
					WebElement metaInput = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[label[contains(.,'Meta Data') or contains(.,'Metadata')]]//input")));
					metaInput.sendKeys(scenario.metaData);
					metaInput.sendKeys(Keys.ENTER);
					Thread.sleep(200);
				} catch (Exception e) {
					System.err.println("Meta Data skipped: " + e.getMessage());
				}

				// 19. Event Includes
				System.out.println("Step 8.19: Entering Event Includes");
				try {
					WebElement eventIncInput = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//input[contains(@placeholder,'VIP access')]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", eventIncInput);
					Thread.sleep(300);
					eventIncInput.sendKeys(scenario.eventIncludes);
					Thread.sleep(200);
					eventIncInput.sendKeys(Keys.ENTER);
					Thread.sleep(300);
					System.out.println("  Event Includes entered");
				} catch (Exception e) {
					System.err.println("Event Includes skipped: " + e.getMessage());
				}

				// --- Event Details Section ---

				// 20. Event Description
				System.out.println("Step 8.20: Entering Event Description");
				try {
					WebElement eventDescField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//textarea[@placeholder='Enter detailed event description'] | //div[label[contains(.,'Event Description')]]//textarea")));
					safeFill(driver, eventDescField, scenario.eventDescription);
				} catch (Exception e) {
					System.err.println("Event Description skipped: " + e.getMessage());
				}

				// 21. Event Date (dd-mm-yyyy)
				System.out.println("Step 8.21: Entering Event Date");
				try {
					WebElement dateField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[label[contains(.,'Event Date')]]//input")));
					safeFill(driver, dateField, scenario.eventDate);
				} catch (Exception e) {
					System.err.println("Event Date skipped: " + e.getMessage());
				}

				// 22. Start Time
				System.out.println("Step 8.22: Entering Start Time");
				try {
					WebElement startTimeField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[label[contains(.,'Start Time')]]//input")));
					safeFill(driver, startTimeField, scenario.startTime);
				} catch (Exception e) {
					System.err.println("Start Time skipped: " + e.getMessage());
				}

				// 23. Event Name (under Event Details — placeholder: "e.g., Teddy Swims Live in
				// Concert")
				System.out.println("Step 8.23: Entering Event Name");
				try {
					WebElement eventNameField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//input[contains(@placeholder,'Teddy Swims')] | //div[label[contains(.,'Event Name')]]//input")));
					safeFill(driver, eventNameField, scenario.eventName);
				} catch (Exception e) {
					System.err.println("Event Name skipped: " + e.getMessage());
				}

				// 24. Event Category (placeholder: "e.g., Music, Sports, Comedy, Theater")
				System.out.println("Step 8.24: Entering Event Category");
				try {
					WebElement categoryField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//input[contains(@placeholder,'Music, Sports')] | //div[label[contains(.,'Event Category')]]//input")));
					safeFill(driver, categoryField, scenario.category);
				} catch (Exception e) {
					System.err.println("Event Category skipped: " + e.getMessage());
				}

				// 25. Doors Open (HH:mm:ss)
				System.out.println("Step 8.25: Entering Doors Open");
				try {
					WebElement doorsOpenField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[label[contains(.,'Doors Open')]]//input")));
					safeFill(driver, doorsOpenField, scenario.doorsOpen);
				} catch (Exception e) {
					System.err.println("Doors Open skipped: " + e.getMessage());
				}

				// 26. Base Price (placeholder: "e.g., 500")
				System.out.println("Step 8.26: Entering Base Price");
				try {
					WebElement basePriceField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//input[contains(@placeholder,'e.g., 500')] | //div[label[contains(.,'Base Price')]]//input")));
					safeFill(driver, basePriceField, scenario.basePrice);
				} catch (Exception e) {
					System.err.println("Base Price skipped: " + e.getMessage());
				}

				// 27. Last Booking Date & time (datetime-local input)
				System.out.println("Step 8.27: Entering Last Booking Date & Time");
				try {
					WebElement lastBookingField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[label[contains(.,'Last Booking Date')]]//input")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", lastBookingField);
					Thread.sleep(200);
					// datetime-local needs nativeInputValueSetter with ISO format: YYYY-MM-DDTHH:MM
					((JavascriptExecutor) driver).executeScript(
							"var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
									+ "nativeInputValueSetter.call(arguments[0], arguments[1]);"
									+ "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
									+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
							lastBookingField, scenario.lastBookingDate);
					System.out.println("  Last Booking Date set to: " + scenario.lastBookingDate);
				} catch (Exception e) {
					System.err.println("Last Booking Date skipped: " + e.getMessage());
				}

				// --- Ticket Options Section (inside "Ticket type 1") ---

				// 28. Ticket Name (placeholder: "e.g., VIP Gold")
				System.out.println("Step 8.28: Entering Ticket Option Name");
				try {
					WebElement ticketNameField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'VIP Gold')]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", ticketNameField);
					Thread.sleep(200);
					ticketNameField.click();
					Thread.sleep(100);
					ticketNameField.sendKeys(scenario.ticketTier);
					System.out.println("  Ticket Name: " + scenario.ticketTier);
				} catch (Exception e) {
					System.err.println("Ticket Option Name skipped: " + e.getMessage());
				}
				Thread.sleep(200);

				// 29. Ticket Price (placeholder: "e.g., 1500")
				System.out.println("Step 8.29: Entering Ticket Price");
				try {
					WebElement ticketPriceField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'1500')]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", ticketPriceField);
					Thread.sleep(200);
					ticketPriceField.click();
					Thread.sleep(100);
					ticketPriceField.sendKeys(scenario.ticketPrice);
					System.out.println("  Ticket Price: " + scenario.ticketPrice);
				} catch (Exception e) {
					System.err.println("Ticket Price skipped: " + e.getMessage());
				}
				Thread.sleep(200);

				// 30. Available Ticket Counts (placeholder: "e.g., 50")
				System.out.println("Step 8.30: Entering Available Ticket Counts");
				try {
					WebElement ticketCountField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[@placeholder='e.g., 50']")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", ticketCountField);
					Thread.sleep(200);
					ticketCountField.click();
					Thread.sleep(100);
					ticketCountField.sendKeys(scenario.ticketQuantity);
					System.out.println("  Ticket Count: " + scenario.ticketQuantity);
				} catch (Exception e) {
					System.err.println("Available Ticket Counts skipped: " + e.getMessage());
				}
				Thread.sleep(200);

				// 31. Ticket Description (textarea)
				System.out.println("Step 8.31: Entering Ticket Description");
				try {
					WebElement ticketDescField = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//textarea[contains(@placeholder,'Premium front row')]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", ticketDescField);
					Thread.sleep(200);
					ticketDescField.click();
					Thread.sleep(100);
					ticketDescField.sendKeys(scenario.ticketTierDescription);
					System.out.println("  Ticket Description entered");
				} catch (Exception e) {
					System.err.println("Ticket Description skipped: " + e.getMessage());
				}

				// Click "+ Add Ticket Option" (optional, only needed if adding more tickets)
				Thread.sleep(500);

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
					System.out.println("Result: SUCCESS - " + toast.getText());
					isCreated = true;
				} catch (Exception e) {
					System.out.println("Success toast not found, checking if form disappeared...");
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

		// Final Result Printing
		System.out.println("\n" + "=".repeat(40));
		System.out.println("       EVENT TEST SUMMARY");
		System.out.println("=".repeat(40));
		System.out.println("Total: " + totalScenarios + " | Passed: " + passedCount + " | Failed: " + failedCount);
		if (!failedScenariosNames.isEmpty())
			System.out.println("Failed: " + String.join(", ", failedScenariosNames));
		System.out.println("=".repeat(40));

		// Logging to file
		try {
			File logDir = new File("src/test/resource/logs");
			if (!logDir.exists())
				logDir.mkdirs();
			File logFile = new File(logDir, "event_execution_"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log");
			try (PrintWriter pw = new PrintWriter(new FileWriter(logFile))) {
				pw.println("Event Automation Execution Report");
				pw.println("Timestamp: " + LocalDateTime.now());
				pw.println("-".repeat(40));
				for (String log : detailedLogs)
					pw.println(log);
				pw.println("-".repeat(40));
				pw.println("Summary: " + passedCount + " Passed, " + failedCount + " Failed");
			}
			System.out.println("Log created: " + logFile.getAbsolutePath());
		} catch (Exception e) {
		}
	}

	private static void safeFill(org.openqa.selenium.WebDriver driver, WebElement element, String text) {
		if (element == null)
			return;
		try {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
					element);
			Thread.sleep(200);
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			System.err.println("Failed to fill field: " + e.getMessage());
		}
	}
}
