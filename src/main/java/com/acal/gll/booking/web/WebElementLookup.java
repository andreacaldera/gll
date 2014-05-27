package com.acal.gll.booking.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.awaitility.core.ConditionTimeoutException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.core.IsNull.notNullValue;


public class WebElementLookup {

    private static final int DEFAULT_TIMEOUT = 20;
    private final By selector;
    private final Map<String, String> styles;
    private final Map<String, String> attributes;
    private final List<String> cssClasses;
    private final int timeout;
    private String withText;
    private Boolean visibility;
    private Boolean selected;

    public WebElementLookup(final By selector, final int timeout) {
        this.selector = selector;
        this.styles = Maps.newHashMap();
        this.attributes = Maps.newHashMap();
        this.cssClasses = Lists.newArrayList();
        this.timeout = timeout;
        this.visibility = true;
    }

    public WebElementLookup(final By selector) {
        this(selector, DEFAULT_TIMEOUT);
    }

    public WebElementLookup withText(final String regex) {
        withText = regex;
        return this;
    }

    public WebElementLookup withVisibility(final boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public WebElementLookup withSelected(final boolean selected) {
        this.selected = selected;
        return this;
    }

    public WebElementLookup withCssClass(final String... cssClass) {
        cssClasses.addAll(Lists.newArrayList(cssClass));
        return this;
    }

    public WebElementLookup withStyle(final String property, final String value) {
        styles.put(property, value);
        return this;
    }

    public WebElementLookup withAttribute(final String name, final String value) {
        attributes.put(name, value);
        return this;
    }

    public WebElement element(final BrowserDriver browserDriver) {
        try {
            return await().atMost(timeout, SECONDS).until(testFind(browserDriver), notNullValue());
        } catch (final ConditionTimeoutException cte) {
            return null;
        }
    }

    private Callable<WebElement> testFind(final BrowserDriver browserDriver) {
        return new Callable<WebElement>() {
            @Override
            public WebElement call() throws Exception {
                for (final WebElement webElement : browserDriver.findPageElements(selector)) {
                    if (matches(webElement)) {
                        return webElement;
                    }
                }
                return null;
            }
        };
    }

    private boolean matches(final WebElement webElement) {
        if (webElement == null) {
            return false;
        }
        if (!matchText(webElement)) {
            return false;
        }
        if (!matchVisibility(webElement)) {
            return false;
        }
        if (!matchSelected(webElement)) {
            return false;
        }
        if (!matchCssClass(webElement)) {
            return false;
        }
        if (!matchCssStyles(webElement)) {
            return false;
        }
        if (!matchAttributes(webElement)) {
            return false;
        }
        return true;
    }

    private boolean matchText(final WebElement webElement) {
        if (StringUtils.hasText(withText)) {
            final String text = StringUtils.hasText(webElement.getText()) ? webElement.getText() : "";
            return text.matches(withText);
        }
        return true;
    }

    private boolean matchCssClass(final WebElement webElement) {
        if (!CollectionUtils.isEmpty(cssClasses)) {
            return true;
        }
        for (final String cssClass : cssClasses) {
            if (!webElement.getAttribute("class").contains(cssClass)) {
                return false;
            }

        }
        return true;
    }

    private boolean matchVisibility(final WebElement webElement) {
        if (visibility == null) {
            return true;
        }
        return webElement.isDisplayed() == visibility;
    }

    private boolean matchSelected(final WebElement webElement) {
        if (selected == null) {
            return true;
        }
        return selected == webElement.getAttribute("class").contains("selected");
    }

    private boolean matchCssStyles(final WebElement webElement) {
        if (CollectionUtils.isEmpty(styles)) {
            return true;
        }
        for (String property : styles.keySet()) {
            if (styles.get(property) != null && !webElement.getCssValue(property).equals(styles.get(property))) {
                return false;
            }
        }
        return true;
    }

    private boolean matchAttributes(final WebElement webElement) {
        if (CollectionUtils.isEmpty(attributes)) {
            return true;
        }
        for (String name : attributes.keySet()) {
            if (!webElement.getAttribute(name).equals(attributes.get(name))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}

