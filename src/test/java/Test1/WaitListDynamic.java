package Test1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WaitListDynamic {
    public static void main(String[] args) throws Exception {

        // 👤 Test data: multiple users
        String[][] users = {
            {"Jagadeesh", "jagadeeswara89@gmail.com", "7095297275"},
            {"kishore", "kishore123@gmail.com", "9876543210"},
            {"Suresh", "suresh456@gmail.com", "9123456789"}
        };
        for (String[] user : users) {
            String name = user[0];
            String email = user[1];
            String phone = user[2];

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://dev.miftah.ai/landing/");
            Thread.sleep(5000);
            driver.findElement(By.xpath("//button[normalize-space()='Join the Waitlist']")).click();
            Thread.sleep(4000);

            driver.findElement(By.xpath("//input[@placeholder='Enter your full name']")).sendKeys(name);
            Thread.sleep(1000);
            driver.findElement(By.id("email")).sendKeys(email);
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@placeholder='Enter your phone number']")).sendKeys(phone);
            Thread.sleep(1000);
            driver.findElement(By.xpath("//button[normalize-space()='Join Waitlist']")).click();
            System.out.println("✅ Submitted for: " + name + " | " + email + " | " + phone);
            Thread.sleep(5000);
            driver.quit();
        }
    }
}
