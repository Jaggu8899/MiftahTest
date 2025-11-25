package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    
    public void slow() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    By loginBtn = By.xpath("//button[.//span[normalize-space()='Login']]");
    By email = By.id("email");
    By password = By.id("password");
    By signIn = By.xpath("//button[normalize-space()='Sign In']");

    public void login(String mail, String pass) {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        slow();
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(mail);
        slow();
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
        slow();
        wait.until(ExpectedConditions.elementToBeClickable(signIn)).click();
        slow();
    }
}
