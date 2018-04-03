package examples.tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class SignInErrorsTests {

    @Test
    public void signInBlankPasswordUnsuccessful() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("deviceName", "Android_Emulator");
        capabilities.setCapability("browserName", "Chrome");

        AndroidDriver driver = new AndroidDriver<>(
                new URL("http://localhost:4723/wd/hub"), capabilities);

        driver.get("http://a.testaddressbook.com");
        driver.findElement(By.tagName("button")).click();
        driver.findElement(By.id("sign-in")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement emailElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("session_email")));

        emailElement.sendKeys("user@example.com");
        driver.findElement(By.id("session_password")).sendKeys("");
        driver.findElement(By.name("commit")).click();

        By alertNotice = By.className("alert-notice");
        assertEquals(1, driver.findElements(alertNotice).size());

        assertEquals("Address Book - Sign In", driver.getTitle());
        assertEquals("http://a.testaddressbook.com/session", driver.getCurrentUrl());

        By currentUser = By.cssSelector("span[data-test=current-user]");
        assertEquals(0, driver.findElements(currentUser).size());

        driver.quit();
    }

}