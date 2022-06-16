package com.trendyol.pages;

import com.trendyol.base.BasePage;
import com.trendyol.listener.ReportListener;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class CommonPage extends BasePage {
    private static final Logger LOGGER = LogManager.getLogger(CommonPage.class);

    @FindBy(how = How.XPATH, using="//div[@class='lr-title']//h3[text()='Trendyol’a giriş yap veya hesap oluştur, indirimleri kaçırma!']")
    public WebElement loginPageTitle;
    @FindBy(how = How.XPATH, using="//a[@href='/Hesabim/Favoriler']//span[text()='Favorilerim']")
    public WebElement myFavoritePageTitle;
    @FindBy(how = How.XPATH, using="//*[text()='Sepetinde ürün bulunmamaktadır.' or text()='Sipariş Özeti']")
    public WebElement myBasketPageTitle;
    @FindBy(how = How.XPATH, using="//div[@class='homepage-popup']/parent::*//div[@class='modal-close']")
    public WebElement homePagePopupCloseButton;
    @FindBy(how = How.XPATH, using="//div[@class='popup']")
    public WebElement productPopupCloseButton;
    @FindBy(how = How.XPATH, using="//div[@class='link account-user']//p[text()='Hesabım']")
    public WebElement myAccountButton;
    @FindBy(how = How.XPATH, using="//p[@class='user-name']")
    public WebElement usernameTitle;
    @FindBy(how = How.XPATH, using="//div[@class='link account-user']//p[text()='Giriş Yap']")
    public WebElement accountLoginButton;
    @FindBy(how = How.XPATH, using="//div[@class='login-dropdown']//div[@class='login-button']")
    public WebElement loginDropdownButton;
    @FindBy(how = How.XPATH, using="//div[@class='login-dropdown']//div[@class='signup-button']")
    public WebElement signupDropdownButton;
    @FindBy(how = How.XPATH, using="//a[@class='account-nav-item account-favorites']//p[text()='Favorilerim']")
    public WebElement accountMyFavoritesButton;
    @FindBy(how = How.XPATH, using="//a[@class='link account-basket']//p[text()='Sepetim']")
    public WebElement accountMyBasketButton;
    @FindBy(how = How.XPATH, using="//input[@class='search-box']")
    public WebElement searchTextbox;
    @FindBy(how = How.XPATH, using="//i[@class='search-icon']")
    public WebElement searchButton;

    @Step("Go to Login page")
    public void goToLoginPage(){
        try {
            ReportListener.info("goToLoginPage is started!");
            LOGGER.info("goToLoginPage is started.");
            waitUntilElementVisible(accountLoginButton);
            accountLoginButton.click();
            waitForPageToLoad();
            waitUntilElementVisible(loginPageTitle);
            ReportListener.pass("goToLoginPage is completed!");
            LOGGER.info("goToLoginPage is completed.");
        } catch (Exception e) {
            ReportListener.fail("goToLoginPage is not completed!");
            LOGGER.error("goToLoginPage is not completed! The reason: "+e.getMessage());
            Assert.fail("goToLoginPage is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Go to My Basket page")
    public void goToMyBasketPage(){
        try {
            ReportListener.info("goToMyBasketPage is started!");
            LOGGER.info("goToMyBasketPage is started.");
            waitFor(2);
            accountMyBasketButton.click();
            waitForPageToLoad();
            waitUntilElementVisible(myBasketPageTitle);
            ReportListener.pass("goToMyBasketPage is completed!");
            LOGGER.info("goToMyBasketPage is completed.");
        } catch (Exception e) {
            ReportListener.fail("goToMyBasketPage is not completed!");
            LOGGER.error("goToMyBasketPage is not completed! The reason: "+e.getMessage());
            Assert.fail("goToMyBasketPage is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Go to My Favorites page")
    public void goToMyFavoritesPage(){
        try {
            ReportListener.info("goToMyFavoritesPage is started!");
            LOGGER.info("goToMyFavoritesPage is started.");
            waitFor(2);
            accountMyFavoritesButton.click();
            waitForPageToLoad();
            waitUntilElementVisible(myFavoritePageTitle);
            ReportListener.pass("goToMyFavoritesPage is completed!");
            LOGGER.info("goToMyFavoritesPage is completed.");
        } catch (Exception e) {
            ReportListener.fail("goToMyFavoritesPage is not completed!");
            LOGGER.error("goToMyFavoritesPage is not completed! The reason: "+e.getMessage());
            Assert.fail("goToMyFavoritesPage is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Check that account is logged in correct")
    public void checkAccountIsCorrect(String loggedUser){
        try {
            ReportListener.info("checkAccountIsCorrect is started!");
            LOGGER.info("checkAccountIsCorrect is started.");
            waitUntilElementVisible(myAccountButton);
            clickAndHold(myAccountButton);
            waitUntilElementVisible(usernameTitle);
            String currentUser = usernameTitle.getText().toLowerCase();
            if(!loggedUser.equals(currentUser)){
                ReportListener.fail("The users are not equal. Current User: "+currentUser+"; Logged User: "+loggedUser);
                LOGGER.error("The users are not equal. Current User: "+currentUser+"; Logged User: "+loggedUser);
                Assert.fail("The users are not equal. Current User: "+currentUser+"; Logged User: "+loggedUser);
            }
            ReportListener.pass("checkAccountIsCorrect is completed! Current User: "+currentUser);
            LOGGER.info("checkAccountIsCorrect is completed. Current User: "+currentUser);
        } catch (Exception e) {
            ReportListener.fail("checkAccountIsCorrect is not completed!");
            LOGGER.error("checkAccountIsCorrect is not completed! The reason: "+e.getMessage());
            Assert.fail("checkAccountIsCorrect is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Close popup if exist")
    public void closePopupIfExist(){
        try {
            ReportListener.info("closePopupIfExist is started!");
            LOGGER.info("closePopupIfExist is started.");
            waitForPageToLoad();
            if(checkElementIsDisplayed(homePagePopupCloseButton))
                homePagePopupCloseButton.click();
            if(checkElementIsDisplayed(productPopupCloseButton))
                getWebElementGivenLocator(By.xpath("//div[@id='container']")).click();
            waitFor(1);
            ReportListener.pass("closePopupIfExist is completed!");
            LOGGER.info("closePopupIfExist is completed.");
        } catch (Exception e) {
            ReportListener.fail("closePopupIfExist is not completed!");
            LOGGER.error("closePopupIfExist is not completed! The reason: "+e.getMessage());
            Assert.fail("closePopupIfExist is not completed! The reason: "+e.getMessage());
        }
    }


}
