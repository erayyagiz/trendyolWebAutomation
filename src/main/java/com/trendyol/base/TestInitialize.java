package com.trendyol.base;

import com.trendyol.config.ConfigReader;
import com.trendyol.config.Settings;
import com.trendyol.pages.*;
import io.qameta.allure.Step;
import org.testng.annotations.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;


public class TestInitialize {
    public static boolean TEST_ENABLED = Boolean.FALSE;
    public TestInitialize() {
    }

    @BeforeSuite(alwaysRun = true)
    public void Initialize() throws IOException {
        ConfigReader.readBrowserConfig();
        TEST_ENABLED = Boolean.TRUE;
    }

    @Step("Open the browser")
    @BeforeMethod(alwaysRun = true)
    public void beforeTest() throws MalformedURLException {
        LocalDriverContext.setDriver(FrameworkInitialize.InitializeBrowser());
        LocalDriverContext.getDriver().get(Settings.BaseURL);
        LocalDriverContext.getDriver().manage().window().maximize();
        //LocalDriverContext.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LocalDriverContext.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
    }

    @Step("Close the browser")
    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        if(LocalDriverContext.getDriver() != null){
            LocalDriverContext.getDriver().quit();
        }
    }


    public LoginPage loginPage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(LoginPage.class));
        LoginPage loginPage = CurrentPageContext.getCurrentPage().As(LoginPage.class);
        return loginPage;
    }
    public CommonPage commonPage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(CommonPage.class));
        CommonPage commonPage = CurrentPageContext.getCurrentPage().As(CommonPage.class);
        return commonPage;
    }
    public HomePage homePage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(HomePage.class));
        HomePage homePage = CurrentPageContext.getCurrentPage().As(HomePage.class);
        return homePage;
    }
    public MyBasketPage myBasketPage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(MyBasketPage.class));
        MyBasketPage myBasketPage = CurrentPageContext.getCurrentPage().As(MyBasketPage.class);
        return myBasketPage;
    }
    public ProductPage productPage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(ProductPage.class));
        ProductPage productPage = CurrentPageContext.getCurrentPage().As(ProductPage.class);
        return productPage;
    }
    public SearchPage searchPage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(SearchPage.class));
        SearchPage searchPage = CurrentPageContext.getCurrentPage().As(SearchPage.class);
        return searchPage;
    }
    public MyFavoritesPage myFavoritesPage(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(MyFavoritesPage.class));
        MyFavoritesPage myFavoritesPage = CurrentPageContext.getCurrentPage().As(MyFavoritesPage.class);
        return myFavoritesPage;
    }
}
