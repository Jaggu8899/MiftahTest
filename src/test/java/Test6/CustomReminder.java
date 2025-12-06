package Test6;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CustomReminder {
	@Test
	public static void main(String[] args) throws Exception {
		
		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[.//span[text()='Calendar']]")).click();
           Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Add Event']]")).click();
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//input[@placeholder='e.g., Dinner at Nobu']")).sendKeys("Night Clubs");
	       Thread.sleep(3000);
	    // Start Date
	       WebElement start = driver.findElement(By.xpath("//label[contains(text(),'Start Date')]/following::input[1]"));
	       start.click();
	       start.clear();
	       start.sendKeys("9-12-2025");

	       // End Date
	       WebElement end = driver.findElement(By.xpath("//label[contains(text(),'End Date')]/following::input[1]"));
	       end.click();
	       end.clear();
	       end.sendKeys("9-12-2025");
	       Thread.sleep(5000);
	    // Event Time
	       WebElement time = driver.findElement(By.xpath("//input[@type='time']"));
	       time.sendKeys("11:00");


	       // End Time
	       WebElement endTime = driver.findElement(
	    		    By.xpath("//label[text()='End Time']/following::input[@type='time'][1]")
	    		);
	    		endTime.sendKeys("12:30");

           
	       WebElement dropdown = driver.findElement(By.xpath("//label[contains(text(),'Event Type')]/following::select[1]"));
	       Select type = new Select(dropdown);

	       // choose the option
	       type.selectByVisibleText("Dining");
	       
	       Thread.sleep(2000);
	       driver.findElement(By.xpath("//button[@title='Select location on map']")).click();
	       Thread.sleep(2000);
	       

	       driver.findElement(By.xpath("//button[contains(text(),'Use this location')]")).click();
	       Thread.sleep(2000);
	       WebElement addBtn = driver.findElement(By.xpath("//span[text()='Add notification']"));
	       addBtn.click();
	       
	       WebElement reminderValue = driver.findElement(By.xpath("//input[@placeholder='30']"));
	       reminderValue.clear();
	       reminderValue.sendKeys("44");
           Thread.sleep(10000);
	       WebElement unitDropdown = driver.findElement(
	    			   By.xpath("(//select)[last()]")
	    			);

	    			Select select = new Select(unitDropdown);
	    			select.selectByVisibleText("minutes");
	    			driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
	    			Thread.sleep(5000);
	    			driver.findElement(By.xpath("//input[@placeholder='Search luxury experiences and events...']")).sendKeys("Ritz Carlton");

	    			

		}

}
