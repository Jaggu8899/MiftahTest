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

public class creatTravel {

	public static void main(String[] args) throws Exception {

		WebDriverManager.chromedriver().setup();

		int totalScenarios = 0;
		int passedCount = 0;
		int failedCount = 0;
		java.util.List<String> passedScenariosNames = new java.util.ArrayList<>();
		java.util.List<String> failedScenariosNames = new java.util.ArrayList<>();
		java.util.List<String> detailedLogs = new java.util.ArrayList<>();
		List<TravelData.TravelScenario> scenarios = TravelData.getScenarios();
		totalScenarios = scenarios.size();

		for (TravelData.TravelScenario scenario : scenarios) {
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
				WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

				// Select Travel category
				System.out.println("Step 7: Selecting Travel category");
				try {
					WebElement chooseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"//button[.//h3[contains(text(),'Travel')]]//span[contains(text(),'Choose')] | //button[contains(.,'Travel')]//span[text()='Choose']")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", chooseBtn);
					Thread.sleep(1000);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", chooseBtn);
				} catch (Exception e) {
					WebElement travelCard = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//button[contains(.,'Travel')]")));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", travelCard);
				}
				Thread.sleep(3000);

				// --- Form Data Entry ---
				System.out.println("Step 8: Filling form fields");

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

				// 2. Rating
				System.out.println("  2. Rating");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//label[contains(.,'Rating')]/following::input[1]")));
					safeFill(driver, el, scenario.stars);
				} catch (Exception e) {
					System.err.println("  FAIL Rating: " + e.getMessage());
				}
				Thread.sleep(200);

