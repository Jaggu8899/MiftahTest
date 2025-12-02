package Test6;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CustomWindow {

	public static void main(String[] args) throws Exception {
		
		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(3000);
	       driver.findElement( By.xpath("//button[.//span[text()='Itinerary']]")).click();
		   Thread.sleep(3000);
		   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		   WebElement dotMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
		           By.xpath("//*[name()='svg' and contains(@class,'ellipsis-vertical')]")
		   ));

		   dotMenu.click();
		   WebElement shareBooking = wait.until(ExpectedConditions.elementToBeClickable(
				    By.xpath("//div[@role='menuitem' and contains(normalize-space(),'Share Booking')]")
				));

				shareBooking.click();
				
				
				// Store parent window handle
				String parentWindow = driver.getWindowHandle();
				System.out.println("Parent Window: " + parentWindow);

				// Wait a moment for any popup or new window
				Thread.sleep(2000);

				// Get all window handles
				Set<String> allWindows = driver.getWindowHandles();

				// Print all windows for debugging
				System.out.println("All Windows: " + allWindows);

				// Check if Share Booking opened a new window
				

				// Get current window handle
				String currentWindow = driver.getWindowHandle();
				System.out.println("Current Window: " + currentWindow);

				// Compare parent and current
				if (parentWindow.equals(currentWindow)) {
				    System.out.println("Parent and Current window are SAME ✔");
				} else {
				    System.out.println("Parent and Current window are DIFFERENT ✘");
				}
				 driver.quit();

				
				
				
	}

}
