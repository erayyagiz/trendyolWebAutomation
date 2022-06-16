package com.trendyol.pages;

import com.trendyol.listener.ReportListener;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class LoginPage extends CommonPage {
    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);
    @FindBy(how = How.XPATH, using="//input[@id='login-email']")
    public WebElement loginEmailTextbox;
    @FindBy(how = How.XPATH, using="//input[@id='login-password-input']")
    public WebElement loginPasswordTextbox;
    @FindBy(how = How.XPATH, using="//button[@type='submit']")
    public WebElement loginSubmitButton;
    @FindBy(how = How.XPATH, using="//div[@class='forgot-password']")
    public WebElement loginForgotPasswordButton;
    @FindBy(how = How.XPATH, using="//i[contains(@class,'i-eye')]")
    public WebElement loginPasswordUnhideButton;
    @FindBy(how = How.XPATH, using="//div[@id='error-box-wrapper']/span[@class='message']")
    public WebElement errorMessage;

    @Step("Login to Trendyol")
    public void login(String email, String password, Boolean passwordUnhide){
        try {
            ReportListener.info("login is started!");
            LOGGER.info("login is started.");
            waitUntilElementVisible(loginEmailTextbox);
            loginEmailTextbox.sendKeys(email);
            loginPasswordTextbox.sendKeys(password);
            if(passwordUnhide)
                loginPasswordUnhideButton.click();
            loginSubmitButton.click();
            ReportListener.pass("login is completed!");
            LOGGER.info("login is completed.");
        } catch (Exception e) {
            ReportListener.fail("login is not completed!");
            LOGGER.error("login is not completed! The reason: "+e.getMessage());
            Assert.fail("login is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Check that login is incorrect")
    public void checkLoginIsIncorrect(String expectedErrorMessage){
        try {
            ReportListener.info("checkLoginIsIncorrect is started!");
            LOGGER.info("checkLoginIsIncorrect is started.");
            waitUntilElementVisible(errorMessage);
            String currentErrorMessage = errorMessage.getText();
            if(!expectedErrorMessage.equals(currentErrorMessage)){
                ReportListener.fail("The error message is not correct. Current Error Message: "+currentErrorMessage+"; Expected Error Message: "+expectedErrorMessage);
                LOGGER.error("The error message is not correct. Current Error Message: "+currentErrorMessage+"; Expected Error Message: "+expectedErrorMessage);
                Assert.fail("The error message is not correct. Current Error Message: "+currentErrorMessage+"; Expected Error Message: "+expectedErrorMessage);
            }
            ReportListener.pass("checkLoginIsIncorrect is completed!");
            LOGGER.info("checkLoginIsIncorrect is completed.");
        } catch (Exception e) {
            ReportListener.fail("checkLoginIsIncorrect is not completed!");
            LOGGER.error("checkLoginIsIncorrect is not completed! The reason: "+e.getMessage());
            Assert.fail("checkLoginIsIncorrect is not completed! The reason: "+e.getMessage());
        }
    }

}
