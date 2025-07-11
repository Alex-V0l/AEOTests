package API.Tests;

import API.Controllers.AuthorizationController;
import API.Controllers.CartController;
import API.Controllers.SearchController;
import API.Models.CartModels.*;
import Steps.APISteps;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import static API.TestData.TestData.*;
import static Utils.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("API tests")
public class CartTests {

    static CartController cartController;
    static AuthorizationController auth;
    static SearchController searchController;
    static APISteps apiSteps;

    @BeforeAll
    public static void setup() {
        auth = new AuthorizationController();
        String token = auth.getAccessToken();
        searchController = new SearchController(token);
        cartController = new CartController(token);
        apiSteps = new APISteps(searchController, auth, cartController);
    }

    @DisplayName("Add item to the cart and check status code")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void addItemToCart() {
        int expectedStatusCode = 202;

        assertThat(cartController.addItemToCart(ITEMS_OF_JEANS).statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL, ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART)
                .isEqualTo(expectedStatusCode);
    }

    @DisplayName("Add item to the cart, check status code, response body, go to the cart and compare cart id")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemAndCheckCart() {
        int expectedStatusCode = 202;

        Response afterAdding = cartController.addItemToCart(ITEMS_OF_JACKET);
        int actualStatusCode = afterAdding.statusCode();
        String cartIDAfterAddingItem = apiSteps.getCartIdFromResponseBodyAfterAddingItems(afterAdding);
        String cartIdAfterGettingItem =  apiSteps.getCartIDFromResponseBodyAfterGettingItemsFromCart();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualStatusCode)
                .as(VALUES_HAVE_TO_BE_EQUAL, ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART)
                .isEqualTo(expectedStatusCode);
        softly.assertThat(cartIdAfterGettingItem)
                .as(VALUES_HAVE_TO_BE_EQUAL, "Sessions must be the same").isEqualTo(cartIDAfterAddingItem);
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, check status code, check response body and check that item is in the cart")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemCheckCartIDAndItemPresence(){
        int expectedStatusCode = 202;
        String PoloSkuID = "0043256270";

