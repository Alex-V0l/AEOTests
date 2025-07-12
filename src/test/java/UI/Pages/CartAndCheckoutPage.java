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
    private WebElement pagesHeader;
    @FindBy (xpath = "(//div[contains(@class, 'qa-search-results-list')]//div[contains (@class, 'product-tile')])[3]")
    private WebElement firstItemAfterSearch;
    @FindBy (xpath = "(//div[contains(@class, 'product-tile')])[3]//h3[contains(@class, 'product-name')]")
    private WebElement itemsNameToAddBySearch;
    @FindBy (xpath = "(//div[contains(@class, 'product-tile')])[3]//div[@data-testid='sale-price']")
    private WebElement itemsSalePriceToAddBySearch;
    @FindBy (xpath = "(//div[contains(@class, 'product-tile')])[3]//div[@data-testid='list-price']")
    private WebElement itemsRegularPriceToAddBySearch;
    @FindBy (xpath = "//div[@data-id='modalSidetrayQuickview']")
    private WebElement quickShopModal;
    @FindBy (className ="modal-title")
    private WebElement modalsTitle;
    @FindBy(xpath = "//ul[contains(@class, 'dropdown-menu')]//li[not(contains(@class, 'visually-disabled'))][1]/a")
    private WebElement firstAvailableSizeInsideDropdown;
    @FindBy(name = "addToBag")
    private WebElement addToBagButton;
    @FindBy(name = "add-to-bag")
    private WebElement addToBagButtonInQuickShop;
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
    @FindBy (xpath = "//label[@data-test-accordion='price: high to low']")
    private WebElement highToLowRadio;
    @FindBy (xpath = "//span[@data-test-accordion='sort']")
    private WebElement sortFilter;
    @FindBy (xpath = "//span[@data-test-threshold-max]")
    private WebElement maxValueOfCostForFreeShipping;

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
        return pagesHeader.getText();
    }

    @Step("scroll to first that was found via search")
    public void scrollToFirstItemForBag(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(firstItemAfterSearch).moveToElement(firstItemAfterSearch).pause(2).perform();
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

    @Step("move to 'Quick Shop' button, wait for it and click")
    public void moveWaitAndClickOnQuickShopButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement quickShopButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class, 'product-tile')])[3]//a[contains(@class, 'quickshop-product-btn')]")
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
        By sizeButtonLocator = By.xpath
                ("//div[@role='button' and @aria-label='Size' and contains(@class, 'dropdown-toggle')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sizeButton = wait.until(ExpectedConditions.presenceOfElementLocated(sizeButtonLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", sizeButton);
        js.executeScript("arguments[0].click();", sizeButton);
    }

    @Step("click on first size option inside 'Size' dropdown")
    public void clickFirstAvailableSize(){
        firstAvailableSizeInsideDropdown.click();
    }

    @Step("click on 'Add to Bag' button")
    public void clickOnAddToBag(){
        addToBagButton.click();
    }

    @Step("click on 'Add to Bag' button inside 'Quick Shop' modal")
    public void clickOnAddToBagInQuickShop(){
        addToBagButtonInQuickShop.click();
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

    @Step("scroll and click on increase amount (+) button")
    public void scrollAndClickIncreaseAmountButton(){
        By buttonLocator = By.xpath("//button[@aria-label='increase']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator));
        WebElement increaseAmountButton = driver.findElement(buttonLocator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", increaseAmountButton);
        js.executeScript("arguments[0].click();", increaseAmountButton);
    }

    @Step("click on 'Update Bag' button")
    public void clickUpdateBagButton(){
        updateBagButton.click();
    }

    @Step("get text about amount of items")
    public String getAmountOfItems(){
        return amountOfItemsValue.getText();
    }

    @Step("wait for quantity to be visible after changes")
    public void waitForQuantity(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(addedToCartItemsQuantity));
    }

    @Step("scroll to 'Remove' button and click it")
    public void scrollAndClickOnRemove() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(removeButton).moveToElement(removeButton).pause(2).click().perform();
    }

    @Step("get text from 'Empty Bag' message")
    public String getEmptyBagText() {
        return emptyBagMessage.getText();
    }

    @Step("wait for 'Empty Bag' field to be visible")
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

    @Step("pick Illinois option from 'States'")
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

    @Step("scroll to '2 Day' radio and click")
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

    @Step("get 'Tax' value text")
    public String getTaxValeText(){
        return taxValue.getText();
    }

    @Step("click on radio 'Price: High to Low' inside search result page")
    public void scrollAndClickPriceHighToLow(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(highToLowRadio).moveToElement(highToLowRadio).pause(Duration.ofSeconds(2))
                .click().perform();
    }

    @Step("click on filter 'Sort' inside search result page")
    public void scrollAndClickSort(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(sortFilter).moveToElement(sortFilter).pause(Duration.ofSeconds(2))
                .click().perform();
    }

    @Step("get text of max value cost for free shipping")
    public String getMaxCostOfShipping(){
        return maxValueOfCostForFreeShipping.getText();
    }
}
