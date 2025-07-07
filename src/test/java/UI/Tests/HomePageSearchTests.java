package UI.Tests;

import UI.Pages.HomePage;
import Utils.AllureExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI tests")
@ExtendWith(AllureExtension.class)
public class HomePageSearchTests extends BaseTest{

    private HomePage hPage;

    @BeforeEach
    void setup(){
        hPage = new HomePage(driver);
        hPage.openHomePage();
    }

    @DisplayName("Click on Loupe-like button and switch to a modal window with Search")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void clickSearchAndCheckModal(){
        hPage.clickSearch();
        hPage.waitForSearchModal();
        boolean expectedSearchModalState = hPage.isSearchModalVisible();

        assertThat(expectedSearchModalState).as("Modal should be visible after click on Loupe-like button").isTrue();
    }

    @DisplayName("Type name of the item in the 'Search' field, click on Loupe-like button and check url and status code")
    @Tags({@Tag("P0"), @Tag("extended")})
    @Test
    void searchItemsAndCheckUrlAndSubtitle() {
        String textToFind = "new york yankees";
        String expectedUrl = "https://www.ae.com/us/en/s/new+york+yankees";

        hPage.clickSearch();
        hPage.waitForSearchModal();
        boolean expectedSearchModalState = hPage.isSearchModalVisible();

        assertThat(expectedSearchModalState).as("Modal should be visible after click on Loupe-like button").isTrue();

        hPage.waitForSearchField();
        hPage.typeIntoSearchField(textToFind);
        hPage.clickLoupeSubmit();
        hPage.waitForUrl(expectedUrl);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(hPage.getCurrentURL())
                .as("Transition to the corresponding to query page should have been executed").isEqualTo(expectedUrl);
        softly.assertThat(hPage.getSubtitleAfterSearchText())
                .as("Subtitle text should contain query text").contains(textToFind);
        softly.assertAll();
    }

    @DisplayName("type name of the item in the 'Search' field and check that dropdown is visible")
    @Tags({@Tag("P1"), @Tag("smoke")})
    @Test
    void searchAndWaitSuggestions(){
        String textToFind = "new york";

        hPage.clickSearch();
        hPage.waitForSearchModal();
        hPage.waitForSearchField();
        hPage.typeIntoSearchField(textToFind);
        boolean isSuggestionsVisible = hPage.isSuggestionsDropdownVisible();

        assertThat(isSuggestionsVisible)
                .as("After typing part of the item's name suggestions should have appeared").isTrue();
    }

    @DisplayName("Type name of the item in the search field, check that dropdown is visible, " +
            "click on the appeared option, check url and subtitle")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void searchAndPickSuggestion() {
        String textToFind = "new york";
        String expectedUrl = "https://www.ae.com/us/en/s/new+york+yankees";

        hPage.clickSearch();
        hPage.waitForSearchModal();
        hPage.waitForSearchField();
        hPage.typeIntoSearchField(textToFind);
        boolean isSuggestionsVisible = hPage.isSuggestionsDropdownVisible();

        assertThat(isSuggestionsVisible)
                .as("After typing part of the item's name suggestions should have appeared").isTrue();

        hPage.clickOnSuitableSuggestion();
        hPage.waitForUrl(expectedUrl);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(hPage.getCurrentURL())
                .as("Transition to the corresponding to query page should have been executed").isEqualTo(expectedUrl);
        softly.assertThat(hPage.getSubtitleAfterSearchText())
                .as("Subtitle text should contain query text").contains(textToFind);
        softly.assertAll();
    }

    @DisplayName("Type the name of the item in the search field and make mistake in one letter - " +
            "check that item with correct name has appeared in dropdown")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void typeWithMistakeAndCheckSuggestion(){
        String textToFind = "rainciat";
        String expectedSuggestionText = "Raincoat";

        hPage.clickSearch();
        hPage.waitForSearchModal();
        hPage.waitForSearchField();
        hPage.typeIntoSearchField(textToFind);
        boolean isSuggestionsVisible = hPage.isSuggestionsDropdownVisible();

        assertThat(isSuggestionsVisible)
                .as("After typing part of the item's name suggestions should have appeared").isTrue();

        hPage.waitForRainciatSuggestion();
        String suggestionText = hPage.getSuggestionRainciatText();

        assertThat(suggestionText)
                .as("System should recognize the mistake and make a right suggestion")
                .isEqualTo(expectedSuggestionText);
    }

    @DisplayName("Type name of non-existing item in the 'Search' field and check message")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void searchForNonExistingItem() throws InterruptedException {
        String textToFind = "000000000000";
        String expectedErrorText = "Sorry! We couldn't find a match for " + textToFind + ".";
        String expectedUrl = "https://www.ae.com/us/en/s/" + textToFind;

        hPage.clickSearch();
        hPage.waitForSearchModal();
        hPage.waitForSearchField();
        hPage.typeIntoSearchField(textToFind);
        hPage.clickLoupeSubmit();
        hPage.waitForUrl(expectedUrl);

        String errorMessageText = hPage.getTextOfErrorSearchMessage();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorMessageText).as("Error message should contain query").isEqualTo(expectedErrorText);
        softly.assertThat(hPage.getCurrentURL()).as("url should contain query").isEqualTo(expectedUrl);
        softly.assertAll();
    }
}
