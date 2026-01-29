package Test11;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import Test11.FormTestData;

public class TitleMultipleCurated {

    public static void main(String[] args) throws Exception {

        // ================= SETUP =================
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://crmdev.miftah.ai/dashboard/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // ================= LOGIN =================
        driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("password")).sendKeys("Jaggu@89");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        // ================= OPEN MODULE =================
        By miftahBtn = By.xpath("//span[normalize-space()='Miftah Recommendations']/ancestor::button");
        wait.until(ExpectedConditions.elementToBeClickable(miftahBtn)).click();
        Thread.sleep(3000);

        // ================= LOAD TEST DATA =================
        String[] titles    = FormTestData.TITLES;
        String[] ratings   = FormTestData.RATINGS;
        String[] leadTimes = FormTestData.LEAD_TIMES;

        int maxRuns = Math.max(titles.length, Math.max(ratings.length, leadTimes.length));

        // ================= MAIN LOOP =================
        for (int i = 0; i < maxRuns; i++) {

            String title    = titles[i % titles.length];
            String rating   = ratings[i % ratings.length];
            String leadTime = leadTimes[i % leadTimes.length];

            System.out.println("======================================");
            System.out.println("RUN #: " + (i + 1));
            System.out.println("Title    = " + title);
            System.out.println("Rating   = " + rating);
            System.out.println("LeadTime = " + leadTime);

            // ================= OPEN FORM =================
            driver.findElement(By.xpath("//button[contains(@class,'group p-2')]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//p[text()='Curated Experiences']/following-sibling::p")).click();
            Thread.sleep(2000);

            // ================= FILL BASIC FIELDS =================
            // Title
            WebElement titleBox = driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]"));
            titleBox.clear();
            titleBox.sendKeys(title);

            // Rating
           WebElement ratingBox = driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]"));
            ratingBox.clear();
            ratingBox.sendKeys(rating);
             
             // Lead Time
            WebElement leadBox = driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]"));
            leadBox.clear();
            leadBox.sendKeys(leadTime);
            
            
            
            
            

            // Currency
            Select currency = new Select(driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
            currency.selectByIndex(12);

            // Description
            driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[1]"))
                  .sendKeys("Description for " + title);

            // Location
            driver.findElement(By.xpath("//input[@placeholder='Enter location']")).sendKeys("Dubai");
            driver.findElement(By.xpath("//input[@placeholder='Enter city']")).sendKeys("Dubai");
            driver.findElement(By.xpath("//input[@placeholder='contact@example.com']")).sendKeys("jagadeesh7275@gmail.com");

            // Phone
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='+971']]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'+91')]"))).click();
            driver.findElement(By.xpath("(//label[normalize-space(text())='Contact Phone']/following::input)[1]")).sendKeys("9876543211");
            driver.findElement(By.name("contactPerson")).sendKeys("jagadeesh");

            // ================= IMAGE UPLOAD =================
            WebElement coverInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
            coverInput.sendKeys("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");

            WebElement additionalInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[2]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);

            File folder = new File("C:\\Users\\NS\\Desktop\\images");
            File[] files = folder.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    additionalInput.sendKeys(f.getAbsolutePath());
                    Thread.sleep(800);
                    additionalInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[2]")));
                }
            }

            // ================= OTHER FIELDS =================
            driver.findElement(By.xpath("(//label[normalize-space(text())='Map Embed URL']/following::input)[1]"))
                  .sendKeys("<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107029.2201364434!2d54.568041327437584!3d25.0745656650172!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e1!3m2!1sen!2sin!4v1769513886714!5m2!1sen!2sin\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
            driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]")).sendKeys("Directions");
            driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[3]")).sendKeys("Terms");

            WebElement metaInput = driver.findElement(By.xpath("//input[@placeholder='Enter meta data (comma-separated) and press Enter']"));
            metaInput.sendKeys("selenium,automation");
            driver.findElement(By.xpath("//button[normalize-space()='Add']")).click();

            driver.findElement(By.xpath("(//textarea[@placeholder='Enter experience description'])[2]"))
                  .sendKeys("Experience for " + title);

            driver.findElement(By.xpath("//input[@placeholder='YYYY-MM-DD']")).sendKeys("30-01-2026");
            driver.findElement(By.xpath("//input[@placeholder='e.g., 60']")).sendKeys("60");
            driver.findElement(By.xpath("//input[@placeholder='e.g., 120']")).sendKeys("120");
            driver.findElement(By.xpath("//input[@placeholder='e.g., Smart Casual']")).sendKeys("Smart Casual");
            driver.findElement(By.xpath("//input[@placeholder='HH:mm']")).sendKeys("09:00");
            driver.findElement(By.xpath("(//label[normalize-space(text())='Total Guests']/following::input)[1]")).sendKeys("40");

            driver.findElement(By.xpath("//button[normalize-space(text())='Add Time Slot']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("(//input[@placeholder='HH:mm'])[2]")).sendKeys("12:00");
            driver.findElement(By.xpath("(//input[@placeholder='e.g. 20'])[2]")).sendKeys("50");

            driver.findElement(By.xpath("//textarea[@placeholder='Enter important notes']")).sendKeys("Important notes");
            driver.findElement(By.name("cancellation_policy")).sendKeys("No cancellation");

            WebElement expInput = driver.findElement(By.xpath("(//input[contains(@class,'flex-1 px-3')])[2]"));
            expInput.sendKeys("Hotel pickup");
            driver.findElement(By.xpath("(//input[contains(@class,'flex-1 px-3')]/following-sibling::button)[2]")).click();

            driver.findElement(By.xpath("//input[@placeholder='e.g., 2300']")).sendKeys("2000");
            driver.findElement(By.xpath("//input[@placeholder='e.g., Premium Wine']")).sendKeys("Premium");
            driver.findElement(By.xpath("//textarea[@placeholder='e.g., Best premium wine selection']")).sendKeys("Premium desc");

            // ================= SUBMIT =================
            By submitBtn = By.xpath("//button[normalize-space(text())='Create Recommendation']");
            By closeBtn  = By.xpath("//button[@aria-label='Close form']");

            try {
                driver.findElement(submitBtn).click();
                Thread.sleep(4000);

                if (driver.findElements(submitBtn).size() > 0) {
                    throw new Exception("Still on form");
                }

                System.out.println("✅ SUCCESS for: " + title);

            } catch (Exception e) {

                System.out.println("❌ FAILED for: " + title);
                wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
                Thread.sleep(2000);
            }
        }

        System.out.println("🎉 ALL TEST DATA COMPLETED");
        driver.quit();
    }
}
