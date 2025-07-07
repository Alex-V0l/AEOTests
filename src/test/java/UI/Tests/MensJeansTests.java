package UI.Tests;

import Steps.UISteps;
import UI.Pages.HomePage;
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

    private MensJeansPage mJPage;
    private HomePage hPage;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        hPage = new HomePage(driver);
        mJPage = new MensJeansPage(driver);
        uiSteps = new UISteps(driver);
        hPage.openHomePage();
    }

    @DisplayName("Move cursor to Men's section, click on Jeans link, check url and subtitle")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToMeansJeans() {
        String expectedSubtitlesText = "Men's Jeans";

        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        String actualSubtitleText = mJPage.getSubtitleText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mJPage.getCurrentURL()).as("Transition to 'Men's Jeans' page should have been executed")
                .isEqualTo(MENS_JEANS_URL);
        softly.assertThat(actualSubtitleText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSubtitlesText);
        softly.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page pick filter size, check that it is visible, " +
            "pick one checkbox, check that the filter has appeared")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void pickFilterAndCheckboxTest(){
        String expectedUrl = "https://www.ae.com/us/en/c/men/bottoms/jeans/1nlqcolZ2hazmn-filtered";

        uiSteps.goToMensJeansAndClickSize();
        boolean sizeAreaVisible = mJPage.isSizeAreaVisible();

        assertThat(sizeAreaVisible).as("'Size' area with different size checkboxes should vahe appeared").isTrue();

        uiSteps.selectSizeCheckboxAndCloseAdverts();
        String actualUrl = mJPage.getCurrentURL();
        boolean isFilterApplied = mJPage.isFilterSizeVisible();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualUrl).as("Transition to the page with applied filter should have been executed")
                .isEqualTo(expectedUrl);
        softly.assertThat(isFilterApplied).as("Chosen checkbox should have been applied").isTrue();
        softly.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page pick filter size, check that it is visible," +
            " pick one checkbox, check that it is checked and disable it by clicking on appeared filter button")
    @Tags({@Tag("P2"), @Tag("extended")})
    @Test
    void pickFilterAndCheckboxAndDisableFilter(){
        String expectedUrl = "https://www.ae.com/us/en/c/men/bottoms/jeans/1nlqcolZ2hazmn-filtered";

        uiSteps.goToMensJeansAndClickSize();
        boolean sizeAreaVisible = mJPage.isSizeAreaVisible();

        assertThat(sizeAreaVisible).as("'Size' area with different 'Size' checkboxes should have appeared").isTrue();

        uiSteps.selectSizeCheckboxAndCloseAdverts();
        String actualUrl = mJPage.getCurrentURL();
        boolean isFilterApplied = mJPage.isFilterSizeVisible();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualUrl).as("Transition to the page with applied filter should have been executed")
                .isEqualTo(expectedUrl);
        softly.assertThat(isFilterApplied).as("Chosen checkbox should have been applied").isTrue();
        softly.assertAll();

        mJPage.closeChosenFilter();
        boolean isFilterNotApplied = mJPage.isFilterDisappeared();
        assertThat(isFilterNotApplied).as("Filter should have been disabled").isTrue();
    }

    @DisplayName("On 'Men's Jeans' page click on 'Online Exclusives' radio and check its state")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void pickRadioOnlineOnly() {
        String expectedUrl = "https://www.ae.com/us/en/c/men/bottoms/jeans/cat6430041?Ns=product_onlineOnly%7C1";

        uiSteps.goToMensJeansAndCloseAdverts();
        boolean isSortByButtonSelected = mJPage.isSortByButtonSelected();
        boolean isSortByVisible =  mJPage.isSortByAreaVisible();
        boolean isOnlineExclusivesRadioPicked =  mJPage.isOnlineExclusivesSelected();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isSortByVisible).as("'Sort by' button should have been enabled").isTrue();
        softly.assertThat(isSortByButtonSelected).as("Area with radios under 'Sort by' button should be visible")
                .isTrue();
        softly.assertThat(isOnlineExclusivesRadioPicked).as("'Online Exclusives' radio should not be selected")
                .isTrue();
        softly.assertAll();

        mJPage.clickOnlineExclusivesRadio();
        mJPage.scrollToOnlineExclusiveRadio();
        boolean isOnlineExclusivesRadioNotSelected = mJPage.isOnlineExclusivesSelected();
        mJPage.waitForUrl(expectedUrl);
        String actualUrl = mJPage.getCurrentURL();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(isOnlineExclusivesRadioNotSelected)
                .as("'Online Exclusive' Radio should have been selected").isFalse();
        softlyAgain.assertThat(actualUrl).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedUrl);
        softlyAgain.assertAll();
    }

    @DisplayName("On 'Men's Jeans' page scroll to the 'Slim Straight Jeans' item, move cursor to it, click and check url")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void goToCurrentItemsPage() {
        String expectedUrl = "https://www.ae.com/us/en/p/men/slim-fit-jeans/slim-straight-jeans-/ae-airflex-slim-straight-jean/0116_6848_001?menu=cat4840004";

        uiSteps.goToMensJeansAndCloseAdverts();
        mJPage.scrollAndMoveToSSJ();
        mJPage.waitForUrl(expectedUrl);

        String actualUrl = mJPage.getCurrentURL();

        assertThat(actualUrl).as("Transition to 'Slim Straight Jeans' item's page should have been executed")
                .isEqualTo(expectedUrl);
    }
}
