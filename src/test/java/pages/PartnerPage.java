package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PartnerPage {
    private WebDriver driver;

    private By nameField = By.xpath("//input[@placeholder='Enter your full name']");
    private By emailField = By.xpath("//input[@placeholder='Enter your email']");
    private By businessField = By.xpath("//input[@placeholder='Enter your business name']");
    private By submitBtn = By.xpath("//button[contains(text(),'Submit')]");

    public PartnerPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillPartnerForm(String name, String email, String business) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(businessField).sendKeys(business);
        driver.findElement(submitBtn).click();
    }
}
