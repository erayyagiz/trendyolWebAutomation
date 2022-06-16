package com.trendyol.base;


import org.openqa.selenium.support.PageFactory;

public class Base extends DriverContext {
    public Base() {
    }

    public <TPage> TPage GetInstance(Class<TPage> page) {
        Object obj = PageFactory.initElements(LocalDriverContext.getDriver(), page);
        return page.cast(obj);
    }
}
