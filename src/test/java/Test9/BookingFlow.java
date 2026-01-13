package Test9;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BookingFlow {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void bookExperienceAndVerifyInCRM() {

        /* ================= USER PORTAL ================= */

        driver.get("https://dev.miftah.ai/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                .sendKeys("jagadeeswara89@gmail.com");
        driver.findElement(By.id("password"))
                .sendKeys("Jaggu@89");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Miftah Recommends']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Curated Experiences')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[contains(@alt,'Cauto')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Book Now']"))).click();

        /* ================= DATE (FIXED) ================= */

        String bookingDate = "2026-01-28";
        validateDate(bookingDate);

        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@type='date']")));
        dateInput.sendKeys(bookingDate);

        /* ================= TIME ================= */

        WebElement timeSlot = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(text(),'available') and not(contains(text(),'0 available'))]")));
        timeSlot.click();

        /* ================= GUESTS ================= */

        int guests = 5;
        validateGuests(guests);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[normalize-space()='5 Guests']"))).click();

        /* ================= CONTINUE ================= */

        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Continue']")));

        if (!continueBtn.isEnabled()) {
            throw new RuntimeException("Continue button disabled due to invalid input");
        }
        continueBtn.click();

        /* ================= CONFIRM BOOKING ================= */

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Confirm Booking']"))).click();

        /* ================= CRM ================= */

        driver.get("https://crmdev.miftah.ai/dashboard/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                .sendKeys("jagadeeswara89@gmail.com");
        driver.findElement(By.id("password"))
                .sendKeys("Jaggu@89");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Service Requests']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[normalize-space()='Cauto']"))).click();

        WebElement verifyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Verify & Confirm']")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", verifyBtn);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[starts-with(normalize-space(),'Confirmed')]"))).click();
    }

    /* ================= VALIDATIONS ================= */

    private void validateDate(String date) {
        if (date.compareTo("2026-01-13") < 0) {
            throw new RuntimeException("Invalid date selected: " + date);
        }
    }

    private void validateGuests(int guests) {
        if (guests <= 1 || guests > 50) {
            throw new RuntimeException("Invalid guest count: " + guests);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
