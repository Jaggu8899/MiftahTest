package Test6;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Itinerary {

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

		   Thread.sleep(3000);
		   
		// After clicking the 3 dots, wait for menu items to appear
		   WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

		   // 1. Locate "View Details"
		   WebElement viewDetails = wait1.until(ExpectedConditions.visibilityOfElementLocated(
		           By.xpath("//div[contains(., 'View Details') and @role='menuitem']"))
		   );

		   // 2. Locate "Download Details"
		   WebElement downloadDetails = wait1.until(ExpectedConditions.visibilityOfElementLocated(
		           By.xpath("//div[contains(., 'Download Details') and @role='menuitem']"))
		   );

		   // Check if enabled
		   boolean isViewDetailsEnabled = viewDetails.isEnabled();
		   boolean isDownloadEnabled = downloadDetails.isEnabled();

		   System.out.println("View Details Enabled: " + isViewDetailsEnabled);
		   System.out.println("Download Details Enabled: " + isDownloadEnabled);







		     

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
