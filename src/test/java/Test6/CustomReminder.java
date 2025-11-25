package Test6;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CustomReminder {

	public static void main(String[] args) throws Exception {
		
		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Login']]")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[normalize-space()='Sign In']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Calendar']]")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Add Event']]")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@placeholder='e.g., Dinner at Nobu']")).sendKeys("night clubs");
	       Thread.sleep(3000);
	    // Start Date
	       WebElement start = driver.findElement(By.xpath("//label[contains(text(),'Start Date')]/following::input[1]"));
	       start.click();
	       start.clear();
	       start.sendKeys("29-11-2025");

	       // End Date
	       WebElement end = driver.findElement(By.xpath("//label[contains(text(),'End Date')]/following::input[1]"));
	       end.click();
	       end.clear();
	       end.sendKeys("29-11-2025");
	       Thread.sleep(3000);
	    // Event Time
	       WebElement eventTime = driver.findElement(By.xpath("//label[contains(text(),'Event Time')]/following::input[1]"));
	       eventTime.click();
	       eventTime.clear();
	       eventTime.sendKeys("11:30");

	       // End Time
	       WebElement endTime = driver.findElement(By.xpath("//label[contains(text(),'End Time')]/following::input[1]"));
	       endTime.click();
	       endTime.clear();
	       endTime.sendKeys("12:30");
	       Thread.sleep(3000);
           
	       WebElement dropdown = driver.findElement(By.xpath("//label[contains(text(),'Event Type')]/following::select[1]"));
	       Select type = new Select(dropdown);

	       // choose the option
	       type.selectByVisibleText("Dining");
	       
	       Thread.sleep(2000);
	       driver.findElement(By.xpath("//button[@title='Select location on map']")).click();
	       Thread.sleep(2000);
	       

	       driver.findElement(By.xpath("//button[contains(text(),'Use this location')]")).click();
	       Thread.sleep(2000);
	       driver.findElement(By.xpath("//span[text()='Add notification']")).click();
	       
	       WebElement numberInput = driver.findElement(
	    		    By.xpath("(//input[@type='number'])[last()]")
	    		);

	    		numberInput.click();
	    		numberInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    		numberInput.sendKeys(Keys.DELETE);
	    		numberInput.sendKeys("5");
	    		
	    		 Thread.sleep(10000);
	    		

	       
	    		WebElement unitDropdown = driver.findElement(
	    			    By.xpath("(//select)[last()]")
	    			);

	    			Select select = new Select(unitDropdown);
	    			select.selectByVisibleText("hours");

	       
	       
	    
           driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();

		}

}
