package UI.Tests;

import Steps.UISteps;
import UI.Pages.HomeSearchSectionPage;
import Utils.AllureExtension;
import Utils.Utils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static Utils.Constants.BASE_URL_UI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI tests")
@ExtendWith(AllureExtension.class)
public class HomePageSearchTests extends BaseTest{

    private HomeSearchSectionPage homeSearchPage;
    private Utils utils;
    private UISteps uiSteps;

    @BeforeEach
    void setup(){
        homeSearchPage = new HomeSearchSectionPage(driver);
        utils = new Utils(driver);
        uiSteps = new UISteps(driver);
        homeSearchPage.openHomePage();
    }

    @DisplayName("Click on Loupe-like button and switch to a modal window with Search")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void clickSearchAndCheckModal(){
        uiSteps.openSearch();

        assertThat(homeSearchPage.isSearchModalVisible())
                .as("Modal should be visible after click on Loupe-like button").isTrue();
    }

    @DisplayName("Type name of the item in the 'Search' field, click on Loupe-like button and check url and status code")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void searchItemsAndCheckUrlAndSubtitle() {
        String textToFind = "new york yankees";
        String formattedAsQueryText = utils.formatSearchQueryForUrl(textToFind);
        String expectedUrl = BASE_URL_UI + "s/" + formattedAsQueryText;

        uiSteps.openSearch();

        assertThat(homeSearchPage.isSearchModalVisible())
                .as("Modal should be visible after click on Loupe-like button").isTrue();

        homeSearchPage.waitForSearchField();
        homeSearchPage.typeIntoSearchField(textToFind);
        uiSteps.submitAndWait(expectedUrl);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(homeSearchPage.getCurrentURL())
                .as("Transition to the corresponding to query page should have been executed").isEqualTo(expectedUrl);
        softly.assertThat(homeSearchPage.getSubtitleAfterSearchText())
                .as("Subtitle text should contain query text").contains(textToFind);
        softly.assertAll();
    }

    @DisplayName("Type name of the item in the 'Search' field and check that dropdown is visible")
    @Tags({@Tag("P1"), @Tag("smoke")})
    @Test
    void searchAndWaitSuggestions(){
        String textToFind = "new york";

        uiSteps.openSearchAndType(textToFind);

        assertThat(homeSearchPage.isSuggestionsDropdownVisible())
                .as("After typing part of the item's name suggestions should have appeared").isTrue();
    }

    @DisplayName("Type name of the item in the search field, check that dropdown is visible, " +
            "click on the appeared option, check url and subtitle")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void searchAndPickSuggestion() {
        String textToFind = "new york";
        String queryText = utils.formatSearchQueryForUrl(textToFind);
        String expectedPartUrl = BASE_URL_UI + "s/" + queryText;

        uiSteps.openSearchAndType(textToFind);

        assertThat(homeSearchPage.isSuggestionsDropdownVisible())
                .as("After typing part of the item's name suggestions should have appeared").isTrue();

        homeSearchPage.clickOnSuitableSuggestion();
        homeSearchPage.waitForUrlContains(expectedPartUrl);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(homeSearchPage.getCurrentURL())
                .as("Transition to the corresponding to query page should have been executed").contains(expectedPartUrl);
        softly.assertThat(homeSearchPage.getSubtitleAfterSearchText())
                .as("Subtitle text should contain query text").contains(textToFind);
        softly.assertAll();
    }

    @DisplayName("Type the name of the item in the search field and make mistake in one letter - " +
            "check that simial type of items with correct name has appeared in dropdown")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void typeWithMistakeAndCheckSuggestion(){
        String textToFind = "rainciat";
        String expectedSuggestionText = "Raincoat";

        uiSteps.openSearchAndType(textToFind);

        assertThat(homeSearchPage.isSuggestionsDropdownVisible())
                .as("After typing part of the item's name suggestions should have appeared").isTrue();

        homeSearchPage.waitForRainciatSuggestion();

        assertThat(homeSearchPage.getSuggestionRainciatText())
                .as("System should recognize the mistake and make a right suggestion")
                .isEqualTo(expectedSuggestionText);
    }

    @DisplayName("Type name of non-existing item in the 'Search' field and check message")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void searchForNonExistingItem() {
        String textToFind = "000000000000";
        String expectedErrorText = "Sorry! We couldn't find a match for " + textToFind + ".";
        String expectedUrl = BASE_URL_UI + "s/" + textToFind;

        uiSteps.openSearchAndType(textToFind);
        uiSteps.submitAndWait(expectedUrl);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(homeSearchPage.getTextOfErrorSearchMessage()).as("Error message should contain query")
                .isEqualTo(expectedErrorText);
        softly.assertThat(homeSearchPage.getCurrentURL()).as("url should contain query").isEqualTo(expectedUrl);
        softly.assertAll();
    }
}
