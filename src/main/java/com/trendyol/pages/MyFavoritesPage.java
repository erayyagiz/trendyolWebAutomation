package com.trendyol.pages;

import com.trendyol.listener.ReportListener;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import java.util.List;

public class MyFavoritesPage extends CommonPage {
    private static final Logger LOGGER = LogManager.getLogger(MyFavoritesPage.class);

    public By removeProductButtons = By.xpath("//i[@class='i-close']");

    @FindBy(how = How.XPATH, using="//span[@class='prdct-desc-cntnr-name no-white-space']")
    public WebElement productNameTitle;
    @FindBy(how = How.XPATH, using="//div[@class='prc-box-dscntd']")
    public WebElement productPriceTitle;
    @FindBy(how = How.XPATH, using="//p[@class='empty-favorites-header']")
    public WebElement emptyMyFavoriteMessage;

    @Step("Check that product is added to favorites correctly")
    public void checkProduct(String expectedProductName, String expectedProductPrice){
        try {
            ReportListener.info("checkProduct is started!");
            LOGGER.info("checkProduct is started.");
            waitUntilElementVisible(productNameTitle);
            String currentProductName = productNameTitle.getText();
            String currentProductPrice = productPriceTitle.getText();
            if(!currentProductPrice.equals(expectedProductPrice) && !currentProductName.equals(expectedProductName)){
                ReportListener.fail("Current Product Name: "+currentProductName+"; Expected Product Name: "+expectedProductName+"; Current Product Price: "+currentProductPrice+"; Expected Product Price: "+expectedProductPrice);
                LOGGER.error("Current Product Name: "+currentProductName+"; Expected Product Name: "+expectedProductName+"; Current Product Price: "+currentProductPrice+"; Expected Product Price: "+expectedProductPrice);
                Assert.fail("Current Product Name: "+currentProductName+"; Expected Product Name: "+expectedProductName+"; Current Product Price: "+currentProductPrice+"; Expected Product Price: "+expectedProductPrice);
            }
            ReportListener.pass("checkProduct is completed!");
            LOGGER.info("checkProduct is completed.");
        } catch (Exception e) {
            ReportListener.fail("checkProduct is not completed!");
            LOGGER.error("checkProduct is not completed! The reason: "+e.getMessage());
            Assert.fail("checkProduct is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Remove all my favorites")
    public void removeAllMyFavorites(){
        try {
            ReportListener.info("removeAllMyFavorites is started!");
            LOGGER.info("removeAllMyFavorites is started.");
            if(!checkElementIsDisplayed(emptyMyFavoriteMessage)){
                List<WebElement> removeButtons = presenceOfAllWait(removeProductButtons, 5);
                for (int i = 0; i < removeButtons.size(); i++) {
                    removeButtons.get(i).click();
                    waitFor(1);
                }
            }
            if(!checkElementIsDisplayed(emptyMyFavoriteMessage)){
                ReportListener.fail("All products cannot be removed!");
                LOGGER.error("All products cannot be removed!");
                Assert.fail("All products cannot be removed!");
            }
            ReportListener.pass("removeAllMyFavorites is completed!");
            LOGGER.info("removeAllMyFavorites is completed.");
        } catch (Exception e) {
            ReportListener.fail("removeAllMyFavorites is not completed!");
            LOGGER.error("removeAllMyFavorites is not completed! The reason: "+e.getMessage());
            Assert.fail("removeAllMyFavorites is not completed! The reason: "+e.getMessage());
        }
    }
}
