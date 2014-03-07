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
    private BrowserDriver browser;

    public boolean login(final String username, final String password) {
        browser.load("https://gll.legendonlineservices.co.uk/enterprise/account/Login#");
        final WebElement usernameInput = browser.element(new WebElementLookup(By.id("login_Email")));
        usernameInput.sendKeys(username);
        final WebElement passwordInput = browser.element(new WebElementLookup(By.id("login_Password")));
        passwordInput.sendKeys(password);
        browser.element(new WebElementLookup(By.id("login_Submit"))).click();
        return browser.getSourcePage().contains("Welcome Andrea Caldera");
    }

    public void selectClub(final String clubName) {
        browser.element(new WebElementLookup(By.xpath("//*[@id='cscNavBookings']//span")).withCssClass("ui-btn-text").withText("\\bBookings\\b")).click();
        browser.element(new WebElementLookup(By.xpath("//*[@id='bookingMenu']//a")).withCssClass("ui-link-inherit").withText("\\bMake a booking\\b")).click();
        browser.element(new WebElementLookup(By.xpath("//*[@id='facilityList']//a")).withCssClass("ui-link-inherit").withText("\\b" + clubName + "\\b")).click();
    }

    public void selectSquashTimetable() {
        browser.element(new WebElementLookup(By.xpath("//*[@id='activityTypesList']//a")).withCssClass("ui-link-inherit").withText("\\bOther Activities\\b")).click();
        browser.element(new WebElementLookup(By.xpath("//*[@id='activityList']//a")).withCssClass("ui-link-inherit").withText(".*(Squash).*")).click();
    }

    public void quit() {
        browser.quit();
    }

}