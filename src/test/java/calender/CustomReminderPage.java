package calender;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class CustomReminderPage {

    WebDriver driver;

    public CustomReminderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Login
    By email = By.id("email");
    By password = By.id("password");
    By loginBtn = By.xpath("//button[text()='Login']");

    // Calendar
    By calendarBtn = By.xpath("//button[.//span[text()='Calendar']]");

    // Add Event
    By addEventBtn = By.xpath("//button[.//span[normalize-space()='Add Event']]");
    By eventName = By.xpath("//input[@placeholder='e.g., Dinner at Nobu']");
    
    // Date fields
    By startDate = By.xpath("//label[contains(text(),'Start Date')]/following::input[1]");
    By endDate = By.xpath("//label[contains(text(),'End Date')]/following::input[1]");

    // Time fields
    By startTime = By.xpath("//input[@type='time']");
    By endTime = By.xpath("//label[text()='End Time']/following::input[@type='time'][1]");

    // Event Type
    By eventTypeDropdown = By.xpath("//label[contains(text(),'Event Type')]/following::select[1]");

    // Map
    By selectMapBtn = By.xpath("//button[@title='Select location on map']");
    By useLocationBtn = By.xpath("//button[contains(text(),'Use this location')]");

    // Reminder
    By addNotification = By.xpath("//span[text()='Add notification']");
    By reminderValue = By.xpath("//input[@placeholder='30']");
    By reminderUnit = By.xpath("(//select)[last()]");

    By saveBtn = By.xpath("//button[normalize-space()='Save']");
    By searchEvent = By.xpath("//input[@placeholder='Search luxury experiences and events...']");

    // ******** METHODS (Reusable) ********

    public void login(String userEmail, String userPassword) {
        driver.findElement(email).sendKeys(userEmail);
        driver.findElement(password).sendKeys(userPassword);
        driver.findElement(loginBtn).click();
    }

    public void openCalendar() {
        driver.findElement(calendarBtn).click();
    }

    public void clickAddEvent() {
        driver.findElement(addEventBtn).click();
    }

    public void enterEventName(String name) {
        driver.findElement(eventName).sendKeys(name);
    }

    public void setDates(String start, String end) {
        WebElement s = driver.findElement(startDate);
        s.click();
        s.clear();
        s.sendKeys(start);

        WebElement e = driver.findElement(endDate);
        e.click();
        e.clear();
        e.sendKeys(end);
    }

    public void setTimes(String st, String et) {
        driver.findElement(startTime).sendKeys(st);
        driver.findElement(endTime).sendKeys(et);
    }

    public void selectEventType(String type) {
        WebElement dropdown = driver.findElement(eventTypeDropdown);
        Select select = new Select(dropdown);
        select.selectByVisibleText(type);
    }

    public void chooseMapLocation() {
        driver.findElement(selectMapBtn).click();
        driver.findElement(useLocationBtn).click();
    }

    public void addReminder(String value, String unit) {
        driver.findElement(addNotification).click();

        WebElement reminder = driver.findElement(reminderValue);
        reminder.clear();
        reminder.sendKeys(value);

        Select unitSelect = new Select(driver.findElement(reminderUnit));
        unitSelect.selectByVisibleText(unit);
    }

    public void saveEvent() {
        driver.findElement(saveBtn).click();
    }

    public void searchEventByName(String text) {
        driver.findElement(searchEvent).sendKeys(text);
    }
}

