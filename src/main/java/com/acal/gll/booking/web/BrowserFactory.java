package com.acal.gll.booking.web;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserFactory {

    private final static String IPHONE_USER_AGENT = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

    public static BrowserDriver phantomJs() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("phantomjs.page.settings.userAgent", IPHONE_USER_AGENT);
        return new BrowserDriver(new PhantomJSDriver(capabilities));
    }

    public static BrowserDriver firefox() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("general.useragent.override", IPHONE_USER_AGENT);
        return new BrowserDriver(new FirefoxDriver(profile));
    }

}
