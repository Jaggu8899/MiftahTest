package pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By joinWaitlistBtn = By.xpath("//button[normalize-space()='Join the Waitlist']");
    
      private By loginBtn = By.xpath("//button[.//span[normalize-space()='Login']]");
    private By bePartnerBtn = By.xpath("//button[contains(text(),'Be a Partner')]"); 

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickJoinWaitlist() {
        driver.findElement(joinWaitlistBtn).click();
    }

     public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public void clickBePartner() {
        driver.findElement(bePartnerBtn).click();
    }  
}