        Response addedPoloResponse = cartController.addItemToCart(ITEMS_OF_POLO);
        int actualStatusCode = addedPoloResponse.statusCode();
        String cartIDAfterAddingItem = apiSteps.getCartIdFromResponseBodyAfterAddingItems(addedPoloResponse);
        CartResponse getItemsResponse = apiSteps.getItemsResponse();
        String cartIDAfterGettingItem = apiSteps.getItemsIDFromCartResponse(getItemsResponse);
        boolean isItemInTheCart = apiSteps.isItemInCart(getItemsResponse, PoloSkuID);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualStatusCode).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedStatusCode);
        softly.assertThat(cartIDAfterGettingItem).as(VALUES_HAVE_TO_BE_EQUAL,
                "cartID after adding an item must be the same as id of the get request").isEqualTo(cartIDAfterAddingItem);
        softly.assertThat(isItemInTheCart).as("Item must be in the cart").isTrue();
        softly.assertAll();
    }

    @DisplayName("Add item to the cart with non-existing ID and check status code")
    @Tags({@Tag("P2"), @Tag("negative")})
    @Test
    void addItemWithNonExistingID(){
        int expectedStatusCode = 422;

        assertThat(cartController.addItemToCart(ITEMS_WITH_WRONG_ID).statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL, "Item should not have been added to the cart because of the wrong skuID")
                .isEqualTo(expectedStatusCode);
    }

    @DisplayName("Add item to the cart with invalid path and check status code")
    @Tags({@Tag("P2"), @Tag("negative")})
    @Test
    void addItemUsingInvalidPath(){
        int expectedStatusCode = 404;

        assertThat(cartController.addItemToCartUsingInvalidPath(ITEMS_OF_JEANS).statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL, "Item should not have been added to the cart because of the wrong path")
                .isEqualTo(expectedStatusCode);
    }

    @DisplayName("Add more then 10 amount of one item to the cart and check status code")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void addMoreItemsThanPossible(){
        int expectedStatusCode = 422;

        assertThat(cartController.addItemToCart(ITEMS_OF_11_JEANS).statusCode())
                .as(VALUES_HAVE_TO_BE_EQUAL,
                        "Item should not have been added to the cart because it is possible to add only 10 pieces of one item")
                .isEqualTo(expectedStatusCode);
    }

    @DisplayName("Add item to the cart, change amount of added to the cart item and check status code")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void
    addItemAndChangeAmount(){
        int expectedStatusCode = 202;
        String shirtsSKU = ITEMS_OF_SHIRT.getItems().get(0).getSkuId();
        int newQuantity = ITEMS_OF_DRESSES.getItems().get(0).getQuantity() + 1;

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_SHIRT);
        String ItemID = apiSteps.firstItemsIDAfterGettingItems();
        ItemsForPatchRequest patchRequest = apiSteps.buildPatchRequest(ItemID, shirtsSKU, newQuantity);
        int actualStatusCode = apiSteps.patchItemAndGetStatusCode(patchRequest);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode).as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART)
                .isEqualTo(expectedStatusCode);
        softly.assertThat(actualStatusCode).as("Item's amount has been changed").isEqualTo(expectedStatusCode);
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, change amount of added to the cart item and check that amount has changed")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemChangeAmountCheckCart(){
        int expectedPostAndPatchStatusCode = 202;
        int expectedGetStatusCode = 200;
        String dressesSKU = ITEMS_OF_DRESSES.getItems().get(0).getSkuId();
        int newQuantity = ITEMS_OF_DRESSES.getItems().get(0).getQuantity() + 2;

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_DRESSES);
        String ItemID = apiSteps.firstItemsIDAfterGettingItems();
        ItemsForPatchRequest patchRequest = apiSteps.buildPatchRequest(ItemID, dressesSKU, newQuantity);
        int statusCodeAfterPatch = apiSteps.patchItemAndGetStatusCode(patchRequest);
        Response afterNewGetResponse = cartController.getItemsFromCart();
        int actualStatusCode = apiSteps.getStatusCodeAfterGetItems(afterNewGetResponse);
        int actualQuantity = apiSteps.getFirstItemsQuantityFromCart(afterNewGetResponse);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode)
                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART).isEqualTo(expectedPostAndPatchStatusCode);
        softly.assertThat(statusCodeAfterPatch)
                .as("Item must have been changed").isEqualTo(expectedPostAndPatchStatusCode);
        softly.assertThat(actualStatusCode)
                .as("Item with new amount must have appeared").isEqualTo(expectedGetStatusCode);
        softly.assertThat(actualQuantity).as("Item's amount must have been changed").isEqualTo(3);
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, change amount of added to the cart item for negative and check status code")
    @Tags({@Tag("P2"), @Tag("negative")})
    @Test
    void addItemAndChangeAmountForInvalidValue(){
        int expectedPostAndPatchStatusCode = 202;
        int expectedErrorStatusCode = 403;
        String colongeSKU = ITEMS_OF_COLONGES.getItems().get(0).getSkuId();
        int negativeQuantity = ITEMS_OF_COLONGES.getItems().get(0).getQuantity() -2;

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_COLONGES);
        String ItemID = apiSteps.firstItemsIDAfterGettingItems();
        ItemsForPatchRequest patchRequest = apiSteps.buildPatchRequest(ItemID, colongeSKU, negativeQuantity);
        int actualStatusCode = apiSteps.patchItemAndGetStatusCode(patchRequest);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode)
                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART).isEqualTo(expectedPostAndPatchStatusCode);
        softly.assertThat(actualStatusCode)
                .as("Item's amount must not have changed because negative values are invalid")
                .isEqualTo(expectedErrorStatusCode);
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, change itemID of the added to the cart item and check status code")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void addItemChangeID(){
        int expectedPostAndPatchStatusCode = 202;
        int expectedErrorStatusCode = 422;
        String shirtsSKU = ITEMS_OF_T_SHIRTS.getItems().get(0).getSkuId();
        int quantity = ITEMS_OF_T_SHIRTS.getItems().get(0).getQuantity();

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_T_SHIRTS);
        String ItemID = apiSteps.firstItemsIDAfterGettingItems();
        String changedID = ItemID +"new";
        ItemsForPatchRequest patchRequest = apiSteps.buildPatchRequest(changedID, shirtsSKU, quantity);
        int actualStatusCode = apiSteps.patchItemAndGetStatusCode(patchRequest);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode)
                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART).isEqualTo(expectedPostAndPatchStatusCode);
        softly.assertThat(actualStatusCode)
                .as("Item's id must not have been changed").isEqualTo(expectedErrorStatusCode);
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, delete it and check status code")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void addItemAndDeleteIt(){
        int expectedStatusCode = 202;

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_PANTS);
        String ItemID = apiSteps.firstItemsIDAfterGettingItems();
        int actualStatusCode = apiSteps.deleteItemAndGetStatusCode(ItemID);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode)
                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART).isEqualTo(expectedStatusCode);
        softly.assertThat(actualStatusCode)
                .as(ITEM_MUST_HAVE_BEEN_DELETED).isEqualTo(expectedStatusCode);
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, delete it, check that there is no deleted item in the cart and status code")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void addItemDeleteItAndCheckCart(){
        int expectedPostAndDeleteStatusCode = 202;
        int expectedGetItemsStatusCode = 200;

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_WOMENS_JEANS);
        String ItemID = apiSteps.firstItemsIDAfterGettingItems();
        int afterDeletingStatusCode = apiSteps.deleteItemAndGetStatusCode(ItemID);
        Response AfterGetItemsResponse = cartController.getItemsFromCart();
        int actualStatusCode = apiSteps.getStatusCodeAfterGetItems(AfterGetItemsResponse);
        boolean isItemInTheCart =  apiSteps.isItemInCart(AfterGetItemsResponse.as(CartResponse.class), ItemID);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode)
                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART).isEqualTo(expectedPostAndDeleteStatusCode);
        softly.assertThat(afterDeletingStatusCode)
                .as(ITEM_MUST_HAVE_BEEN_DELETED).isEqualTo(expectedPostAndDeleteStatusCode);
        softly.assertThat(actualStatusCode)
                .as(REQUEST_FOR_GETTING_ITEMS_FROM_CART_SHOULD_BEEN_EXECUTED).isEqualTo(expectedGetItemsStatusCode);
        softly.assertThat(isItemInTheCart).as("cart should not contain already deleted item").isFalse();
        softly.assertAll();
    }

    @DisplayName("Add item to the cart, delete it using empty itemID and check status code")
    @Tags({@Tag("P2"), @Tag("negative")})
    @Test
    void deleteItemWhichIsNotInTheCart(){
        int expectedGetItemsStatusCode = 200;
        int expectedPostStatusCode = 202;
        int expectedDeleStatusCode = 403;
        String poloTShirtsSkuID = ITEMS_OF_POLO_T_SHIRTS.getItems().get(0).getSkuId();

        int afterAddingItemsStatusCode = apiSteps.postItemAndGetStatusCode(ITEMS_OF_POLO_T_SHIRTS);
        Response afterGetItemsResponse = cartController.getItemsFromCart();
        int afterGetStatusCode = apiSteps.getStatusCodeAfterGetItems(afterGetItemsResponse);
        boolean isItemInTheCart =
                apiSteps.isItemInCart(afterGetItemsResponse.as(CartResponse.class), poloTShirtsSkuID);
        int afterDeletingStatusCode = apiSteps.deleteItemWithNoIDAndGetStatusCode();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(afterAddingItemsStatusCode)
                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_CART).isEqualTo(expectedPostStatusCode);
        softly.assertThat(afterGetStatusCode)
                .as(REQUEST_FOR_GETTING_ITEMS_FROM_CART_SHOULD_BEEN_EXECUTED).isEqualTo(expectedGetItemsStatusCode);
        softly.assertThat(isItemInTheCart).as("Cart should contain added item").isTrue();
        softly.assertThat(afterDeletingStatusCode)
                .as("Item should have been deleted because itemId wasn't applied").isEqualTo(expectedDeleStatusCode);
        softly.assertAll();
    }
}
