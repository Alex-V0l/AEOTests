package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utils.Utils;

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
        Utils.scrollIntoViewJs(driver, sizeFilterButton);
        Utils.waitForCondition(driver, ExpectedConditions.elementToBeClickable(sizeFilterButton));
        Utils.clickWithJs(driver, sizeFilterButton);
    }

    @Step("Check if 'Size' area is visible after clicking the size button")
    public boolean isSizeAreaVisible() {
        try {
            return Utils.waitForCondition(driver, ExpectedConditions.visibilityOf(sizeMenuArea)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("check if checkbox  '28x30' is visible")
    public boolean isCheckbox28x30Visible(){
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
        Utils.scrollWithActions(driver, enabledFilterSize28x30);
        return enabledFilterSize28x30.isDisplayed();
    }

    @Step("close filter '28x30' by clicking on it")
    public void closeChosenFilter(){
        enabledFilterSize28x30.click();
    }

    @Step("scroll to 'Size' filter are and check that filter '28x30' disappeared")
    public boolean isFilter28x30Disappeared(){
        Utils.scrollWithActions(driver, sizeFilterButton);
        return driver.findElements(By.xpath("//button[@data-test-toggle-filter='28 X 30']")).isEmpty();
    }

    @Step("scroll to 'Sort by' button and check if 'Sort by' radio is selected")
    public boolean isSortByButtonSelected(){
        Utils.scrollWithActions(driver, sortByButton);
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

    @Step("scroll to 'Online Exclusive' radio with retry")
    public void scrollToOnlineExclusiveRadio(){
        Utils.waitForCondition(driver, ExpectedConditions.presenceOfElementLocated
                (By.xpath("//label[@data-test-accordion='Online Exclusives']")));
        WebElement onlineExclusive = driver.findElement(By.xpath("//label[@data-test-accordion='Online Exclusives']"));

        try {
            Utils.scrollWithActions(driver, onlineExclusive);
        } catch (StaleElementReferenceException e) {
            onlineExclusive = driver.findElement(By.xpath("//label[@data-test-accordion='Online Exclusives']"));
            Utils.scrollWithActions(driver, onlineExclusive);
        }
    }

    @Step("click on 'Online Exclusives' checkbox")
    public void clickOnlineExclusivesRadio(){
        radioOnlineExclusivesLabel.click();
    }

    @Step("scroll to second item, move cursor to it and click")
    public void scrollAndClickToSecondItemJeans(){
        Utils.scrollToAndClickWithActions(driver, secondJeansItem);
    }

    @Step("scroll to first item from cheapest, move cursor to it and click")
    public void scrollAndClickToFirstCheapestItemJeans(){
        Utils.waitForCondition(driver, ExpectedConditions.presenceOfElementLocated
                (By.xpath("(//div[@class='results-list qa-results-list']//div[contains (@class, 'product-tile')])[1]")));
        WebElement firstCheapestJeansItem = driver.findElement
                (By.xpath("(//div[@class='results-list qa-results-list']//div[contains (@class, 'product-tile')])[1]"));
        Utils.scrollToAndClickWithActions(driver, firstCheapestJeansItem);
    }

    @Step("scroll to 'Low to High' radio an click")
    public void scrollAndClickLowToHigh(){
        Utils.scrollToAndClickWithActions(driver,SortFromLowToHighRadio);
    }

    @Step ("get url of second item from 'Men's Jeans' page")
    public String getSecondsItemsUrl(){
        return Utils.extractNormalizedUrl(urlOfSecondJeansItem);
    }

    @Step ("get text of first cheapest item from 'Men's Jeans' page after sorting")
    public String getFirstCheapestItemsUrl(){
        WebElement firstCheapestItemsUrl = driver.findElement(By.xpath
                ("((//div[@class='results-list qa-results-list']//div[contains(@class, 'product-tile')])[1]//a[contains(@href, '/us/en/p/')])[1]"));
        return Utils.extractNormalizedUrl(firstCheapestItemsUrl);
    }

    @Step("wait for item's url")
    public void waitForItemsUrl(String itemsUrl){
        Utils.waitForCondition(driver, ExpectedConditions.urlContains(itemsUrl));
    }
}
