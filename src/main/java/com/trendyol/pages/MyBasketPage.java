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

public class MyBasketPage extends CommonPage {
    private static final Logger LOGGER = LogManager.getLogger(MyBasketPage.class);

    public By removeProductButtons = By.xpath("//i[@class='i-trash']");

    @FindBy(how = How.XPATH, using="//div[@class='pb-basket-item']//p[@class='pb-item']")
    public WebElement productNameTitle;
    @FindBy(how = How.XPATH, using="//div[@class='pb-basket-item']//div[@class='pb-basket-item-price']")
    public WebElement productPriceTitle;
    @FindBy(how = How.XPATH, using="//div[@class='pb-empty-basket']")
    public WebElement emptyBasketMessage;
    @FindBy(how = How.XPATH, using="//button[text()='Sil']")
    public WebElement removeButton;

    @Step("Check that product is added to basket correctly")
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

    @Step("Remove all my basket")
    public void removeAllMyBasket(){
        try {
            ReportListener.info("removeAllMyBasket is started!");
            LOGGER.info("removeAllMyBasket is started.");
            if(!checkElementIsDisplayed(emptyBasketMessage)){
                List<WebElement> removeButtons = presenceOfAllWait(removeProductButtons, 5);
                for (int i = 0; i < removeButtons.size(); i++) {
                    removeButtons.get(i).click();
                    waitUntilElementVisible(removeButton);
                    removeButton.click();
                    waitFor(1);
                }
            }
            if(!checkElementIsDisplayed(emptyBasketMessage)){
                ReportListener.fail("All products cannot be removed!");
                LOGGER.error("All products cannot be removed!");
                Assert.fail("All products cannot be removed!");
            }
            ReportListener.pass("removeAllMyBasket is completed!");
            LOGGER.info("removeAllMyBasket is completed.");
        } catch (Exception e) {
            ReportListener.fail("removeAllMyBasket is not completed!");
            LOGGER.error("removeAllMyBasket is not completed! The reason: "+e.getMessage());
            Assert.fail("removeAllMyBasket is not completed! The reason: "+e.getMessage());
        }
    }
}
