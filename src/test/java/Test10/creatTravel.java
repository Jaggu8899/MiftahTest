package Test10;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class creatTravel {
	
	
	public static void main(String[] args) throws Exception {
		
		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://crmdev.miftah.ai/dashboard/");
	       driver.manage().window().maximize();
	       driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.id("password")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	       By miftahBtn = By.xpath("//span[normalize-space()='Miftah Recommendations']/ancestor::button");

	       WebElement element = wait.until(ExpectedConditions.elementToBeClickable(miftahBtn));
	       element.click();
	      
	       Thread.sleep(5000);
	       String[] titles = {"","   ", "@#$%^", "<script>alert(1)</script>"," Dubai"," Dubai","123456","Dubai\nMall","Dubai\tMall","Dubai@@@###","asd123@@@###$$$","CauDubai","@","A","https://google.com","C:\test\file.txt"}; // Add as many as you want

	        for (String title : titles) {
	       driver.findElement(By.xpath("//button[contains(@class,'group p-2')]")).click();
	
	       driver.findElement(By.xpath("//p[normalize-space(text())='Travel']")).click();
	
	       driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")).sendKeys(title);

           driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")).sendKeys("5");
	
           driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")).sendKeys("Dubai");
           
           driver.findElement(By.xpath("//input[@placeholder='e.g., Dubai']")).sendKeys("Dubai");
           
           Select currency = new Select(driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
           currency.selectByIndex(12);  // INR
           
           driver.findElement(By.name("leadTime")).sendKeys("1.5");
           
           driver.findElement(By.name("base_price")).sendKeys("1000");
           
           driver.findElement(By.name("description")).sendKeys(" description");
           
           driver.findElement(By.name("travel_description")).sendKeys("Travel Description");
           
           
           
           
           
           
           
	        }
	}
	

}
