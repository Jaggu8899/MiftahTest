package Test10;

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
			ChromeDriver driver = new ChromeDriver();
			try {
				driver.get("https://crmdev.miftah.ai/dashboard/");
				driver.manage().window().maximize();

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

				// Login
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")))
						.sendKeys("jagadeeswara89@gmail.com");
				driver.findElement(By.id("password")).sendKeys("Jaggu@89");
				driver.findElement(By.xpath("//button[text()='Login']")).click();

				// Navigate
				By miftahBtn = By.xpath("//span[normalize-space()='Miftah Recommendations']/ancestor::button");
				wait.until(ExpectedConditions.elementToBeClickable(miftahBtn)).click();
				Thread.sleep(3000);

				WebElement addBtn = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'group p-2')]")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);

				WebElement travelBtn = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space(text())='Travel']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", travelBtn);

				// Basic Information
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")).sendKeys(scenario.title);
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")).sendKeys(scenario.stars);
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")).sendKeys(scenario.city);
				driver.findElement(By.xpath("//input[@placeholder='e.g., Dubai']")).sendKeys(scenario.location);

				Select currency = new Select(
						driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
				currency.selectByIndex(12); // INR

				driver.findElement(By.name("leadTime")).sendKeys(scenario.leadTime);
				driver.findElement(By.name("base_price")).sendKeys(scenario.basePrice);
				driver.findElement(By.name("description")).sendKeys(scenario.description);
				driver.findElement(By.name("travel_description")).sendKeys(scenario.travelDescription);
				driver.findElement(By.name("contact_person")).sendKeys(scenario.contactPerson);

				// Country Code
				By countryDropdown = By.xpath("//button[.//span[normalize-space()='+971']]");
				wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)).click();
				By indiaCode = By.xpath("//*[contains(text(),'+91')]");
				wait.until(ExpectedConditions.elementToBeClickable(indiaCode)).click();

				driver.findElement(By.xpath("(//label[normalize-space(text())='Contact Phone']/following::input)[1]"))
						.sendKeys(scenario.contactPhone);
				driver.findElement(By.name("contact_email")).sendKeys(scenario.contactEmail);
				driver.findElement(By.name("hospitality_group")).sendKeys(scenario.hospitalityGroup);
				driver.findElement(By.name("check_in_time")).sendKeys(scenario.checkInTime);
				driver.findElement(By.name("check_out_time")).sendKeys(scenario.checkOutTime);

				// Tags & Inclusions
				driver.findElement(By.xpath("(//input[contains(@class,'flex-1 px-3')])[1]")).sendKeys(scenario.tags);
				driver.findElement(By.xpath("(//button[contains(@class,'px-4 py-2')])[1]")).click();

				driver.findElement(By.xpath("(//input[contains(@class,'flex-1 px-3')])[2]"))
						.sendKeys(scenario.inclusions);
				driver.findElement(By.xpath("(//button[contains(@class,'px-4 py-2')])[2]")).click();

				// Room Details
				driver.findElement(By.xpath("//input[@placeholder='e.g., Deluxe Room']")).sendKeys(scenario.roomName);
				driver.findElement(By.xpath("(//input[@min='0'])[2]")).sendKeys(scenario.roomBasePrice);
				driver.findElement(By.xpath("(//input[@min='1'])[2]")).sendKeys(scenario.roomMaxOccupancy);
				driver.findElement(By.xpath("(//input[@min='0'])[3]")).sendKeys(scenario.roomMaxAdults);
				driver.findElement(By.xpath("(//input[@step='1'])[1]")).sendKeys(scenario.roomMaxChildren);
				driver.findElement(By.xpath("(//input[@step='1'])[2]")).sendKeys(scenario.roomMaxInfants);
				driver.findElement(By.xpath("//input[@placeholder='e.g., Sea view']")).sendKeys(scenario.roomView);

				driver.findElement(By.xpath("(//input[contains(@class,'flex-1 px-3')])[3]"))
						.sendKeys(scenario.roomAmenities);
				driver.findElement(By.xpath("(//input[contains(@class,'flex-1 px-3')]/following-sibling::button)[3]"))
						.click();

				// --- IMAGES UPLOAD (Room) ---
				File roomImagesFolder = new File("C:\\Users\\NS\\Desktop\\images");
				File[] roomImages = roomImagesFolder.listFiles();
				if (roomImages != null && roomImages.length > 0) {
					WebElement roomInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"(//label[normalize-space(text())='Room Images (upload multiple)']/following::input)[1]")));
					((JavascriptExecutor) driver).executeScript(
							"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1';",
							roomInput);

					StringBuilder roomPaths = new StringBuilder();
					for (File file : roomImages) {
						if (file.isFile() && (file.getName().toLowerCase().endsWith(".jpg") ||
								file.getName().toLowerCase().endsWith(".jpeg") ||
								file.getName().toLowerCase().endsWith(".png"))) {
							roomPaths.append(file.getAbsolutePath()).append("\n");
						}
					}
					if (roomPaths.length() > 0) {
						roomInput.sendKeys(roomPaths.toString().trim());
					}
				}

				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[3]"))
						.sendKeys(scenario.mapEmbedUrl);
				driver.findElement(By.name("direction")).sendKeys(scenario.direction);
				driver.findElement(By.name("term_and_conditions")).sendKeys(scenario.termsAndConditions);
				driver.findElement(By.name("age_policy")).sendKeys(scenario.agePolicy);
				driver.findElement(By.name("cancellation_policy")).sendKeys(scenario.cancellationPolicy);

				// Metadata
				WebElement metaInput = driver.findElement(By.xpath(
						"//input[@placeholder='Add metadata tag (comma separated allowed) e.g., beachfront, all-inclusive']"));
				metaInput.sendKeys(scenario.metaData);
				metaInput.sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				WebElement metaAddBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//input[contains(@placeholder,'metadata')]/following::button[normalize-space()='Add'][1]")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", metaAddBtn);

				// Cover Image
				WebElement coverInput = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//label[normalize-space(text())='Cover Image']/following::input[@type='file'][1]")));
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.display='block'; arguments[0].style.visibility='visible';", coverInput);
				File coverFile = new File("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");
				if (coverFile.exists()) {
					coverInput.sendKeys(coverFile.getAbsolutePath());
				}

				// Additional Images
				WebElement additionalInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"(//*[normalize-space(text())='Additional Images']/following::input[@type='file'])[1]")));
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1';",
						additionalInput);

				File additionalFolder = new File("C:\\Users\\NS\\Desktop\\images");
				File[] additionalFiles = additionalFolder.listFiles();
				if (additionalFiles != null && additionalFiles.length > 0) {
					StringBuilder addPaths = new StringBuilder();
					for (File file : additionalFiles) {
						if (file.isFile() && (file.getName().toLowerCase().endsWith(".jpg") ||
								file.getName().toLowerCase().endsWith(".jpeg") ||
								file.getName().toLowerCase().endsWith(".png"))) {
							addPaths.append(file.getAbsolutePath()).append("\n");
						}
					}
					if (addPaths.length() > 0) {
						additionalInput.sendKeys(addPaths.toString().trim());
					}
				}

				Thread.sleep(2000);
				WebElement createBtn = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[normalize-space(text())='Create Recommendation']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", createBtn);

				// Verification of success
				boolean isCreated = false;
				try {
					// Wait for success toast or a general "successfully" message
					WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(10))
							.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[contains(text(),'successfully') or contains(text(),'Success')]")));
					System.out.println("Iteration Result: SUCCESS - " + toast.getText());
					isCreated = true;
				} catch (Exception e) {
					// Check if we are still on the form page (if button still exists)
					if (driver.findElements(By.xpath("//button[normalize-space(text())='Create Recommendation']"))
							.size() > 0) {
						System.out
								.println("Iteration Result: FAILED - Form still visible (possibly validation error).");
					} else {
						System.out.println("Iteration Result: SUCCESS - Form disappeared (likely redirected).");
						isCreated = true;
					}
				}

				System.out.println("Scenario Status for [" + scenario.scenarioName + "]: "
						+ (isCreated ? "CREATED" : "NOT CREATED"));

				String logEntry = "Scenario: " + scenario.scenarioName + "\n" +
						"  - Title: " + scenario.title + "\n" +
						"  - City: " + scenario.city + "\n" +
						"  - Status: " + (isCreated ? "PASSED" : "FAILED (Form not submitted)");

				if (isCreated) {
					passedCount++;
					passedScenariosNames.add(scenario.scenarioName);
				} else {
					failedCount++;
					failedScenariosNames.add(scenario.scenarioName);
				}
				detailedLogs.add(logEntry);
				Thread.sleep(2000);

			} catch (Exception e) {
				System.err.println("Error in scenario " + scenario.scenarioName + ": " + e.getMessage());
				System.out.println("Iteration Result: FAILED - Exception occurred.");
				failedCount++;
				failedScenariosNames.add(scenario.scenarioName);
				detailedLogs.add(
						"Scenario: " + scenario.scenarioName + "\n  - Status: FAILED\n  - Error: " + e.getMessage());
			} finally {
				driver.quit();
			}

			System.out.println("Starting 5-second wait before next scenario...");
			Thread.sleep(5000); // 5 seconds wait between fresh sessions
		}

		System.out.println("\n========================================");
		System.out.println("         TEST EXECUTION SUMMARY");
		System.out.println("========================================");
		System.out.println("Total Scenarios Run: " + totalScenarios);
		System.out.println("Total Passed:        " + passedCount);
		if (!passedScenariosNames.isEmpty()) {
			System.out.println("  - Passed Scenarios: " + String.join(", ", passedScenariosNames));
		}
		System.out.println("Total Failed:        " + failedCount);
		if (!failedScenariosNames.isEmpty()) {
			System.out.println("  - Failed Scenarios: " + String.join(", ", failedScenariosNames));
		}
		System.out.println("========================================\n");

		// --- LOG FILE GENERATION ---
		try {
			// Create resource/logs folder if it doesn't exist
			File logDir = new File("src/test/resource/logs");
			if (!logDir.exists()) {
				logDir.mkdirs();
			}

			String fileName = "test_results_"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log";
			File logFile = new File(logDir, fileName);
			try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
				writer.println("========================================");
				writer.println("         DETAILED EXECUTION REPORT");
				writer.println("Executed on: "
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				writer.println("========================================");
				writer.println("Total Scenarios Run: " + totalScenarios);
				writer.println("Total Passed:        " + passedCount);
				writer.println("Total Failed:        " + failedCount);
				writer.println("========================================\n");

				writer.println("--- SCENARIO DETAILS ---");
				if (detailedLogs.isEmpty()) {
					writer.println("No scenario details captured.");
				} else {
					for (String log : detailedLogs) {
						writer.println(log);
						writer.println("----------------------------------------");
					}
				}

				writer.println("\n--- FINAL SUMMARY ---");
				writer.println("PASSED Scenarios: "
						+ (passedScenariosNames.isEmpty() ? "None" : String.join(", ", passedScenariosNames)));
				writer.println("FAILED Scenarios: "
						+ (failedScenariosNames.isEmpty() ? "None" : String.join(", ", failedScenariosNames)));
				writer.println("========================================");
			}
			System.out.println("Detailed log file generated: " + logFile.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("Failed to generate log file: " + e.getMessage());
		}
	}
}
