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

public class HomePage extends CommonPage {
    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    public By categoryHeaderMenuList = By.xpath("(//ul[@class='main-nav']/li[@class='tab-link'])");
    public By productImageList = By.xpath("//div[contains(@class,'image-container')]/img");
    public By productNameList = By.xpath("//div[@class='product-brand-description two-line-text']/span[@class='name']");
    public By productPriceList = By.xpath("//div[@class='price-box discountedv2']");

    @FindBy(how = How.XPATH, using = "(//a[contains(@class,'campaign campaign')]//summary[@class='campaign-summary']/span)[1]")
    public WebElement firstProductCompenent;
    @FindBy(how = How.XPATH, using = "//div[@class='boutique-description']//h1")
    public WebElement currentCompenentTitle;

    @Step("Go to the campaigns")
    public Boolean goToCampaignToIndex(Object index,int indexProduct) {
        try {
            ReportListener.info("goToCampaignToIndex is started!");
            LOGGER.info("goToCampaignToIndex is started.");
            Boolean flag=true;
            int categoryHeaderMenuSize = 0;
            if(index.equals("ALL"))
                categoryHeaderMenuSize = presenceOfAllWait(categoryHeaderMenuList, 30).size();
            else
                categoryHeaderMenuSize = (int) index;
            for (int i = 0; i < categoryHeaderMenuSize; i++) {
                List<WebElement> categoryHeaderMenus = presenceOfAllWait(categoryHeaderMenuList, 30);
                String categoryHeaderMenuName = categoryHeaderMenus.get(i).getText();
                categoryHeaderMenus.get(i).click();
                waitUntilElementVisible(firstProductCompenent);
                ReportListener.info(categoryHeaderMenuName + " page is opened!");
                LOGGER.info(categoryHeaderMenuName + " page is opened!");
                String expectedCompenentName = firstProductCompenent.getText();
                firstProductCompenent.click();
                closePopupIfExist();
                String currentCompenentName = currentCompenentTitle.getText();
                Assert.assertEquals(expectedCompenentName, currentCompenentName);
                checkProductsToIndex(indexProduct);
                ReportListener.info(currentCompenentName + " page's products are displayed!");
                LOGGER.info(currentCompenentName + " page's products are displayed!");
                if(i==categoryHeaderMenuSize-1)
                    flag=false;
            }
            ReportListener.pass("goToCampaignToIndex is completed!");
            LOGGER.info("goToCampaignToIndex is completed.");
            return flag;
        } catch (Exception e) {
            ReportListener.fail("goToCampaignToIndex is not completed!");
            LOGGER.error("goToCampaignToIndex is not completed! The reason: " + e.getMessage());
            Assert.fail("goToCampaignToIndex is not completed! The reason: " + e.getMessage());
            return null;
        }
    }

    @Step("Check that products are displayed")
    public void checkProductsToIndex(int index) {
        try {
            ReportListener.info("checkProductsToIndex is started!");
            LOGGER.info("checkProductsToIndex is started.");
            List<WebElement> productImages = presenceOfAllWait(productImageList, 5);
            List<WebElement> productNames = presenceOfAllWait(productNameList, 5);
            List<WebElement> productPrices = presenceOfAllWait(productPriceList, 5);
            for (int j = 0; j < index; j++) {
                clickAndHold(productNames.get(j));
                String src = productImages.get(j).getAttribute("src");
                String name = productNames.get(j).getText();
                String price = productPrices.get(j).getText();
                ReportListener.info(j+1 + ". Product's image: " + src);
                LOGGER.info(j+1 + ". Product's image: " + src);
                ReportListener.info(j+1 + ". Product's name: " + name);
                LOGGER.info(j+1 + ". Product's name: " + name);
                ReportListener.info(j+1 + ". Product's price: " + price);
                LOGGER.info(j+1 + ". Product's price: " + price);
                if (src.isEmpty() || name.isEmpty() || price.isEmpty()) {
                    ReportListener.fail("The product's cannot be displayed!");
                    LOGGER.error("The product's cannot be displayed!");
//                    Assert.fail("The product's cannot be displayed!");
                }
            }
            ReportListener.pass("checkProductsToIndex is completed!");
            LOGGER.info("checkProductsToIndex is completed.");
        } catch (Exception e) {
            ReportListener.fail("checkProductsToIndex is not completed!");
            LOGGER.error("checkProductsToIndex is not completed! The reason: " + e.getMessage());
            Assert.fail("checkProductsToIndex is not completed! The reason: " + e.getMessage());
        }
    }

    @Step("Check that products are displayed")
    public void checkCampaingsProducts(Object indexCampaign,int indexProduct) {
        try {
            ReportListener.info("checkCampaingsProducts is started!");
            LOGGER.info("checkCampaingsProducts is started.");
            Boolean flag;
            do{
                flag = goToCampaignToIndex(indexCampaign,indexProduct);
            }while (flag);

            ReportListener.pass("checkCampaingsProducts is completed!");
            LOGGER.info("checkCampaingsProducts is completed.");
        } catch (Exception e) {
            ReportListener.fail("checkCampaingsProducts is not completed!");
            LOGGER.error("checkCampaingsProducts is not completed! The reason: " + e.getMessage());
            Assert.fail("checkCampaingsProducts is not completed! The reason: " + e.getMessage());
        }
    }
}
