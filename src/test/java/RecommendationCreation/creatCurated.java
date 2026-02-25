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

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class creatCurated {

	@Test
	public void runAction() throws Exception {
		main(new String[0]);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Starting execution of creatCurated (Curated Experiences)...");
		WebDriverManager.chromedriver().setup();

		int totalScenarios = 0;
		int passedCount = 0;
		int failedCount = 0;
		List<String> passedScenariosNames = new java.util.ArrayList<>();
		List<String> failedScenariosNames = new java.util.ArrayList<>();
		List<String> detailedLogs = new java.util.ArrayList<>();
		List<CuratedData.CuratedScenario> scenarios = CuratedData.getScenarios();
		totalScenarios = scenarios.size();

		for (CuratedData.CuratedScenario scenario : scenarios) {
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

				// Select Curated Experiences
				System.out.println("Step 7: Selecting Curated Experiences category");
				By curatedChooseXPath = By.xpath(
						"//button[.//h3[contains(text(),'Curated Experiences')]]//span[contains(text(),'Choose')] | //button[contains(.,'Curated Experiences')]//span[text()='Choose']");
				try {
					WebElement chooseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(curatedChooseXPath));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", chooseBtn);
					Thread.sleep(1000);

					// Click the specific 'Choose' span
					try {
						chooseBtn.click();
					} catch (Exception e) {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", chooseBtn);
					}
					System.out.println("Step 7: 'Choose' clicked.");
				} catch (Exception e) {
					System.err.println(
							"Step 7 Warning: Detailed selection failed, trying direct text click: " + e.getMessage());
					WebElement textEl = driver.findElement(By.xpath("//h3[contains(text(),'Curated Experiences')]"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", textEl);
				}

				// Verify if form loads
				System.out.println("Step 7.1: Waiting for form to load...");
				try {
					wait.until(ExpectedConditions.or(
							ExpectedConditions.urlContains("recommendations/create"),
							ExpectedConditions.presenceOfElementLocated(By.xpath("//label[contains(.,'Title')]"))));
					System.out.println("Step 7.1: Form loaded successfully.");
				} catch (Exception e) {
					System.err.println("Step 7.1 Warning: Form load verification timed out, proceeding anyway.");
				}
				Thread.sleep(2000);

				// --- Sequential Data Entry: Title to Description (Exact UI Order) ---
				System.out.println("Step 8: Filling form fields sequentially");

				// 1. Title
				System.out.println("Step 8.1: Entering Title");
				WebElement titleField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Title')]/following::input[1]")));
				safeFill(driver, titleField, scenario.title);
				Thread.sleep(200);

				// 2. Rating
				System.out.println("Step 8.2: Entering Rating");
				WebElement ratingField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Rating')]/following::input[1]")));
				safeFill(driver, ratingField, scenario.stars);
				Thread.sleep(200);

				// 3. Lead Time (Hours)
				System.out.println("Step 8.3: Entering Lead Time (Hours)");
				WebElement leadTimeField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Lead Time')]/following::input[1]")));
				safeFill(driver, leadTimeField, scenario.durationHours);
				Thread.sleep(200);

				// 4. Currency
				System.out.println("Step 8.4: Selecting Currency");
				try {
					WebElement currencySelect = wait.until(ExpectedConditions
							.presenceOfElementLocated(
									By.xpath("//label[contains(.,'Currency')]/following::select[1]")));
					new Select(currencySelect).selectByVisibleText(scenario.currency);
				} catch (Exception e) {
					System.out.println("Currency fallback used.");
				}
				Thread.sleep(200);

				// 5. Description (General)
				System.out.println("Step 8.5: Entering Description");
				WebElement descField = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//label[contains(.,'Description')]/following::textarea[1]")));
				safeFill(driver, descField, scenario.generalDescription);
				Thread.sleep(200);

				// 6. Location
				System.out.println("Step 8.6: Entering Location");
				WebElement locField = wait.until(
						ExpectedConditions
								.elementToBeClickable(By.xpath("//label[contains(.,'Location')]/following::input[1]")));
				safeFill(driver, locField, scenario.location);
				Thread.sleep(200);

				// 7. City
				System.out.println("Step 8.7: Entering City");
				WebElement cityField = wait
						.until(ExpectedConditions
								.elementToBeClickable(By.xpath("//label[contains(.,'City')]/following::input[1]")));
				safeFill(driver, cityField, scenario.city);
				Thread.sleep(200);

				// 8. Contact Email
				System.out.println("Step 8.8: Entering Contact Email");
				WebElement emailField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Contact Email')]/following::input[1]")));
				safeFill(driver, emailField, scenario.contactEmail);
				Thread.sleep(200);

				// 9. Contact Phone Number
				System.out.println("Step 8.9: Entering Contact Phone Number");
				try {
					WebElement countryToggle = driver
							.findElement(By.xpath(
									"//button[contains(text(),'+971') or contains(@class,'flex items-center')]"));
					countryToggle.click();
					Thread.sleep(200);
					driver.findElement(By.xpath("//span[contains(text(),'+91')]")).click();
				} catch (Exception e) {
				}
				WebElement phoneInput = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Contact Phone')]/following::input[1]")));
				safeFill(driver, phoneInput, scenario.contactPhone);
				Thread.sleep(200);

				// 10. Contact Person
				System.out.println("Step 8.10: Entering Contact Person");
				WebElement personField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Contact Person')]/following::input[1]")));
				safeFill(driver, personField, scenario.contactPerson);
				Thread.sleep(200);

				// 11. Cover Image
				System.out.println("Step 8.11: Uploading Cover Image");
				WebElement coverInput = wait.until(
						ExpectedConditions.presenceOfElementLocated(
								By.xpath("//label[contains(.,'Cover Image')]/following::input[@type='file'][1]")));
				if (coverInput != null) {
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px';",
							coverInput);
					File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
					File[] images = imgDir.listFiles();
					if (images != null && images.length > 0) {
						coverInput.sendKeys(images[0].getAbsolutePath());
					}
				}
				Thread.sleep(500);

				// 12. Additional Images
				System.out.println("Step 8.12: Uploading Additional Images");
				WebElement additionalInput = wait.until(
						ExpectedConditions.presenceOfElementLocated(By
								.xpath("//label[contains(.,'Additional Images')]/following::input[@type='file'][1] | (//input[@type='file'])[2]")));
				if (additionalInput != null) {
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1'; arguments[0].style.height='30px'; arguments[0].style.width='100px';",
							additionalInput);
					File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
					File[] images = imgDir.listFiles();
					if (images != null && images.length > 0) {
						StringBuilder paths = new StringBuilder();
						for (File f : images) {
							if (f.isFile() && (f.getName().toLowerCase().endsWith(".jpg")
									|| f.getName().toLowerCase().endsWith(".jpeg")
									|| f.getName().toLowerCase().endsWith(".png"))) {
								paths.append(f.getAbsolutePath()).append("\n");
							}
						}
						if (paths.length() > 0) {
							additionalInput.sendKeys(paths.toString().trim());
						}
					}
				}
				Thread.sleep(500);

				// 13. Map Embed URL
				System.out.println("Step 8.13: Entering Map Embed URL");
				WebElement mapField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath(
								"//label[contains(.,'Map Embed URL')]/following::textarea[1] | //label[contains(.,'Map Embed URL')]/following::input[1]")));
				safeFill(driver, mapField, scenario.mapEmbedUrl);
				Thread.sleep(200);

				// 14. Directions
				System.out.println("Step 8.14: Entering Directions");
				WebElement directionsField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//label[contains(.,'Directions')]/following::textarea[1]")));
				safeFill(driver, directionsField, scenario.directions);
				Thread.sleep(200);

				// 15. Terms & Conditions
				System.out.println("Step 8.15: Entering Terms & Conditions");
				WebElement termsField = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//label[contains(.,'Terms & Conditions')]/following::textarea[1]")));
				safeFill(driver, termsField, scenario.termsAndConditions);
				Thread.sleep(200);

				// 16. Age Policy
				System.out.println("Step 8.16: Entering Age Policy");
				WebElement agePolicyField = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath(
								"//label[contains(.,'Age Policy')]/following::textarea[1] | //input[@placeholder='e.g., 18+']")));
				safeFill(driver, agePolicyField, scenario.agePolicy);
				Thread.sleep(200);

				// 17. Meta Data
				System.out.println("Step 8.17: Entering Meta Data");
				WebElement metaInput = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath(
								"//label[contains(.,'Meta Data')]/following::input[contains(@placeholder,'meta data')]")));
				metaInput.sendKeys(scenario.metaData);
				metaInput.sendKeys(Keys.ENTER);
				Thread.sleep(200);
				try {
					WebElement metaAddBtn = driver.findElement(
							By.xpath("//label[contains(.,'Meta Data')]/following::button[normalize-space()='Add'][1]"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", metaAddBtn);
				} catch (Exception e) {
				}
				Thread.sleep(200);

				// 18. Experience Description (Detailed)
				System.out.println("Step 8.18: Entering Experience Description");
				WebElement expDescField = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//label[contains(.,'Experience Description')]/following::textarea[1]")));
				safeFill(driver, expDescField, scenario.detailedDescription);
				Thread.sleep(200);

				// 19. Experience Date
				System.out.println("Step 8.19: Entering Experience Date");
				WebElement dateField = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath(
								"//label[contains(.,'Date')]/following::input[1]")));
				safeFill(driver, dateField, scenario.date);
				Thread.sleep(200);

				// 20. Duration Min (minutes)
				System.out.println("Step 8.20: Entering Duration Min");
				WebElement durMinField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='e.g., 60']")));
				safeFill(driver, durMinField, scenario.durationMinutes);
				Thread.sleep(200);

				// 21. Duration Max (minutes)
				System.out.println("Step 8.21: Entering Duration Max");
				WebElement durMaxField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='e.g., 120']")));
				safeFill(driver, durMaxField, scenario.durationMaxMinutes);
				Thread.sleep(200);

				// 22. Dress Code
				System.out.println("Step 8.22: Entering Dress Code");
				WebElement dressField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='e.g., Smart Casual']")));
				safeFill(driver, dressField, scenario.dressCode);
				Thread.sleep(200);

				// 23. Time Slot
				System.out.println("Step 8.23: Entering Time Slot");
				WebElement timeSlotField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='HH:mm']")));
				safeFill(driver, timeSlotField, scenario.startTime);
				Thread.sleep(200);

				// 24. Total Guests
				System.out.println("Step 8.24: Entering Total Guests");
				WebElement guestsField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='e.g. 20']")));
				safeFill(driver, guestsField, scenario.totalGuests);
				Thread.sleep(200);

				// 25. Important Notes
				System.out.println("Step 8.25: Entering Important Notes");
				WebElement notesField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//textarea[@placeholder='Enter important notes']")));
				safeFill(driver, notesField, scenario.importantNotes);
				Thread.sleep(200);

				// 26. Policy Name (Cancellation Policy section) — clear existing and enter new
				System.out.println("Step 8.26: Entering Policy Name");
				try {
					WebElement policyEl = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[@placeholder='e.g. Flexible Travel Policy']")));
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

				// 27. Cancellation Rules — loop through Minutes, Hours, Days
				System.out.println("Step 8.27: Entering Cancellation Rules");
				if (scenario.refundRuleTimes != null && scenario.refundRuleTimes.length > 0) {
					for (int i = 0; i < scenario.refundRuleTimes.length; i++) {
						System.out.println("  Rule " + (i + 1) + ": " + scenario.refundRuleTimes[i] + " "
								+ scenario.refundRuleUnits[i] + " -> " + scenario.refundRulePercentages[i]
								+ "% refund");
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
							timeBefore.sendKeys(scenario.refundRuleTimes[i]);
							System.out.println("    Time Before: " + scenario.refundRuleTimes[i]);
							Thread.sleep(300);

							// === UNIT (select dropdown) ===
							WebElement unitSelect = driver.findElement(
									By.xpath("//label[contains(text(),'Unit')]/following-sibling::select"));
							((JavascriptExecutor) driver).executeScript(
									"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", unitSelect);
							Thread.sleep(200);
							new Select(unitSelect).selectByVisibleText(scenario.refundRuleUnits[i]);
							System.out.println("    Unit: " + scenario.refundRuleUnits[i]);
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
							refund.sendKeys(scenario.refundRulePercentages[i]);
							System.out.println("    Refund: " + scenario.refundRulePercentages[i] + "%");
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

				// 28. Experience Includes
				System.out.println("Step 8.28: Entering Experience Includes");
				WebElement expIncInput = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='Enter items (comma-separated) and press Enter']")));
				if (expIncInput != null) {
					safeFill(driver, expIncInput, scenario.experienceIncludes);
					expIncInput.sendKeys(Keys.ENTER);
					Thread.sleep(100);
					try {
						WebElement expAddBtn = driver.findElement(By.xpath(
								"//button[text()='Add' and ./preceding-sibling::input[contains(@placeholder, 'Enter items')]] | //button[normalize-space()='Add']"));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", expAddBtn);
					} catch (Exception e) {
					}
				}
				Thread.sleep(200);

				// 29. Price
				System.out.println("Step 8.29: Entering Price");
				WebElement priceField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='e.g., 2300']")));
				safeFill(driver, priceField, scenario.basePrice);
				Thread.sleep(200);

				// 30. Item Title
				System.out.println("Step 8.30: Entering Item Title");
				WebElement itemTitleField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@placeholder='e.g., Premium Wine']")));
				safeFill(driver, itemTitleField, scenario.itemTitle);
				Thread.sleep(200);

				// 31. Item Description
				System.out.println("Step 8.31: Entering Item Description");
				WebElement itemDescField = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//textarea[@placeholder='e.g., Best premium wine selection']")));
				safeFill(driver, itemDescField, scenario.itemDescription);
				Thread.sleep(200);

				// Final Submission
				Thread.sleep(1000);
				WebElement createBtn = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[normalize-space(text())='Create Recommendation']")));
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
					System.out.println("Checking if form disappeared...");
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
				System.err.println("Error in scenario " + scenario.scenarioName + ": " + e.getMessage());
				failedCount++;
				failedScenariosNames.add(scenario.scenarioName);
				detailedLogs.add("Scenario: " + scenario.scenarioName + " -> FAILED (" + e.getMessage() + ")");
			} finally {
				driver.quit();
			}
			Thread.sleep(2000);
		}

		// Final Result
		System.out.println("\n" + "=".repeat(40));
		System.out.println("       TEST SUMMARY");
		System.out.println("Total Scenarios Run: " + totalScenarios);
		System.out.println("=".repeat(40));
		System.out.println("Total Passed:        " + passedCount + " | Failed: " + failedCount);
		System.out.println("=".repeat(40));

		// Logging
		try {
			File logDir = new File("src/test/resource/logs");
			if (!logDir.exists())
				logDir.mkdirs();
			File logFile = new File(logDir, "curated_results_"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log");
			try (PrintWriter pw = new PrintWriter(new FileWriter(logFile))) {
				pw.println("Curated Experiences Execution Report");
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
			// Scroll into view first
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
					element);
			Thread.sleep(200);

			// Wait for visibility and clear/type
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			System.err.println("Failed to fill field: " + e.getMessage());
		}
	}
}
