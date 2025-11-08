package Test1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WaitList {
    public static void main(String[] args) throws Exception {
        
       WebDriverManager.chromedriver().setup();
       WebDriver driver = new ChromeDriver();
       driver.get("https://dev.miftah.ai/");
       driver.manage().window().maximize();
       Thread.sleep(5000);
       driver.findElement(By.xpath("//button[normalize-space()='Join the Waitlist']")).click();
       Thread.sleep(5000);
       driver.findElement(By.xpath("//input[@placeholder='Enter your full name']")).sendKeys("Jagadeesh");
       Thread.sleep(3000);
       driver.findElement(By.id("email")).sendKeys("jagadeesh7275@gmail.com");
       Thread.sleep(3000);
       driver.findElement(By.xpath("//input[@placeholder='Enter your phone number']")).sendKeys("7095297275");
       Thread.sleep(3000);
       driver.findElement(By.xpath("//button[normalize-space()='Join Waitlist']")).click();
       Thread.sleep(10000);
       driver.quit();
    }
}