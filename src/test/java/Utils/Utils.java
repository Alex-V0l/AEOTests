package Utils;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class Utils {

    private static final int DEFAULT_TIMEOUT = 10;

    public static double parseToDouble (String valueToParse){
        return Double.parseDouble(valueToParse.replaceAll("[^\\d.]", ""));
    }

    public static int parseToInt(String valueToParse){
        return Integer.parseInt(valueToParse.replaceAll("\\D", ""));
    }

    public static String formatSearchQueryForUrl(String query) {
        if (query == null) {
            return "";
        }
        return query.trim().replaceAll("\\s+", "+");
    }

    public static void clickWithJs(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void scrollIntoViewJs(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    public static void scrollToAndClickWithActions(WebDriver driver, WebElement element) {
        new Actions(driver)
                .scrollToElement(element)
                .moveToElement(element)
                .pause(Duration.ofSeconds(2))
                .click()
                .perform();
    }

    public static <T> T waitForCondition(WebDriver driver, Function<WebDriver, T> condition) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT)).until(condition);
    }

    public static void scrollWithActions(WebDriver driver, WebElement element) {
        new Actions(driver)
                .scrollToElement(element)
                .moveToElement(element)
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    public static void moveWithActions(WebDriver driver, WebElement element) {
        new Actions(driver)
                .moveToElement(element)
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    public static void setValueViaJS(WebDriver driver, WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                element, value
        );
    }

    public static String extractNormalizedUrl(WebElement element) {
        String href = element.getDomAttribute("href");
        if (href == null || href.isEmpty()) {
            throw new IllegalArgumentException("Element does not contain a valid href attribute.");
        }
        if (!href.startsWith("http")) {
            href = "https://www.ae.com" + href;
        }
        return href.split("\\?")[0];
    }


}
