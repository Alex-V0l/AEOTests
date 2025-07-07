package UI.Tests;

import UI.Pages.CartAndCheckoutPage;
import UI.Pages.HomePage;
import UI.Pages.SlimStraightJeansItemsPage;
import Utils.AllureExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static Utils.Constants.SLIM_STRAIGHT_JEANS_ITEMS_URL;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI test")
@ExtendWith(AllureExtension.class)
public class SlimStraightJeansItemsTests extends BaseTest{

    private HomePage hPage;
    private SlimStraightJeansItemsPage sSJIPage;
    private CartAndCheckoutPage cPage;

    @BeforeEach
    void setup(){
        hPage = new HomePage(driver);
        sSJIPage = new SlimStraightJeansItemsPage(driver);
        cPage = new CartAndCheckoutPage(driver);
        sSJIPage.openPage(SLIM_STRAIGHT_JEANS_ITEMS_URL);
        hPage.closeModalAdverts();
    }

    @DisplayName("Click on 'Bright Blue' color radio," +
            " check that it is selected and check that photos of item has changed")
    @Tags({@Tag("P1"), @Tag("smoke")})
    @Test
    void changeRadioColorAndChekStateAndPhotos() {
        String blackColorItemCode = "6848_001";
        String brightBlueColorItemCode = "7255_426";

        boolean isBlackColorSelected = sSJIPage.isBlackSelected();
        boolean isBrightBlueColorSelected = sSJIPage.isBrightBlueSelected();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isBlackColorSelected).as("Black color should been selected").isTrue();
        softly.assertThat(isBrightBlueColorSelected).as("Bright blue color should not be selected").isFalse();
        softly.assertAll();

        sSJIPage.clickBrightBlueRadio();
        boolean isPhotosForBrightBlueDisplayed = sSJIPage.isPhotoDisplayedForColor(brightBlueColorItemCode);

        assertThat(isPhotosForBrightBlueDisplayed)
                .as("Photos for bright blue color of the item should have appeared").isTrue();

        sSJIPage.clickBlackRadio();
        boolean isPhotoForBlackDisplayed = sSJIPage.isPhotoDisplayedForColor(blackColorItemCode);

        assertThat(isPhotoForBlackDisplayed)
                .as("Photos for black color of the item should have appeared").isTrue();
    }

    @DisplayName("Click 'Size' button, check that dropdown is visible, scroll to element," +
            " click on it and check that it has been applied")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void pickSizeFromDropdownTest(){
        String expectedSize = "30 X 30";

        sSJIPage.moveToSizeButtonAndClick();
        boolean isDropdownVisible = sSJIPage.isSizeDropdownIsVisible();

        assertThat(isDropdownVisible).as("Dropdown should have appeared after click on size button").isTrue();

        sSJIPage.scrollTo30X30SizeAndPick();
        String sizeText = sSJIPage.getSelectedSizeText();

        assertThat(sizeText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSize);
    }

    @DisplayName("Check that you can't change amount of item before you choose size, " +
            "than change amount of item and check it")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void checkAndChangeAmount(){
        String expectedInitAmount = "1";
        String expectedAfterClickAmount = "2";

        String actualInitAmount = sSJIPage.getAmountOfItems();
        boolean isIncreaseAmountButtonDisabled = sSJIPage.isIncreaseAmountButtonDisabled();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualInitAmount).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedInitAmount);
        softly.assertThat(isIncreaseAmountButtonDisabled).as("Button have to be disabled").isTrue();
        softly.assertAll();

        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo30X30SizeAndPick();
        sSJIPage.clickIncreaseAmountButton();
        String actualAfterClickAmount = sSJIPage.getAmountOfItems();
        boolean isIncreaseAmountButtonNotDisabled = sSJIPage.isIncreaseAmountButtonDisabled();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(actualAfterClickAmount).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedAfterClickAmount);
        softlyAgain.assertThat(isIncreaseAmountButtonNotDisabled).as("Button have to be disabled").isFalse();
        softlyAgain.assertAll();
    }

    @DisplayName("Pick size, click add to bag and check that added to bag modal has appeared")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void pickSizeAndAddItemToBag(){
        String expectedTitlesText = "Added to bag!";

        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo30X30SizeAndPick();
        sSJIPage.clickAddToBag();
        boolean isAddedToBagModalVisible = sSJIPage.isAddedToBagModalVisible();
        String TitlesText = sSJIPage.getModalsTitleAddedToBagText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isAddedToBagModalVisible).as("Modal 'Added to Bag' should be visible").isTrue();
        softly.assertThat(TitlesText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedTitlesText);
        softly.assertAll();
    }

    @DisplayName("Pick size, click add to bag, check that added to bag modal has appeared, " +
            "then go to basket and check url")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void pickSizeAndAddItemToBagAndCheckTransition(){
        String expectedTitlesText = "Added to bag!";
        String expectedUrl = "https://www.ae.com/us/en/cart";

        sSJIPage.moveToSizeButtonAndClick();
        sSJIPage.scrollTo28X34SizeAndPick();
        sSJIPage.clickAddToBag();
        boolean isAddedToBagModalVisible = sSJIPage.isAddedToBagModalVisible();
        String titlesText = sSJIPage.getModalsTitleAddedToBagText();
        String itemNameText = sSJIPage.getItemsNameText();
        String itemsSalePriceText = sSJIPage.getItemsSalePriceText();
        String itemsRegularPriceText = sSJIPage.getItemsRegularPriceText();
        String itemsColorText = sSJIPage.getItemsColorText();
        String itemsSizeText = sSJIPage.getItemsSizeText();
        String itemsQuantityText = sSJIPage.getItemsQuantityText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isAddedToBagModalVisible).as("Modal 'Added to bag' should be visible").isTrue();
        softly.assertThat(titlesText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedTitlesText);
        softly.assertAll();

        sSJIPage.clickViewBagButton();
        sSJIPage.waitForUrl(expectedUrl);
        cPage.scrollToAddedItemsInfoSection();
        String addedItemsName = cPage.getAddedItemsName();
        String addedItemsSalePrice = cPage.getAddedItemsSalePrice();
        String addedItemsRegularPrice = cPage.getAddedItemsRegularPrice();
        String addedItemsColor = cPage.getAddedItemsColor();
        String addedItemsSize = cPage.getAddedItemsSize();
        String addedItemsQuantity = cPage.getAddedItemsQuantity();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(sSJIPage.getCurrentURL())
                    .as("Transition to the 'Cart' page should have been executed").isEqualTo(expectedUrl);
        softlyAgain.assertThat(addedItemsName).isEqualTo(itemNameText);
        softlyAgain.assertThat(addedItemsSalePrice).isEqualTo(itemsSalePriceText);
        softlyAgain.assertThat(addedItemsRegularPrice).isEqualTo(itemsRegularPriceText);
        softlyAgain.assertThat(addedItemsColor).isEqualTo(itemsColorText);
        softlyAgain.assertThat(addedItemsSize).isEqualTo(itemsSizeText);
        softlyAgain.assertThat(addedItemsQuantity).isEqualTo(itemsQuantityText);
        softlyAgain.assertAll();
    }
}
