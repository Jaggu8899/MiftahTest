package tests;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.JoinWaitlistPage;

public class WaitlistTest extends BaseTest {

    @Test
    public void joinWaitlistForm() {
    	
        JoinWaitlistPage page = new JoinWaitlistPage(driver);
        page.clickJoin();
        page.enterDetails("Jagadeesh", "jagadeesh7275@gmail.com", "7095297275");
        page.clickSubmit();
    }
}
