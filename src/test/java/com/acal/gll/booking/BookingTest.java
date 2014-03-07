package com.acal.gll.booking;

import com.acal.gll.booking.web.BrowserDriver;
import com.acal.gll.booking.web.WebElementLookup;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class BookingTest {

    @InjectMocks
    private Booking booking;
    @Mock
    private BrowserDriver browserDriverMock;

    private final String username = "username";
    private final String password = "password";

    public void before() {
        initMocks(this);
    }

    public void shouldLogin() throws Exception {
        final WebElement usernameInputElementMock = mock(WebElement.class);
        final WebElement passwordInputElementMock = mock(WebElement.class);

        when(browserDriverMock.element(eq(new WebElementLookup(By.id("login_Email"))))).thenReturn(usernameInputElementMock);
        when(browserDriverMock.element(eq(new WebElementLookup(By.id("login_Password"))))).thenReturn(passwordInputElementMock);
        when(browserDriverMock.element(eq(new WebElementLookup(By.id("login_Submit"))))).thenReturn(mock(WebElement.class));
        when(browserDriverMock.getSourcePage()).thenReturn("[...] Welcome Andrea Caldera [...]");

        assertTrue(booking.login(username, password));
        verify(usernameInputElementMock).sendKeys(username);
        verify(passwordInputElementMock).sendKeys(password);
    }

    public void shouldNotLogin() throws Exception {
        when(browserDriverMock.element(eq(new WebElementLookup(By.id("login_Email"))))).thenReturn(mock(WebElement.class));
        when(browserDriverMock.element(eq(new WebElementLookup(By.id("login_Password"))))).thenReturn(mock(WebElement.class));
        when(browserDriverMock.element(eq(new WebElementLookup(By.id("login_Submit"))))).thenReturn(mock(WebElement.class));
        when(browserDriverMock.getSourcePage()).thenReturn("[...] Some page content [...]");
        assertFalse(booking.login(username, password));
    }

    public void shouldQuit() {
        booking.quit();
        verify(browserDriverMock).quit();
    }

}
