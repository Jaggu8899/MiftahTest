package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class signupPage {
	
	 WebDriver driver;
	    WebDriverWait wait;

	    public signupPage(WebDriver driver) {
	        this.driver = driver;
	        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    }
	    
	    By login=  By.xpath("//button[.//span[normalize-space()='Login']]");
	    By signup =By.xpath("//button[normalize-space()='Sign up']");
	    By fullName = By.xpath("//input[@placeholder='Enter your full name']");
	    By email = By.id("email");
	    By phone = By.xpath("//input[@placeholder='Enter your phone number']");
	    By password = By.xpath("//input[@id='password']");
	    By createAccountBtn = By.xpath("//button[normalize-space()='Create Account']");
	    
	    public void Login() {
	        wait.until(ExpectedConditions.elementToBeClickable(login)).click();
	        
	    }
	   
	    public void signupin() {
	        wait.until(ExpectedConditions.elementToBeClickable(signup)).click();
	        
	    }
	    
	    
	    public void slow() {
	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	  public void enterDetails(String name, String mail, String number,String pass ) {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(fullName)).sendKeys(name);
	        slow();
	        driver.findElement(email).sendKeys(mail);
	        slow();
	        
	        slow();
	        driver.findElement(phone).sendKeys(number);
	        slow();
	        driver.findElement(password).sendKeys(pass);
	        slow();
	        
	        
	    }
	  public void clickSubmit() {
	        wait.until(ExpectedConditions.elementToBeClickable(createAccountBtn)).click();
	  
	
	
	
	

}
	 
}