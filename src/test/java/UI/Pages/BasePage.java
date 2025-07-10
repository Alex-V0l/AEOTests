package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Utils.Constants.BASE_URL_UI;

public class BasePage {
    protected static WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Step("get current URL")
    public String getCurrentURL (){
        return driver.getCurrentUrl();
    }

    @Step("open home page")
    public void openHomePage(){
        driver.get(BASE_URL_UI);
    }

    @Step("wait for url")
    public void waitForUrl(String expectedUrl){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    @Step("check if there is a modal layer with shadow root and close it")
    public void closeModalAdverts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.bloomreach-weblayer")));
            SearchContext shadowRoot = shadowHost.getShadowRoot();
            WebElement promoBox = shadowRoot.findElement(By.cssSelector(".weblayer--box-promotion-1"));
            WebElement closeButton = promoBox.findElement(By.cssSelector("button.close"));
            wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            closeButton.click();
            System.out.println("Modal was found and closed.");
            wait.until(ExpectedConditions.invisibilityOf(promoBox));
        } catch (TimeoutException e) {
            System.out.println("Modal layer not present or already closed.");
        } catch (Exception e) {
            System.err.println("Unexpected error while trying to close modal: " + e.getMessage());
        }
    }
}
