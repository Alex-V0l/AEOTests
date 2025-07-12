package UI.Tests;

import Steps.UISteps;
import UI.Pages.HomeSearchSectionPage;
import UI.Pages.MensJeansPage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import Utils.AllureExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;

import static Utils.Constants.MENS_JEANS_URL;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI Tests")
@ExtendWith(AllureExtension.class)
public class MensJeansTests extends BaseTest{

    private MensJeansPage mJPage;
    private HomeSearchSectionPage hssPage;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        hssPage = new HomeSearchSectionPage(driver);
        mJPage = new MensJeansPage(driver);
        uiSteps = new UISteps(driver);
        hssPage.openHomePage();
    }

    @DisplayName("Move cursor to Men's section, click on Jeans link, check url and subtitle")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToMeansJeans() {
        String expectedSubtitlesText = "Men's Jeans";

        hssPage.moveToMens();
        hssPage.clickOnJeansInsideMens();
        hssPage.waitForUrl(MENS_JEANS_URL);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mJPage.getCurrentURL()).as("Transition to 'Men's Jeans' page should have been executed")
                .isEqualTo(MENS_JEANS_URL);
        softly.assertThat(mJPage.getSubtitleText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSubtitlesText);
        softly.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page pick filter size, check that it is visible, " +
            "pick one checkbox, check that the filter has appeared")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void pickFilterAndCheckboxTest(){
        String expectedUrlPart = "filtered";

        uiSteps.goToMensJeansAndClickSize();

        assertThat(mJPage.isSizeAreaVisible()).as("'Size' area with different size checkboxes should have appeared")
                .isTrue();

        uiSteps.selectSizeCheckboxAndCloseAdvertsIfAppears();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mJPage.getCurrentURL())
                .as("Transition to the page with applied filter should contain \"filtered\" part")
                .contains(expectedUrlPart);
        softly.assertThat(mJPage.isFilterSizeVisible()).as("Chosen checkbox should have been applied").isTrue();
        softly.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page pick filter size, check that it is visible," +
            " pick one checkbox, check that it is checked and disable it by clicking on appeared filter button")
    @Tags({@Tag("P2"), @Tag("extended")})
    @Test
    void pickFilterAndCheckboxAndDisableFilter(){
        String expectedUrlPart = "filtered";

        uiSteps.goToMensJeansAndClickSize();

        assertThat(mJPage.isSizeAreaVisible()).as("'Size' area with different 'Size' checkboxes should have appeared")
                .isTrue();

        uiSteps.selectSizeCheckboxAndCloseAdvertsIfAppears();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mJPage.getCurrentURL())
                .as("Transition to the page with applied filter should contain \"filtered\" part")
                .contains(expectedUrlPart);
        softly.assertThat(mJPage.isFilterSizeVisible()).as("Chosen checkbox should have been applied").isTrue();
        softly.assertAll();

        mJPage.closeChosenFilter();

        assertThat(mJPage.isFilterDisappeared()).as("Filter should have been disabled").isTrue();
    }

    @DisplayName("On 'Men's Jeans' page click on 'Online Exclusives' radio and check its state")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void pickRadioOnlineOnly() {
        String expectedUrl = "https://www.ae.com/us/en/c/men/bottoms/jeans/cat6430041?Ns=product_onlineOnly%7C1";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mJPage.isSortByAreaVisible()).as("'Sort by' button should have been enabled").isTrue();
        softly.assertThat(mJPage.isSortByButtonSelected())
                .as("Area with radios under 'Sort by' button should be visible").isTrue();
        softly.assertThat(mJPage.isOnlineExclusivesSelected()).as("'Online Exclusives' radio should not be selected")
                .isTrue();
        softly.assertAll();

        mJPage.clickOnlineExclusivesRadio();
        mJPage.scrollToOnlineExclusiveRadio();
        boolean isOnlineExclusivesRadioNotSelected = mJPage.isOnlineExclusivesSelected();
        mJPage.waitForUrl(expectedUrl);

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(mJPage.getCurrentURL()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedUrl);
        softlyAgain.assertThat(isOnlineExclusivesRadioNotSelected)
                .as("'Online Exclusive' Radio should have been selected").isFalse();
        softlyAgain.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page scroll to the first item, move cursor to it, click and check url")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToCurrentItemsPage() {
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        String itemsUrl = mJPage.getSecondsItemsUrl();
        mJPage.scrollAndClickToSecondItemJeans();
        mJPage.waitForItemsUrl(itemsUrl);

        String actualUrl = mJPage.getCurrentURL();

        assertThat(actualUrl).as("Transition to 'Slim Straight Jeans' item's page should have been executed")
                .contains(itemsUrl);
    }
}
