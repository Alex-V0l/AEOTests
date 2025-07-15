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

    private HomeSearchSectionPage homeSearchPage;
    private Utils utils;
    private CartAndCheckoutPage cartCheckoutPage;
    private MensJeansPage mensJeansPage;
    private JeansItemsPage jeansItemsPage;
    private CreateAnAccountPage createAccountPage;
    private SingInPage signInPage;

    public UISteps(WebDriver driver) {
        this.homeSearchPage = new HomeSearchSectionPage(driver);
        this.utils = new Utils(driver);
        this.cartCheckoutPage = new CartAndCheckoutPage(driver);
        this.mensJeansPage = new MensJeansPage(driver);
        this.jeansItemsPage = new JeansItemsPage(driver);
        this.createAccountPage = new CreateAnAccountPage(driver);
        this.signInPage = new SingInPage(driver);
    }

    @Step("Open 'Sign In' modal, check that 'Sign In' modal is visible and check modal header's text")
    public void openSignInModalCheckItsVisibilityAndSignInHeaderText() {
        createAccountPage.clickAccount();
        createAccountPage.waitForAccountModal();
        signInPage.clickSignInBasic();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(signInPage.isSignInModalVisible()).as("'Sign In' modal should be visible").isTrue();
        softly.assertThat(signInPage.getSignInModalsHeaderText()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo("Sign In");
        softly.assertAll();
    }

    @Step("type first name and last name and click 'Sigh in' button")
    public void fillInNecessarySignInFieldsAndClick(String login, String password) {
        signInPage.typeIntoEmail(login);
        signInPage.typeIntoPassword(password);
        signInPage.clickSignIn();
    }

    @Step("type first name and last name, click 'Sign in' button and wait for error")
    public void fillInNecessarySignInFieldsClickAndWait(String login, String password) {
        signInPage.typeIntoEmail(login);
        signInPage.typeIntoPassword(password);
        signInPage.clickSignIn();
        signInPage.waitForError();
    }

    @Step("Go to 'Men's Jeans' section and close adverts modal, if appears")
    public void goToMensJeansAndCloseAdvertsIfAppears() {
        homeSearchPage.moveToMens();
        homeSearchPage.clickOnJeansInsideMens();
        homeSearchPage.closeModalAdverts();
    }

    @Step("Go to 'Men's Jeans' section, scroll to size and click")
    public void goToMensJeansAndClickSize() {
        homeSearchPage.moveToMens();
        homeSearchPage.clickOnJeansInsideMens();
        mensJeansPage.scrollAndClickSize();
    }

    @Step("Select size checkbox and close adverts modal, if appears")
    public void selectSizeCheckboxAndCloseAdvertsIfAppears() {
        homeSearchPage.closeModalAdverts();
        mensJeansPage.isCheckbox28x30Visible();
        mensJeansPage.clickOnCheckbox28x30();
        homeSearchPage.closeModalAdverts();
    }

    @Step("move to size and click, then scroll to first and click")
    public void moveToSizeAndPickFirst(){
        jeansItemsPage.moveToSizeButtonAndClick();
        jeansItemsPage.scrollToFirstAvailableSizeAndPick();
    }

    @Step("move to size and click, then scroll to first, click on it and add item to bag")
    public void moveToSizeAndPickFirstAndAddToBag(){
        jeansItemsPage.moveToSizeButtonAndClick();
        jeansItemsPage.scrollToFirstAvailableSizeAndPick();
        jeansItemsPage.clickAddToBag();
    }

    @Step("on 'Men's Jeans' page sort items from least expensive to most, scroll to first item, click on it," +
            " wait for url and close adverts modal, if appears")
    public void pickFirstCheapestJeansItemAndWaitForUrlAndAdvertsIfAppears() {
        mensJeansPage.scrollAndClickLowToHigh();
        String itemsUrl = mensJeansPage.getFirstCheapestItemsUrl();
        mensJeansPage.scrollAndClickToFirstCheapestItemJeans();
        mensJeansPage.waitForItemsUrl(itemsUrl);
        homeSearchPage.closeModalAdverts();
    }

    @Step("on 'Men's Jeans' page scroll to second item, click on it," +
            " wait for url and close adverts modal, if appears")
    public void pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears() {
        String itemsUrl = mensJeansPage.getSecondsItemsUrl();
        mensJeansPage.scrollAndClickToSecondItemJeans();
        mensJeansPage.waitForItemsUrl(itemsUrl);
        homeSearchPage.closeModalAdverts();
    }

    @Step("Get item info from 'Added to Bag' modal")
    public ItemData getItemDataFromAddedToBagModal() {
        return ItemData.builder()
                .name(jeansItemsPage.getItemsNameText())
                .salePrice(jeansItemsPage.getItemsSalePriceText())
                .regularPrice(jeansItemsPage.getItemsRegularPriceText())
                .color(jeansItemsPage.getItemsColorText())
                .size(jeansItemsPage.getItemsSizeText())
                .quantity(jeansItemsPage.getItemsQuantityText())
                .build();
    }

    @Step("Get item info from Cart page")
    public ItemData getItemDataFromCartPage() {
        return ItemData.builder()
                .name(cartCheckoutPage.getAddedItemsName())
                .salePrice(cartCheckoutPage.getAddedItemsSalePrice())
                .regularPrice(cartCheckoutPage.getAddedItemsRegularPrice())
                .color(cartCheckoutPage.getAddedItemsColor())
                .size(cartCheckoutPage.getAddedItemsSize())
                .quantity(cartCheckoutPage.getAddedItemsQuantity())
                .build();
    }

    @Step("Go to 'Search' section, find some items and close adverts, if appears")
    public void searchActionsPath(String searchQuery, String urlAfterSearch){
        homeSearchPage.clickSearch();
        homeSearchPage.waitForSearchModal();
        homeSearchPage.waitForSearchField();
        homeSearchPage.typeIntoSearchField(searchQuery);
        homeSearchPage.clickLoupeSubmit();
        homeSearchPage.closeModalAdverts();
        homeSearchPage.waitForUrl(urlAfterSearch);
    }

    @Step("choose to sort items from the most expensive to less one and pick first item")
    public void sortByHighToLowPriceAndPickFirstItem() {
        cartCheckoutPage.scrollAndClickSort();
        cartCheckoutPage.scrollAndClickPriceHighToLow();
        cartCheckoutPage.scrollToSecondItemForBag();
    }

    @Step("Get item info from Cart page shortly (only name and prices)")
    public ItemData getItemDataFromCartShorten() {
        String salePrice = null;
        try {
            salePrice = cartCheckoutPage.getAddedItemsSalePrice();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
        }

        return ItemData.builder()
                .name(cartCheckoutPage.getAddedItemsName())
                .salePrice(cartCheckoutPage.getAddedItemsSalePrice())
                .regularPrice(cartCheckoutPage.getAddedItemsRegularPrice())
                .build();
    }

    @Step("Get item info from 'Quick shop' modals")
    public ItemData getItemDataFromQuickShop() {

        return ItemData.builder()
                .name(cartCheckoutPage.getNameFroItemForBag())
                .salePrice(cartCheckoutPage.getSalePriceForItemForBag())
                .regularPrice(cartCheckoutPage.getRegularPriceForItemForBag())
                .build();
    }

    @Step("pick size, add item to bag, go to cart and wait for it's url")
    public void pickSizeAddItemAndGoToCart(){
        cartCheckoutPage.scrollToSizeAndClick();
        cartCheckoutPage.clickFirstAvailableSize();
        cartCheckoutPage.clickOnAddToBag();
        cartCheckoutPage.isAddedToBagModalVisible();
        cartCheckoutPage.clickViewBagButton();
        cartCheckoutPage.waitForUrl(CART_URL);
    }

    @Step("pick size, add item to bag inside 'Quick Shop' modal, go to cart and wait for it's url")
    public void pickSizeAddItemInQuickShopAndGoToCart(){
        cartCheckoutPage.scrollToSizeAndClick();
        cartCheckoutPage.clickFirstAvailableSize();
        cartCheckoutPage.clickOnAddToBagInQuickShop();
        cartCheckoutPage.isAddedToBagModalVisible();
        cartCheckoutPage.clickViewBagButton();
        cartCheckoutPage.waitForUrl(CART_URL);
        cartCheckoutPage.waitForSpinnerToDisappear();
    }

    @Step ("click 'View Bag' and wait for transition to cart")
    public void viewBagAndWaitForCart(){
        jeansItemsPage.clickViewBagButton();
        jeansItemsPage.waitForUrl(CART_URL);
    }

    @Step("get info about sub total, shipping cost, item's name, free shipping progress")
    public CartInfo getInfoFromCart() {
        cartCheckoutPage.scrollToAddedItemsInfoSection();

        CartInfo info = CartInfo.builder()
                .shippingMessage(cartCheckoutPage.getTextOfShippingMessage())
                .shippingProgressValue(cartCheckoutPage.getShippingProgressValue())
                .shippingProgressStatus(cartCheckoutPage.getShippingProgressStatus())
                .maxShippingCostBeforeFree(cartCheckoutPage.getMaxCostOfShipping())
                .addedItemsName(cartCheckoutPage.getAddedItemsName())
                .addedItemsSalePrice(cartCheckoutPage.getAddedItemsSalePrice())
                .addedItemsQuantity(cartCheckoutPage.getAddedItemsQuantity())
                .subTotal(cartCheckoutPage.getSubTotalText())
                .shippingPrice(cartCheckoutPage.getShippingPrice())
                .build();
        return info;
    }

    @Step("increase amount of item, get new quantity, update item and wait for changes")
    public String increaseQuantityGetNewQuantityAndWaitForChanges(String oldQuantity){
        cartCheckoutPage.scrollAndClickIncreaseAmountButton();
        cartCheckoutPage.waitForQuantityChange(oldQuantity);
        String changedAmountOfItems = cartCheckoutPage.getAmountOfItems();
        cartCheckoutPage.clickUpdateBagButton();
        cartCheckoutPage.waitForSpinnerToDisappear();
        cartCheckoutPage.waitForQuantity();
        return changedAmountOfItems;
    }

    @Step("scroll to 'Let's Check Out' button and wait for transition")
    public void scrollToLetsCheckOutClickAndWaitForCheckOutPage(){
        cartCheckoutPage.scrollAndClickOnCheckOut();
        cartCheckoutPage.waitForUrl(CHECKOUT_URL);
    }

    @Step("go to Order Total and fill in all necessary fields and pick state")
    public void goToOrderTotalAndFillInCheckOutForm(String firstName, String lastName, String streetAddress, String city, String zipCode){
        cartCheckoutPage.scrollToOrderTotalAndClick();
        cartCheckoutPage.scrollToFirstName();
        cartCheckoutPage.typeIntoFirstNameField(firstName);
        cartCheckoutPage.typeIntoLastNameField(lastName);
        cartCheckoutPage.typeIntoStreetAddressField(streetAddress);
        cartCheckoutPage.typeIntoCityField(city);
        cartCheckoutPage.scrollAndClickOnState();
        cartCheckoutPage.pickIllinois();
        cartCheckoutPage.typeIntoZipCodeField(zipCode);
    }

    @Step("get info about prices on 'Checkout' page")
    public PricesCheckoutInfo getPricesInCheckout(){

        return PricesCheckoutInfo.builder()
                .standardShippingPriceText(cartCheckoutPage.getStandardPriceText())
                .currentShippingCost(cartCheckoutPage.getShippingPrice())
                .currentOrderTotal(cartCheckoutPage.getTotalPriceText())
                .twoDayShippingCost(cartCheckoutPage.getTwoDayPriceText())
                .currentTaxValue(cartCheckoutPage.getTaxValeText())
                .build();
    }

    @Step("change shipping method for '2 Day' and wait for changes to be applied")
    public void changeFor2DayAndWaitForChanges(){
        cartCheckoutPage.scrollToOrderTotalAndClick();
        cartCheckoutPage.waitForSpinnerToDisappear();
        cartCheckoutPage.scrollToTwoDayAndClick();
        cartCheckoutPage.waitForSpinnerToDisappear();
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
        createAccountPage.clickAccount();
        createAccountPage.waitForAccountModal();
        createAccountPage.clickCreateAnAccount();
        createAccountPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
    }

    @Step("fill in all necessary fields, pick birth date, pick 'I Agree' checkbox and click 'Create Account' button")
    public void fillInRegistrationForm(TestUser user, String firstName, String password, String dayToSelect){
        createAccountPage.scrollAndTypeIntoEmailField(user.getEmail());
        createAccountPage.scrollAndTypeIntoFirstNameField(firstName);
        createAccountPage.scrollAndTypeIntoLastNameField(user.getLastName());
        createAccountPage.scrollAndTypeIntoPasswordField(password);
        createAccountPage.scrollAndTypeIntoConfirmPasswordField(password);
        createAccountPage.scrollAndTypeIntoPostalCodeField(user.getZipCode());
        createAccountPage.scrollAndClickOnMonthSelector();
        createAccountPage.scrollAndClickOnNovember();
        createAccountPage.clickOnDaySelector(dayToSelect);
        createAccountPage.scrollAndClickIAcceptCheckbox();
        createAccountPage.waitForCreateAccountButton();
        createAccountPage.scrollAndClickOnCreateAccountButton();
    }

    @Step("wait for transition to successful creation of a new account page and corresponding message")
    public void waitForSuccessfulUrlAndMessage(){
        createAccountPage.waitForUrl(SUCCESSFUL_CREATION_OF_ACCOUNT_URL);
        createAccountPage.waitForSuccessfulMessage();
    }

    @Step("fill in all necessary fields, pick birth date, pick 'I Agree' checkbox but don't click 'Create Account' button")
    public void fillInRegistrationFormWithoutFinalClick
            (TestUser user, String login, String password, String passwordToConfirm, String dayToSelect){
        createAccountPage.scrollAndTypeIntoEmailField(login);
        createAccountPage.scrollAndTypeIntoFirstNameField(user.getFirstName());
        createAccountPage.scrollAndTypeIntoLastNameField(user.getLastName());
        createAccountPage.scrollAndTypeIntoPasswordField(password);
        createAccountPage.scrollAndTypeIntoConfirmPasswordField(passwordToConfirm);
        createAccountPage.scrollAndTypeIntoPostalCodeField(user.getZipCode());
        createAccountPage.scrollAndClickOnMonthSelector();
        createAccountPage.scrollAndClickOnNovember();
        createAccountPage.clickOnDaySelector(dayToSelect);
        createAccountPage.scrollAndClickIAcceptCheckbox();
    }

    @Step("fill in all necessary fields, pick 'I Agree' checkbox but don't click 'Create Account' button")
    public void fillInRegistrationFormWithoutFinalClickAndBirthDate(TestUser user, String password){
        createAccountPage.scrollAndTypeIntoEmailField(user.getEmail());
        createAccountPage.scrollAndTypeIntoFirstNameField(user.getFirstName());
        createAccountPage.scrollAndTypeIntoLastNameField(user.getLastName());
        createAccountPage.scrollAndTypeIntoPasswordField(password);
        createAccountPage.scrollAndTypeIntoConfirmPasswordField(password);
        createAccountPage.scrollAndTypeIntoPostalCodeField(user.getZipCode());
        createAccountPage.scrollAndClickIAcceptCheckbox();
    }

    @Step("open 'Search' section")
    public void openSearch(){
        homeSearchPage.clickSearch();
        homeSearchPage.waitForSearchModal();
    }

    @Step("open 'Search' section, wait for field to type and type query")
    public void openSearchAndType(String query) {
        openSearch();
        homeSearchPage.waitForSearchField();
        homeSearchPage.typeIntoSearchField(query);
    }

    @Step("click on 'Loupe'-like button and wait for url")
    public void submitAndWait(String expectedUrl){
        homeSearchPage.clickLoupeSubmit();
        homeSearchPage.waitForUrl(expectedUrl);
    }
}
