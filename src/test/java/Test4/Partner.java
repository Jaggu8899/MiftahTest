package Test4;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Partner {
	
	
    public static void main(String[] args) throws Exception {
       WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://dev.miftah.ai/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        
        WebElement partnerLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[normalize-space()='Be a Partner' and @href='/be-a-partner']")
        ));

        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", partnerLink);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", partnerLink);
        Thread.sleep(5000);
       WebElement dropdown = driver.findElement(By.xpath("//select[option[normalize-space()='Select partnership type']]"));
       Select select = new Select(dropdown);
       select.selectByVisibleText("Technology Partner");
       driver.findElement(By.xpath("//input[@placeholder='Enter your full name']")).sendKeys("jagadeesh");
       Thread.sleep(1000);
       driver.findElement(By.xpath("//input[@placeholder='Enter your email address']")).sendKeys("jagadeeswara89@gmail.com");
       Thread.sleep(1000);
       
       driver.findElement(By.xpath("//input[@placeholder='Enter your phone number']")).sendKeys("7095297275");
       Thread.sleep(1000);
       driver.findElement(By.xpath("//input[@placeholder='Enter your job title']")).sendKeys("Automation Engineer");
       Thread.sleep(1000);
       driver.findElement(By.xpath("//input[@placeholder='Enter your company name']")).sendKeys("Miftah Technologies");
       Thread.sleep(1000);
       Select industry = new Select(driver.findElement(By.xpath("//select[option[normalize-space()='Select your industry']]")));
       industry.selectByVisibleText("Technology");
       Thread.sleep(1000);
       WebElement referralDropdown = driver.findElement(By.xpath("//label[contains(text(),'How did you hear about us?')]/following::select[1]"));
       Select referralSelect = new Select(referralDropdown);
       referralSelect.selectByVisibleText("LinkedIn");
       Thread.sleep(5000);
       WebElement captchaInput = driver.findElement(By.xpath("//input[@placeholder='Enter captcha']"));
       captchaInput.sendKeys("text");
       Thread.sleep(15000);
       driver.findElement(By.xpath("//input[@id='accept-terms']")).click();
       Thread.sleep(5000);
       WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("//button[@type='submit' and contains(.,'Submit')]")
           ));

           submitBtn.click();

           Thread.sleep(2000);
           driver.quit();
       
  }
}


		

	


