package com.trendyol.base;

import com.trendyol.config.Settings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class FrameworkInitialize {

    public FrameworkInitialize() {
    }

    public static WebDriver InitializeBrowser () throws MalformedURLException {
        WebDriver driver = null;
        if (driver == null) {
            if(Settings.Remote.equals("true")){
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("browserName", Settings.BrowserType);
                driver= new RemoteWebDriver(new URL(Settings.GridURL), capabilities);
            }else {
                switch (Settings.BrowserType) {
                    case Chrome:
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions capability = new ChromeOptions();
                        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                        capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                        capability.addArguments("--disable-notifications");
                        driver = new ChromeDriver(capability);
                        break;
                    case ChromeHeadless:
                        WebDriverManager.chromedriver().setup();
                        driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
                        break;
                    case Safari:
                        if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                            throw new WebDriverException("Your OS doesn't support Safari");
                        WebDriverManager.getInstance(SafariDriver.class).setup();
                        driver = new SafariDriver();
                        break;
                    case IE:
                        if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                            throw new WebDriverException("Your OS doesn't support Internet Explorer");
                        WebDriverManager.iedriver().setup();
                        driver = new InternetExplorerDriver();
                        break;
                    case Firefox:
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver();
                        break;
                }
            }
        }
        LocalDriverContext.setDriver(driver);
        return driver;
    }
}