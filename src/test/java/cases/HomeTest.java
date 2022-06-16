package cases;

import com.trendyol.base.TestInitialize;
import org.testng.annotations.Test;

public class HomeTest extends TestInitialize {

    @Test
    public void checkProductsAndImages(){
        commonPage().closePopupIfExist();
        homePage().checkCampaingsProducts("ALL",4);
    }
}
