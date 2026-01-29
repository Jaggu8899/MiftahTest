package Test12;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PersonalTrainer {
	
	
	public static void main(String[] args) throws Exception {
		
		   WebDriverManager.chromedriver().setup();
		   ChromeDriver driver= new ChromeDriver();
		   driver.get("https://crmdev.miftah.ai/dashboard/");
	       driver.manage().window().maximize();
	       driver.findElement(By.id("email")).sendKeys("jagadeeswara89@gmail.com");
	       Thread.sleep(3000);
	       driver.findElement(By.id("password")).sendKeys("Jaggu@89");
	       driver.findElement(By.xpath("//button[text()='Login']")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//span[normalize-space(text())='Miftah Masters']")).click();
	       Thread.sleep(3000);
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("//button[normalize-space(text())='Add Service Provider']")).click();
	       driver.findElement(By.xpath("(//h4[contains(@class,'text-sm font-semibold')])[2]")).click();
	       driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[1]")).sendKeys("Automation");
	       Thread.sleep(3000);
	       driver.findElement(By.tagName("textarea")).sendKeys("Description");
	       Thread.sleep(3000);
	       driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[2]")).sendKeys("1000");
	       Thread.sleep(3000);
	       Select priceType = new Select(driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[1]")));
	       priceType.selectByVisibleText("PER_EVENT");
	       Thread.sleep(3000);
	       Select currency = new Select(driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[2]")));
	       currency.selectByVisibleText("EUR");
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	       wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='+971']]"))).click();
           wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'+91')]"))).click();
           driver.findElement(By.xpath("(//input[contains(@class,'w-full h-10')])[1]")).sendKeys("98765432111");
           Thread.sleep(3000);
           
        // Click second dropdown
           wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("(//button[@type='button'])[2]")
           )).click();

           // Select +91 from LAST visible dropdown only
           wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("(//*[contains(text(),'+91')])[last()]")
           )).click();

           driver.findElement(By.xpath("(//input[contains(@class,'w-full h-10')])[2]")).sendKeys("9876543211");
           Thread.sleep(3000);
           Select select = new Select(driver.findElement(By.xpath("(//select[contains(@class,'w-full px-3')])[3]")));
           select.selectByVisibleText("REMOTE");
           Thread.sleep(3000);
           driver.findElement(By.xpath("(//input[contains(@class,'w-full px-3')])[3]")).sendKeys("H.No 3-5-789, Road No 2, Kukatpally");
           
           
           Thread.sleep(3000);
           driver.findElement(By.xpath("(//label[contains(.,'City *')]/following::input)[1]")).sendKeys("Dubai");
           Thread.sleep(3000);
           driver.findElement(By.xpath("(//input[@min='0'])[2]")).sendKeys("6");
           driver.findElement(By.xpath("(//input[@min='0'])[3]")).sendKeys("2.5");
           Thread.sleep(3000);
          WebElement trainingInput = driver.findElement(By.xpath("//input[@placeholder='Enter training types (comma separated)']"));
         trainingInput.sendKeys("selenium,auto,mation");
         trainingInput.sendKeys(Keys.ENTER);
         Thread.sleep(3000);
         driver.findElement(By.xpath("(//textarea[contains(@class,'w-full px-3')])[2]")).sendKeys("Certification Details");
         Thread.sleep(3000);
         driver.findElement(By.xpath("//input[@min='1']")).sendKeys("6");
         Thread.sleep(3000);
      
         WebElement levelsInput = driver.findElement(By.xpath("//input[@placeholder='Enter levels (comma separated)']"));
         levelsInput.sendKeys("selenium,auto,mation");
         levelsInput.sendKeys(Keys.ENTER);
         Thread.sleep(3000);
         driver.findElement(By.xpath("(//input[contains(@class,'w-4 h-4')])[2]")).click();
	
	
      // Upload cover image
         WebElement coverInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));
         ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", coverInput);
         coverInput.sendKeys("C:\\Users\\NS\\Desktop\\images\\download (3).jpg");
         Thread.sleep(3000);
         //Additional images
         File folder = new File("C:\\Users\\NS\\Desktop\\images");

         if (!folder.exists()) {
             System.out.println("Folder does not exist!");
             return;
         }

         File[] files = folder.listFiles();
         if (files == null || files.length == 0) {
             System.out.println("No files found!");
             return;
         }

         WebElement additionalInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                 By.xpath("(//input[@type='file'])[last()]")
         ));
         ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", additionalInput);

         // Build list of absolute paths separated by \n
         StringBuilder allFiles = new StringBuilder();
         for (File f : files) {
             if (f.isFile()) {
                 System.out.println("Adding file: " + f.getAbsolutePath());
                 allFiles.append(f.getAbsolutePath()).append("\n");  // ✅ newline
             }
         }

         // Remove last newline
         if (allFiles.length() > 0) {
             allFiles.setLength(allFiles.length() - 1);
         }

         additionalInput.sendKeys(allFiles.toString());

         Thread.sleep(3000);

         driver.findElement(By.xpath("//button[contains(.,'Create Service Provider')]")).click();

         
         
        }
}