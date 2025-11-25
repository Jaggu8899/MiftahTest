package Test6;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class mapsandcar {

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
		   
		   driver.findElement(By.xpath("//button[contains(normalize-space(),'Select Yalla')]")).click();
		   Thread.sleep(5000);
		   
		   WebElement element = driver.findElement(
				    By.xpath("//*[name()='path' and @d='m6 6 12 12']")
				);
		           element.click();
		           Thread.sleep(5000);
		   
		   WebElement resortName = driver.findElement(
				    By.xpath("//h3[normalize-space()='Anantara Mina Al Arab Resort']"));
				
		   resortName.click();
		   
		
		  

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
