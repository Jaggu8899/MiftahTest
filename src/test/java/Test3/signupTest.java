package Test3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class signupTest {
	
	public static void main(String[] args) throws Exception {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://dev.miftah.ai/");
		Thread.sleep(5000);
	    driver.findElement(By.xpath("//button[.//span[normalize-space()='Login']]")).click();
	    Thread.sleep(5000);
	    driver.findElement(By.xpath("//button[normalize-space()='Sign up']")).click();
	    driver.findElement(By.xpath("//input[@placeholder='Enter your full name']")).sendKeys("Jagadeesh");
	    Thread.sleep(3000);
	    driver.findElement(By.id("email")).sendKeys("jagadeesh7275@gmail.com");
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//input[@placeholder='Enter your phone number']")).sendKeys("7095297275");
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
	    Thread.sleep(3000);
	    By createAccountBtn = By.xpath("//button[normalize-space()='Create Account']");
	    driver.findElement(createAccountBtn).click();
	    Thread.sleep(3000);
        driver.quit();

		
	}

}
