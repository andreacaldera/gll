package com.acal.gll.booking.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BrowserDriver {

    private static final int ELEMENT_LOOKUP_TIMEOUT = 10;
    private final WebDriver driver;

    public BrowserDriver(final WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void load(String url) {
        driver.get(url);
    }

    public WebElement findPageElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_LOOKUP_TIMEOUT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> findPageElements(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_LOOKUP_TIMEOUT);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement element(final WebElementLookup webElementLookup) {
        return webElementLookup.element(this);
    }

    public List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    public String getSourcePage() {
        return driver.getPageSource();
    }

    public void quit() {
        driver.quit();
    }

}
