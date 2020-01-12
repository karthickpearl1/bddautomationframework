package steps;

import databojects.UIDataFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;
import wrappers.UIFactory;

public class StepDefinitions extends UIFactory{
	
	LoginPage loginPage;
	HomePage homePage;
	
	@Before
	public void before(Scenario sc){
		System.out.println("Sceanrio name: "+sc.getName());
		System.out.println("Sceanrio name: "+sc.getId());
		extentReports=startTestReporter();
		test=startTestCase(sc.getName());
		test.assignAuthor("Shiva");
		
	}
	
	@After
	public void after(Scenario sc){
		saveTestResults();
		driver.quit();
		
		
	}

	@Given("Launched application url")
	public void launched_application_url() {
		launchBrowser(browser);
		launchAppUrl("http://www.google.com");
		reportStep("PASS", "Launched application url");
	}

	@When("Login to the application")
	public void login_to_the_application() {
		reportStep("PASS", "Login to the application");
		loginPage = new LoginPage(driver,test);
		loginPage.getAssignedVariableFromLogin();
		
	}
	
	@Then("Home page should be displayed")
	public void home_page_should_be_displayed() {
		homePage = new HomePage(driver, test);
		homePage.getAssignedVariableFromHome();
		reportStep("PASS", "Home page should be displayed");
		
	}
}
