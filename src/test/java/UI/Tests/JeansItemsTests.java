package UI.Tests;

import Steps.UISteps;
import UI.Models.ItemData;
import UI.Pages.HomeSearchSectionPage;
import UI.Pages.JeansItemsPage;
import Utils.AllureExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;

import static Utils.Constants.CART_URL;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI test")
@ExtendWith(AllureExtension.class)
public class JeansItemsTests extends BaseTest{

    private HomeSearchSectionPage homeSearchPage;
    private JeansItemsPage jeansItemsPage;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        homeSearchPage = new HomeSearchSectionPage(driver);
        jeansItemsPage = new JeansItemsPage(driver);
        uiSteps = new UISteps(driver);
        homeSearchPage.openHomePage();
        homeSearchPage.closeCookieBannerIfVisible();
    }

    @DisplayName("On current item's page find selected color radio," +
            " check that it is selected, click on other first available radio and check that radios states have changed")
    @Tags({@Tag("P1"), @Tag("smoke")})
    @Test
    void changeRadioColorAndChekStateAndPhotos() {
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        WebElement initSelectedRadio = jeansItemsPage.getSelectedColorRadio();

        assertThat(jeansItemsPage.isColorRadioSelected(initSelectedRadio)).as("Found radio should have been selected").isTrue();

        jeansItemsPage.clickOnFirstAvailableColorRadioAndWaitForChanges(initSelectedRadio);

        assertThat(jeansItemsPage.isColorRadioSelected(initSelectedRadio)).as("Radio should not have been selected").isFalse();
    }

    @DisplayName("On current item's page click 'Size' button, check that dropdown is visible, scroll to first size," +
            " click on it and check that size have appeared")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void pickSizeFromDropdownTest(){
        String expectedSizePattern = "\\d{2} X \\d{2}";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        jeansItemsPage.moveToSizeButtonAndClick();

        assertThat(jeansItemsPage.isSizeDropdownIsVisible())
                .as("Dropdown should have appeared after click on size button").isTrue();

        jeansItemsPage.scrollToFirstAvailableSizeAndPick();
        String sizeText = jeansItemsPage.getSelectedSizeText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(sizeText).as("After picking a size you should see what you've chosen").isNotNull();
        softly.assertThat(sizeText).as("Selected size should match format like '30 X 30'").matches(expectedSizePattern);
        softly.assertAll();
    }

    @DisplayName("On current item's page check that you can't change amount of item before you choose size, " +
            "than change amount of item and check it")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void checkAndChangeAmount(){
        String initQuantity = "1";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        String currentQuantity = jeansItemsPage.getAmountOfItems();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(currentQuantity).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(initQuantity);
        softly.assertThat(jeansItemsPage.isIncreaseAmountButtonDisabled()).as("Button have to be disabled").isTrue();
        softly.assertAll();

        uiSteps.moveToSizeAndPickFirst();
        jeansItemsPage.clickIncreaseAmountButton();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(jeansItemsPage.getAmountOfItems()).as("Item's quantity should have increased")
                .isGreaterThan(currentQuantity);
        softlyAgain.assertThat(jeansItemsPage.isIncreaseAmountButtonDisabled()).as("Button have to be enabled").isFalse();
        softlyAgain.assertAll();
    }

    @DisplayName("On current item's page pick size, click 'Add to bag' and check that 'Added to Bag' modal has appeared")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void pickSizeAndAddItemToBag(){
        String expectedTitlesText = "Added to bag!";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.moveToSizeAndPickFirst();
        jeansItemsPage.clickAddToBag();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(jeansItemsPage.isAddedToBagModalVisible()).as("Modal 'Added to Bag' should be visible").isTrue();
        softly.assertThat(jeansItemsPage.getModalsTitleAddedToBagText()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(expectedTitlesText);
        softly.assertAll();
    }

    @DisplayName("On current item's page pick size, click 'Add to bag', get info about added to bag item," +
            " check that added to bag modal has appeared, then go to basket and check url and compare info about item")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void pickSizeAndAddItemToBagAndCheckTransition(){
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        uiSteps.moveToSizeAndPickFirst();
        jeansItemsPage.clickAddToBag();

        assertThat(jeansItemsPage.isAddedToBagModalVisible()).as("Modal 'Added to bag' should be visible").isTrue();

        ItemData itemInAddedToBag = uiSteps.getItemDataFromAddedToBagModal();
        jeansItemsPage.clickViewBagButton();
        jeansItemsPage.waitForUrl(CART_URL);
        ItemData itemInCart = uiSteps.getItemDataFromCartPage();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(jeansItemsPage.getCurrentURL())
                    .as("Transition to the 'Cart' page should have been executed").isEqualTo(CART_URL);
        softlyAgain.assertThat(itemInAddedToBag)
                .as("Items have to be equal, but sale's price may differ because of extra discount")
                .usingRecursiveComparison().ignoringFields("salePrice").isEqualTo(itemInCart);
        softlyAgain.assertAll();
    }
}
