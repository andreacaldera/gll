package com.acal.gll.booking;

import com.acal.gll.booking.web.BrowserDriver;
import com.acal.gll.booking.web.WebElementLookup;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Booking {

    private static final Logger LOG = LoggerFactory.getLogger(Booking.class);

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("EEE, dd MMM");
    private static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");


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
        if (hasBooking()) {
            clearBasket();
        }
        browser.element(new WebElementLookup(By.xpath("//span")).withCssClass("ui-btn-text").withText("\\bBookings\\b")).click();
        browser.element(new WebElementLookup(By.xpath("//*[@id='bookingMenu']//a")).withCssClass("ui-link-inherit").withText("\\bMake a booking\\b")).click();
        browser.element(new WebElementLookup(By.xpath("//*[@id='facilityList']//a")).withCssClass("ui-link-inherit").withText("\\b" + clubName + "\\b")).click();
        LOG.info("Club selected [" + clubName + "]");
    }

    private void clearBasket() {
        final List<WebElement> bookings = browser.elements(By.xpath("//a[contains(@class,'removeItem')]"));
        for (final WebElement webElement : bookings) {
            webElement.click();
        }
    }

    private boolean hasBooking() {
        try {
            final String bookingsText = browser.element(new WebElementLookup(By.id("Badge")).withCssClass("badger-number", "badger-number")).getText();
            return Integer.parseInt(bookingsText) > 0;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void selectSquashTimetable() {
        browser.element(new WebElementLookup(By.xpath("//*[@id='activityTypesList']//a")).withCssClass("ui-link-inherit").withText("\\bOther Activities\\b")).click();
        browser.element(new WebElementLookup(By.xpath("//*[@id='activityList']//a")).withCssClass("ui-link-inherit").withText(".*(Squash).*")).click();
        LOG.debug("Timetable selected");
    }

    public void quit() {
        browser.quit();
    }

    public void test() {
        browser.getSourcePage();
    }

    public void selectSlot(final Date date) {
        try {
            final String dateString = dateFormatter.print(date.getTime());
            final String timeString = timeFormatter.print(date.getTime());
            final By dateTimeXpath = By.xpath("//li[@role='heading' and text()='" + dateString + "']/following-sibling::li//a[contains(., '" + timeString + "')]");
            browser.element(new WebElementLookup(dateTimeXpath)).click();
            if (!browser.getSourcePage().contains(dateString) || !browser.getSourcePage().contains(timeString)) {
                throw new BookingNotFound("Cannot find booking for date " + date);
            }
            LOG.info("Slot selected [" + dateString + " - " + timeString + "]");
            browser.element(new WebElementLookup(By.id("addToBasketBtn"))).click();
            //browser.element(new WebElementLookup(By.className("useVoucher"))).getText().click();
        } catch (final Exception exception) {
            throw new BookingNotFound(exception);
        }
    }

}