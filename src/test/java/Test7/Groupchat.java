package Test7;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Groupchat {

	public static void main(String[] args) throws Exception {

		WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://crmdev.miftah.ai/dashboard/");
	       driver.manage().window().maximize();
	       Thread.sleep(3000);
	       driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.id("password")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//span[text()='Service Request']")).click();
	       Thread.sleep(3000);
	       WebElement confirmed = driver.findElement(By.xpath("//span[normalize-space()='Confirmed']"));
	       confirmed.click();  // if clickable

	       Thread.sleep(3000);
	       WebElement name = driver.findElement(By.xpath("//div[normalize-space()='jagadeesh']"));
	       name.click();   // if clickable

	       Thread.sleep(3000);
	       WebElement noteBox = driver.findElement(By.xpath("//textarea[@placeholder='Add a note...']"));
	       noteBox.sendKeys("Test note added by automation");

	       Thread.sleep(3000);
	       WebElement sendBtn = driver.findElement(By.xpath("//button[.//span[text()='Send']]"));
	       sendBtn.click();



		

	}

}
