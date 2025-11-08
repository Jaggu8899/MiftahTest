package tests;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.HomePage;
import pages.JoinWaitlistPage;
import pages.LoginPage;
import pages.PartnerPage;

public class MiftahTest extends BaseTest {

    @Test
    public void fullAppFlowTest() throws InterruptedException {
        HomePage home = new HomePage(driver);

        // Step 1: Join Waitlist
        home.clickJoinWaitlist();
        Thread.sleep(2000);
        JoinWaitlistPage joinPage = new JoinWaitlistPage(driver);
        joinPage.fillJoinWaitlistForm("Jagadeeswara Rao", "test@example.com", "9876543210");

        // Step 2: Login from top
        Thread.sleep(2000);
        home.clickLogin();
        Thread.sleep(2000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("test@example.com", "Test@1234");

        // Step 3: Scroll down → Be a Partner
        Thread.sleep(2000);
        home.clickBePartner();
        Thread.sleep(2000);
        PartnerPage partnerPage = new PartnerPage(driver);
        partnerPage.fillPartnerForm("Jagadeeswara Rao", "partner@example.com", "My Business");

        System.out.println("✅ Full app flow executed successfully!");
    }
}

