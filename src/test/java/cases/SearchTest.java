package cases;

import com.trendyol.base.TestInitialize;
import org.testng.annotations.Test;

public class SearchTest extends TestInitialize {

    @Test
    public void searchProductAddToBasket(){
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("bddtest300@gmail.com", "TestAuto123!", false);
        commonPage().checkAccountIsCorrect("bddtest300@gmail.com");
        commonPage().goToMyBasketPage();
        myBasketPage().removeAllMyBasket();
        searchPage().searchProduct("Oyuncu Bilgisayarı");
        searchPage().selectBrand("Monster");
        searchPage().selectPrice(3000,10000);
        String[] getNameAndPrice = searchPage().selectProduct();
        productPage().checkProduct(getNameAndPrice[0],getNameAndPrice[1]);
        productPage().addToBasketProduct();
        commonPage().goToMyBasketPage();
        myBasketPage().checkProduct(getNameAndPrice[0],getNameAndPrice[1]);
    }

    @Test
    public void searchProductAddToFavorite(){
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("bddtest300@gmail.com", "TestAuto123!", false);
        commonPage().checkAccountIsCorrect("bddtest300@gmail.com");
        commonPage().goToMyFavoritesPage();
        myFavoritesPage().removeAllMyFavorites();
        searchPage().searchProduct("Gömlek");
        commonPage().closePopupIfExist();
        String[] getNameAndPrice = searchPage().addToFavoriteProduct();
        commonPage().goToMyFavoritesPage();
        myFavoritesPage().checkProduct(getNameAndPrice[0],getNameAndPrice[1]);
    }
}
