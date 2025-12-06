package Test8;

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
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MapsTest {

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

        driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("password")).sendKeys("Jaggu@89");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Login']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[.//span[normalize-space()='Calendar']]")).click();
        Thread.sleep(2000);
     // ---- STORE OLD LOCATION BEFORE CHANGE ----
        String oldLocation =  driver.findElement(By.xpath("//button[normalize-space()='Visakhapatnam, India']")).getText();

        
        driver.findElement(By.xpath("//button[normalize-space()='Visakhapatnam, India']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[normalize-space()='The Dubai Mall, Dubai']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[normalize-space()='Use this location']")).click();
        Thread.sleep(3000);

        // ---- GET NEW LOCATION AFTER CHANGE ----
        String newLocation =  driver.findElement(By.xpath("//button[normalize-space()='The Dubai Mall, Dubai']")).getText();

        // ---- VALIDATION ----
        if (oldLocation.equals(newLocation)) {
            // ❌ FAIL TEST so screenshot will be captured
        	Assert.fail("Location NOT changed! Screenshot required.");
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (ITestResult.FAILURE == result.getStatus()) {

            // timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // screenshot path
            String screenshotPath = System.getProperty("user.dir") + "/Screenshots/"
                    + result.getName() + "_" + timestamp + ".png";

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));

            System.out.println("📸 Screenshot captured: " + screenshotPath);
        }

        driver.quit();
    }
}



