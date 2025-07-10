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

    private HomeSearchSectionPage hssPage;
    private JeansItemsPage jIPage;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        hssPage = new HomeSearchSectionPage(driver);
        jIPage = new JeansItemsPage(driver);
        uiSteps = new UISteps(driver);
        hssPage.openHomePage();
    }

    @DisplayName("On current item's page find selected color radio," +
            " check that it is selected, click on other first available radio and check that radios states have changed")
    @Tags({@Tag("P1"), @Tag("smoke")})
    @Test
    void changeRadioColorAndChekStateAndPhotos() {
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        WebElement initSelectedRadio = jIPage.getSelectedColorRadio();

        assertThat(jIPage.isColorRadioSelected(initSelectedRadio)).as("Found radio should have been selected").isTrue();

        jIPage.clickOnFirstAvailableColorRadioAndWaitForChanges(initSelectedRadio);

        assertThat(jIPage.isColorRadioSelected(initSelectedRadio)).as("Radio should not have been selected").isFalse();
    }

    @DisplayName("On current item's page click 'Size' button, check that dropdown is visible, scroll to first size," +
            " click on it and check that size have appeared")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void pickSizeFromDropdownTest(){
        String expectedSizePattern = "\\d{2} X \\d{2}";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        jIPage.moveToSizeButtonAndClick();

        assertThat(jIPage.isSizeDropdownIsVisible())
                .as("Dropdown should have appeared after click on size button").isTrue();

        jIPage.scrollToFirstAvailableSizeAndPick();
        String sizeText = jIPage.getSelectedSizeText();

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
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        uiSteps.pickSecondJeansItemAndWaitForUrlAndAdvertsIfAppears();
        String initQuantity = jIPage.getAmountOfItems();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(initQuantity).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo("1");
        softly.assertThat(jIPage.isIncreaseAmountButtonDisabled()).as("Button have to be disabled").isTrue();
        softly.assertAll();

        uiSteps.moveToSizeAndPickFirst();
        jIPage.clickIncreaseAmountButton();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(jIPage.getAmountOfItems()).as("Item's quantity should have increased")
                .isGreaterThan(initQuantity);
        softlyAgain.assertThat(jIPage.isIncreaseAmountButtonDisabled()).as("Button have to be enabled").isFalse();
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
        jIPage.clickAddToBag();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(jIPage.isAddedToBagModalVisible()).as("Modal 'Added to Bag' should be visible").isTrue();
        softly.assertThat(jIPage.getModalsTitleAddedToBagText()).as(VALUES_HAVE_TO_BE_EQUAL)
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
        jIPage.clickAddToBag();

        assertThat(jIPage.isAddedToBagModalVisible()).as("Modal 'Added to bag' should be visible").isTrue();

        ItemData itemInAddedToBag = uiSteps.getItemDataFromAddedToBagModal();
        jIPage.clickViewBagButton();
        jIPage.waitForUrl(CART_URL);
        ItemData itemInCart = uiSteps.getItemDataFromCartPage();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(jIPage.getCurrentURL())
                    .as("Transition to the 'Cart' page should have been executed").isEqualTo(CART_URL);
        softlyAgain.assertThat(itemInAddedToBag)
                .as("Items have to be equal, but sale's price may differ because of extra discount")
                .usingRecursiveComparison().ignoringFields("salePrice").isEqualTo(itemInCart);
        softlyAgain.assertAll();
    }
}
