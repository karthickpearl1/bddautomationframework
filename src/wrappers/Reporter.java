package wrappers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.reporter.ExtentEmailReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public  abstract class Reporter {

	protected static Properties prop;
	protected static ExtentReports extentReports;
	protected static ExtentHtmlReporter htmlReporter;
	protected ExtentTest test;
	protected static String env, reportPath;
	protected static boolean takeScreentShotsIfPass = true, base64ImageInReport = false;

	protected synchronized ExtentReports startTestReporter() {

		try {
			if (extentReports == null) {

				extentReports = new ExtentReports();
				htmlReporter = new ExtentHtmlReporter(new File(reportPath + "/reports.html"));
				extentReports.attachReporter(htmlReporter);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return extentReports;
	}
	
	protected synchronized ExtentTest startTestCase(String desc) {
		test = extentReports.createTest( desc);

		return test;
	}
	
	protected synchronized void saveTestResults(){
		extentReports.flush();
	}

	abstract String getScreenShotAsBinary();
	abstract String getScreenShotAsBase64();

	

	protected void reportStep(String status, String desc) {

		try {
			switch (status.trim().toUpperCase()) {

			case "PASS":
				if (takeScreentShotsIfPass && base64ImageInReport) {

					test.log(Status.PASS, desc,
							MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenShotAsBase64()).build());

				} else if (takeScreentShotsIfPass && !base64ImageInReport) {
					
					test.log(Status.PASS, desc,
							MediaEntityBuilder.createScreenCaptureFromPath(getScreenShotAsBinary()).build());
					
					
				} else {
					test.log(Status.PASS, desc);
				}
				break;

			case "FAIL":
				if (base64ImageInReport) {

					test.log(Status.FAIL, desc,
							MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenShotAsBase64()).build());
					throw new RuntimeException("Test script Failed: " + desc);
				} else {

					test.log(Status.PASS, desc,
							MediaEntityBuilder.createScreenCaptureFromPath(getScreenShotAsBinary()).build());
					throw new RuntimeException("Test script Failed: " + desc);
				}

			case "INFO":

				test.log(Status.INFO, desc);

			default:
				new RuntimeException("Invalid step, Please check");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	protected void reportStep(String status, String desc, Throwable e) {

		try {
			if (base64ImageInReport) {

				test.fail(desc,
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenShotAsBase64()).build());
				test.fail(e);
				throw new RuntimeException("Test script Failed: " + desc);
			} else {

				test.fail(desc, MediaEntityBuilder.createScreenCaptureFromPath(getScreenShotAsBinary()).build());

				test.fail(e);
				throw new RuntimeException("Test script Failed: " + desc);
			}

		} catch (Exception ex) {

		}
	}

	protected void reportStepWithoutSnap(String status, String desc) {

	}

}
