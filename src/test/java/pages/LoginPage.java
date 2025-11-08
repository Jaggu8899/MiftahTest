package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By emailField = By.xpath("//input[@placeholder='Enter your email']");
    private By passwordField = By.xpath("//input[@placeholder='Enter your password']");
    private By loginButton = By.xpath("//button[contains(text(),'Login')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
