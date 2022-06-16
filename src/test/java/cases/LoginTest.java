package cases;

import com.trendyol.base.TestInitialize;
import org.testng.annotations.Test;

public class LoginTest extends TestInitialize {

    @Test
    public void loginWithRegisteredAccount() {
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("bddtest300@gmail.com", "TestAuto123!", true);
        commonPage().checkAccountIsCorrect("bddtest300@gmail.com");
    }

    @Test
    public void loginWithoutEmail() {
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("", "TestAuto123!", false);
        loginPage().checkLoginIsIncorrect("Lütfen geçerli bir e-posta adresi giriniz.");
    }

    @Test
    public void loginWithoutPassword() {
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("bddtest300@gmail.com", "", false);
        loginPage().checkLoginIsIncorrect("Lütfen şifrenizi giriniz.");
    }

    @Test
    public void loginWithoutEmailAndPassword() {
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("", "", false);
        loginPage().checkLoginIsIncorrect("Lütfen geçerli bir e-posta adresi giriniz.");
    }

    @Test
    public void loginWithUnregisteredAccount() {
        commonPage().closePopupIfExist();
        commonPage().goToLoginPage();
        loginPage().login("bddtest301@gmail.com", "Test123!", true);
        loginPage().checkLoginIsIncorrect("E-posta adresiniz ve/veya şifreniz hatalı.");
    }
}
