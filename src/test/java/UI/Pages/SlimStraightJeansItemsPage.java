package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SlimStraightJeansItemsPage extends BasePage{

    //locators
    @FindBy(xpath = "(//div[@data-test-color-swatches]//div[@role='button'])[1]")
    private WebElement firstColorRadioBrightBlue;
    @FindBy(xpath = "(//div[@data-test-color-swatches]//div[@role='button'])[2]")
    private WebElement secondColorRadioBlack;
    @FindBy (xpath = "//div[@aria-label='Size']")
    private WebElement sizeButton;
    @FindBy(xpath = "//ul[@role='menu']")
    private WebElement sizeDropdown;
    @FindBy(xpath = "//li/a[text()='30 X 30']")
    private WebElement optionSize30X30;
    @FindBy(xpath = "//li/a[text()='28 X 34']")
    private WebElement optionSize28X34;
    @FindBy (className = "dropdown-text")
    private WebElement selectedSizeText;
    @FindBy(xpath = "//span[@data-test-value]")
    private WebElement amountOfItemsValue;
    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement increaseAmountButton;
    @FindBy (name = "addToBag")
    private WebElement addToBagButton;
    @FindBy(xpath = "//div[@data-test-sidetray='added-to-bag']")
    private WebElement addedToBagModal;
    @FindBy (className = "modal-title")
    private WebElement modalsTitle;
    @FindBy (name = "viewBag")
    private WebElement viewBagButton;
    @FindBy(xpath = "//div[@class='modal-body']//div[contains(@class, 'added-to-bag-product')]//div[contains(@class, '_title_')]")
    private WebElement modalsSubtitlesText;
    @FindBy(xpath = "//div[@class='modal-body']//div[contains(@class, 'added-to-bag-product')]")
    private WebElement modalItemsInfoSection;
    @FindBy(xpath = "//div[@class='modal-body']//div[contains(@class, 'added-to-bag-product')]//*[contains(text(),'Slim Straight Jean')]")
    private WebElement itemsNameSection;
    @FindBy(xpath = "//div[@class='modal-body']//span[@data-test-sale-price]")
    private WebElement itemsSalePriceSection;
    @FindBy(xpath = "//div[@class='modal-body']//span[@data-test-price]")
    private WebElement itemsRegularPriceSection;
    @FindBy (xpath = "//div[@class='modal-body']//div[@data-test-product-color]")
    private WebElement itemsColorSection;
    @FindBy(xpath = "//div[@class='modal-body']//div[@data-test-product-size]")
    private WebElement itemsSizeSection;
    @FindBy (xpath = "//div[@class='modal-body']//div[@data-test-product-quantity]")
    private WebElement itemsQuantitySection;

    //methods
    public SlimStraightJeansItemsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("chek if 'Bright Blue' radio is selected")
    public boolean isBrightBlueSelected(){
        return firstColorRadioBrightBlue.getDomAttribute("class").contains("swatch-active");
    }

    @Step("click on 'Bright Blue' radio")
    public void clickBrightBlueRadio(){
        firstColorRadioBrightBlue.click();
    }

    @Step("chek if 'Black' radio is selected")
    public boolean isBlackSelected(){
        return secondColorRadioBlack.getDomAttribute("class").contains("swatch-active");
    }

    @Step("click on 'Black' radio")
    public void clickBlackRadio(){
        secondColorRadioBlack.click();
    }

    @Step("check if photos of the chosen radio color have appeared")
    public boolean isPhotoDisplayedForColor(String photoColor) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            return wait.until(ExpectedConditions.refreshed(ExpectedConditions.attributeContains(
                    By.xpath("(//div[@data-test-grid-item='0']//img)[1]"),
                    "src",
                    photoColor
            )));
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("scroll to 'Size' button and click")
    public void moveToSizeButtonAndClick(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(sizeButton).moveToElement(sizeButton)
                .pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("check that dropdown of sizes is visible")
    public boolean isSizeDropdownIsVisible(){
       return sizeDropdown.isDisplayed();
    }

    @Step("scroll to '30 X 30' size inside dropdown and pick it")
    public void scrollTo30X30SizeAndPick(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(optionSize30X30).moveToElement(optionSize30X30)
                .pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("scroll to '28 X 34' size inside dropdown and pick it")
    public void scrollTo28X34SizeAndPick(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(optionSize28X34).moveToElement(optionSize28X34)
                .pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("get text of selected size")
    public String getSelectedSizeText(){
       return selectedSizeText.getText();
    }

    @Step("get amount of items")
    public String getAmountOfItems(){
        return amountOfItemsValue.getText();
    }

    @Step("check that amount increase button (+) is disabled")
    public boolean isIncreaseAmountButtonDisabled(){
        return !increaseAmountButton.isEnabled();
    }

    @Step("click on increase amount button (+) ")
    public void clickIncreaseAmountButton(){
        increaseAmountButton.click();
    }

    @Step("click on 'Add to Bag' button")
    public void clickAddToBag(){
        addToBagButton.click();
    }

    @Step("check if 'Added to Bag' modal is visible")
    public boolean isAddedToBagModalVisible(){
        return addedToBagModal.isDisplayed();
    }

    @Step("get detailed info about 'Added to Bag' item")
    public String getModalsTitleAddedToBagText(){
        return modalsTitle.getText();
    }

    @Step("click on 'View bag' button")
    public void clickViewBagButton(){
        viewBagButton.click();
    }

    @Step("get item's name from modal 'Added to bag' section")
    public String getItemsNameText (){
        return itemsNameSection.getText();
    }

    @Step("get item's 'Sale Price' from modal 'Added to bag' section")
    public String getItemsSalePriceText(){
        return itemsSalePriceSection.getText();
    }

    @Step("get item's 'Regular Price' from modal 'Added to bag' section")
    public String getItemsRegularPriceText(){
        return itemsRegularPriceSection.getText();
    }

    @Step("get item's 'Color' from modal 'Added to bag' section")
    public String getItemsColorText(){
        return itemsColorSection.getText();
    }

    @Step("get item's 'Size' from modal 'Added to bag' section")
    public String getItemsSizeText(){
        return itemsSizeSection.getText();
    }

    @Step("get item's 'Quantity' from modal 'Added to bag' section")
            public String getItemsQuantityText(){
        return itemsQuantitySection.getText();
    }
}
