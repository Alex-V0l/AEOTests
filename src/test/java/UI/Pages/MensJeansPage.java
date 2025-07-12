package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MensJeansPage extends BasePage{

    //locators
    @FindBy(xpath = "//h1[contains(@class, 'qa-non-link-label') and text()=\"Men's Jeans\"]")
    private WebElement pagesSubtitle;
    @FindBy (xpath = "//span[@data-test-accordion='size']")
    private WebElement sizeFilterButton;
    @FindBy (id = "side-size-filter")
    private WebElement sizeMenuArea;
    @FindBy (xpath = "//label[@data-test-accordion='28 X 30']")
    private WebElement checkboxSize28x30;
    @FindBy (xpath = "//button[@data-test-toggle-filter='28 X 30']")
    private WebElement enabledFilterSize28x30;
    @FindBy (xpath = "//span[@aria-controls='side-sortBy-filter']")
    private WebElement sortByButton;
    @FindBy (id = "side-sortBy-filter")
    private WebElement sortByArea;
    @FindBy (xpath = "//input[@type='radio' and contains(@class,'qa-radio-online-exclusives')]")
    private WebElement onlineExclusivesRadio;
    @FindBy (xpath = "//label[normalize-space()='Online Exclusives']")
    private WebElement radioOnlineExclusivesLabel;
    @FindBy (xpath = "(//div[@class='results-list qa-results-list']//div[contains (@class, 'product-tile')])[2]")
    private WebElement secondJeansItem;
    @FindBy (xpath = "//label[contains(@data-test-accordion, 'Price: Low to High')]")
    private  WebElement SortFromLowToHighRadio;
    @FindBy (xpath = "((//div[@class='results-list qa-results-list']//div[contains(@class, 'product-tile')])[2]//a[contains(@href, '/us/en/p/')])[1]")
    private WebElement urlOfSecondJeansItem;
    @FindBy (xpath = "((//div[@class='results-list qa-results-list']//div[contains(@class, 'product-tile')])[1]//a[contains(@href, '/us/en/p/')])[1]")
    private WebElement urlOfFirstCheapestJeansItem;

    //methods
    public MensJeansPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("get text of 'Men's' page's subtitle")
    public String getSubtitleText(){
        return pagesSubtitle.getText();
    }

    @Step("scroll to 'Size' button and click on it")
    public void scrollAndClickSize(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", sizeFilterButton);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until
                (ExpectedConditions.elementToBeClickable(sizeFilterButton));
        js.executeScript("arguments[0].click();", sizeFilterButton);
    }

    @Step("Check if 'Size' area is visible after clicking the size button")
    public boolean isSizeAreaVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(sizeMenuArea)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("check if checkbox  '28x30' is visible")
    public boolean isCheckboxVisible(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(checkboxSize28x30)).isEnabled();
        } catch (TimeoutException e){
            return false;
        }
    }

    @Step("click on checkbox '28x30'")
    public void clickOnCheckbox28x30(){
        checkboxSize28x30.click();
    }

    @Step("scroll to filter '28x30' and check if it was applied and visible")
    public boolean isFilterSizeVisible(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(enabledFilterSize28x30).moveToElement(enabledFilterSize28x30)
                .pause(Duration.ofSeconds(2)).perform();
        return enabledFilterSize28x30.isDisplayed();
    }

    @Step("close filter '28x30' by clicking on it")
    public void closeChosenFilter(){
        enabledFilterSize28x30.click();
    }

    @Step("scroll to 'Size' filter are and check that filter '28x30' disappeared")
    public boolean isFilterDisappeared(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(sizeFilterButton).moveToElement(sizeFilterButton)
                .pause(Duration.ofSeconds(2)).perform();
        return driver.findElements(By.xpath("//button[@data-test-toggle-filter='28 X 30']")).isEmpty();
    }

    @Step("scroll to 'Sort by' button and check if 'Sort by' radio is selected")
    public boolean isSortByButtonSelected(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(sortByButton).moveToElement(sortByButton)
                .pause(Duration.ofSeconds(2)).perform();
        return sortByButton.getDomAttribute("aria-expanded").equals("true");
    }

    @Step("check if 'Sort by' area is visible")
    public boolean isSortByAreaVisible(){
        return sortByArea.isDisplayed();
    }

    @Step("check that 'Online Exclusives' radio button is not selected")
    public boolean isOnlineExclusivesSelected() {
        return !onlineExclusivesRadio.isSelected();
    }

    @Step("scroll to 'Online Exclusive' radio")
    public void scrollToOnlineExclusiveRadio(){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated
                        (By.xpath("//label[@data-test-accordion='Online Exclusives']")));
        WebElement onlineExclusive = driver.findElement(By.xpath("//label[@data-test-accordion='Online Exclusives']"));

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", onlineExclusive);
        } catch (StaleElementReferenceException e) {
            onlineExclusive = driver.findElement(By.xpath("//label[@data-test-accordion='Online Exclusives']"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", onlineExclusive);
        }
    }

    @Step("click on 'Online Exclusives' checkbox")
    public void clickOnlineExclusivesRadio(){
        radioOnlineExclusivesLabel.click();
    }

    @Step("scroll to second item, move cursor to it and click")
    public void scrollAndClickToSecondItemJeans(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(secondJeansItem)
                .moveToElement(secondJeansItem).pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("scroll to first item from cheapest, move cursor to it and click")
    public void scrollAndClickToFirstCheapestItemJeans(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("(//div[@class='results-list qa-results-list']//div[contains (@class, 'product-tile')])[1]")));
        WebElement firstCheapestJeansItem = driver.findElement
                (By.xpath("(//div[@class='results-list qa-results-list']//div[contains (@class, 'product-tile')])[1]"));

        Actions actions = new Actions(driver);
        actions.scrollToElement(firstCheapestJeansItem)
                .moveToElement(firstCheapestJeansItem).pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("scroll to 'Low to High' radio an click")
    public void scrollAndClickLowToHigh(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(SortFromLowToHighRadio).moveToElement(SortFromLowToHighRadio).click().perform();
    }

    @Step ("get text of second item from 'Men's Jeans' page")
    public String getSecondsItemsUrl(){
        String href = urlOfSecondJeansItem.getDomAttribute("href");
        if (!href.startsWith("http")) {
            href = "https://www.ae.com" + href;
        }
        return href.split("\\?")[0];
    }

    @Step ("get text of first cheapest item from 'Men's Jeans' page after sorting")
    public String getFirstCheapestItemsUrl(){
        String href = urlOfFirstCheapestJeansItem.getDomAttribute("href");
        if (!href.startsWith("http")) {
            href = "https://www.ae.com" + href;
        }
        return href.split("\\?")[0];
    }

    @Step("wait for second item's url")
    public void waitForItemsUrl(String itemsUrl){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains(itemsUrl));
    }
}
