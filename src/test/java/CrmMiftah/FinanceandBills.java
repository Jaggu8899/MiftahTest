package Test7;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FinanceandBills {

	public static void main(String[] args) throws Exception {
		
		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://crmdev.miftah.ai/dashboard/");
	       driver.manage().window().maximize();
	       driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.id("password")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//span[text()='Finance & Bills']")).click();
	       Thread.sleep(3000);
	       WebElement clickable = driver.findElement(By.xpath("//div[contains(@class,'bg-white/20')]/parent::*"));
	       clickable.click();

		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
