package Test6;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HidingBookings {

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

		   Thread.sleep(3000);
		
		   WebElement hideBooking = wait.until(ExpectedConditions.elementToBeClickable(
				    By.xpath("//div[@role='menuitem' and contains(normalize-space(),'Hide Booking')]")
				));

				hideBooking.click();
				Thread.sleep(3000);

		
				WebElement hideBooking1 = wait.until(ExpectedConditions.elementToBeClickable(
					    By.xpath("//button[contains(normalize-space(),'Hide Booking')]")
					));
				Thread.sleep(3000);
					hideBooking1.click();
					
					System.out.println("Hide bookings success");
					
					  
					driver.quit();

		
		
		
		
		
		
		

	}

}
