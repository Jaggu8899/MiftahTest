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

public class PrivateChef {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://crmdev.miftah.ai/dashboard/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Login
        driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Jaggu@89");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        // Navigate to Miftah Masters
        wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space(text())='Miftah Masters']")))
                .click();

        Thread.sleep(3000);
        // Click first button after paragraph
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[normalize-space(text())='Add Service Provider']"))).click();

        // Click first group button
        wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'group bg-gray-50')])[1]")))
                .click();

        // Fill in basic info
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")))
                .sendKeys("Automation");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("textarea"))).sendKeys("Description");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")))
                .sendKeys("1000");

        // Select price type
        Select priceType = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//select[contains(@class,'w-full px-3')])[1]"))));
        priceType.selectByVisibleText("PER_EVENT");

        // Select currency
        Select currency = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//select[contains(@class,'w-full px-3')])[2]"))));
        currency.selectByVisibleText("EUR");

        // Phone numbers
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='+971']]")))
                .click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'+91')]"))).click();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full h-10')])[1]")))
                .sendKeys("98765432111");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'])[2]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'+91')])[last()]"))).click();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full h-10')])[2]")))
                .sendKeys("9876543211");

        // Work type
        Select select = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//select[contains(@class,'w-full px-3')])[3]"))));
        select.selectByVisibleText("REMOTE");

        // Address
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")))
                .sendKeys("H.No 3-5-789, Road No 2, Kukatpally");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("(//label[contains(.,'City *')]/following::input)[1]")))
                .sendKeys("Dubai");

        // Lead time / duration
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@min='0'])[2]"))).sendKeys("6");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@min='0'])[3]"))).sendKeys("2.5");

        // Cuisines & dietary options
        WebElement cuisineInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter cuisines (comma separated)']")));
        cuisineInput.sendKeys("selenium,auto,mation");
        cuisineInput.sendKeys(Keys.ENTER);

        WebElement dietaryInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Enter dietary options (comma separated)']")));
        dietaryInput.sendKeys("mation,auto,selenium");
        dietaryInput.sendKeys(Keys.ENTER);

        // Servings & preferences
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@min='1']"))).sendKeys("6");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[contains(@class,'w-4 h-4')])[2]")))
                .click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='radio'])[3]"))).click();

        // Upload cover image
        WebElement coverInput = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
        coverInput.sendKeys("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");

        // Additional images
        File folder = new File("C:\\Users\\NS\\Desktop\\images");
        if (!folder.exists() || folder.listFiles() == null || folder.listFiles().length == 0) {
            System.out.println("No files found!");
            driver.quit();
            return;
        }

        WebElement additionalInput = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[last()]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);

        StringBuilder allFiles = new StringBuilder();
        for (File f : folder.listFiles()) {
            if (f.isFile()) {
                allFiles.append(f.getAbsolutePath()).append("\n");
            }
        }
        if (allFiles.length() > 0)
            allFiles.setLength(allFiles.length() - 1);
        additionalInput.sendKeys(allFiles.toString());
        Thread.sleep(3000);
        // Submit form
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Create Service Provider')]")))
                .click();
    }
}
