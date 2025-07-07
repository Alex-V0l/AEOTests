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
    private WebElement sizeFiletButton;
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
    @FindBy (xpath = "//div[@data-test-product-tile-image-container]//img[@data-test='product-image' and contains(@data-track-args, '\"interaction_desc\":\"0116_6848_001\"')]")
    private WebElement slimStraightJeansItem;

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
        Actions actions = new Actions(driver);
        actions.scrollToElement(sizeFiletButton).moveToElement(sizeFiletButton)
                .pause(Duration.ofSeconds(2)).click().perform();
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
        actions.scrollToElement(sizeFiletButton).moveToElement(sizeFiletButton)
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
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@data-test-accordion='Online Exclusives']")));

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

    @Step("scroll to 'Slim Straight Jeans' item, move cursor to it and click")
    public void scrollAndMoveToSSJ(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(slimStraightJeansItem)
                .moveToElement(slimStraightJeansItem).pause(Duration.ofSeconds(2)).click().perform();
    }
}
