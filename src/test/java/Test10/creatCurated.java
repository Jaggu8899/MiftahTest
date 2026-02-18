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

public class creatCurated {

	public static void main(String[] args) throws Exception {

		WebDriverManager.chromedriver().setup();

		int totalScenarios = 0;
		int passedCount = 0;
		int failedCount = 0;
		java.util.List<String> passedScenariosNames = new java.util.ArrayList<>();
		java.util.List<String> failedScenariosNames = new java.util.ArrayList<>();
		java.util.List<String> detailedLogs = new java.util.ArrayList<>();
		List<CuratedData.CuratedScenario> scenarios = CuratedData.getScenarios();
		totalScenarios = scenarios.size();

		for (CuratedData.CuratedScenario scenario : scenarios) {
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

				WebElement addBtnNav = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'group p-2')]")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtnNav);

				WebElement curatedBtn = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//p[text()='Curated Experiences']/following-sibling::p")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", curatedBtn);

				// Basic Information
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")).sendKeys(scenario.title);
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")).sendKeys(scenario.stars);
				driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]"))
						.sendKeys(scenario.durationHours);

				Select currency = new Select(
						driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
				currency.selectByIndex(12); // INR

				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[1]"))
						.sendKeys(scenario.generalDescription);
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
				driver.findElement(By.name("contactPerson")).sendKeys(scenario.contactPerson);

				// --- IMAGES UPLOAD (Batch) ---
				// Cover Image
				WebElement coverInput = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.display='block'; arguments[0].style.visibility='visible';", coverInput);
				File imgDir = new File("C:\\Users\\NS\\Desktop\\images");
				File[] images = imgDir.listFiles();
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

				driver.findElement(By.xpath("(//label[normalize-space(text())='Map Embed URL']/following::input)[1]"))
						.sendKeys(scenario.mapEmbedUrl);
				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]"))
						.sendKeys(scenario.directions);
				driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[3]"))
						.sendKeys(scenario.termsAndConditions);
				driver.findElement(By.xpath("//input[@placeholder='e.g., 18+']")).sendKeys(scenario.agePolicy);

				// Metadata
				WebElement metaInput = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//input[@placeholder='Enter meta data (comma-separated) and press Enter']")));
				metaInput.sendKeys(scenario.metaData);
				metaInput.sendKeys(Keys.ENTER);
				WebElement metaAddBtn = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", metaAddBtn);

				driver.findElement(By.xpath("(//textarea[@placeholder='Enter experience description'])[2]"))
						.sendKeys(scenario.detailedDescription);
				driver.findElement(By.xpath("//input[@placeholder='YYYY-MM-DD']")).sendKeys(scenario.date);
				driver.findElement(By.xpath("//input[@placeholder='e.g., 60']")).sendKeys(scenario.minAge);
				driver.findElement(By.xpath("//input[@placeholder='e.g., 120']")).sendKeys(scenario.durationMinutes);
				driver.findElement(By.xpath("//input[@placeholder='e.g., Smart Casual']"))
						.sendKeys(scenario.dressCode);
				driver.findElement(By.xpath("//input[@placeholder='HH:mm']")).sendKeys(scenario.startTime);
				driver.findElement(By.xpath("(//label[normalize-space(text())='Total Guests']/following::input)[1]"))
						.sendKeys(scenario.totalGuests);

				driver.findElement(By.xpath("//button[normalize-space(text())='Add Time Slot']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("(//input[@placeholder='HH:mm'])[2]")).sendKeys(scenario.slot2Time);
				driver.findElement(By.xpath("(//input[@placeholder='e.g. 20'])[2]")).sendKeys(scenario.slot2Guests);

				driver.findElement(By.xpath("//textarea[@placeholder='Enter important notes']"))
						.sendKeys(scenario.importantNotes);
				driver.findElement(By.name("cancellation_policy")).sendKeys(scenario.cancellationPolicy);

				// Experience Includes
				WebElement expInput = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("(//input[contains(@class,'flex-1 px-3')])[2]")));
				expInput.sendKeys(scenario.experienceIncludes);
				WebElement expAddBtn = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("(//input[contains(@class,'flex-1 px-3')]/following-sibling::button)[2]")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", expAddBtn);

				driver.findElement(By.xpath("//input[@placeholder='e.g., 2300']")).sendKeys(scenario.basePrice);
				driver.findElement(By.xpath("//input[@placeholder='e.g., Premium Wine']")).sendKeys(scenario.itemTitle);
				driver.findElement(By.xpath("//textarea[@placeholder='e.g., Best premium wine selection']"))
						.sendKeys(scenario.itemDescription);

				Thread.sleep(2000);
				WebElement createBtn = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[normalize-space(text())='Create Recommendation']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", createBtn);

				// Verification of success
				boolean isCreated = false;
				try {
					WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'successfully') or contains(text(),'Success')]")));
					System.out.println("Iteration Result: SUCCESS - " + toast.getText());
					isCreated = true;
				} catch (Exception e) {
					if (driver.findElements(By.xpath("//button[normalize-space(text())='Create Recommendation']"))
							.size() > 0) {
						System.out.println("Iteration Result: FAILED - Form still visible.");
					} else {
						System.out.println("Iteration Result: SUCCESS - Form disappeared.");
						isCreated = true;
					}
				}

				System.out.println("Scenario Status for [" + scenario.scenarioName + "]: "
						+ (isCreated ? "CREATED" : "NOT CREATED"));

				String logEntry = "Scenario: " + scenario.scenarioName + "\n" + "  - Title: " + scenario.title + "\n"
						+ "  - Status: " + (isCreated ? "PASSED" : "FAILED");

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
				failedCount++;
				failedScenariosNames.add(scenario.scenarioName);
				detailedLogs.add("Scenario: " + scenario.scenarioName + "\n  - Status: FAILED\n  - Error: " + e.getMessage());
			} finally {
				driver.quit();
			}

			System.out.println("Waiting 5 seconds before next scenario...");
			Thread.sleep(5000);
		}

		// Final Summary Console
		System.out.println("\n========================================");
		System.out.println("         TEST EXECUTION SUMMARY");
		System.out.println("========================================");
		System.out.println("Total Scenarios Run: " + totalScenarios);
		System.out.println("Total Passed:        " + passedCount);
		if (!passedScenariosNames.isEmpty())
			System.out.println("  - Passed: " + String.join(", ", passedScenariosNames));
		System.out.println("Total Failed:        " + failedCount);
		if (!failedScenariosNames.isEmpty())
			System.out.println("  - Failed: " + String.join(", ", failedScenariosNames));
		System.out.println("========================================\n");

		// Log File
		try {
			File logDir = new File("src/test/resource/logs");
			if (!logDir.exists())
				logDir.mkdirs();
			String fileName = "curated_results_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".log";
			File logFile = new File(logDir, fileName);
			try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
				writer.println("================================");
				writer.println("  CURATED EXPERIENCES LOG REPORT");
				writer.println("  Executed: " + LocalDateTime.now());
				writer.println("================================\n");
				for (String log : detailedLogs) {
					writer.println(log);
					writer.println("--------------------------------");
				}
				writer.println("\nSummary: " + passedCount + " Passed, " + failedCount + " Failed");
			}
			System.out.println("Log generated: " + logFile.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("Log failed: " + e.getMessage());
		}
	}
}
	
	
	
	
	
	
	
	
	
	


