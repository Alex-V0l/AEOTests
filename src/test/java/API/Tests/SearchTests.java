//package API.Tests;
//
//import API.Controllers.AuthorizationController;
//import API.Controllers.SearchController;
//import API.Models.SearchModels.ItemAfterSearch;
//import io.restassured.response.Response;
//import org.assertj.core.api.SoftAssertions;
//import org.junit.jupiter.api.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static API.TestData.TestData.NEW_YORK_YANKEES_QUERY;
//import static Utils.Constants.SEARCH_QUERY_SHOULD_NOT_HAVE_BEEN_EXECUTED;
//import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@Tag("API tests")
//public class SearchTests {
//
//    static SearchController searchController;
//
//    @BeforeAll
//    static void setup() {
//        AuthorizationController authController = new AuthorizationController();
//        searchController = new SearchController(authController.getAccessToken());
//    }
//
//    @DisplayName("Search all items by name and check status code")
//    @Tags({@Tag("P0"), @Tag("smoke")})
//    @Test
//    void getItemsByName(){
//        int expectedStatusCode = 200;
//
//        int actualStatusCode = searchController.getItemsByName(NEW_YORK_YANKEES_QUERY).statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, "Search query should have been executed").isEqualTo(expectedStatusCode);
//    }
//
//    @DisplayName("Search all items by name, check that their names contain the requested text and check status code")
//    @Tags({@Tag("P1"), @Tag("extended")})
//    @Test
//    void getItemsByNameAndCheckNames() {
//        int expectedStatusCode = 200;
//        String queryText = "new york yankees";
//
//        Response queryResponse = searchController.getItemsByName(NEW_YORK_YANKEES_QUERY);
//
//        assertThat(queryResponse.statusCode()).isEqualTo(expectedStatusCode);
//
//        List<ItemAfterSearch> items = queryResponse.jsonPath().getList("included", ItemAfterSearch.class);
//        List<ItemAfterSearch> yankeesItems = items.stream()
//                .filter(item -> item.getAttributes().getDisplayName().toLowerCase().contains(queryText))
//                .collect(Collectors.toList());
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(yankeesItems).isNotNull();
//        softly.assertThat(yankeesItems.isEmpty()).isFalse();
//
//        for (ItemAfterSearch item : yankeesItems) {
//            String itemName = item.getAttributes().getDisplayName();
//            softly.assertThat(itemName).isNotNull()
//                    .as("There must be items with name, corresponding to query, in the search result")
//                    .containsIgnoringCase(queryText.toLowerCase());
//        }
//        softly.assertAll();
//    }
//
//    @DisplayName("Search all items by name using invalid path and check status code")
//    @Tags({@Tag("P2"), @Tag("negative")})
//    @Test
//    void getItemsByNameUsingInvalidPath(){
//        int expectedStatusCode = 404;
//
//        int actualStatusCode = searchController.getItemsByNameUsingInvalidPath(NEW_YORK_YANKEES_QUERY).statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, SEARCH_QUERY_SHOULD_NOT_HAVE_BEEN_EXECUTED).isEqualTo(expectedStatusCode);
//    }
//
//    @DisplayName("Search all items using empty query and check status code")
//    @Tags({@Tag("P1"), @Tag("negative")})
//    @Test
//    void getItemsUsingEmptyQuery(){
//        int expectedStatusCode = 400;
//
//        int actualStatusCode = searchController.getItemsWithEmptyName().statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, SEARCH_QUERY_SHOULD_NOT_HAVE_BEEN_EXECUTED).isEqualTo(expectedStatusCode);
//    }
//}
