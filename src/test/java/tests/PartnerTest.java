package tests;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.PartnerPage;

public class PartnerTest extends BaseTest {

    @Test
    public void partnerFormTest() {
        PartnerPage page = new PartnerPage(driver);
        page.openPage();
        page.fillForm();
        page.clickSubmit();
    }
}
