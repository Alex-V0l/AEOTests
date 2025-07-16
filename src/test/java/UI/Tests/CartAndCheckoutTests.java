package UI.Tests;

import Steps.UISteps;
import UI.Models.CartInfo;
import UI.Models.ItemData;
import UI.Models.ParsedToDoublePricesCheckoutInfo;
import UI.Models.PricesCheckoutInfo;
import UI.Pages.CartAndCheckoutPage;
import UI.Pages.HomeSearchSectionPage;
import UI.Pages.JeansItemsPage;
import Utils.Utils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import Utils.AllureExtension;

import java.util.Locale;

import static Utils.Constants.*;
import static org.assertj.core.api.Assertions.offset;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI Tests")
@ExtendWith(AllureExtension.class)
public class CartAndCheckoutTests extends BaseTest{

    private HomeSearchSectionPage homeSearchPage;
    private CartAndCheckoutPage cartCheckoutPage;
    private JeansItemsPage jeansItemsPage;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        homeSearchPage = new HomeSearchSectionPage(driver);
        cartCheckoutPage = new CartAndCheckoutPage(driver);
        jeansItemsPage = new JeansItemsPage(driver);
        uiSteps = new UISteps(driver);
        cartCheckoutPage.openHomePage();
        homeSearchPage.closeCookieBannerIfVisible();
    }

    @DisplayName("Click on bag button, check text of page's header and url")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToBagCheckHeaderAndUrl(){
        String expectedHeadersText = "Shopping Bag";

        homeSearchPage.clickOnBagButton();
        cartCheckoutPage.waitForUrl(CART_URL);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cartCheckoutPage.getCurrentURL())
                .as("Transition to the bag page should have been executed").isEqualTo(CART_URL);
        softly.assertThat(cartCheckoutPage.getPageHeadersText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedHeadersText);
        softly.assertAll();
    }

    @DisplayName("Add item to the bag, using search and quick shop, which price is bigger than 75$," +
            " check that items name and price from search and bag are identical" +
            " and check status of shipping progress bar")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemUsingSearchAndCheckItemAndShipping(){
        String searchQuery = "Raincoat";
        String expectedUrlAfterSearch = BASE_URL_UI + "s/" + searchQuery;
        String expectedQuickShopTitleText = "Quick Shop";
        String expectedShippingProgressBarStatus = "full";
        String expectedShippingProgressMessage = "Free shipping unlocked — make the most of it!";

        uiSteps.searchActionsPath(searchQuery, expectedUrlAfterSearch);
        uiSteps.sortByHighToLowPriceAndPickFirstItem();
        ItemData itemInfoFromQuickShop  = uiSteps.getItemDataFromQuickShop();
        cartCheckoutPage.moveWaitAndClickOnQuickShopButton();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cartCheckoutPage.isQuickShopVisible()).as("'Quick Shop' modal should be visible").isTrue();
        softly.assertThat(cartCheckoutPage.getModalsTitleText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedQuickShopTitleText);
        softly.assertAll();

        uiSteps.pickSizeAddItemInQuickShopAndGoToCart();
        ItemData itemInfoFromCartShorten = uiSteps.getItemDataFromCartShorten();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(itemInfoFromCartShorten).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(itemInfoFromQuickShop);
        softlyAgain.assertThat(cartCheckoutPage.getTextOfShippingMessage()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(expectedShippingProgressMessage);
        softlyAgain.assertThat(cartCheckoutPage.getShippingProgressStatus()).as("Shipping progress bar have to be full")
                .isEqualTo(expectedShippingProgressBarStatus);
        softlyAgain.assertThat(cartCheckoutPage.getSubTotalText())
                .as("Price of the item should be equal to sub total value because shipping is free")
                .isEqualTo(itemInfoFromCartShorten.getSalePrice());
        softlyAgain.assertAll();
    }

    @DisplayName("Add item to the bag from section of items in current item's page, which price is less than 75$," +
            " check that items name and price from section page and bag are identical" +
            " and check status, value of shipping progress bar and prices")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemFromPageAddToBagCheckItemAndShipping(){
        String expectedShippingProgressStatus = "not-full";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickFirstCheapestJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.moveToSizeAndPickFirstAndAddToBag();
        String itemNameText = jeansItemsPage.getItemsNameText();
        uiSteps.viewBagAndWaitForCart();
        CartInfo infoFromCart = uiSteps.getInfoFromCart();
        double parsedShippingPrice = Utils.parseToDouble(infoFromCart.getShippingPrice());
        double parsedItemsFinalPrice = Utils.parseToDouble(infoFromCart.getAddedItemsSalePrice());
        double parsedShippingSubtotal = Utils.parseToDouble(infoFromCart.getSubTotal());
        double parsedShippingProgressValue = Utils.parseToDouble(infoFromCart.getShippingProgressValue());
        double parsedMaxShippingCostForFree = Utils.parseToDouble(infoFromCart.getMaxShippingCostBeforeFree());
        String expectedShippingMessage =
                String.format(Locale.US, "Spend $%.2f more to unlock free shipping!", parsedMaxShippingCostForFree - parsedItemsFinalPrice);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(infoFromCart.getAddedItemsName()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(itemNameText);
        softly.assertThat(infoFromCart.getShippingMessage()).as("Messages supposed to be the same")
                .isEqualTo(expectedShippingMessage);
        softly.assertThat(infoFromCart.getShippingProgressStatus()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(expectedShippingProgressStatus);
        softly.assertThat(parsedShippingProgressValue)
                .as("Values of shipping progress should be equal to sale price of the item if it costs less that 75$")
                .isEqualTo(parsedItemsFinalPrice);
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

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.pickSizeAddItemAndGoToCart();
        CartInfo itemFromCartInfo = uiSteps.getInfoFromCart();
        String initQuantity = String.valueOf(Utils.parseToInt(itemFromCartInfo.getAddedItemsQuantity()));
        cartCheckoutPage.scrollAndClickEditButton();

        SoftAssertions softly =  new SoftAssertions();
        softly.assertThat(cartCheckoutPage.isEditItemModalIsVisible()).as("'Edit Item' modal should be visible").isTrue();
        softly.assertThat(cartCheckoutPage.getModalsTitleText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedModalTitleText);
        softly.assertAll();

        String quantityFromEditItem =
                uiSteps.increaseQuantityGetNewQuantityAndWaitForChanges(initQuantity);
        ItemData updatedItemInfo = uiSteps.getItemDataFromCartPage();
        double parsedFirstSalePrice = Utils.parseToDouble(itemFromCartInfo.getAddedItemsSalePrice());
        int parsedFirstAmount = Utils.parseToInt(itemFromCartInfo.getAddedItemsQuantity());
        int parsedEditedAmount = Utils.parseToInt(quantityFromEditItem);
        int parsedChangedAmount = Utils.parseToInt(updatedItemInfo.getQuantity());
        double parsedChangedSalePrice = Utils.parseToDouble(updatedItemInfo.getSalePrice());

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(parsedFirstAmount).as("Quantity should have increased").isLessThan(parsedChangedAmount);
        softlyAgain.assertThat(parsedEditedAmount).as("Changes of quantity should have been applied")
                .isEqualTo(parsedChangedAmount);
        softlyAgain.assertThat(parsedChangedSalePrice).as("Price should have doubled").isEqualTo(parsedFirstSalePrice*2);
        softlyAgain.assertAll();
    }

    @DisplayName("Add item to the bag, remove it and check that bag is empty")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void addItemRemoveItAndCheck(){
        String expectedMessage = "Your bag is empty. Find something you love!";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.pickSizeAddItemAndGoToCart();
        cartCheckoutPage.scrollAndClickOnRemove();
        cartCheckoutPage.waitForEmptyBag();
        String messageForEmptyBag = cartCheckoutPage.getEmptyBagText();

        assertThat(messageForEmptyBag).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedMessage);
    }

    @DisplayName("Add item to the bag, click on 'Let's Check Out' button and check url and page's title")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void addItemAndGoToCheckOut(){
        String expectedTitleText = "Checkout";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.pickSizeAddItemAndGoToCart();
        uiSteps.scrollToLetsCheckOutClickAndWaitForCheckOutPage();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cartCheckoutPage.getCurrentURL())
                .as("Transitions to 'Checkout' page should nave been executed").isEqualTo(CHECKOUT_URL);
        softly.assertThat(cartCheckoutPage.getCheckoutsTitleText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedTitleText);
        softly.assertAll();
    }

    @DisplayName("Add item to the bag, click on 'Let's Check Out' button" +
            " and check that price in the bag page is the same as in checkout page")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void addItemCheckPriceInBagAndCheckout(){
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.pickSizeAddItemAndGoToCart();
        String priceInBag = cartCheckoutPage.getSubTotalText();
        uiSteps.scrollToLetsCheckOutClickAndWaitForCheckOutPage();
        cartCheckoutPage.scrollToOrderTotalAndClick();

        assertThat(cartCheckoutPage.getTotalPriceText())
                .as("Price should not have changed before address and shipping method wouldn't have been chosen")
                .isEqualTo(priceInBag);
    }

    @DisplayName("Add item to the bag, to go checkout page," +
            " fill all necessary fields," +
            " pick different shipping method and check that 'Order Total' section has changed on new shipping method price")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void addItemCheckOutChangeShippingAndCheckPrice() {
        String firstName = "Scott";
        String lastName = "Folk";
        String streetAddress = "533 Elite Ave";
        String city = "West Chicago";
        String zipCode = "60185";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.pickSizeAddItemAndGoToCart();
        uiSteps.scrollToLetsCheckOutClickAndWaitForCheckOutPage();
        uiSteps.goToOrderTotalAndFillInCheckOutForm(firstName, lastName, streetAddress, city, zipCode);
        boolean isStandardRadioSelected = cartCheckoutPage.isStandardRadioSelected();
        PricesCheckoutInfo pricesCheckoutInfo = uiSteps.getPricesInCheckout();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isStandardRadioSelected).as("'Standard' radio should be selected by default").isTrue();
        softly.assertThat(pricesCheckoutInfo.getCurrentShippingCost()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(pricesCheckoutInfo.getStandardShippingPriceText());
        softly.assertAll();

        uiSteps.changeFor2DayAndWaitForChanges();
        PricesCheckoutInfo pricesCheckoutInfoAfterChangingShipping = uiSteps.getPricesInCheckout();
        ParsedToDoublePricesCheckoutInfo pricesWithStandardShippingAsDouble =
                uiSteps.getAsDoubleValuesOfPrices(pricesCheckoutInfo);
        ParsedToDoublePricesCheckoutInfo pricesWith2DayShippingAsDouble =
                uiSteps.getAsDoubleValuesOfPrices(pricesCheckoutInfoAfterChangingShipping);

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(pricesCheckoutInfo.getTwoDayShippingCost()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(pricesCheckoutInfoAfterChangingShipping.getCurrentShippingCost());
        softlyAgain.assertThat(pricesWith2DayShippingAsDouble.getCurrentShippingCost())
                .as("After adding info about address and changing shipping method shipping cost should have increased")
                .isGreaterThan(pricesWithStandardShippingAsDouble.getCurrentShippingCost());
        softlyAgain.assertThat(pricesWith2DayShippingAsDouble.getCurrentTaxValue())
                .as("After shipping cost increased tax value increased too")
                .isGreaterThan(pricesWithStandardShippingAsDouble.getCurrentTaxValue());
        softlyAgain.assertThat(pricesWith2DayShippingAsDouble.getCurrentOrderTotal())
                .as("After shipping cost and tax values increased order total has increased too")
                .isGreaterThan(pricesWithStandardShippingAsDouble.getCurrentOrderTotal());
        softlyAgain.assertAll();
    }
}
