package Steps;

import UI.Models.*;
import UI.Pages.*;
import Utils.Utils;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.NoSuchElementException;

import static Utils.Constants.*;

public class UISteps {

    private HomeSearchSectionPage hssPage;
    private Utils utils;
    private CartAndCheckoutPage cPage;
    private MensJeansPage mJPage;
    private JeansItemsPage jIPage;
    private CreateAnAccountPage CAPage;
    private SingInPage SIPage;

    public UISteps(WebDriver driver) {
        this.hssPage = new HomeSearchSectionPage(driver);
        this.utils = new Utils(driver);
        this.cPage = new CartAndCheckoutPage(driver);
        this.mJPage = new MensJeansPage(driver);
        this.jIPage = new JeansItemsPage(driver);
        this.CAPage = new CreateAnAccountPage(driver);
        this.SIPage = new SingInPage(driver);
    }

    @Step("Open 'Sign In' modal, check that 'Sign In' modal is visible and check modal header's text")
    public void openSignInModalCheckItsVisibilityAndSignInHeaderText() {
        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        SIPage.clickSignInBasic();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SIPage.isSignInModalVisible()).as("'Sign In' modal should be visible").isTrue();
        softly.assertThat(SIPage.getSignInModalsHeaderText()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo("Sign In");
        softly.assertAll();
    }

    @Step("type first name and last name and click 'Sigh in' button")
    public void fillInNecessarySignInFieldsAndClick(String login, String password) {
        SIPage.typeIntoEmail(login);
        SIPage.typeIntoPassword(password);
        SIPage.clickSignIn();
    }

    @Step("type first name and last name, click 'Sign in' button and wait for error")
    public void fillInNecessarySignInFieldsClickAndWait(String login, String password) {
        SIPage.typeIntoEmail(login);
        SIPage.typeIntoPassword(password);
        SIPage.clickSignIn();
        SIPage.waitForError();
    }

    @Step("Go to 'Men's Jeans' section and close adverts modal, if appears")
    public void goToMensJeansAndCloseAdvertsIfAppears() {
        hssPage.moveToMens();
        hssPage.clickOnJeansInsideMens();
        hssPage.closeModalAdverts();
    }

    @Step("Go to 'Men's Jeans' section, scroll to size and click")
    public void goToMensJeansAndClickSize() {
        hssPage.moveToMens();
        hssPage.clickOnJeansInsideMens();
        mJPage.scrollAndClickSize();
    }

    @Step("Select size checkbox and close adverts modal, if appears")
    public void selectSizeCheckboxAndCloseAdvertsIfAppears() {
        hssPage.closeModalAdverts();
        mJPage.isCheckbox28x30Visible();
        mJPage.clickOnCheckbox28x30();
        hssPage.closeModalAdverts();
    }

    @Step("move to size and click, then scroll to first and click")
    public void moveToSizeAndPickFirst(){
        jIPage.moveToSizeButtonAndClick();
        jIPage.scrollToFirstAvailableSizeAndPick();
    }

    @Step("move to size and click, then scroll to first, click on it and add item to bag")
    public void moveToSizeAndPickFirstAndAddToBag(){
        jIPage.moveToSizeButtonAndClick();
        jIPage.scrollToFirstAvailableSizeAndPick();
        jIPage.clickAddToBag();
    }

    @Step("on 'Men's Jeans' page sort items from least expensive to most, scroll to first item, click on it," +
            " wait for url and close adverts modal, if appears")
    public void pickFirstCheapestJeansItemAndWaitForUrlAndAdvertsIfAppears() {
        mJPage.scrollAndClickLowToHigh();
        String itemsUrl = mJPage.getFirstCheapestItemsUrl();
        mJPage.scrollAndClickToFirstCheapestItemJeans();
        mJPage.waitForItemsUrl(itemsUrl);
        hssPage.closeModalAdverts();
    }

    @Step("on 'Men's Jeans' page scroll to second item, click on it," +
            " wait for url and close adverts modal, if appears")
    public void pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears() {
        String itemsUrl = mJPage.getSecondsItemsUrl();
        mJPage.scrollAndClickToSecondItemJeans();
        mJPage.waitForItemsUrl(itemsUrl);
        hssPage.closeModalAdverts();
    }

    @Step("Get item info from 'Added to Bag' modal")
    public ItemData getItemDataFromAddedToBagModal() {
        return ItemData.builder()
                .name(jIPage.getItemsNameText())
                .salePrice(jIPage.getItemsSalePriceText())
                .regularPrice(jIPage.getItemsRegularPriceText())
                .color(jIPage.getItemsColorText())
                .size(jIPage.getItemsSizeText())
                .quantity(jIPage.getItemsQuantityText())
                .build();
    }

    @Step("Get item info from Cart page")
    public ItemData getItemDataFromCartPage() {
        return ItemData.builder()
                .name(cPage.getAddedItemsName())
                .salePrice(cPage.getAddedItemsSalePrice())
                .regularPrice(cPage.getAddedItemsRegularPrice())
                .color(cPage.getAddedItemsColor())
                .size(cPage.getAddedItemsSize())
                .quantity(cPage.getAddedItemsQuantity())
                .build();
    }

