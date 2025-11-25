package Test5;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AskMiftah {
	
	public static void main(String[] args) throws Exception {
		
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver= new ChromeDriver();
		 driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Login']]")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//button[normalize-space()='Sign In']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//textarea[@rows='1']")).sendKeys("Events");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[.//img[contains(@src,'send.svg')]]")).click();
	       Thread.sleep(10000);
	       driver.findElement(By.xpath("//h4[text()='Teddy Swims Live in Dubai']")).click();

	       

	       
}
	
	
	
	
	
	
	
	
	
	

}
