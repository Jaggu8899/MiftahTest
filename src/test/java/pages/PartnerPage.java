package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class PartnerPage {

    WebDriver driver;
    WebDriverWait wait;

    public PartnerPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    By partnerLink = By.xpath("//a[normalize-space()='Be a Partner' and @href='/be-a-partner']");
    By partnership = By.xpath("//select[option[normalize-space()='Select partnership type']]");
    By fullName = By.xpath("//input[@placeholder='Enter your full name']");
    By email = By.xpath("//input[@placeholder='Enter your email address']");
   
    By phone = By.xpath("//input[@placeholder='Enter your phone number']");
    By job = By.xpath("//input[@placeholder='Enter your job title']");
    By company = By.xpath("//input[@placeholder='Enter your company name']");
    By industry = By.xpath("//select[option[normalize-space()='Select your industry']]");
    By referral = By.xpath("//label[contains(text(),'How did you hear about us?')]/following::select[1]");
    By captcha = By.xpath("//input[@placeholder='Enter captcha']");
    By terms = By.id("accept-terms");
    By submit = By.xpath("//button[@type='submit' and contains(.,'Submit')]");

    public void openPage() {
        WebElement p = wait.until(ExpectedConditions.presenceOfElementLocated(partnerLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", p);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", p);
    }
    public void slow() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

      public void fillForm() {
    	
        new Select(driver.findElement(partnership)).selectByVisibleText("Technology Partner");
        slow();
        
        driver.findElement(fullName).sendKeys("jagadeesh");
        slow();
        driver.findElement(email).sendKeys("jagadeeswara89@gmail.com");
        slow();
        
        driver.findElement(phone).sendKeys("7095297275");
        slow();
        driver.findElement(job).sendKeys("Automation Engineer");
        slow();
        driver.findElement(company).sendKeys("Miftah Technologies");
        slow();

        new Select(driver.findElement(industry)).selectByVisibleText("Technology");
        slow();
        new Select(driver.findElement(referral)).selectByVisibleText("LinkedIn");
        slow();

        driver.findElement(captcha).sendKeys("text"); // manual captcha
        slow();
        driver.findElement(terms).click();
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }
}

