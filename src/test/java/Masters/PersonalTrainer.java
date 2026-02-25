package Test12;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PersonalTrainer {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://crmdev.miftah.ai/dashboard/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("jagadeeswara89@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Jaggu@89");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        // Navigate to Miftah Masters
        wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space(text())='Miftah Masters']")))
                .click();

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[normalize-space(text())='Add Service Provider']"))).click();

        // Select Personal Trainer
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("(//h4[contains(@class,'text-sm font-semibold')])[2]"))).click();

        // Basic Info
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")))
                .sendKeys("Automation");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("textarea"))).sendKeys("Description");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")))
                .sendKeys("1000");

        Select priceType = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//select[contains(@class,'w-full px-3')])[1]"))));
        priceType.selectByVisibleText("PER_EVENT");

        Select currency = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//select[contains(@class,'w-full px-3')])[2]"))));
        currency.selectByVisibleText("EUR");

        // Phone Numbers
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='+971']]")))
                .click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'+91')]"))).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full h-10')])[1]")))
                .sendKeys("9876543211");

        // Click second dropdown
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'])[2]"))).click();

        // Select +91 from LAST visible dropdown only
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'+91')])[last()]"))).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full h-10')])[2]")))
                .sendKeys("9876543211");

        Select select = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//select[contains(@class,'w-full px-3')])[3]"))));
        select.selectByVisibleText("REMOTE");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")))
                .sendKeys("H.No 3-5-789, Road No 2, Kukatpally");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("(//label[contains(.,'City *')]/following::input)[1]")))
                .sendKeys("Dubai");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@min='0'])[2]"))).sendKeys("6");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@min='0'])[3]"))).sendKeys("2.5");

        WebElement trainingInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Enter training types (comma separated)']")));
        trainingInput.sendKeys("selenium,auto,mation");
        trainingInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]")))
                .sendKeys("Certification Details");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@min='1']"))).sendKeys("6");

        WebElement levelsInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter levels (comma separated)']")));
        levelsInput.sendKeys("selenium,auto,mation");
        levelsInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[contains(@class,'w-4 h-4')])[2]")))
                .click();

        // Upload cover image
        WebElement coverInput = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
        coverInput.sendKeys("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");

        // Additional images
        File folder = new File("C:\\Users\\NS\\Desktop\\images");

        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                WebElement additionalInput = wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[last()]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);

                StringBuilder allFiles = new StringBuilder();
                for (File f : files) {
                    if (f.isFile()) {
                        allFiles.append(f.getAbsolutePath()).append("\n");
                    }
                }
                if (allFiles.length() > 0) {
                    allFiles.setLength(allFiles.length() - 1);
                }
                additionalInput.sendKeys(allFiles.toString());
            }
        }

        // Submit
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Create Service Provider')]")))
                .click();
        Thread.sleep(2000);

        // Optional Back button if still on page
        if (driver.findElements(By.xpath("//button[contains(@class,'p-2 hover:bg-gray-100')]")).size() > 0) {
            driver.findElement(By.xpath("//button[contains(@class,'p-2 hover:bg-gray-100')]")).click();
        }
    }
}