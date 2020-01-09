package com.onTime.project.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleMap {

	private WebDriver driver;

	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\0.encore\\99.devEnv\\chromedriver.exe";
	
	public GoogleMap() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1920x1080");
		options.addArguments("disable-gpu");
		driver = new ChromeDriver(options);
//		driver = new ChromeDriver();
	}
	
	public String getTime(String locA, String locB) {
		try {
			driver.get("https://www.google.com/maps/dir/" + locA + "/" + locB + "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver.findElement(By.xpath("//*[@id=\"section-directions-trip-0\"]/div[2]/div[2]/div[1]/div")).getText();
	}
	
	public static void main(String[] args) {
		GoogleMap gm = new GoogleMap();
		System.out.println(gm.getTime("37.492199,127.1155983", "37.102199,127.1155983"));
	}

}
