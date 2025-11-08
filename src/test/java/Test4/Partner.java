package Test4;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
       driver.findElement(By.xpath("//input[@placeholder='Enter your full name']")).sendKeys("Jagadeesh");
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
       Thread.sleep(10000); 
       WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
    		    By.xpath("//button[normalize-space()='Submit']")
    		));

    		
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
    		Thread.sleep(1000);

    		
    		try {
    		    submitBtn.click();
    		    System.out.println("✅ Normal click worked!");
    		} catch (Exception e) {
    		    System.out.println("⚠️ Normal click failed, using JavaScript click...");
    		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
    		}

    		Thread.sleep(4000);
    		System.out.println("✅ Form submitted successfully (check backend response).");
       
       


       


       


        
    }
}


		

	