    @Step("Go to 'Search' section, find some items and close adverts, if appears")
    public void searchActionsPath(String searchQuery, String urlAfterSearch){
        hssPage.clickSearch();
        hssPage.waitForSearchModal();
        hssPage.waitForSearchField();
        hssPage.typeIntoSearchField(searchQuery);
        hssPage.clickLoupeSubmit();
        hssPage.closeModalAdverts();
        hssPage.waitForUrl(urlAfterSearch);
    }

    @Step("choose to sort items from the most expensive to less one and pick first item")
    public void sortByHighToLowPriceAndPickFirstItem() {
        cPage.scrollAndClickSort();
        cPage.scrollAndClickPriceHighToLow();
        cPage.scrollToSecondItemForBag();
    }

    @Step("Get item info from Cart page shortly (only name and prices)")
    public ItemData getItemDataFromCartShorten() {
        String salePrice = null;
        try {
            salePrice = cPage.getAddedItemsSalePrice();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
        }

        return ItemData.builder()
                .name(cPage.getAddedItemsName())
                .salePrice(cPage.getAddedItemsSalePrice())
                .regularPrice(cPage.getAddedItemsRegularPrice())
                .build();
    }

    @Step("Get item info from 'Quick shop' modals")
    public ItemData getItemDataFromQuickShop() {

        return ItemData.builder()
                .name(cPage.getNameFroItemForBag())
                .salePrice(cPage.getSalePriceForItemForBag())
                .regularPrice(cPage.getRegularPriceForItemForBag())
                .build();
    }

    @Step("pick size, add item to bag, go to cart and wait for it's url")
    public void pickSizeAddItemAndGoToCart(){
        cPage.scrollToSizeAndClick();
        cPage.clickFirstAvailableSize();
        cPage.clickOnAddToBag();
        cPage.isAddedToBagModalVisible();
        cPage.clickViewBagButton();
        cPage.waitForUrl(CART_URL);
    }

    @Step("pick size, add item to bag inside 'Quick Shop' modal, go to cart and wait for it's url")
    public void pickSizeAddItemInQuickShopAndGoToCart(){
        cPage.scrollToSizeAndClick();
        cPage.clickFirstAvailableSize();
        cPage.clickOnAddToBagInQuickShop();
        cPage.isAddedToBagModalVisible();
        cPage.clickViewBagButton();
        cPage.waitForUrl(CART_URL);
        cPage.waitForSpinnerToDisappear();
    }

    @Step ("click 'View Bag' and wait for transition to cart")
    public void viewBagAndWaitForCart(){
        jIPage.clickViewBagButton();
        jIPage.waitForUrl(CART_URL);
    }

    @Step("get info about sub total, shipping cost, item's name, free shipping progress")
    public CartInfo getInfoFromCart() {
        cPage.scrollToAddedItemsInfoSection();

        CartInfo info = CartInfo.builder()
                .shippingMessage(cPage.getTextOfShippingMessage())
                .shippingProgressValue(cPage.getShippingProgressValue())
                .shippingProgressStatus(cPage.getShippingProgressStatus())
                .maxShippingCostBeforeFree(cPage.getMaxCostOfShipping())
                .addedItemsName(cPage.getAddedItemsName())
                .addedItemsSalePrice(cPage.getAddedItemsSalePrice())
                .addedItemsQuantity(cPage.getAddedItemsQuantity())
                .subTotal(cPage.getSubTotalText())
                .shippingPrice(cPage.getShippingPrice())
                .build();
        return info;
    }

    @Step("increase amount of item, get new quantity, update item and wait for changes")
    public String increaseQuantityGetNewQuantityAndWaitForChanges(String oldQuantity){
        cPage.scrollAndClickIncreaseAmountButton();
        cPage.waitForQuantityChange(oldQuantity);
        String changedAmountOfItems = cPage.getAmountOfItems();
        cPage.clickUpdateBagButton();
        cPage.waitForQuantity();
        cPage.waitForSpinnerToDisappear();
        return changedAmountOfItems;
    }

    @Step("scroll to 'Let's Check Out' button and wait for transition")
    public void scrollToLetsCheckOutClickAndWaitForCheckOutPage(){
        cPage.scrollAndClickOnCheckOut();
        cPage.waitForUrl(CHECKOUT_URL);
    }

    @Step("go to Order Total and fill in all necessary fields and pick state")
    public void goToOrderTotalAndFillInCheckOutForm(String firstName, String lastName, String streetAddress, String city, String zipCode){
        cPage.scrollToOrderTotalAndClick();
        cPage.scrollToFirstName();
        cPage.typeIntoFirstNameField(firstName);
        cPage.typeIntoLastNameField(lastName);
        cPage.typeIntoStreetAddressField(streetAddress);
        cPage.typeIntoCityField(city);
        cPage.scrollAndClickOnState();
        cPage.pickIllinois();
        cPage.typeIntoZipCodeField(zipCode);
    }

