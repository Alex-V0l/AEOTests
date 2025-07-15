package UI.Tests;

import Steps.UISteps;
import UI.Pages.HomeSearchSectionPage;
import UI.Pages.MensJeansPage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import Utils.AllureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static Utils.Constants.MENS_JEANS_URL;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI Tests")
@ExtendWith(AllureExtension.class)
public class MensJeansTests extends BaseTest{

    private MensJeansPage mensJeansPage;
    private HomeSearchSectionPage homeSearchPage;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        homeSearchPage = new HomeSearchSectionPage(driver);
        mensJeansPage = new MensJeansPage(driver);
        uiSteps = new UISteps(driver);
        homeSearchPage.openHomePage();
        homeSearchPage.closeCookieBannerIfVisible();
    }

    @DisplayName("Move cursor to Men's section, click on Jeans link, check url and subtitle")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToMeansJeans() {
        String expectedSubtitlesText = "Men's Jeans";

        homeSearchPage.moveToMens();
        homeSearchPage.clickOnJeansInsideMens();
        homeSearchPage.waitForUrl(MENS_JEANS_URL);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mensJeansPage.getCurrentURL()).as("Transition to 'Men's Jeans' page should have been executed")
                .isEqualTo(MENS_JEANS_URL);
        softly.assertThat(mensJeansPage.getSubtitleText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSubtitlesText);
        softly.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page pick filter size, check that it is visible, " +
            "pick one checkbox, check that the filter has appeared")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void pickFilterAndCheckboxTest(){
        String expectedUrlPart = "filtered";

        uiSteps.goToMensJeansAndClickSize();

        assertThat(mensJeansPage.isSizeAreaVisible()).as("'Size' area with different size checkboxes should have appeared")
                .isTrue();

        uiSteps.selectSizeCheckboxAndCloseAdvertsIfAppears();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mensJeansPage.getCurrentURL())
                .as("Transition to the page with applied filter should contain \"filtered\" part")
                .contains(expectedUrlPart);
        softly.assertThat(mensJeansPage.isFilterSizeVisible()).as("Chosen checkbox should have been applied").isTrue();
        softly.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page pick filter size, check that it is visible," +
            " pick one checkbox, check that it is checked and disable it by clicking on appeared filter button")
    @Tags({@Tag("P2"), @Tag("extended")})
    @Test
    void pickFilterAndCheckboxAndDisableFilter(){
        String expectedUrlPart = "filtered";

        uiSteps.goToMensJeansAndClickSize();

        assertThat(mensJeansPage.isSizeAreaVisible()).as("'Size' area with different 'Size' checkboxes should have appeared")
                .isTrue();

        uiSteps.selectSizeCheckboxAndCloseAdvertsIfAppears();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mensJeansPage.getCurrentURL())
                .as("Transition to the page with applied filter should contain \"filtered\" part")
                .contains(expectedUrlPart);
        softly.assertThat(mensJeansPage.isFilterSizeVisible()).as("Chosen checkbox should have been applied").isTrue();
        softly.assertAll();

        mensJeansPage.closeChosenFilter();

        assertThat(mensJeansPage.isFilter28x30Disappeared()).as("Filter should have been disabled").isTrue();
    }

    @DisplayName("On 'Men's Jeans' page click on 'Online Exclusives' radio and check its state")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void pickRadioOnlineOnly() {
        String expectedUrlPart = "product_onlineOnl";

        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mensJeansPage.isSortByAreaVisible()).as("'Sort by' button should have been enabled").isTrue();
        softly.assertThat(mensJeansPage.isSortByButtonSelected())
                .as("Area with radios under 'Sort by' button should be visible").isTrue();
        softly.assertThat(mensJeansPage.isOnlineExclusivesSelected()).as("'Online Exclusives' radio should not be selected")
                .isTrue();
        softly.assertAll();

        mensJeansPage.clickOnlineExclusivesRadio();
        mensJeansPage.scrollToOnlineExclusiveRadio();
        boolean isOnlineExclusivesRadioNotSelected = mensJeansPage.isOnlineExclusivesSelected();
        homeSearchPage.waitForUrlContains(expectedUrlPart);

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(mensJeansPage.getCurrentURL()).as("Current url should contain filter name")
                .contains(expectedUrlPart);
        softlyAgain.assertThat(isOnlineExclusivesRadioNotSelected)
                .as("'Online Exclusive' Radio should have been selected").isFalse();
        softlyAgain.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page scroll to the first item, move cursor to it, click and check url")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToCurrentItemsPage() {
        uiSteps.goToMensJeansAndCloseAdvertsIfAppears();
        String itemsUrl = mensJeansPage.getSecondsItemsUrl();
        mensJeansPage.scrollAndClickToSecondItemJeans();
        mensJeansPage.waitForItemsUrl(itemsUrl);

        String actualUrl = mensJeansPage.getCurrentURL();

        assertThat(actualUrl).as("Transition to 'Slim Straight Jeans' item's page should have been executed")
                .contains(itemsUrl);
    }
}
