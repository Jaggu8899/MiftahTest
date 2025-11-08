package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JoinWaitlistPage {
    private WebDriver driver;

    private By nameField = By.xpath("//input[@placeholder='Enter your full name']");
    private By emailField = By.xpath("//input[@placeholder='Enter your email']");
    private By phoneField = By.xpath("//input[@placeholder='Enter your phone number']");
    private By submitBtn = By.xpath("//button[contains(text(),'Submit')]");

    public JoinWaitlistPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillJoinWaitlistForm(String name, String email, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(submitBtn).click();
    }
}

