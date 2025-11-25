package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class JoinWaitlistPage {

    WebDriver driver;
    WebDriverWait wait;

    public JoinWaitlistPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    By joinBtn = By.xpath("//button[normalize-space()='Join the Waitlist']");
    By fullName = By.xpath("//input[@placeholder='Enter your full name']");
    By email = By.id("email");
    By phone = By.xpath("//input[@placeholder='Enter your phone number']");
    By submit = By.xpath("//button[normalize-space()='Join Waitlist']");

    public void clickJoin() {
        wait.until(ExpectedConditions.elementToBeClickable(joinBtn)).click();
        
    }
    public void slow() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

  public void enterDetails(String name, String mail, String number) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullName)).sendKeys(name);
        slow();
        driver.findElement(email).sendKeys(mail);
        slow();
        
        slow();
        driver.findElement(phone).sendKeys(number);
        slow();
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }
}

