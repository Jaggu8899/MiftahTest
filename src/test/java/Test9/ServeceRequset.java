package Test9;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServeceRequset {

	public static void main(String[] args) throws Exception {
		
		  WebDriverManager.chromedriver().setup();
	       WebDriver driver = new ChromeDriver();
	       driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		  driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
		  driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
		  driver.findElement(By.xpath("//button[text()='Login']")).click();
		  Thread.sleep(5000);
		  driver.findElement(By.xpath("//span[normalize-space()='Miftah Recommends']")).click();
		  Thread.sleep(5000);
		  driver.findElement(By.xpath("//span[contains(text(),'Curated Experiences')]")).click();
		  Thread.sleep(10000);
		  driver.findElement(By.xpath("//img[contains(@alt,'Cauto')]")).click();
		  Thread.sleep(10000);
		  driver.findElement(By.xpath("//button[normalize-space()='Book Now']")).click();
		  Thread.sleep(15000);
		  
		  WebElement dateInput = driver.findElement(
				    By.xpath("//input[@type='date' and @min='2026-01-13']")
				);

				// Set date directly
		  dateInput.sendKeys("28-01-2026");
		  Thread.sleep(10000);
          driver.findElement(By.xpath("//*[normalize-space()='8:20 PM (50 available)']")).click();
          Thread.sleep(10000);
		  driver.findElement(By.xpath("//*[normalize-space()='5 Guests']")).click();
		  Thread.sleep(10000);
          driver.findElement(By.xpath("//button[normalize-space()='Continue']")).click();
          Thread.sleep(10000);
          driver.findElement(By.xpath("//button[normalize-space()='Confirm Booking']")).click();
          Thread.sleep(10000);
          //CRM Part
           driver.get("https://crmdev.miftah.ai/dashboard/");
	       driver.manage().window().maximize();
	       driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.id("password")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//span[normalize-space()='Service Requests']")).click();
	       
	       driver.findElement(By.xpath("//td[normalize-space()='Cauto']")).click();
	       
	       WebElement verifyBtn = driver.findElement(
	    		    By.xpath("//button[normalize-space()='Verify & Confirm']")
	    		);

	    		((JavascriptExecutor) driver).executeScript(
	    		    "arguments[0].scrollIntoView(true);", verifyBtn
	    		);

	    		((JavascriptExecutor) driver).executeScript(
	    		    "arguments[0].click();", verifyBtn
	    		);
	    		
	    		WebElement confirmedTab = driver.findElement(
	    			    By.xpath("//button[starts-with(normalize-space(),'Confirmed')]")
	    			);
	    			confirmedTab.click();

	    		
	    		

	    } 
}
