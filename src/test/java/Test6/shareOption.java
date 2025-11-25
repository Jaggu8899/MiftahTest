package Test6;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class shareOption {

	public static void main(String[] args) throws Exception {
		

		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Login']]")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[normalize-space()='Sign In']")).click();
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
				
				
				
				
				// Store parent window + URL
				String parentWindow = driver.getWindowHandle();
				String parentURL = driver.getCurrentUrl();

				System.out.println("Parent Window ID: " + parentWindow);
				System.out.println("Parent URL: " + parentURL);

				// Wait for any new window/tab to open
				Thread.sleep(2000);

				// Get all windows
				Set<String> allWindows = driver.getWindowHandles();

				if (allWindows.size() == 1) {
				    System.out.println("No new window opened ✔ Share Booking opened in SAME WINDOW");
				} else {
				    System.out.println("New window opened ✘");
				}

				// Find the child window
				String childWindow = parentWindow;

				for (String win : allWindows) {
				    if (!win.equals(parentWindow)) {
				        childWindow = win;
				        break;
				    }
				}

				// Switch to child window if different
				driver.switchTo().window(childWindow);

				// Capture child URL
				String childURL = driver.getCurrentUrl();

				System.out.println("Child Window ID: " + childWindow);
				System.out.println("Child URL: " + childURL);

				// Compare URLs
				if (parentURL.equals(childURL)) {
				    System.out.println("SUCCESS ✔ Parent URL and Share Booking URL are SAME");
				} else {
				    System.out.println("FAILED ✘ URLs are DIFFERENT");
				}

				// Switch back to parent (optional)
				driver.switchTo().window(parentWindow);

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
