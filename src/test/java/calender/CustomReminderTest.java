package calender;



import org.testng.annotations.Test;
import calender.CustomReminderPage;

public class CustomReminderTest extends BaseTest {

    @Test
    public void verifyCustomReminderCreation() throws InterruptedException {

        CustomReminderPage cr = new CustomReminderPage(driver);

        // LOGIN
        cr.login("jagadeeswara89@gmail.com", "Jaggu@89");
        Thread.sleep(3000);

        // NAVIGATE TO CALENDAR
        cr.openCalendar();
        Thread.sleep(2000);

        // ADD EVENT
        cr.clickAddEvent();
        Thread.sleep(2000);

        cr.enterEventName("Night Clubs");

        // SET DATE
        cr.setDates("9-12-2025", "9-12-2025");
        Thread.sleep(1000);

        // SET TIME
        cr.setTimes("11:00", "10:30");

        // EVENT TYPE
        cr.selectEventType("Dining");
        Thread.sleep(1000);

        // LOCATION
        cr.chooseMapLocation();
        Thread.sleep(1000);

        // REMINDER
        cr.addReminder("44", "minutes");
        Thread.sleep(1000);

        // SAVE
        cr.saveEvent();
        Thread.sleep(3000);

        // SEARCH
        cr.searchEventByName("Ritz Carlton");
        Thread.sleep(2000);
    }
}

