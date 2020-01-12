package pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import io.cucumber.java.en.Then;

public class HomePage extends CommonPage {

	public HomePage(RemoteWebDriver driver, ExtentTest test) {

		this.driver = driver;
		this.test = test;

	}

	public void getAssignedVariableFromHome() {
		reportStep("PASS", "Assigned variable from home: ");
	}
}