    @Step("get info about prices on 'Checkout' page")
    public PricesCheckoutInfo getPricesInCheckout(){

        return PricesCheckoutInfo.builder()
                .standardShippingPriceText(cPage.getStandardPriceText())
                .currentShippingCost(cPage.getShippingPrice())
                .currentOrderTotal(cPage.getTotalPriceText())
                .twoDayShippingCost(cPage.getTwoDayPriceText())
                .currentTaxValue(cPage.getTaxValeText())
                .build();
    }

    @Step("change shipping method for '2 Day' and wait for changes to be applied")
    public void changeFor2DayAndWaitForChanges(){
        cPage.scrollToOrderTotalAndClick();
        cPage.waitForSpinnerToDisappear();
        cPage.scrollToTwoDayAndClick();
        cPage.waitForSpinnerToDisappear();
    }

    @Step("get all prices in double format")
    public ParsedToDoublePricesCheckoutInfo getAsDoubleValuesOfPrices(PricesCheckoutInfo checkoutInfo){

        return ParsedToDoublePricesCheckoutInfo.builder()
                .standardShippingPrice(utils.parseToDouble(checkoutInfo.getStandardShippingPriceText()))
                .currentShippingCost(utils.parseToDouble(checkoutInfo.getCurrentShippingCost()))
                .currentOrderTotal(utils.parseToDouble(checkoutInfo.getCurrentOrderTotal()))
                .twoDayShippingCost(utils.parseToDouble(checkoutInfo.getTwoDayShippingCost()))
                .currentTaxValue(utils.parseToDouble(checkoutInfo.getCurrentTaxValue()))
                .build();
    }

    @Step("go to 'Create An Account' page")
    public void goToCreateAnAccount(){
        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
    }

    @Step("fill in all necessary fields, pick birth date, pick 'I Agree' checkbox and click 'Create Account' button")
    public void fillInRegistrationForm(TestUser user, String firstName, String password, String dayToSelect){
        CAPage.scrollAndTypeIntoEmailField(user.getEmail());
        CAPage.scrollAndTypeIntoFirstNameField(firstName);
        CAPage.scrollAndTypeIntoLastNameField(user.getLastName());
        CAPage.scrollAndTypeIntoPasswordField(password);
        CAPage.scrollAndTypeIntoConfirmPasswordField(password);
        CAPage.scrollAndTypeIntoPostalCodeField(user.getZipCode());
        CAPage.scrollAndClickOnMonthSelector();
        CAPage.scrollAndClickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.scrollAndClickIAcceptCheckbox();
        CAPage.waitForCreateAccountButton();
        CAPage.scrollAndClickOnCreateAccountButton();
    }

    @Step("wait for transition to successful creation of a new account page and corresponding message")
    public void waitForSuccessfulUrlAndMessage(){
        CAPage.waitForUrl(SUCCESSFUL_CREATION_OF_ACCOUNT_URL);
        CAPage.waitForSuccessfulMessage();
    }

    @Step("fill in all necessary fields, pick birth date, pick 'I Agree' checkbox but don't click 'Create Account' button")
    public void fillInRegistrationFormWithoutFinalClick
            (TestUser user, String login, String password, String passwordToConfirm, String dayToSelect){
        CAPage.scrollAndTypeIntoEmailField(login);
        CAPage.scrollAndTypeIntoFirstNameField(user.getFirstName());
        CAPage.scrollAndTypeIntoLastNameField(user.getLastName());
        CAPage.scrollAndTypeIntoPasswordField(password);
        CAPage.scrollAndTypeIntoConfirmPasswordField(passwordToConfirm);
        CAPage.scrollAndTypeIntoPostalCodeField(user.getZipCode());
        CAPage.scrollAndClickOnMonthSelector();
        CAPage.scrollAndClickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.scrollAndClickIAcceptCheckbox();
    }

    @Step("fill in all necessary fields, pick 'I Agree' checkbox but don't click 'Create Account' button")
    public void fillInRegistrationFormWithoutFinalClickAndBirthDate(TestUser user, String password){
        CAPage.scrollAndTypeIntoEmailField(user.getEmail());
        CAPage.scrollAndTypeIntoFirstNameField(user.getFirstName());
        CAPage.scrollAndTypeIntoLastNameField(user.getLastName());
        CAPage.scrollAndTypeIntoPasswordField(password);
        CAPage.scrollAndTypeIntoConfirmPasswordField(password);
        CAPage.scrollAndTypeIntoPostalCodeField(user.getZipCode());
        CAPage.scrollAndClickIAcceptCheckbox();
    }

    @Step("open 'Search' section")
    public void openSearch(){
        hssPage.clickSearch();
        hssPage.waitForSearchModal();
    }

    @Step("open 'Search' section, wait for field to type and type query")
    public void openSearchAndType(String query) {
        openSearch();
        hssPage.waitForSearchField();
        hssPage.typeIntoSearchField(query);
    }

    @Step("click on 'Loupe'-like button and wait for url")
    public void submitAndWait(String expectedUrl){
        hssPage.clickLoupeSubmit();
        hssPage.waitForUrl(expectedUrl);
    }
}