				// 3. City
				System.out.println("  3. City");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//label[contains(.,'City')]/following::input[1]")));
					safeFill(driver, el, scenario.city);
				} catch (Exception e) {
					System.err.println("  FAIL City: " + e.getMessage());
				}
				Thread.sleep(200);

				// 4. Location
				System.out.println("  4. Location");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//label[contains(.,'Location')]/following::input[1]")));
					safeFill(driver, el, scenario.location);
				} catch (Exception e) {
					System.err.println("  FAIL Location: " + e.getMessage());
				}
				Thread.sleep(200);

				// 5. Currency (select dropdown)
				System.out.println("  5. Currency");
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

				// 6. Lead Time — placeholder: "0.0000"
				System.out.println("  6. Lead Time");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//label[contains(.,'Lead Time')]/following::input[1]")));
					safeFill(driver, el, scenario.leadTime);
				} catch (Exception e) {
					System.err.println("  FAIL Lead Time: " + e.getMessage());
				}
				Thread.sleep(200);

				// 7. Base Price — placeholder: "0.0000"
				System.out.println("  7. Base Price");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//label[contains(.,'Base Price')]/following::input[1]")));
					safeFill(driver, el, scenario.basePrice);
				} catch (Exception e) {
					System.err.println("  FAIL Base Price: " + e.getMessage());
				}
				Thread.sleep(200);

				// 8. Description — textarea, placeholder: "Brief description of the travel
				// recommendation"
				System.out.println("  8. Description");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath(
									"//textarea[contains(@placeholder,'Brief description')] | //label[contains(.,'Description')]/following::textarea[1]")));
					safeFill(driver, el, scenario.description);
				} catch (Exception e) {
					System.err.println("  FAIL Description: " + e.getMessage());
				}
				Thread.sleep(200);

				// 9. Travel Description — textarea, placeholder: "Detailed travel description"
				System.out.println("  9. Travel Description");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath(
									"//textarea[contains(@placeholder,'Detailed travel')] | //label[contains(.,'Travel Description')]/following::textarea[1]")));
					safeFill(driver, el, scenario.travelDescription);
				} catch (Exception e) {
					System.err.println("  FAIL Travel Description: " + e.getMessage());
				}
				Thread.sleep(200);

				// 10. Contact Person — placeholder: "Contact person name"
				System.out.println("  10. Contact Person");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath(
									"//input[contains(@placeholder,'Contact person')] | //label[contains(.,'Contact Person')]/following::input[1]")));
					safeFill(driver, el, scenario.contactPerson);
				} catch (Exception e) {
					System.err.println("  FAIL Contact Person: " + e.getMessage());
				}
				Thread.sleep(200);

				// 11. Contact Phone — placeholder: "501234567"
				System.out.println("  11. Contact Phone");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath(
									"//input[contains(@placeholder,'501234567')] | //label[contains(.,'Contact Phone')]/following::input[1]")));
					safeFill(driver, el, scenario.contactPhone);
				} catch (Exception e) {
					System.err.println("  FAIL Contact Phone: " + e.getMessage());
				}
				Thread.sleep(200);

				// 12. Contact Email — placeholder: "contact@example.com"
				System.out.println("  12. Contact Email");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath(
									"//input[contains(@placeholder,'contact@example.com')] | //label[contains(.,'Contact Email')]/following::input[1]")));
					safeFill(driver, el, scenario.contactEmail);
				} catch (Exception e) {
					System.err.println("  FAIL Contact Email: " + e.getMessage());
				}
				Thread.sleep(200);

				// 13. Hospitality Group — placeholder: "e.g., Marriott Bonvoy"
				System.out.println("  13. Hospitality Group");
				try {
					WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath(
									"//input[contains(@placeholder,'Marriott')] | //label[contains(.,'Hospitality')]/following::input[1]")));
					safeFill(driver, el, scenario.hospitalityGroup);
				} catch (Exception e) {
					System.err.println("  FAIL Hospitality Group: " + e.getMessage());
				}
				Thread.sleep(200);

				// 14. Check-in Time — TIME PICKER input (placeholder "--:--", type "time" with
				// clock icon)
				System.out.println("  14. Check-in Time");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//label[contains(.,'Check-in Time')]/following::input[1]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", el);
					Thread.sleep(200);
					// Use nativeInputValueSetter for React compatibility
					((JavascriptExecutor) driver).executeScript(
							"var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
									+ "nativeInputValueSetter.call(arguments[0], arguments[1]);"
									+ "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
									+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
							el, scenario.checkInTime);
					System.out.println("  Check-in Time set to: " + scenario.checkInTime);
				} catch (Exception e) {
					System.err.println("  FAIL Check-in Time: " + e.getMessage());
				}
				Thread.sleep(200);

				// 15. Check-out Time — TIME PICKER input
				System.out.println("  15. Check-out Time");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//label[contains(.,'Check-out Time')]/following::input[1]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", el);
					Thread.sleep(200);
					// Use nativeInputValueSetter for React compatibility
					((JavascriptExecutor) driver).executeScript(
							"var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
									+ "nativeInputValueSetter.call(arguments[0], arguments[1]);"
									+ "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
									+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
							el, scenario.checkOutTime);
					System.out.println("  Check-out Time set to: " + scenario.checkOutTime);
				} catch (Exception e) {
					System.err.println("  FAIL Check-out Time: " + e.getMessage());
				}
				Thread.sleep(200);

				// 16. Facilities — placeholder: "Add facility (comma separated allowed) e.g.,
				// Pool, Gym"
				// Add button is the SIBLING right after the input
				System.out.println("  16. Facilities");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'facility')]")));
					scrollAndFill(driver, el, scenario.facilities);
					Thread.sleep(300);
					// Click Add button (sibling)
					WebElement addBtn = driver.findElement(
							By.xpath(
									"//input[contains(@placeholder,'facility')]/following-sibling::button[normalize-space()='Add']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
					System.out.println("  Facilities Add clicked");
				} catch (Exception e) {
					System.err.println("  FAIL Facilities: " + e.getMessage());
				}
				Thread.sleep(300);

				// 17. Travel Includes — placeholder: "Add include (comma separated allowed)
				// ..."
				System.out.println("  17. Travel Includes");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'include')]")));
					scrollAndFill(driver, el, scenario.travelIncludes);
					Thread.sleep(300);
					WebElement addBtn = driver.findElement(
							By.xpath(
									"//input[contains(@placeholder,'include')]/following-sibling::button[normalize-space()='Add']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
					System.out.println("  Travel Includes Add clicked");
				} catch (Exception e) {
					System.err.println("  FAIL Travel Includes: " + e.getMessage());
				}
				Thread.sleep(300);

				// --- Room Information Section ---
				System.out.println("  18-24. Room Information");

				// 18. Type — placeholder: "e.g., Deluxe Room"
				System.out.println("  18. Room Type");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'Deluxe Room')]")));
					safeFill(driver, el, scenario.roomType);
				} catch (Exception e) {
					System.err.println("  FAIL Room Type: " + e.getMessage());
				}
				Thread.sleep(200);

				// 19. Price (Room) — label "Price", placeholder "0"
				// This is INSIDE Room Information section, AFTER the Type field
				System.out.println("  19. Room Price");
				try {
					// Find by going from the unique "Deluxe Room" input to the next input
					WebElement el = driver.findElement(
							By.xpath("//input[contains(@placeholder,'Deluxe Room')]/following::input[1]"));
					safeFill(driver, el, scenario.roomPrice);
				} catch (Exception e) {
					System.err.println("  FAIL Room Price: " + e.getMessage());
				}
				Thread.sleep(200);

				// 20. Max guests per room — placeholder "0", unique label
				System.out.println("  20. Max guests per room");
				try {
					WebElement el = driver.findElement(
							By.xpath("//input[contains(@placeholder,'Deluxe Room')]/following::input[2]"));
					safeFill(driver, el, scenario.roomMaxGuests);
				} catch (Exception e) {
					System.err.println("  FAIL Max guests: " + e.getMessage());
				}
				Thread.sleep(200);

				// 21. No. of rooms — placeholder "0"
				System.out.println("  21. No. of rooms");
				try {
					WebElement el = driver.findElement(
							By.xpath("//input[contains(@placeholder,'Deluxe Room')]/following::input[3]"));
					safeFill(driver, el, scenario.roomCount);
				} catch (Exception e) {
					System.err.println("  FAIL No. of rooms: " + e.getMessage());
				}
				Thread.sleep(200);

				// 22. Sq. ft area — placeholder "0"
				System.out.println("  22. Sq. ft area");
				try {
					WebElement el = driver.findElement(
							By.xpath("//input[contains(@placeholder,'Deluxe Room')]/following::input[4]"));
					safeFill(driver, el, scenario.roomSqFtArea);
				} catch (Exception e) {
					System.err.println("  FAIL Sq. ft area: " + e.getMessage());
				}
				Thread.sleep(200);

				// 23. Beds — placeholder "0"
				System.out.println("  23. Beds");
				try {
					WebElement el = driver.findElement(
							By.xpath("//input[contains(@placeholder,'Deluxe Room')]/following::input[5]"));
					safeFill(driver, el, scenario.roomBeds);
				} catch (Exception e) {
					System.err.println("  FAIL Beds: " + e.getMessage());
				}
				Thread.sleep(200);

				// 24. Views — placeholder: "e.g., Sea view"
				System.out.println("  24. Views");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'Sea view')]")));
					safeFill(driver, el, scenario.roomView);
				} catch (Exception e) {
					System.err.println("  FAIL Views: " + e.getMessage());
				}
				Thread.sleep(200);

				// 25. Room Amenities — placeholder: "Add amenity for this room (comma separated
				// allowed)"
				System.out.println("  25. Room Amenities");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'amenity')]")));
					scrollAndFill(driver, el, scenario.roomAmenities);
					Thread.sleep(300);
					WebElement addBtn = driver.findElement(
							By.xpath(
									"//input[contains(@placeholder,'amenity')]/following-sibling::button[normalize-space()='Add']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
					System.out.println("  Room Amenities Add clicked");
				} catch (Exception e) {
					System.err.println("  FAIL Room Amenities: " + e.getMessage());
				}
				Thread.sleep(300);

				// 26. Room Images (upload multiple)
				System.out.println("  26. Room Images");
				try {
					List<WebElement> fileInputs = driver.findElements(By.xpath("//input[@type='file']"));
					if (!fileInputs.isEmpty()) {
						WebElement fi = fileInputs.get(0);
						((JavascriptExecutor) driver).executeScript(
								"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
								fi);
						Thread.sleep(300);
						File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
						File[] imgs = imgDir.listFiles();
						if (imgs != null && imgs.length > 0) {
							for (int i = 0; i < Math.min(imgs.length, 3); i++) {
								if (imgs[i].isFile()) {
									fi.sendKeys(imgs[i].getAbsolutePath());
									System.out.println("  Room image: " + imgs[i].getName());
								}
							}
						}
					}
				} catch (Exception e) {
					System.err.println("  SKIP Room Images: " + e.getMessage());
				}
				Thread.sleep(1000);

				// --- Additional Information ---

				// 27. Map Embed (Google Maps iframe) — textarea, placeholder: "Enter Google
				// Maps iframe code"
				System.out.println("  27. Map Embed");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//textarea[contains(@placeholder,'Google Maps iframe')]")));
					safeFill(driver, el, scenario.mapEmbedUrl);
				} catch (Exception e) {
					System.err.println("  FAIL Map Embed: " + e.getMessage());
				}
				Thread.sleep(200);

				// 28. Direction — textarea, placeholder: "Directions to the location"
				System.out.println("  28. Direction");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//textarea[contains(@placeholder,'Directions to the location')]")));
					safeFill(driver, el, scenario.direction);
				} catch (Exception e) {
					System.err.println("  FAIL Direction: " + e.getMessage());
				}
				Thread.sleep(200);

				// 29. Terms & Conditions — textarea, placeholder: "Terms and conditions"
				System.out.println("  29. Terms & Conditions");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//textarea[contains(@placeholder,'Terms and conditions')]")));
					safeFill(driver, el, scenario.termsAndConditions);
				} catch (Exception e) {
					System.err.println("  FAIL Terms & Conditions: " + e.getMessage());
				}
				Thread.sleep(200);

				// 30. Age Policy — textarea/input, placeholder: "Age policy information"
				System.out.println("  30. Age Policy");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath(
									"//textarea[contains(@placeholder,'Age policy')] | //input[contains(@placeholder,'Age policy')]")));
					safeFill(driver, el, scenario.agePolicy);
				} catch (Exception e) {
					System.err.println("  FAIL Age Policy: " + e.getMessage());
				}
				Thread.sleep(200);

				// 31. Policy Name — clear existing "Standard" and enter new one
				System.out.println("  31. Policy Name");
				try {
					WebElement policyEl = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[@placeholder='e.g. Flexible Travel Policy']")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", policyEl);
					Thread.sleep(300);
					// Triple-clear strategy for React inputs with pre-filled values
					policyEl.click();
					Thread.sleep(100);
					// Select all existing text and delete it
					policyEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
					Thread.sleep(100);
					policyEl.sendKeys(Keys.DELETE);
					Thread.sleep(100);
					// Also clear via JS to be certain
					((JavascriptExecutor) driver).executeScript(
							"var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
									+ "nativeInputValueSetter.call(arguments[0], '');"
									+ "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
									+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
							policyEl);
					Thread.sleep(200);
					// Now type the new policy name
					policyEl.sendKeys(scenario.policyName);
					System.out.println("  Policy Name set to: " + scenario.policyName);
				} catch (Exception e) {
					System.err.println("  FAIL Policy Name: " + e.getMessage());
				}
				Thread.sleep(300);

				// 32-35. Cancellation Rules — loop through Minutes, Hours, Days
				System.out.println("  32-35. Cancellation Rules");
				if (scenario.timeBefores != null && scenario.timeBefores.length > 0) {
					for (int i = 0; i < scenario.timeBefores.length; i++) {
						System.out.println("  Rule " + (i + 1) + ": " + scenario.timeBefores[i] + " "
								+ scenario.timeUnits[i] + " -> " + scenario.refundPercentages[i] + "% refund");
						try {
							// === TIME BEFORE ===
							WebElement timeBefore = wait.until(ExpectedConditions.presenceOfElementLocated(
									By.xpath(
											"//label[contains(text(),'Time Before')]/following-sibling::input")));
							((JavascriptExecutor) driver).executeScript(
									"arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", timeBefore);
							Thread.sleep(200);
							// Clear pre-filled value using Ctrl+A then type
							timeBefore.click();
							Thread.sleep(100);
							timeBefore.sendKeys(Keys.chord(Keys.CONTROL, "a"));
							Thread.sleep(100);
							timeBefore.sendKeys(scenario.timeBefores[i]);
							System.out.println("    Time Before: " + scenario.timeBefores[i]);
							Thread.sleep(300);

							// === UNIT (select dropdown) ===
							// Find the select element that comes after the TIME BEFORE input
							WebElement unitSelect = driver.findElement(
									By.xpath(
											"//label[contains(text(),'Unit')]/following-sibling::select"));
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
							// Clear pre-filled value using Ctrl+A then type
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
							Thread.sleep(1000); // Wait for rule to appear below
						} catch (Exception e) {
							System.err.println("  FAIL Rule " + (i + 1) + ": " + e.getMessage());
						}
					}
				}
				Thread.sleep(300);

				// 36. Meta Data — placeholder: "Add metadata tag (comma separated allowed)
				// e.g., beachfront, all-inclusive"
				System.out.println("  36. Meta Data");
				try {
					WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//input[contains(@placeholder,'metadata')]")));
					scrollAndFill(driver, el, scenario.metaData);
					Thread.sleep(300);
					WebElement addBtn = driver.findElement(
							By.xpath(
									"//input[contains(@placeholder,'metadata')]/following-sibling::button[normalize-space()='Add']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
					System.out.println("  Meta Data Add clicked");
				} catch (Exception e) {
					System.err.println("  FAIL Meta Data: " + e.getMessage());
				}
				Thread.sleep(300);

				// --- Images ---

				// 37. Cover Image
				System.out.println("  37. Cover Image");
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

				// 38. Additional Images
				System.out.println("  38. Additional Images");
				try {
					WebElement addlInput = driver.findElement(
							By.xpath(
									"//label[contains(.,'Additional Images')]/following::input[@type='file'][1] | (//input[@type='file'])[2]"));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='30px'; arguments[0].style.width='100px'; arguments[0].style.opacity='1';",
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

				// --- Submit ---
				System.out.println("  39. Submit");
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
		System.out.println("       TRAVEL TEST SUMMARY");
		System.out.println("Total Scenarios Run: " + totalScenarios);
		System.out.println("=".repeat(40));
		System.out.println("Total Passed: " + passedCount + " | Failed: " + failedCount);

		// Logging
		try {
			File logDir = new File("src/test/resource/logs");
			if (!logDir.exists())
				logDir.mkdirs();
			File logFile = new File(logDir, "travel_execution_"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log");
			try (PrintWriter pw = new PrintWriter(new FileWriter(logFile))) {
				pw.println("Travel Automation Report: " + LocalDateTime.now());
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
