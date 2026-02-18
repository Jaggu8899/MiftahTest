package Test10;

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

				WebElement addBtnNav = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'group p-2')]")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtnNav);

				WebElement eventBtn = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Events']/following-sibling::p")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", eventBtn);

				// Basic Information
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")))
						.sendKeys(scenario.title);
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")).sendKeys(scenario.stars);

				Select currency = new Select(
						driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
				currency.selectByIndex(12); // INR

				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[1]"))
						.sendKeys(scenario.description);
				driver.findElement(By.xpath("//input[@placeholder='Enter location']")).sendKeys(scenario.location);
				driver.findElement(By.xpath("//input[@placeholder='Enter city']")).sendKeys(scenario.city);
				driver.findElement(By.xpath("//input[@placeholder='contact@example.com']"))
						.sendKeys(scenario.contactEmail);

				// Country Code
				By countryDropdown = By.xpath("//button[.//span[normalize-space()='+971']]");
				wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)).click();
				By indiaCode = By.xpath("//*[contains(text(),'+91')]");
				wait.until(ExpectedConditions.elementToBeClickable(indiaCode)).click();

				driver.findElement(By.xpath("(//label[normalize-space(text())='Contact Phone']/following::input)[1]"))
						.sendKeys(scenario.contactPhone);
				driver.findElement(By.xpath("//input[@placeholder='Enter contact person name']"))
						.sendKeys(scenario.contactPerson);

				// --- IMAGES UPLOAD (Batch) ---
				File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
				File[] images = imgDir.listFiles();

				// Cover Image
				WebElement coverInput = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1';",
						coverInput);
				if (images != null && images.length > 0) {
					coverInput.sendKeys(images[0].getAbsolutePath());
				}

				Thread.sleep(2000);

				// Additional Images
				WebElement additionalInput = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[2]")));
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1';",
						additionalInput);
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

				Thread.sleep(2000);

				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]"))
						.sendKeys(scenario.mapEmbedUrl);
				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[3]"))
						.sendKeys(scenario.directions);
				driver.findElement(By.name("term_and_conditions")).sendKeys(scenario.termsAndConditions);
				driver.findElement(By.name("age_policy")).sendKeys(scenario.agePolicy);
				driver.findElement(By.name("cancellation_policy")).sendKeys(scenario.cancellationPolicy);
				driver.findElement(By.name("leadTime")).sendKeys(scenario.leadTime);

				// Metadata
				WebElement metaInput = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("(//input[contains(@class,'flex-1 px-3')])[1]")));
				metaInput.sendKeys(scenario.metaData);
				metaInput.sendKeys(Keys.ENTER);
				Thread.sleep(500);

				// Experience Includes
				WebElement expInput = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("(//input[contains(@class,'flex-1 px-3')])[2]")));
				expInput.sendKeys(scenario.experienceIncludes);
				expInput.sendKeys(Keys.ENTER);
				Thread.sleep(500);

				driver.findElement(By.name("eventDescription")).sendKeys(scenario.eventDescription);
				driver.findElement(By.xpath("(//label[contains(.,'Event Date *')]/following::input)[1]"))
						.sendKeys(scenario.eventDate);
				driver.findElement(By.xpath("(//label[contains(.,'Start Time *')]/following::input)[1]"))
						.sendKeys(scenario.startTime);
				driver.findElement(By.xpath("//input[@placeholder='e.g., Teddy Swims Live in Concert']"))
						.sendKeys(scenario.ticketName);
				driver.findElement(By.name("eventCategory")).sendKeys(scenario.category);
				driver.findElement(
						By.xpath("(//label[normalize-space(text())='Doors Open (HH:mm:ss)']/following::input)[1]"))
						.sendKeys(scenario.doorsOpen);
				driver.findElement(By.xpath("//input[@placeholder='e.g., 500']")).sendKeys(scenario.totalCapacity);

				// --- FIX: Last Booking Date & Time ---
				WebElement dateTimeField = driver.findElement(By.xpath("//input[@name='lastBookingDate']"));
				dateTimeField.clear();
				// Robust Method for datetime-local/masked fields in Chrome:
				// DDMMYYYY -> TAB -> HHmm
				String rawDigits = scenario.lastBookingDate; // e.g., "290120261547"
				String datePart = rawDigits.substring(0, 8);
				String timePart = rawDigits.substring(8);

				System.out.println("Entering Date: " + datePart + " and Time: " + timePart);
				dateTimeField.sendKeys(datePart);
				dateTimeField.sendKeys(Keys.TAB);
				dateTimeField.sendKeys(timePart);

				driver.findElement(By.xpath("//input[@placeholder='e.g., VIP Gold']")).sendKeys(scenario.ticketTier);
				driver.findElement(By.xpath("(//input[@min='0'])[2]")).sendKeys(scenario.ticketPrice);
				driver.findElement(By.xpath("(//input[@min='0'])[3]")).sendKeys(scenario.ticketQuantity);
				driver.findElement(
						By.xpath("//textarea[@placeholder='e.g., Premium front row seating with meet & greet access']"))
						.sendKeys(scenario.ticketTierDescription);

				Thread.sleep(3000);
				WebElement createBtn = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[normalize-space(text())='Create Recommendation']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createBtn);
				Thread.sleep(1000);
				createBtn.click();

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
}
