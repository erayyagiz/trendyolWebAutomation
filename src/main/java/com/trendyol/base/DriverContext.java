package com.trendyol.base;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class DriverContext {
    final Logger driverContextLogger = LogManager.getLogger(DriverContext.class);
    String parentWindowId = null;
    ArrayList<String> windowList = new ArrayList();

    public DriverContext() {
    }

    public void navigateToUrl(String url) {
        try {
            LocalDriverContext.getDriver().get(url);
            this.driverContextLogger.info("Driver navigated to URL: " + url);
        } catch (Exception e) {
        }
    }

    public void waitFor(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (Exception e) {
        }
    }

    public void waitForPageToLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) LocalDriverContext.getDriver();
            ExpectedCondition<Boolean> jsLoad = webDriver -> ((JavascriptExecutor) LocalDriverContext.getDriver())
                    .executeScript("return document.readyState").toString().equals("complete");
            boolean jsReady = jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            if (!jsReady)
                wait.until(jsLoad);
            else
                this.driverContextLogger.info("The page is loaded successfully!");
        } catch (Throwable var6) {
            this.driverContextLogger.error("The page cannot be not loaded! The reason: "+ExceptionUtils.getMessage(var6));
            Assert.fail("The page cannot be loaded! The reason: "+ExceptionUtils.getMessage(var6));
        }
    }

    public void waitUntilElementVisible(WebElement elementFindBy) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element is not visible! The reason: "+ExceptionUtils.getMessage(var3));
            Assert.fail("The element is not visible! The reason: "+ExceptionUtils.getMessage(var3));
        }
    }

    public boolean checkElementIsDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Throwable var3) {
            return false;
        }
    }

    public void switchTabByGivenTitle(String title) {
        try {
            Set<String> allTabs = LocalDriverContext.getDriver().getWindowHandles();
            Iterator var3 = allTabs.iterator();
            while (var3.hasNext()) {
                String eachTab = (String) var3.next();
                LocalDriverContext.getDriver().switchTo().window(eachTab);
                if (LocalDriverContext.getDriver().getTitle().contains(title)) {
                    break;
                }
            }
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.titleContains(title));
        } catch (Throwable var5) {
            this.driverContextLogger.error("The title: "+title+" cannot be found! The reason: "+ExceptionUtils.getMessage(var5));
            Assert.fail("The title: "+title+" cannot be found! The reason: "+ExceptionUtils.getMessage(var5));
        }
    }

    public void getLatestWindow() {
        try {
            this.parentWindowId = LocalDriverContext.getDriver().getWindowHandle();
            this.driverContextLogger.info("Parent Window Id: " + this.parentWindowId);
            Set<String> handles = LocalDriverContext.getDriver().getWindowHandles();
            Iterator var2 = handles.iterator();
            while (var2.hasNext()) {
                String window = (String) var2.next();
                this.driverContextLogger.info("Window : " + window);
                this.windowList.add(this.windowList.size(), window);
            }
            LocalDriverContext.getDriver().switchTo().window((String) this.windowList.get(this.windowList.size() - 1));
        } catch (Throwable var4) {
            this.driverContextLogger.error("New page or tab cannot be switced! The reason: "+ExceptionUtils.getMessage(var4));
            Assert.fail("New page or tab cannot be switched! The reason: "+ExceptionUtils.getMessage(var4));
        }

    }

    public void returnParentWindow() {
        try {
            LocalDriverContext.getDriver().switchTo().window(this.parentWindowId);
        } catch (Throwable var2) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var2));
            Assert.fail("Parent window cannot be returned! The reason: "+ExceptionUtils.getMessage(var2));
        }

    }

    public void findAndScrollWebElement(WebElement element) {
        try {
            Actions actions = new Actions(LocalDriverContext.getDriver());
            actions.moveToElement(element).build().perform();
            waitFor(1);
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: "+element+" cannot be found! The reason: "+ExceptionUtils.getMessage(var3));
            Assert.fail("The element: "+element+" cannot be found! The reason: "+ExceptionUtils.getMessage(var3));
        }

    }


    public WebElement getWebElementGivenLocator(By locator) {
        WebElement element = null;
        try {
            element = LocalDriverContext.getDriver().findElement(locator);
        } catch (Throwable var4) {
            this.driverContextLogger.error("The element: "+locator+" cannot be found! The reason: "+ExceptionUtils.getMessage(var4));
            Assert.fail("The element: "+locator+" cannot be found! The reason: "+ExceptionUtils.getMessage(var4));
        }
        return element;
    }

    public void clickAndHold(WebElement element) {
        try {
            Actions actions = new Actions(LocalDriverContext.getDriver());
            actions.clickAndHold(element).perform();
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: "+element+" cannot be click and hold! The reason: "+ExceptionUtils.getMessage(var3));
            Assert.fail("The element: "+element+" cannot be click and hold! The reason: "+ExceptionUtils.getMessage(var3));
        }
    }

    public List<WebElement> presenceOfAllWait(By locator, int seconds) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(seconds));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception ex) {
            this.driverContextLogger.error("The element: "+locator+" cannot be found! The reason: "+ex.getMessage());
            Assert.fail("The element: "+locator+" cannot be found! The reason: "+ex.getMessage());
            return null;
        }
    }

    public Integer generateRandomInteger(int min, int max) {
        try {
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            return random_int;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRandomString(String characters, int length){
        try {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for(int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                char randomChar = characters.charAt(index);
                sb.append(randomChar);
            }
            String randomString = sb.toString();
            return randomString;
        } catch (Exception e) {
            Assert.fail("Random string cannot be generated.");
            return null;
        }
    }
}
