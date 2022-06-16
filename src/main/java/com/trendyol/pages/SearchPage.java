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

public class SearchPage extends CommonPage {
    private static final Logger LOGGER = LogManager.getLogger(SearchPage.class);

    public By filterBrandList = By.xpath("//div[@data-title='Marka']/following-sibling::*//div[@class='fltr-item-text']");
    public By productBrandList = By.xpath("//span[@class='prdct-desc-cntnr-ttl']");
    public By productNameList = By.xpath("//span[@class='prdct-desc-cntnr-name hasRatings']");
    public By productPriceList = By.xpath("//div[@class='prc-box-dscntd']");
    public By productFavoriteButtons = By.xpath("//i[@class='fvrt-btn']");

    @FindBy(how = How.XPATH, using="//div[@class='dscrptn']/h1")
    public WebElement searchedProductTitle;
    @FindBy(how = How.XPATH, using="//div[@class='fltr-cntnr-ttl' and text()='Marka']/following-sibling::div[@class='i-dropdown-arrow']")
    public WebElement filterBrandDropdownButton;
    @FindBy(how = How.XPATH, using="//div[@class='fltr-cntnr-ttl' and text()='Fiyat']/following-sibling::div[@class='i-dropdown-arrow']")
    public WebElement filterPriceDropdownButton;
    @FindBy(how = How.XPATH, using="//input[@class='fltr-srch-prc-rng-input min']")
    public WebElement filterPriceMinTextbox;
    @FindBy(how = How.XPATH, using="//input[@class='fltr-srch-prc-rng-input max']")
    public WebElement filterPriceMaxTextbox;
    @FindBy(how = How.XPATH, using="//button[@class='fltr-srch-prc-rng-srch']")
    public WebElement filterPriceSearchButton;

