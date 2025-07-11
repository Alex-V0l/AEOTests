package API.Tests;

import API.Controllers.AuthorizationController;
import API.Controllers.CartController;
import API.Controllers.SearchController;
import API.Models.SearchModels.ItemAfterSearch;
import Steps.APISteps;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.util.List;

import static API.TestData.TestData.NEW_YORK_YANKEES_QUERY;
import static Utils.Constants.SEARCH_QUERY_SHOULD_NOT_HAVE_BEEN_EXECUTED;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("API tests")
public class SearchTests {

    static AuthorizationController authController;
    static SearchController searchController;
    static APISteps apiSteps;
    static CartController cartController;

    @BeforeAll
    static void setup() {
        authController = new AuthorizationController();
        String token = authController.getAccessToken();
        searchController = new SearchController(token);
        cartController = new CartController(token);
        apiSteps = new APISteps(searchController, authController, cartController);
    }

    @DisplayName("Search all items by name and check status code")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void getItemsByName(){
        int expectedStatusCode = 200;

        assertThat(searchController.getItemsByName(NEW_YORK_YANKEES_QUERY).statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL, "Search query should have been executed").isEqualTo(expectedStatusCode);
    }

    @DisplayName("Search all items by name, check that their names contain the requested text and check status code")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void getItemsByNameAndCheckNames() {
        int expectedStatusCode = 200;
        String queryText = "new york yankees";

        Response queryResponse = searchController.getItemsByName(NEW_YORK_YANKEES_QUERY);

        assertThat(queryResponse.statusCode()).isEqualTo(expectedStatusCode);

        List <ItemAfterSearch> yankeesItems = apiSteps.getFilteredItemsByQueryText(queryResponse, queryText);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(yankeesItems).isNotNull();
        softly.assertThat(yankeesItems.isEmpty()).isFalse();
        softly.assertAll();

        apiSteps.checkSearchResultsContainQuery(yankeesItems, queryText);
    }

    @DisplayName("Search all items by name using invalid path and check status code")
    @Tags({@Tag("P2"), @Tag("negative")})
    @Test
    void getItemsByNameUsingInvalidPath(){
        int expectedStatusCode = 404;

        assertThat(searchController.getItemsByNameUsingInvalidPath(NEW_YORK_YANKEES_QUERY).statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL, SEARCH_QUERY_SHOULD_NOT_HAVE_BEEN_EXECUTED).isEqualTo(expectedStatusCode);
    }

    @DisplayName("Search all items using empty query and check status code")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void getItemsUsingEmptyQuery(){
        int expectedStatusCode = 400;

        assertThat(searchController.getItemsWithEmptyName().statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL, SEARCH_QUERY_SHOULD_NOT_HAVE_BEEN_EXECUTED).isEqualTo(expectedStatusCode);
    }
}
