package Test6;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class maps {

	public static void main(String[] args) throws Exception {
		
		WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Calendar']]")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[normalize-space()='Visakhapatnam, India']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[normalize-space()='The Dubai Mall, Dubai']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[normalize-space()='Use this location']")).click();
	       

		
		
		
		
		
		
		
		
		
		
		
		
	}

}
