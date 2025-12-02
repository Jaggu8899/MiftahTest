package Test7;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServeceProvider {

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
	       driver.findElement(By.xpath("//span[normalize-space()='Service Providers']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//div[contains(@class,'bg-white/20')]/parent::*")).click();
	       Thread.sleep(3000);
	       WebElement dropdown = driver.findElement(By.xpath("//select[contains(@class,'form-input')]"));
	       Select select = new Select(dropdown);
	       select.selectByVisibleText("Technology");
	       Thread.sleep(3000);
	       WebElement industry = driver.findElement(By.xpath("//label[contains(text(),'Industry Type')]/following::select[1]"));
	       industry.click();

	       // Select an option
	       Select select1 = new Select(industry);
	       select1.selectByVisibleText("Accommodation");   // choose any option
	       driver.findElement(By.xpath("//input[@placeholder='Enter full name']")).sendKeys("1234-");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@placeholder='Enter email address']")).sendKeys("testf-@gmail.com");  // invalid characters
	       Thread.sleep(3000);
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Click dropdown to open it
	    WebElement dropdown1 = driver.findElement(By.xpath("//span[text()='+971']"));
	    dropdown1.click();

	    // Wait for the +91 option to be visible and clickable
	    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//div[contains(@class,'dropdown-menu')]//div[text()='+91']")));
	    option.click();

	    driver.findElement(By.xpath("//input[@type='tel' and @placeholder='+971501234567']")).sendKeys("98765432104");
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//input[@placeholder='Enter company name']")).sendKeys("intelliod");
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//input[@placeholder='Enter job title']")).sendKeys("1234");
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//select//option[text()='VIP']")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//span[text()='Add Provider']")).click();

		
		
		
		
		
		

	}

}
