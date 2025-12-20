package Test6;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class maps {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://dev.miftah.ai/");
    }

    @Test
    public void testMaps() throws Exception {

        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[text()='Login']")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[.//span[normalize-space()='Calendar']]")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[normalize-space()='Visakhapatnam, India']")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[normalize-space()='The Dubai Mall, Dubai']")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button[normalize-space()='Use this location']")).click();
        Thread.sleep(3000);
        // ❌ INTENTIONALLY FAILED STEP (time not changing)
        driver.findElement(By.xpath("//button[text()='Non_existing_time']")).click();
        
        
   
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (ITestResult.FAILURE == result.getStatus()) {

            // timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // screenshot file name
            String screenshotPath = System.getProperty("user.dir") + "/Screenshots/" 
                    + result.getName() + "_" + timestamp + ".png";

            // capture screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));

            System.out.println("📌 Screenshot captured: " + screenshotPath);
        }

       
    }
}

		
		
		
		
		
		
		
		
		
		
		
		
	


