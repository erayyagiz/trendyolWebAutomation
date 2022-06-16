package com.trendyol.pages;

import com.trendyol.listener.ReportListener;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class ProductPage extends CommonPage {
    private static final Logger LOGGER = LogManager.getLogger(ProductPage.class);

    @FindBy(how = How.XPATH, using="//h1[@class='pr-new-br']//span")
    public WebElement productNameTitle;
    @FindBy(how = How.XPATH, using="//div[@class='product-price-container']//span[@class='prc-dsc']")
    public WebElement productPriceTitle;
    @FindBy(how = How.XPATH, using="//div[@class='product-button-container']/button[@class='add-to-basket']")
    public WebElement addToBasketButton;
    @FindBy(how = How.XPATH, using="//div[@class='product-button-container']//div[@class='add-to-basket-button-text-success']")
    public WebElement addToBasketSuccessMessage;

    @Step("Check that product is selected correctly")
    public void checkProduct(String expectedProductName, String expectedProductPrice){
        try {
            ReportListener.info("checkProduct is started!");
            LOGGER.info("checkProduct is started.");
            getLatestWindow();
            waitUntilElementVisible(productNameTitle);
            String currentProductName = productNameTitle.getText();
            String currentProductPrice = productPriceTitle.getText();
            if(!expectedProductPrice.contains(currentProductPrice) && !expectedProductName.contains(currentProductName)){
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

    @Step("Add to basket product")
    public void addToBasketProduct(){
        try {
            ReportListener.info("addToBasketProduct is started!");
            LOGGER.info("addToBasketProduct is started.");
            waitUntilElementVisible(addToBasketButton);
            addToBasketButton.click();
            waitFor(1);
            if(!checkElementIsDisplayed(addToBasketSuccessMessage)){
                ReportListener.fail("The product cannot add to basket.");
                LOGGER.error("The product cannot add to basket.");
                Assert.fail("The product cannot add to basket.");
            }
            ReportListener.pass("addToBasketProduct is completed!");
            LOGGER.info("addToBasketProduct is completed.");
        } catch (Exception e) {
            ReportListener.fail("addToBasketProduct is not completed!");
            LOGGER.error("addToBasketProduct is not completed! The reason: "+e.getMessage());
            Assert.fail("addToBasketProduct is not completed! The reason: "+e.getMessage());
        }
    }
}
