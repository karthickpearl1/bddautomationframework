package wrappers;

import org.openqa.selenium.WebDriver;

interface  UIWrappers {

	public WebDriver launchBrowser(String browser);
	
	public void launchAppUrl(String url);
	
	public void navigateTo();
	
}
