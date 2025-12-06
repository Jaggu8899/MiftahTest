package Test7;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TeamUsers {

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
	       driver.findElement(By.xpath("//span[normalize-space()='Team Users']")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//div[contains(@class,'bg-white/20') and contains(@class,'rounded-lg')]")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//input[@placeholder='Enter full name']")).sendKeys("jagadeesh");
	       Thread.sleep(5000);
	       WebElement roleDropdown = driver.findElement(By.xpath("//select[contains(@class,'form-input')]"));
           Select s = new Select(roleDropdown);
	       s.selectByVisibleText("CRM");
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//input[@placeholder='Enter email address']")).sendKeys("jagadeeshn%@gmail.com");
	       Thread.sleep(5000);
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	       WebElement dropdown1 = driver.findElement(By.xpath("//span[text()='+971']"));
		    dropdown1.click();

		    // Wait for the +91 option to be visible and clickable
		    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//div[contains(@class,'dropdown-menu')]//div[text()='+91']"))); 
		    option.click();
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//input[@placeholder='9876543210']")).sendKeys("1234667893");

		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys("Jagadeesh@5");
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//span[normalize-space()='Add Member']")).click();

	       
	}

}
