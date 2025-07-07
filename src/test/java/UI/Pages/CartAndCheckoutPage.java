package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartAndCheckoutPage extends BasePage{

    //locators
    @FindBy (xpath = "//div[contains(@class,'cart-item-info-text')]")
    private WebElement addedItemsInfoSection;
    @FindBy (xpath = "//h3[@data-test-cart-item-name]")
    private WebElement addedToCartItemsName;
    @FindBy (xpath = "//span[@data-test-cart-item-sale-price]")
    private WebElement addedToCartItemsSalePrice;
    @FindBy (xpath = "//span[@data-test-cart-item-price]")
    private WebElement addedToCartItemsRegularPrice;
    @FindBy (xpath = "//div[@data-test-cart-item-color]")
    private WebElement addedToCartItemsColor;
    @FindBy (xpath = "//div[contains(@class,'cart-item-size')]")
    private WebElement addedToCartItemsSize;
    @FindBy (xpath = "//div[@data-test-cart-item-quantity]")
    private WebElement addedToCartItemsQuantity;
    @FindBy (xpath = "//h1[contains(@class, 'page-header')]")
    private WebElement pageHeader;
    @FindBy (xpath = "(//div[@data-product-id='2308_2255_212']//a[contains(@href, '/2308_2255_212')])[1]")
    private WebElement itemToAddBySearch;
    @FindBy (xpath = "//div[@data-product-id='2308_2255_212']//h3[@data-product-name]")
    private WebElement itemsNameToAddBySearch;
    @FindBy (xpath = "//div[@data-product-id='2308_2255_212']//div[@data-testid='sale-price']")
    private WebElement itemsSalePriceToAddBySearch;
    @FindBy (xpath = "//div[@data-product-id='2308_2255_212']//div[@data-testid='list-price']")
    private WebElement itemsRegularPriceToAddBySearch;
    @FindBy (xpath = "//div[@data-id='modalSidetrayQuickview']")
    private WebElement quickShopModal;
    @FindBy (className ="modal-title")
    private WebElement modalsTitle;
    @FindBy(xpath = "//a[@role='menuitem' and text()='L']")
    private WebElement sizeLInsideDropdown;
    @FindBy(xpath = "//button[@data-test-btn='add-to-bag']")
    private WebElement addToBagButton;
    @FindBy (name = "viewBag")
    private WebElement viewBagButton;
    @FindBy(xpath = "//div[@data-test-free-shipping-widget]//span[@data-test-shipping-message]")
    private WebElement shippingMessage;
    @FindBy(xpath = "//progress[@data-test-progress]")
    private WebElement shippingProgressBar;
    @FindBy(xpath = "//div[@data-id='modalSidetrayAddedToBag']")
    private WebElement addedToBagModal;
    @FindBy (xpath = "//div[@data-testid='row-total-value']")
    private WebElement subTotalValue;
    @FindBy(xpath = "//button[@data-test-btn='editCommerceItem']")
    private WebElement editButton;
    @FindBy (xpath = "//div[@data-id='modalSidetrayQuickview']")
    private WebElement editItemModal;
    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement increaseAmountButton;
    @FindBy (xpath = "//button[contains(@class, 'btn-primary') and text()='Update Bag']")
    private WebElement updateBagButton;
    @FindBy(xpath = "//span[@data-test-value]")
    private WebElement amountOfItemsValue;
    @FindBy (name = "removeCommerceItem")
    private WebElement removeButton;
    @FindBy (xpath = "//h2[contains(@class, 'qa-empty-cart-msg')]")
    private WebElement emptyBagMessage;
    @FindBy (xpath = "//button[contains(@data-test-btn, 'go2checkout')]")
    private WebElement checkoutButton;
    @FindBy (xpath = "//span[text()='Checkout']")
    private WebElement checkoutTitle;
    @FindBy (name = "firstname")
    private WebElement firstnameField;
    @FindBy (name = "lastname")
    private WebElement lastnameField;
    @FindBy (name = "streetAddress1")
    private WebElement streetAddressField;
    @FindBy (xpath = "//input[contains(@class, 'form-input-city') and @name='city']")
    private WebElement cityField;
    @FindBy (name = "states")
    private WebElement stateButton;
    @FindBy (name = "postalCode")
    private WebElement postalCodeField;
    @FindBy (xpath = "//div[@data-test-radio-custom='shipping-method-us-std']")
    private WebElement standardRadio;
    @FindBy (xpath = "//div[@data-test-radio-custom='shipping-method-us-2dy']")
    private WebElement twoDayRadio;
    @FindBy (xpath = "//div[@data-test-radio-custom='shipping-method-us-std']//span[@data-test-price]")
    private WebElement standardPrice;
    @FindBy (xpath = "//div[@data-test-radio-custom='shipping-method-us-2dy']//span[@data-test-price]")
    private WebElement twoDayPrice;
    @FindBy (xpath = "//div[@data-testid='row-shipping-value']")
    private WebElement shippingPrice;
    @FindBy (xpath = "//div[@data-testid='row-total-value']")
    private WebElement totalPrice;
    @FindBy (xpath = "//div[@data-testid='row-tax-value']")
    private WebElement taxValue;
    @FindBy (xpath = "//select[@name='states']//option[@value='IL']")
    private WebElement illinoisOption;

    //methods
    public CartAndCheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("scroll to item's info section")
    public void scrollToAddedItemsInfoSection(){
        Actions actions = new Actions(driver);
        actions.moveToElement(addedItemsInfoSection).pause(2).perform();
    }

    @Step("get added to cart item's name")
    public String getAddedItemsName(){
        return addedToCartItemsName.getText();
    }

    @Step("get added to cart item's sale price")
    public String getAddedItemsSalePrice(){
        return addedToCartItemsSalePrice.getText();
    }

    @Step("get added to cart item's regular price")
    public String getAddedItemsRegularPrice(){
        return addedToCartItemsRegularPrice.getText();
    }

    @Step("get added to cart item's color")
    public String getAddedItemsColor(){
        return addedToCartItemsColor.getText();
    }

    @Step("get added to cart item's size")
    public String getAddedItemsSize(){
        return addedToCartItemsSize.getText();
    }

    @Step("get added to cart item's quantity")
    public String getAddedItemsQuantity(){
        return addedToCartItemsQuantity.getText();
    }

    @Step("get page header's text")
    public String getPageHeadersText(){
        return pageHeader.getText();
    }

    @Step("scroll to item for adding to bag that was found via search")
    public void scrollToItemForBag(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(itemToAddBySearch).moveToElement(itemToAddBySearch).pause(2).perform();
    }

    @Step("get item's name that was found via search for adding to bag")
    public String getNameFroItemForBag(){
        return itemsNameToAddBySearch.getText();
    }

    @Step("get item's sale price that was found via search for adding to bag")
    public String getSalePriceForItemForBag(){
        return itemsSalePriceToAddBySearch.getText();
    }

    @Step("get item's regular price that was found via search for adding to bag")
    public String getRegularPriceForItemForBag(){
        return itemsRegularPriceToAddBySearch.getText();
    }

    @Step("move to quick shop button, wait for it and click")
    public void moveWaitAndClickOnQuickShopButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement quickShopButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@data-product-id='2308_2255_212']//a[contains(@class, 'quickshop-product-btn')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", quickShopButton);
    }

    @Step("check that 'Quick Shop' modal is visible")
    public boolean isQuickShopVisible(){
        return quickShopModal.isDisplayed();
    }

    @Step("get text of modal's title")
    public String getModalsTitleText(){
        return modalsTitle.getText();
    }

    @Step("scroll to 'Size' button and click on it")
    public void scrollToSizeAndClick(){
        WebElement size = driver.findElement(By.xpath
                ("//div[@role='button' and @aria-label='Size' and contains(@class, 'dropdown-toggle')]"));
        Actions actions = new Actions(driver);
        actions.scrollToElement(size)
                .moveToElement(size).pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("click on 'L' size inside 'Size' dropdown")
    public void clickLSize(){
        sizeLInsideDropdown.click();
    }

    @Step("click on 'Add to Bag' button")
    public void clickOnAddToBag(){
        addToBagButton.click();
    }

    @Step("click on 'View Bag' button")
    public void clickViewBagButton(){
        viewBagButton.click();
    }

    @Step("get text of shipping message")
    public String getTextOfShippingMessage(){
        return shippingMessage.getText();
    }

    @Step("get shipping progress bar status")
    public String getShippingProgressStatus(){
        return shippingProgressBar.getDomAttribute("data-test-progress");
    }

    @Step("check that 'Added to Bag' modal is visible")
    public boolean isAddedToBagModalVisible(){
        return addedToBagModal.isDisplayed();
    }

    @Step("get sub total value")
    public String getSubTotalText(){
        return subTotalValue.getText();
    }

    @Step("get shipping progress bar value")
    public String getShippingProgressValue(){
        return shippingProgressBar.getDomAttribute("value");
    }

    @Step("get shipping price")
    public String getShippingPrice(){
        return shippingPrice.getText();
    }

    @Step("scroll to 'Edit' button and click on it")
    public void scrollAndClickEditButton(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(editButton).moveToElement(editButton).pause(2).click().perform();
    }

    @Step("check that 'Edit Item' modal is visible")
    public boolean isEditItemModalIsVisible(){
        return editItemModal.isDisplayed();
    }

    @Step("click on increase amount (+) button")
    public void clickIncreaseAmountButton(){
        increaseAmountButton.click();
    }

    @Step("click on 'Update Bag' button")
    public void clickUpdateBagButton(){
        updateBagButton.click();
    }

    @Step("get text about amount of items")
    public String getAmountOfItems(){
        return amountOfItemsValue.getText();
    }

    @Step("wait for quantity to bee visible after changes")
    public void waitForQuantity(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(addedToCartItemsQuantity));
    }

    @Step("scroll to 'Remove' button and click it")
    public void scrollAndClickOnRemove() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(removeButton).moveToElement(removeButton).pause(2).click().perform();
    }

    @Step("get text from empty bag message")
    public String getEmptyBagText() {
        return emptyBagMessage.getText();
    }

    @Step("wait for Empty bag field to be visible")
    public void waitForEmptyBag(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[contains(@class, 'qa-empty-cart-container')]")));
    }

    @Step("scroll to 'Let's Check Out' button and click it")
    public void scrollAndClickOnCheckOut() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(checkoutButton).moveToElement(checkoutButton).pause(2).click().perform();
    }

    @Step("get checkout title's text")
    public String getCheckoutsTitleText(){
        return checkoutTitle.getText();
    }

    @Step("type into 'First Name' field")
    public void typeIntoFirstNameField(String firstName){
        firstnameField.sendKeys(firstName);
    }

    @Step("type into 'Last Name' field")
    public void typeIntoLastNameField(String lastName){
        lastnameField.sendKeys(lastName);
    }

    @Step("type into 'Street Address' field")
    public void typeIntoStreetAddressField(String address){
        streetAddressField.sendKeys(address);
    }

    @Step("type into 'City' field")
    public void typeIntoCityField(String city){
        cityField.sendKeys(city);
    }

    @Step("scroll to 'State' button and click it")
    public void scrollAndClickOnState() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(stateButton).moveToElement(stateButton).pause(2).click().perform();
    }

    @Step("pick Illinois from 'States'")
    public void pickIllinois(){
        illinoisOption.click();
    }

    @Step("type into 'Zip Code' field")
    public void typeIntoZipCodeField(String postalCode){
        postalCodeField.sendKeys(postalCode);
    }

    @Step("check that 'Standard' radio is selected")
    public boolean isStandardRadioSelected(){
       return "true".equals(standardRadio.getDomAttribute("aria-checked"));
    }

    @Step("scroll to '2 day' radio and click")
    public void scrollToTwoDayAndClick() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(twoDayRadio).moveToElement(twoDayRadio).pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("wait for spinner to disappear")
    public void waitForSpinnerToDisappear() {
        By spinnerOverlay = By.xpath("//div[contains(@class, 'progress-inner-wrapper')]");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(spinnerOverlay));
    }

    @Step("get 'Standard' shipping price text")
    public String getStandardPriceText(){
        return standardPrice.getText();
    }

    @Step("get '2 Day' shipping price text")
    public String getTwoDayPriceText(){
        return twoDayPrice.getText();
    }

    @Step("get total price text")
    public String getTotalPriceText(){
        return totalPrice.getText();
    }

    @Step("scroll to 'Order total' section")
    public void scrollToOrderTotalAndClick(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(totalPrice).moveToElement(totalPrice).pause(Duration.ofSeconds(5)).click().perform();
    }

    @Step("scroll to 'First Name' field")
    public void scrollToFirstName(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(firstnameField).moveToElement(firstnameField).pause(2).perform();
    }

    @Step("get Tax value text")
    public String getTaxValeText(){
        return taxValue.getText();
    }
}
