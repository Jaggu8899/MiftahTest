package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.signupPage;

public class signupTest extends BaseTest {
	
	@Test
	
	 public void signuplistForm() {
		
		signupPage page = new signupPage(driver);
		page.Login();
		page.signupin();
		page.enterDetails("jagadeesh", "jagadeesh7275@gmail.com", "7095297275", "Jaggu@123");
		page.clickSubmit();
		
		
		
		
		
		
		 
	 }

}
