package Test9;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RealityBookings {
	
	 public static void main(String[] args) throws Exception {

	        WebDriverManager.chromedriver().setup();
	        WebDriver driver = new ChromeDriver();

	        driver.get("https://dev.miftah.ai/");
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	        driver.findElement(By.id("email"))
	              .sendKeys("jagadeeswara89@gmail.com");

	        driver.findElement(By.id("password"))
	              .sendKeys("Jaggu@89");

	        driver.findElement(By.xpath("//button[text()='Login']")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//span[normalize-space()='Miftah Recommends']")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//span[text()='Realty']")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//h3[normalize-space()='Reality']")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//button[normalize-space()='Enquire Now']")).click();
            Thread.sleep(5000);
            driver.get("https://crmdev.miftah.ai/dashboard/");
 	       driver.manage().window().maximize();
 	       driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
 	       Thread.sleep(3000);
 	       driver.findElement(By.id("password")).sendKeys("Jaggu@89");
 	       driver.findElement(By.xpath("//button[text()='Login']")).click();
 	       Thread.sleep(3000);
 	       driver.findElement(By.xpath("//span[normalize-space()='Service Requests']")).click();
 	       Thread.sleep(3000);
 	       driver.findElement(By.xpath("//button[contains(normalize-space(),'Leads')]")).click();
 	      Thread.sleep(3000);
 	      driver.findElement(By.xpath("//span[normalize-space()='Realty']")).click();
 	     
 	     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
 	     WebElement verifyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                 By.xpath("//button[normalize-space()='Verify & Confirm']")));

         ((JavascriptExecutor) driver)
                 .executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", verifyBtn);

         // Confirmed Tab
         wait.until(ExpectedConditions.elementToBeClickable(
                 By.xpath("//button[starts-with(normalize-space(),'Confirmed')]"))).click();
         
         
         
         
         
         
         
         
         
         
         

         

 	   }
	}
