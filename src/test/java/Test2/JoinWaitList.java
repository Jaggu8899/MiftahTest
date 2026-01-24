package Test2;


import org.openqa.selenium.By;

public class JoinWaitList extends BaseTest {

    public void joinWaitListForm() throws Exception {
        driver.get("https://dev.miftah.ai/landing/");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//button[normalize-space()='Join the Waitlist']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@placeholder='Enter your full name']")).sendKeys("Jagadeesh");
        Thread.sleep(2000);
        driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Enter your phone number']")).sendKeys("7095297275");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[normalize-space()='Join Waitlist']")).click();
        Thread.sleep(5000);
    }

    public static void main(String[] args) throws Exception {
        JoinWaitList test = new JoinWaitList();
        test.setUp();          // from BaseTest
        test.joinWaitListForm();
        test.tearDown();       // from BaseTest
    }
}




