package test13;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class therapist {

        public static void main(String[] args) throws Exception {

                WebDriverManager.chromedriver().setup();
                ChromeDriver driver = new ChromeDriver();
                driver.get("https://crmdev.miftah.ai/dashboard/");
                driver.manage().window().maximize();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                driver.findElement(By.id("email")).sendKeys("masters.therapist@gmail.com");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("Masters@1122");
                driver.findElement(By.xpath("//button[text()='Login']")).click();

                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath(
                                                "(//span[contains(@class,'font-medium sidebar-text-transition')])[1]")))
                                .click();
                wait.until(ExpectedConditions
                                .elementToBeClickable(
                                                By.xpath("//button[normalize-space(text())='Add Service Provider']")))
                                .click();

                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("(//div[contains(@class,'flex items-start')])[2]")))
                                .click();
                wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("(//input[contains(@class,'w-full px-3')])[1]")))
                                .sendKeys("therapist");
                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//button[.//span[normalize-space()='+971']]")))
                                .click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'+91')]"))).click();
                wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("(//input[contains(@class,'w-full h-10')])[1]")))
                                .sendKeys("98765432111");

                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'])[2]"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'+91')])[last()]")))
                                .click();
                wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("(//input[contains(@class,'w-full h-10')])[2]")))
                                .sendKeys("9876543211");

                // Click to open the Languages dropdown
                // Click to open the Languages dropdown
                driver.findElement(By.xpath("//span[@class='text-gray-400 text-sm']")).click();

                // Select Arabic from the dropdown - using JavascriptExecutor to avoid
                // interception
                WebElement arabicOption = wait
                                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Arabic']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arabicOption);

                WebElement countInput = driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]"));
                countInput.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                countInput.sendKeys(org.openqa.selenium.Keys.DELETE);
                countInput.sendKeys("500");

                wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("(//textarea[contains(@class,'w-full px-3')])[1]")))
                                .sendKeys("description field");

                driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]"))
                                .sendKeys("Experience field");

                // Select AED from Currency dropdown
                WebElement currencySelect = driver
                                .findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]"));
                new org.openqa.selenium.support.ui.Select(currencySelect).selectByVisibleText("AED");

                driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")).sendKeys("3");

                org.openqa.selenium.WebElement priceInput = driver.findElement(By.xpath("(//input[@min='0'])[3]"));
                priceInput.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                priceInput.sendKeys(org.openqa.selenium.Keys.DELETE);
                priceInput.sendKeys("100");

                // Price per Hour - Clear and Enter
                org.openqa.selenium.WebElement pricePerHour = driver.findElement(By.xpath("(//input[@min='0'])[4]"));
                pricePerHour.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                pricePerHour.sendKeys(org.openqa.selenium.Keys.DELETE);
                pricePerHour.sendKeys("50");

                // Bespoke Price - Clear and Enter
                org.openqa.selenium.WebElement bespokePrice = driver.findElement(By.xpath("(//input[@min='0'])[5]"));
                bespokePrice.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                bespokePrice.sendKeys(org.openqa.selenium.Keys.DELETE);
                bespokePrice.sendKeys("200");
                driver.findElement(By.xpath("(//label[contains(.,'License Number *')]/following::input)[1]"))
                                .sendKeys("5464544");
                driver.findElement(By.xpath("//input[@placeholder='Enter therapy methods (comma separated)']"))
                                .sendKeys("therapy, method", org.openqa.selenium.Keys.ENTER);
                driver.findElement(By.xpath("//input[@min='1']")).sendKeys("5");
                driver.findElement(By.xpath("(//input[contains(@class,'w-4 h-4')])[2]")).click();
                driver.findElement(By
                                .xpath("(//label[normalize-space(text())='Telehealth Platform']/following::input)[1]"))
                                .sendKeys("telehealth");

                // Select REMOTE from Location Type dropdown
                WebElement locationSelect = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//label[contains(text(),'Location Type')]/following::select[1]")));
                new org.openqa.selenium.support.ui.Select(locationSelect).selectByVisibleText("REMOTE");

                driver.findElement(By
                                .xpath("(//label[normalize-space(text())='Service Radius (km)']/following::input)[1]"))
                                .sendKeys("6");

                driver.findElement(By.xpath("//input[@placeholder='Enter cities (comma separated)']"))
                                .sendKeys("vskp,vzm", org.openqa.selenium.Keys.ENTER);

                driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[3]"))
                                .sendKeys("policy cancellation");

                driver.findElement(By.xpath("//textarea[@placeholder='Enter terms and conditions...']"))
                                .sendKeys("terms and conditions");

                // Upload cover image
                WebElement coverInput = wait.until(
                                ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
                coverInput.sendKeys("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");

                // Additional images
                File folder = new File("C:\\Users\\NS\\Desktop\\images");
                if (!folder.exists() || folder.listFiles() == null || folder.listFiles().length == 0) {
                        System.out.println("No files found!");
                        driver.quit();
                        return;
                }

                WebElement additionalInput = wait
                                .until(ExpectedConditions
                                                .presenceOfElementLocated(By.xpath("(//input[@type='file'])[last()]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);

                StringBuilder allFiles = new StringBuilder();
                for (File f : folder.listFiles()) {
                        if (f.isFile()) {
                                allFiles.append(f.getAbsolutePath()).append("\n");
                        }
                }
                if (allFiles.length() > 0)
                        allFiles.setLength(allFiles.length() - 1);
                additionalInput.sendKeys(allFiles.toString());
                Thread.sleep(5000);

                // Submit form
                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//button[contains(.,'Create Service Provider')]")))
                                .click();

        }
}
