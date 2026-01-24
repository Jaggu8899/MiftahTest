package tests;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginToMiftah() {
        LoginPage login = new LoginPage(driver);
        login.login("jagadeeswara89@gmail.com", "Jaggu@89");
    }
}
