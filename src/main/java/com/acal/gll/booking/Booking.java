package com.acal.gll.booking;

import com.acal.gll.booking.web.BrowserDriver;
import com.acal.gll.booking.web.WebElementLookup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Booking {

    private static final Logger LOG = LoggerFactory.getLogger(Booking.class);
    @Autowired
    private BrowserDriver browserDriver;

    public void login() {
        browserDriver.load("https://gll.legendonlineservices.co.uk/enterprise/account/Login#");
        final WebElement usernameInput = browserDriver.element(new WebElementLookup(By.id("login_Email")).withVisibility(true));
        usernameInput.sendKeys(System.getProperty("gllUsername"));
        final WebElement passwordInput = browserDriver.element(new WebElementLookup(By.id("login_Password")).withVisibility(true));
        passwordInput.sendKeys(System.getProperty("gllPassword"));

        browserDriver.element(new WebElementLookup(By.id("login_Submit")).withVisibility(true)).click();

        if (browserDriver.getSourcePage().contains("Welcome Andrea Caldera")) {
            LOG.info("User logged in successfully");
        } else {
            LOG.warn("Unable to login");
        }
    }

    public void quit() {
        browserDriver.quit();
    }

}
