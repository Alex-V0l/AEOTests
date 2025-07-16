package UI.Pages;

import Utils.Utils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JeansItemsPage extends BasePage{

    //locators
    @FindBy (xpath = "//div[@aria-label='Size']")
    private WebElement sizeButton;
    @FindBy(xpath = "//ul[@role='menu']")
    private WebElement sizeDropdown;
    @FindBy(xpath = "//ul[contains(@class, 'dropdown-menu')]//li[not(contains(@class, 'visually-disabled'))][1]/a")
    private WebElement firstSizeAvailableOption;
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
    @FindBy(xpath = "//div[@class='modal-body']//div[contains(@class, 'added-to-bag-product')]//div[contains (@class, 'title')]")
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
    public JeansItemsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("check if color radio is selected")
    public boolean isColorRadioSelected(WebElement radio){
        return radio.getDomAttribute("class").contains("swatch-active");
    }

    @Step("scroll to 'Size' button and click")
    public void moveToSizeButtonAndClick(){
        Utils.scrollToAndClickWithActions(driver, sizeButton);
    }

    @Step("check that dropdown of sizes is visible")
    public boolean isSizeDropdownIsVisible(){
       return sizeDropdown.isDisplayed();
    }

    @Step("scroll to first available size option inside dropdown and pick it")
    public void scrollToFirstAvailableSizeAndPick(){
        Utils.scrollToAndClickWithActions(driver, firstSizeAvailableOption);
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
        Utils.waitForCondition(driver, driver -> !modalsTitle.getText().trim().isEmpty());
        return modalsTitle.getText();
    }

    @Step("click on 'View bag' button")
    public void clickViewBagButton(){
        viewBagButton.click();
    }

    @Step("get item's name from modal 'Added to bag' section")
    public String getItemsNameText (){
        Utils.waitForCondition(driver, driver -> !itemsNameSection.getText().trim().isEmpty());
        return itemsNameSection.getText().trim();
    }

    @Step("get item's 'Sale Price' from modal 'Added to bag' section")
    public String getItemsSalePriceText(){
        Utils.waitForCondition(driver, driver -> !itemsSalePriceSection.getText().trim().isEmpty());
        return itemsSalePriceSection.getText().trim();
    }

    @Step("get item's 'Regular Price' from modal 'Added to bag' section")
    public String getItemsRegularPriceText(){
        Utils.waitForCondition(driver, driver -> !itemsRegularPriceSection.getText().trim().isEmpty());
        return itemsRegularPriceSection.getText().trim();
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

    @Step("get title of selected by default radio color")
    public WebElement getSelectedColorRadio() {
        return driver.findElement(By.xpath("//div[@data-test-color-swatches]//div[contains(@class,'swatch-active')]"));
    }

    @Step("get all radios and click on first available")
    public void clickOnFirstAvailableColorRadioAndWaitForChanges(WebElement previousSelected) {
        List<WebElement> allRadios = driver.findElements
                (By.xpath("//div[@data-test-color-swatches]//div[@role='button']"));
        for (WebElement radio : allRadios) {
            if (!radio.getDomAttribute("class").contains("swatch-active")) {
                Utils.scrollIntoViewJs(driver, radio);
                radio.click();
                break;
            }
        }
        Utils.waitForCondition(driver, driver -> {
            String classAttr = previousSelected.getDomAttribute("class");
            return classAttr == null || !classAttr.contains("swatch-active");
        });
    }
}
