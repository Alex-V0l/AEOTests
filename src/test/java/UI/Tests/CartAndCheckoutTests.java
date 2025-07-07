package UI.Tests;

import UI.Pages.CartAndCheckoutPage;
import UI.Pages.HomePage;
import UI.Pages.MensJeansPage;
import UI.Pages.SlimStraightJeansItemsPage;
import Utils.Utils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import Utils.AllureExtension;

import static Utils.Constants.CART_URL;
import static Utils.Constants.CHECKOUT_URL;
import static Utils.Constants.SLIM_STRAIGHT_JEANS_ITEMS_URL;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.Assertions.offset;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI Tests")
@ExtendWith(AllureExtension.class)
public class CartAndCheckoutTests extends BaseTest{

    private HomePage hPage;
    private Utils utils;
    private CartAndCheckoutPage cPage;
    private MensJeansPage mJPage;
    private SlimStraightJeansItemsPage sSJIPage;

    @BeforeEach
    void setup(){
        hPage = new HomePage(driver);
        utils = new Utils(driver);
        cPage = new CartAndCheckoutPage(driver);
        mJPage = new MensJeansPage(driver);
        sSJIPage = new SlimStraightJeansItemsPage(driver);
        cPage.openHomePage();
    }

    @DisplayName("Click on bag button, check text of page's header and url")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToBagCheckHeaderAndUrl(){
        String expectedHeadersText = "Shopping Bag";

        hPage.clickOnBagButton();
        cPage.waitForUrl(CART_URL);
        String actualHeadersText = cPage.getPageHeadersText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cPage.getCurrentURL())
                .as("Transition to the bag page should have been executed").isEqualTo(CART_URL);
        softly.assertThat(actualHeadersText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedHeadersText);
        softly.assertAll();
    }

    @DisplayName("Add item to the bag, using search and quick shop, which price is bigger than 75$," +
            " check that items name and price from search and bag are identical" +
            " and check status of shipping progress bar")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemUsingSearchAndCheckItemAndShipping(){
        String searchQuery = "Raincoat";
        String expectedUrlAfterSearch = "https://www.ae.com/us/en/s/Raincoat";
        String expectedQuickShopTitleText = "Quick Shop";
        String expectedShippingProgressBarStatus = "full";
        String expectedShippingProgressMessage = "Free shipping unlocked — make the most of it!";

        hPage.clickSearch();
        hPage.waitForSearchModal();
        hPage.waitForSearchField();
        hPage.typeIntoSearchField(searchQuery);
        hPage.clickLoupeSubmit();
        hPage.closeModalAdverts();
        hPage.waitForUrl(expectedUrlAfterSearch);
        cPage.scrollToItemForBag();
        String itemsName = cPage.getNameFroItemForBag();
        String itemsSalePrice = cPage.getSalePriceForItemForBag();
        String itemsRegularPrice = cPage.getRegularPriceForItemForBag();
        cPage.moveWaitAndClickOnQuickShopButton();
        boolean isQuickShopModalVisible = cPage.isQuickShopVisible();
        String quickShopTitlesText = cPage.getModalsTitleText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isQuickShopModalVisible).as("'Quick Shop' modal should be visible").isTrue();
        softly.assertThat(quickShopTitlesText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedQuickShopTitleText);
        softly.assertAll();

        cPage.scrollToSizeAndClick();
        cPage.clickLSize();
        cPage.clickOnAddToBag();
        cPage.isAddedToBagModalVisible();
        cPage.clickViewBagButton();
        cPage.waitForUrl(CART_URL);
        String addedToBagItemsName = cPage.getAddedItemsName();
        String addedToBagSalePrice = cPage.getAddedItemsSalePrice();
        String addedToBagRegularPrice = cPage.getAddedItemsRegularPrice();
        String shippingMessageText = cPage.getTextOfShippingMessage();
        String shippingProgressBarStatus = cPage.getShippingProgressStatus();
        String subTotal = cPage.getSubTotalText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(addedToBagItemsName).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(itemsName);
        softlyAgain.assertThat(addedToBagSalePrice).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(itemsSalePrice);
        softlyAgain.assertThat(addedToBagRegularPrice).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(itemsRegularPrice);
        softlyAgain.assertThat(shippingMessageText).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(expectedShippingProgressMessage);
        softlyAgain.assertThat(shippingProgressBarStatus).as("Shipping progress bar have to be full")
                .isEqualTo(expectedShippingProgressBarStatus);
        softlyAgain.assertThat(subTotal)
                .as("Price of the item should be equal to sub total value because shipping is free")
                .isEqualTo(itemsSalePrice);
        softlyAgain.assertAll();
    }

    @DisplayName("Add item to the bag from section of items and current item's page, which price is less than 75$," +
            " check that items name and price from section page and bag are identical" +
            " and check status and value of shipping progress bar")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemFromPageAddToBagCheckItemAndShipping(){
        String expectedShippingMessage = "Spend $37.54 more to unlock free shipping!";
        String expectedShippingProgressStatus = "not-full";
        String expectedShippingPrice = "$7.95";

        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        String itemNameText = sSJIPage.getItemsNameText();
        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(CART_URL);
        String shippingMessage = cPage.getTextOfShippingMessage();
        String shippingProgressValue = cPage.getShippingProgressValue();
        String shippingProgressStatus = cPage.getShippingProgressStatus();
        cPage.scrollToAddedItemsInfoSection();
        String addedItemsName = cPage.getAddedItemsName();
        String addedItemsSalePrice = cPage.getAddedItemsSalePrice();
        String shippingSubtotal = cPage.getSubTotalText();
        String shippingPrice = cPage.getShippingPrice();
        double parsedShippingPrice = utils.parseToDouble(shippingPrice);
        double parsedItemsFinalPrice = utils.parseToDouble(addedItemsSalePrice);
        double parsedShippingSubtotal = utils.parseToDouble(shippingSubtotal);
        double parsedShippingProgressValue = utils.parseToDouble(shippingProgressValue);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(addedItemsName).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(itemNameText);
        softly.assertThat(shippingMessage).as("Messages supposed to be the same").isEqualTo(expectedShippingMessage);
        softly.assertThat(shippingProgressStatus).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedShippingProgressStatus);
        softly.assertThat(parsedShippingProgressValue)
                .as("Values of shipping progress should be equal to sale price of the item if it costs less that 75$")
                .isEqualTo(parsedItemsFinalPrice);
        softly.assertThat(shippingPrice).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedShippingPrice);
        softly.assertThat(parsedShippingSubtotal)
                .as("Sub total must be equal to summa of item's price and shipping cost")
                .isEqualTo(parsedItemsFinalPrice + parsedShippingPrice, offset(0.01));
        softly.assertAll();
    }

    @DisplayName("Add item to the bag, edit it's amount, check that amount and price have changed")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void addItemChangeQuantityAndCheckIt(){
        String expectedModalTitleText = "Edit Item";

        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(CART_URL);
        cPage.scrollToAddedItemsInfoSection();
        String addedItemsQuantity = cPage.getAddedItemsQuantity();
        cPage.scrollAndClickEditButton();
        boolean isEditItemModalVisible = cPage.isEditItemModalIsVisible();
        String ModalsTitleText = cPage.getModalsTitleText();

        SoftAssertions softly =  new SoftAssertions();
        softly.assertThat(isEditItemModalVisible).as("'Edit Item' modal should be visible").isTrue();
        softly.assertThat(ModalsTitleText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedModalTitleText);
        softly.assertAll();

        utils.scrollWithButton();
        cPage.clickIncreaseAmountButton();
        String changedAmountOfItems = cPage.getAmountOfItems();
        cPage.clickUpdateBagButton();
        cPage.waitForQuantity();
        String newAmountOfItems = cPage.getAddedItemsQuantity();
        int parsedFirstAmount = utils.parseToInt(addedItemsQuantity);
        int parsedNewAmount = utils.parseToInt(newAmountOfItems);
        int parsedChangedAmount = utils.parseToInt(changedAmountOfItems);

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(parsedFirstAmount).as("Quantity should have increased").isLessThan(parsedNewAmount);
        softlyAgain.assertThat(parsedNewAmount).as("Changes of quantity should have been applied")
                .isEqualTo(parsedChangedAmount);
        softlyAgain.assertAll();
    }

    @DisplayName("Add item to the bag, remove it and check that that bag is empty")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void addItemRemoveItAndCheck(){
        String expectedMessage = "Your bag is empty. Find something you love!";

        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(CART_URL);
        cPage.scrollAndClickOnRemove();
        cPage.waitForEmptyBag();
        String messageForEmptyBag = cPage.getEmptyBagText();

        assertThat(messageForEmptyBag).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedMessage);
    }

    @DisplayName("Add item to the bag, click on 'Let's Check Out' button and check url and page's title")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void addItemAndGoToCheckOut(){
        String expectedTitleText = "Checkout";

        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(CART_URL);
        cPage.scrollAndClickOnCheckOut();
        cPage.waitForUrl(CHECKOUT_URL);
        String actualTitlesText = cPage.getCheckoutsTitleText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cPage.getCurrentURL())
                .as("Transitions to 'Checkout' page should nave been executed").isEqualTo(CHECKOUT_URL);
        softly.assertThat(actualTitlesText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedTitleText);
        softly.assertAll();
    }

    @DisplayName("Add item to the bag, click on 'Let's Check Out' button" +
            " and check that price in the bag page is the same as in checkout page")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void addItemCheckPriceInBagAndCheckout(){
        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(CART_URL);
        String priceInBag = cPage.getSubTotalText();
        cPage.scrollAndClickOnCheckOut();
        cPage.waitForUrl(CHECKOUT_URL);
        cPage.scrollToOrderTotalAndClick();
        String priceInCheckout = cPage.getTotalPriceText();

        assertThat(priceInCheckout)
                .as("Price should not have changed before address and shipping method wouldn't have been chosen")
                .isEqualTo(priceInBag);
    }

    @DisplayName("Add item to the bag, to go checkout page," +
            " fill all necessary fields," +
            " pick different shipping method and check that 'Order Total' has changed on new shipping method price")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void addItemCheckOutChangeShippingAndCheckPrice() {
        String firstName = "Scott";
        String lastName = "Folk";
        String streetAddress = "533 Elite Ave";
        String city = "West Chicago";
        String zipCode = "60185";

        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(CART_URL);
        cPage.scrollAndClickOnCheckOut();
        cPage.waitForUrl(CHECKOUT_URL);
        cPage.scrollToOrderTotalAndClick();
        cPage.scrollToFirstName();
        cPage.typeIntoFirstNameField(firstName);
        cPage.typeIntoLastNameField(lastName);
        cPage.typeIntoStreetAddressField(streetAddress);
        cPage.typeIntoCityField(city);
        cPage.scrollAndClickOnState();
        cPage.pickIllinois();
        cPage.typeIntoZipCodeField(zipCode);
        boolean isStandardRadioSelected = cPage.isStandardRadioSelected();
        String standardShippingPriceText = cPage.getStandardPriceText();
        String currentShippingCost = cPage.getShippingPrice();
        String currentOrderTotal = cPage.getTotalPriceText();
        String twoDayShippingCost = cPage.getTwoDayPriceText();
        String currentTaxValue = cPage.getTaxValeText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isStandardRadioSelected).as("'Standard' radio should be selected by default").isTrue();
        softly.assertThat(currentShippingCost).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(standardShippingPriceText);
        softly.assertAll();

        cPage.scrollToOrderTotalAndClick();
        cPage.waitForSpinnerToDisappear();
        cPage.scrollToTwoDayAndClick();
        cPage.waitForSpinnerToDisappear();
        String newShippingCost = cPage.getShippingPrice();
        String newOrderTotal = cPage.getTotalPriceText();
        String newTaxValue = cPage.getTaxValeText();
        double parsedCurrentOrderTotal = utils.parseToDouble(currentOrderTotal);
        double parsedCurrentShippingCost = utils.parseToDouble(currentShippingCost);
        double parsedCurrentTaxValue = utils.parseToDouble(currentTaxValue);
        double parsedNewShippingCost = utils.parseToDouble(newShippingCost);
        double parsedNewOrderTotal = utils.parseToDouble(newOrderTotal);
        double parsedNewTaxValue = utils.parseToDouble(newTaxValue);

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(twoDayShippingCost).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(newShippingCost);
        softlyAgain.assertThat(parsedNewShippingCost)
                .as("After adding info about address and changing shipping method shipping cost should have increased")
                .isGreaterThan(parsedCurrentShippingCost);
        softlyAgain.assertThat(parsedNewTaxValue).as("After shipping cost increased tax value increased too")
                .isGreaterThan(parsedCurrentTaxValue);
        softlyAgain.assertThat(parsedNewOrderTotal)
                .as("After shipping cost and tax values increased order total has increased too")
                .isGreaterThan(parsedCurrentOrderTotal);
        softlyAgain.assertAll();
    }
}