    @Step("Search product")
    public void searchProduct(String searchedProducts){
        try {
            ReportListener.info("searchProduct is started!");
            LOGGER.info("searchProduct is started.");
            waitFor(2);
            waitUntilElementVisible(searchTextbox);
            searchTextbox.sendKeys(searchedProducts);
            waitFor(1);
            searchButton.click();
            waitUntilElementVisible(searchedProductTitle);
            String currentProducts = searchedProductTitle.getText();
            if(!currentProducts.contains(searchedProducts)){
                ReportListener.fail("Searched Product: "+searchedProducts+"; Current Products: "+currentProducts);
                LOGGER.error("Searched Product: "+searchedProducts+"; Current Products: "+currentProducts);
                Assert.fail("Searched Product: "+searchedProducts+"; Current Products: "+currentProducts);
            }
            ReportListener.pass("searchProduct is completed!");
            LOGGER.info("searchProduct is completed.");
        } catch (Exception e) {
            ReportListener.fail("searchProduct is not completed!");
            LOGGER.error("searchProduct is not completed! The reason: "+e.getMessage());
            Assert.fail("searchProduct is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Select brand")
    public void selectBrand(String selectedBrandName){
        try {
            ReportListener.info("selectBrand is started!");
            LOGGER.info("selectBrand is started.");
            if(checkElementIsDisplayed(filterBrandDropdownButton))
                filterBrandDropdownButton.click();
            List<WebElement> brands = presenceOfAllWait(filterBrandList, 3);
            for (WebElement brand : brands) {
                if (brand.getText().equals(selectedBrandName)) {
                    findAndScrollWebElement(brand);
                    brand.click();
                    break;
                }
            }
            waitFor(2);
            List<WebElement> productBrands = presenceOfAllWait(productBrandList, 30);
            for (WebElement productBrand : productBrands) {
                if (!productBrand.getText().equals(selectedBrandName)) {
                    ReportListener.fail("Selected Brand Name: "+selectedBrandName+"; Current Brand Name: "+productBrand.getText());
                    LOGGER.error("Selected Brand Name: "+selectedBrandName+"; Current Brand Name: "+productBrand.getText());
                    Assert.fail("Selected Brand Name: "+selectedBrandName+"; Current Brand Name: "+productBrand.getText());
                }
            }
            ReportListener.pass("selectBrand is completed!");
            LOGGER.info("selectBrand is completed.");
        } catch (Exception e) {
            ReportListener.fail("selectBrand is not completed!");
            LOGGER.error("selectBrand is not completed! The reason: "+e.getMessage());
            Assert.fail("selectBrand is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Select price")
    public void selectPrice(int priceMinAmount,int priceMaxAmount){
        try {
            ReportListener.info("selectPrice is started!");
            LOGGER.info("selectPrice is started.");
            waitFor(1);
            if(checkElementIsDisplayed(filterPriceDropdownButton))
                filterPriceDropdownButton.click();
            waitUntilElementVisible(filterPriceMinTextbox);
            filterPriceMinTextbox.sendKeys(String.valueOf(priceMinAmount));
            filterPriceMaxTextbox.sendKeys(String.valueOf(priceMaxAmount));
            filterPriceSearchButton.click();
            waitFor(2);
            List<WebElement> productPrices = presenceOfAllWait(productPriceList, 30);
            for (WebElement productPrice : productPrices) {
                String[] price = productPrice.getText().split(",");
                String currentPriceString = price[0];
                currentPriceString = currentPriceString.replace(".","");
                int currentPrice=Integer.parseInt(currentPriceString);
                if (!(priceMinAmount<=currentPrice && priceMaxAmount>=currentPrice)) {
                    ReportListener.fail("Selected Price Range: "+priceMinAmount+"-"+priceMaxAmount+"; Product Price: "+currentPrice);
                    LOGGER.error("Selected Price Range: "+priceMinAmount+"-"+priceMaxAmount+"; Product Price: "+currentPrice);
                    Assert.fail("Selected Price Range: "+priceMinAmount+"-"+priceMaxAmount+"; Product Price: "+currentPrice);
                }
            }
            ReportListener.pass("selectPrice is completed!");
            LOGGER.info("selectPrice is completed.");
        } catch (Exception e) {
            ReportListener.fail("selectPrice is not completed!");
            LOGGER.error("selectPrice is not completed! The reason: "+e.getMessage());
            Assert.fail("selectPrice is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Select product")
    public String[] selectProduct(){
        try {
            ReportListener.info("selectProduct is started!");
            LOGGER.info("selectProduct is started.");
            waitFor(2);
            List<WebElement> productNames = presenceOfAllWait(productNameList, 3);
            List<WebElement> productPrices = presenceOfAllWait(productPriceList, 3);
            int max = productNames.size();
            int index = generateRandomInteger(0,max-1);
            String getProductName = productNames.get(index).getText();
            String getProductPrice = productPrices.get(index).getText();
            findAndScrollWebElement(productNames.get(index));
            productNames.get(index).click();
            waitForPageToLoad();
            ReportListener.pass("selectProduct is completed!");
            LOGGER.info("selectProduct is completed.");
            return new String[] {getProductName, getProductPrice};
        } catch (Exception e) {
            ReportListener.fail("selectProduct is not completed!");
            LOGGER.error("selectProduct is not completed! The reason: "+e.getMessage());
            Assert.fail("selectProduct is not completed! The reason: "+e.getMessage());
            return null;
        }
    }

    @Step("Add to favorites product")
    public String[] addToFavoriteProduct(){
        try {
            ReportListener.info("addToFavoriteProduct is started!");
            LOGGER.info("addToFavoriteProduct is started.");
            List<WebElement> productFavorites = presenceOfAllWait(productFavoriteButtons, 3);
            List<WebElement> productNames = presenceOfAllWait(productNameList, 3);
            List<WebElement> productPrices = presenceOfAllWait(productPriceList, 3);
            int max = productNames.size();
            int index = generateRandomInteger(0,max-1);
            String getProductName = productNames.get(index).getText();
            String getProductPrice = productPrices.get(index).getText();
            findAndScrollWebElement(productFavorites.get(index));
            productFavorites.get(index).click();
            ReportListener.pass("selectProduct is completed!");
            LOGGER.info("selectProduct is completed.");
            return new String[] {getProductName, getProductPrice};
        } catch (Exception e) {
            ReportListener.fail("addToFavoriteProduct is not completed!");
            LOGGER.error("addToFavoriteProduct is not completed! The reason: "+e.getMessage());
            Assert.fail("addToFavoriteProduct is not completed! The reason: "+e.getMessage());
            return null;
        }
    }
}
