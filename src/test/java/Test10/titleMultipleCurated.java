package Test10;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class titleMultipleCurated {

    public static void main(String[] args) throws Exception {

        // 1️⃣ Setup driver
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://crmdev.miftah.ai/dashboard/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 2️⃣ Login
        driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("password")).sendKeys("Jaggu@89");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        // 3️⃣ Click Miftah Recommendations
        By miftahBtn = By.xpath("//span[normalize-space()='Miftah Recommendations']/ancestor::button");
        wait.until(ExpectedConditions.elementToBeClickable(miftahBtn)).click();
        Thread.sleep(3000);

        // 4️⃣ Prepare titles
        String[] titles = {"","   ", "@#$%^", "<script>alert(1)</script>"," Dubai"," Dubai","123456","Dubai\nMall","Dubai\tMall","Dubai@@@###","asd123@@@###$$$","CauDubai","@","A","https://google.com","C:\test\file.txt"}; // Add as many as you want

        for (String title : titles) {

            // 5️⃣ Click + → Curated Experience
            driver.findElement(By.xpath("//button[contains(@class,'group p-2')]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//p[text()='Curated Experiences']/following-sibling::p")).click();
            Thread.sleep(2000);

            // 6️⃣ Fill form (use title from loop)
            driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")).clear();
            driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")).sendKeys(title);

            driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")).sendKeys("5");
            driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")).sendKeys("1.5");

            Select currency = new Select(driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
            currency.selectByIndex(12);  // INR

            driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[1]")).sendKeys("Description for " + title);

            driver.findElement(By.xpath("//input[@placeholder='Enter location']")).sendKeys("Dubai");
            driver.findElement(By.xpath("//input[@placeholder='Enter city']")).sendKeys("Dubai");
            driver.findElement(By.xpath("//input[@placeholder='contact@example.com']")).sendKeys("jagadeesh7275@gmail.com");

            // Country code
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='+971']]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'+91')]"))).click();
            driver.findElement(By.xpath("(//label[normalize-space(text())='Contact Phone']/following::input)[1]")).sendKeys("9876543211");
            driver.findElement(By.name("contactPerson")).sendKeys("jagadeesh");

            // Upload cover image
            WebElement coverInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
            coverInput.sendKeys("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");

            // Upload additional images
            WebElement additionalInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[2]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);
            File folder = new File("C:\\Users\\NS\\Desktop\\images");
            File[] files = folder.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    additionalInput.sendKeys(f.getAbsolutePath());
                    Thread.sleep(1000);
                    // Re-locate input
                    additionalInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[2]")));
                }
            }

            // Map embed, textareas, meta data, etc.
            driver.findElement(By.xpath("(//label[normalize-space(text())='Map Embed URL']/following::input)[1]"))
                    .sendKeys("<iframe src=\"https://www.google.com/maps/embed?...\"></iframe>");
            driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]")).sendKeys("Directions");
            driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[3]")).sendKeys("Terms and conditions");

            // Meta data
            WebElement metaInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter meta data (comma-separated) and press Enter']")));
            metaInput.clear();
            metaInput.sendKeys("selenium, automation, testing");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add']"))).click();

            // Experience description and other fields
            driver.findElement(By.xpath("(//textarea[@placeholder='Enter experience description'])[2]")).sendKeys("Curated description for " + title);
            driver.findElement(By.xpath("//input[@placeholder='YYYY-MM-DD']")).sendKeys("30-01-2026");
            driver.findElement(By.xpath("//input[@placeholder='e.g., 60']")).sendKeys("60");
            driver.findElement(By.xpath("//input[@placeholder='e.g., 120']")).sendKeys("120");
            driver.findElement(By.xpath("//input[@placeholder='e.g., Smart Casual']")).sendKeys("Smart casual");
            driver.findElement(By.xpath("//input[@placeholder='HH:mm']")).sendKeys("9:00");
            driver.findElement(By.xpath("(//label[normalize-space(text())='Total Guests']/following::input)[1]")).sendKeys("40");

            // Time slot
            driver.findElement(By.xpath("//button[normalize-space(text())='Add Time Slot']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("(//input[@placeholder='HH:mm'])[2]")).sendKeys("12:00");
            driver.findElement(By.xpath("(//input[@placeholder='e.g. 20'])[2]")).sendKeys("50");

            driver.findElement(By.xpath("//textarea[@placeholder='Enter important notes']")).sendKeys("importance notes");
            driver.findElement(By.name("cancellation_policy")).sendKeys("Cancellation policy");

            // Experience includes
            WebElement expInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[contains(@class,'flex-1 px-3')])[2]")));
            expInput.clear();
            expInput.sendKeys("Hotel pickup, Guide included, Free lunch");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[contains(@class,'flex-1 px-3')]/following-sibling::button)[2]"))).click();

            driver.findElement(By.xpath("//input[@placeholder='e.g., 2300']")).sendKeys("2000");
            driver.findElement(By.xpath("//input[@placeholder='e.g., Premium Wine']")).sendKeys("Premium wine");
            driver.findElement(By.xpath("//textarea[@placeholder='e.g., Best premium wine selection']")).sendKeys("Best premium wine Description");
            By submitBtn = By.xpath("//button[normalize-space(text())='Create Recommendation']");
            By closeBtn  = By.xpath("//button[@aria-label='Close form']");
            
            
            
            

            try {
                // Try to click Submit
                driver.findElement(submitBtn).click();
                System.out.println("Submit clicked for title: " + title);

                // Small wait to see if page accepts it
                Thread.sleep(5000);

                // OPTIONAL: If form still visible, treat as fail
                if (driver.findElements(submitBtn).size() > 0) {
                    throw new Exception("Submit did not go through");
                }

                System.out.println("✅ Submit success for title: " + title);

            } catch (Exception e) {

                System.out.println("❌ Submit failed for title: " + title);
                System.out.println("⚠️ Clicking CLOSE button...");

                // Click Close button
                WebElement closeBtnEl = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Close form']")
                    )
                );
                closeBtnEl.click();

                Thread.sleep(2000); // wait for form to close
            }

            
        }

        System.out.println("All curated experiences created successfully!");
        driver.quit();
    }
}

