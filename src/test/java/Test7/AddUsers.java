package Test7;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddUsers {

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
	       driver.findElement(By.xpath("//span[normalize-space()='Miftah Users']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//span[normalize-space()='Add User']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@placeholder='Enter full name']")).sendKeys("12345");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@placeholder='Enter email']")).sendKeys("143456%@gmail.com");
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Click dropdown to open it
		    WebElement dropdown1 = driver.findElement(By.xpath("//span[text()='+971']"));
		    dropdown1.click();

		    // Wait for the +91 option to be visible and clickable
		    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//div[contains(@class,'dropdown-menu')]//div[text()='+91']")));
		    option.click();
		    Thread.sleep(3000);

		    WebElement phone = driver.findElement(By.xpath("//input[@type='tel']"));
		    phone.click();
		    phone.sendKeys(Keys.CONTROL, "a");
		    phone.sendKeys(Keys.DELETE);

		    // Type full phone number including country code
		    phone.sendKeys("9876543210");
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys("Jaggu@89");
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//select//option[text()='VIP']")).click();
		    Thread.sleep(3000);
		   
		    WebElement addUserBtn = driver.findElement(By.xpath("//button[@title='Add New User']"));

		    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", addUserBtn);
		    ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addUserBtn);
		    Thread.sleep(3000);
		    
		  File SourceFile =  driver.getScreenshotAs(OutputType.FILE);
		  File destiFile = new File ("C:\\Users\\NS\\Desktop\\Miftah(Test)\\MaveenProject\\src\\test\\resource\\img4.JPG");
		  FileUtils.copyFile(SourceFile, destiFile);
		  System.out.println("ss saved successfully");
		    
		    
		    
		   
}

}
