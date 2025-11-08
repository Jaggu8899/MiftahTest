package Test3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class loginTest {

	public static void main(String[] args) throws Exception {
		
		WebDriverManager.chromedriver().setup();
	       WebDriver driver = new ChromeDriver();
	       driver.get("https://dev.miftah.ai/");
	       driver.manage().window().maximize();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//button[.//span[normalize-space()='Login']]")).click();
	       driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[normalize-space()='Sign In']")).click();
	       




		
		
		
		
		
		
		
		

	}

}
