package wrappers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.Box.Filler;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;

public class UIFactory extends Reporter implements UIWrappers {

	protected RemoteWebDriver driver;
	protected static String url, username, password, browser,projectPath;
	protected String scenarioName, featureName;
	protected static int imgNumber = 0, implicitWait = 10, longWait = 30;
	protected static boolean remoteExecution = false;
	protected WebDriverWait wait;
	protected DriverService driverService;

	public UIFactory() {
		try {

			if (prop == null) {
				prop = new Properties();
				prop.load(new FileReader(new File("./resources/config.properties")));
				prop.load(new FileReader(new File("./resources/db.properties")));
				prop.load(new FileReader(new File("./resources/environment.properties")));

				env = prop.getProperty("Environment");
				reportPath = new File(prop.getProperty("reportPath")).getAbsolutePath();
				username = prop.getProperty("UserName");
				password = prop.getProperty("Password");
				 browser = prop.getProperty("Browser");
				implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
				longWait = Integer.parseInt(prop.getProperty("longWait"));
				remoteExecution = Boolean.parseBoolean(prop.getProperty("remoteExecution"));
				projectPath =System.getProperty("user.dir");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public UIFactory(RemoteWebDriver driver, ExtentTest test) {
		try {
			this.driver = driver;
			this.test = test;

		} catch (Exception e) {

		}

	}

	@Override
	public void launchAppUrl(String url) {
		
		driver.get(url);
		reportStep("PASS", "Launched Application url: "+url);
	}

	@Override
	public void navigateTo() {
		// TODO Auto-generated method stub

	}

	@Override
	public WebDriver launchBrowser(String browser) {

		DesiredCapabilities caps =new DesiredCapabilities();
		switch (browser.toUpperCase()) {
		case "CHROME":
//			driverService = new ChromeDriverService.Builder()
//					.usingDriverExecutable(new File("resources/drivers/chromedriver.exe")).usingAnyFreePort().build();
//		
			System.setProperty("webdriver.chrome.driver", projectPath+"/resources/drivers/chromedriver.exe");
			driver =new ChromeDriver();

			break;

		case "FIREFOX":
			driverService = new GeckoDriverService.Builder().usingDriverExecutable(new File("resources/drivers/chromedriver.exe"))
					.usingAnyFreePort().build();
			caps = DesiredCapabilities.firefox();
			break;

		case "IE":
			driverService = new InternetExplorerDriverService.Builder()
					.usingDriverExecutable(new File("drivers/chromedriver.exe")).usingAnyFreePort().build();
			caps = DesiredCapabilities.internetExplorer();
			break;

		case "SAFARI":
			driverService = new SafariDriverService.Builder()
					.usingDriverExecutable(new File("drivers/chromedriver.exe")).usingAnyFreePort().build();
			caps = DesiredCapabilities.safari();
			break;

		default:
			throw new RuntimeException("Invalid browser section, Please select CHROME or FIREFOX or IE or SAFARI.");
		}

//		System.out.println("Driver Service url: "+driverService.getUrl());
//		driver = new RemoteWebDriver(driverService.getUrl(), caps);
		return driver;
	}

	@Override
	String getScreenShotAsBinary() {
		FileOutputStream fout = null;
		String imageFile = null;
		try {
			byte[] data = driver.getScreenshotAs(OutputType.BYTES);
			String imgName = "Img" + String.format("%03d", imgNumber++);
			
			File imageFolders =new File(reportPath +File.separator +"images");
			if(!imageFolders.exists()){
				imageFolders.mkdirs();
			}
			
			 imageFile =reportPath+"/images/"+ imgName+".jpeg";
			new File(imageFile).createNewFile();
			fout = new FileOutputStream(new File(imageFile));
			fout.write(data);
			fout.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return imageFile;
	}

	@Override
	String getScreenShotAsBase64() {
		String base64= driver.getScreenshotAs(OutputType.BASE64);
		return base64;

	}

	@BeforeSuite
	public void beforeSuite() {
		try {
			driverService.start();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@AfterSuite
	public void afterSuite() {
		driverService.stop();
	}
}
