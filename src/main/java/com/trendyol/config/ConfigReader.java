package com.trendyol.config;

import com.trendyol.base.BrowserType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static Properties readBrowserConfig() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(inputStream);
        Settings.BrowserType = BrowserType.valueOf(properties.getProperty("BrowserType"));
        Settings.BaseURL = properties.getProperty("BaseURL");
        Settings.Username = properties.getProperty("Username");
        Settings.Password = properties.getProperty("Password");
        Settings.Remote = properties.getProperty("Remote");
        Settings.GridURL = properties.getProperty("GridURL");
        return properties;
    }
}
