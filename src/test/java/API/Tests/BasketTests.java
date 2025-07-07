//package API.Tests;
//
//import API.Controllers.AuthorizationController;
//import API.Controllers.BagController;
//import API.Models.BasketModels.*;
//import io.restassured.response.Response;
//import org.assertj.core.api.SoftAssertions;
//import org.junit.jupiter.api.*;
//
//
//import java.util.Collections;
//import java.util.List;
//
//import static API.TestData.TestData.*;
//import static Utils.Constants.*;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@Tag("API tests")
//public class BasketTests {
//
//    static BagController bagController;
//
//    @BeforeAll
//    public static void setup() {
//        AuthorizationController auth = new AuthorizationController();
//        bagController = new BagController(auth.getAccessToken());
//    }
//
//    @DisplayName("Add item to the basket and check status code")
//    @Tags({@Tag("P0"), @Tag("smoke")})
//    @Test
//    void addItemToBasket() {
//        int expectedStatusCode = 202;
//
//        int actualStatusCode = bagController.addItemToBasket(ITEMS_OF_JEANS).statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET)
//                .isEqualTo(expectedStatusCode);
//    }
//
//    @DisplayName("Add item to the basket, check status code, response body, go to the basket and compare cart id")
//    @Tags({@Tag("P1"), @Tag("extended")})
//    @Test
//    void addItemAndCheckBasket() {
//        int expectedStatusCode = 202;
//
//        Response afterAdding = bagController.addItemToBasket(ITEMS_OF_JACKET);
//        int actualStatusCode = afterAdding.statusCode();
//        AddToBasketResponse bodyResponse = afterAdding.andReturn().then().extract().body()
//                .as(AddToBasketResponse.class);
//        String cartID = bodyResponse.getCartId();
//        BasketResponse getItemsResponse = bagController.getItemsFromBasket().andReturn().then().extract().body()
//                .as(BasketResponse.class);
//        String ID = getItemsResponse.getData().getId();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET)
//                .isEqualTo(expectedStatusCode);
//        softly.assertThat(ID).as(VALUES_HAVE_TO_BE_EQUAL, "Sessions must be the same").isEqualTo(cartID);
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, check status code, check response body and check that item is in the basket")
//    @Tags({@Tag("P1"), @Tag("extended")})
//    @Test
//    void addItemCheckCartIDAndItemPresence(){
//        int expectedStatusCode = 202;
//        String PoloSkuID = "0043256270";
//
//        Response addedPoloResponse = bagController.addItemToBasket(ITEMS_OF_POLO);
//        int actualStatusCode = addedPoloResponse.statusCode();
//        AddToBasketResponse BasketGetResponse = addedPoloResponse.andReturn().then().extract().body()
//                .as(AddToBasketResponse.class);
//        String cartID = BasketGetResponse.getCartId();
//        BasketResponse getItemsResponse = bagController.getItemsFromBasket().andReturn().then().extract().body()
//                .as(BasketResponse.class);
//        String ID = getItemsResponse.getData().getId();
//        List<ItemFromBasketResponse> itemsInBasket = getItemsResponse.getData().getItems();
//        boolean itemFound = itemsInBasket.stream().anyMatch(item -> PoloSkuID.equals
//                (item.getSku()));
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(actualStatusCode).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedStatusCode);
//        softly.assertThat(ID).as(VALUES_HAVE_TO_BE_EQUAL,
//                "cartID after adding an item must be the same as id of the get request").isEqualTo(cartID);
//        softly.assertThat(itemFound).as("Item must be in the basket").isTrue();
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket with non-existing ID and check status code")
//    @Tags({@Tag("P2"), @Tag("negative")})
//    @Test
//    void addItemWithNonExistingID(){
//        int expectedStatusCode = 422;
//
//        int actualStatusCode = bagController.addItemToBasket(ITEMS_WITH_WRONG_ID).statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, "Item should not have been added to the basket because of the wrong skuID")
//                .isEqualTo(expectedStatusCode);
//    }
//
//    @DisplayName("Add item to the basket with invalid path and check status code")
//    @Tags({@Tag("P2"), @Tag("negative")})
//    @Test
//    void addItemUsingInvalidPath(){
//        int expectedStatusCode = 404;
//
//        int actualStatusCode = bagController.addItemToBasketUsingInvalidPath(ITEMS_OF_JEANS).statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL, "Item should not have been added to the basket because of the wrong path")
//                .isEqualTo(expectedStatusCode);
//    }
//
//    @DisplayName("Add more then 10 amount of one item to the basket and check status code")
//    @Tags({@Tag("P1"), @Tag("negative")})
//    @Test
//    void addMoreItemsThanPossible(){
//        int expectedStatusCode = 422;
//
//        int actualStatusCode = bagController.addItemToBasket(ITEMS_OF_11_JEANS).statusCode();
//
//        assertThat(actualStatusCode)
//                .as(VALUES_HAVE_TO_BE_EQUAL,
//                        "Item should not have been added to the basket because it is possible to add only 10 pieces of one item")
//                .isEqualTo(expectedStatusCode);
//    }
//
//    @DisplayName("Add item to the basket, change amount of added to the basket item and check status code")
//    @Tags({@Tag("P0"), @Tag("smoke")})
//    @Test
//    void
//    addItemAndChangeAmount(){
//        int expectedStatusCode = 202;
//
//        Response ShirtAddedResponse = bagController.addItemToBasket(ITEMS_OF_SHIRT);
//        int StatusCodeAfterAdding = ShirtAddedResponse.statusCode();
//        String ItemID = bagController.getItemsFromBasket().as(BasketResponse.class).getData().getItems().get(0)
//                .getItemId();
//        ItemForPatch itemOfShirtsToPatch = ItemForPatch.builder()
//                .skuId("0043043678")
//                .quantity(2)
//                .itemId(ItemID)
//                .build();
//        ItemsForPatchRequest patchRequest = ItemsForPatchRequest.builder()
//                .items(Collections.singletonList(itemOfShirtsToPatch))
//                .build();
//        int actualStatusCode = bagController.changeItemUsingPatch(patchRequest).statusCode();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(StatusCodeAfterAdding).as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET)
//                .isEqualTo(expectedStatusCode);
//        softly.assertThat(actualStatusCode).as("Item's amount has been changed").isEqualTo(expectedStatusCode);
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, change amount of added to the basket item and check that amount has changed")
//    @Tags({@Tag("P1"), @Tag("extended")})
//    @Test
//    void addItemChangeAmountCheckBasket(){
//        int expectedPostAndPatchStatusCode = 202;
//        int expectedGetStatusCode = 200;
//
//        Response DressAddedResponse = bagController.addItemToBasket(ITEMS_OF_DRESSES);
//        int StatusCodeAfterAdding = DressAddedResponse.getStatusCode();
//        String ItemID = bagController.getItemsFromBasket().as(BasketResponse.class).getData().getItems().get(0)
//                .getItemId();
//        ItemForPatch itemOfDressesToPatch = ItemForPatch.builder()
//                .skuId("0043656941")
//                .quantity(3)
//                .itemId(ItemID)
//                .build();
//        ItemsForPatchRequest patchRequest = ItemsForPatchRequest.builder()
//                .items(Collections.singletonList(itemOfDressesToPatch))
//                .build();
//        int statusCodeAfterPatch = bagController.changeItemUsingPatch(patchRequest).statusCode();
//        Response afterNewGetResponse = bagController.getItemsFromBasket();
//        int actualStatusCode = afterNewGetResponse.statusCode();
//        int newQuantity = afterNewGetResponse.as(BasketResponse.class).getData().getItems().get(0).getQuantity();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(StatusCodeAfterAdding)
//                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET).isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(statusCodeAfterPatch)
//                .as("Item must have been changed").isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(actualStatusCode)
//                .as("Item with new amount must have appeared").isEqualTo(expectedGetStatusCode);
//        softly.assertThat(newQuantity)
//                .as("Item's amount must have been changed").isEqualTo(itemOfDressesToPatch.getQuantity());
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, change amount of added to the basket item for negative and check status code")
//    @Tags({@Tag("P2"), @Tag("negative")})
//    @Test
//    void addItemAndChangeAmountForInvalidValue(){
//        int expectedPostAndPatchStatusCode = 202;
//        int expectedErrorStatusCode = 403;
//
//        Response ColongeAddedResponse = bagController.addItemToBasket(ITEMS_OF_COLONGES);
//        int StatusCodeAfterAdding = ColongeAddedResponse.getStatusCode();
//        String ItemID = bagController.getItemsFromBasket().as(BasketResponse.class).getData().getItems().get(0)
//                .getItemId();
//        ItemForPatch itemOfColongesToPatch = ItemForPatch.builder()
//                .skuId("0042423806")
//                .quantity(-1)
//                .itemId(ItemID)
//                .build();
//        ItemsForPatchRequest patchRequest = ItemsForPatchRequest.builder()
//                .items(Collections.singletonList(itemOfColongesToPatch))
//                .build();
//        int actualStatusCode = bagController.changeItemUsingPatch(patchRequest).statusCode();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(StatusCodeAfterAdding)
//                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET).isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(actualStatusCode)
//                .as("Item's amount must not have changed because negative values are invalid")
//                .isEqualTo(expectedErrorStatusCode);
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, change itemID of the added to the basket item and check status code")
//    @Tags({@Tag("P1"), @Tag("negative")})
//    @Test
//    void addItemChangeID(){
//        int expectedPostAndPatchStatusCode = 202;
//        int expectedErrorStatusCode = 422;
//
//        Response TShirtAddedResponse = bagController.addItemToBasket(ITEMS_OF_T_SHIRTS);
//        int StatusCodeAfterAdding = TShirtAddedResponse.getStatusCode();
//        String ItemID = bagController.getItemsFromBasket().as(BasketResponse.class).getData().getItems().get(0)
//                .getItemId();
//        ItemForPatch itemOfTShirtsToPatch = ItemForPatch.builder()
//                .skuId("0043222983")
//                .quantity(1)
//                .itemId(String.format(ItemID + "new"))
//                .build();
//        ItemsForPatchRequest patchRequest = ItemsForPatchRequest.builder()
//                .items(Collections.singletonList(itemOfTShirtsToPatch))
//                .build();
//        int actualStatusCode = bagController.changeItemUsingPatch(patchRequest).statusCode();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(StatusCodeAfterAdding)
//                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET).isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(actualStatusCode)
//                .as("Item's id must not have been changed").isEqualTo(expectedErrorStatusCode);
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, delete it and check status code")
//    @Tags({@Tag("P0"), @Tag("smoke")})
//    @Test
//    void addItemAndDeleteIt(){
//        int expectedStatusCode = 202;
//
//        Response PantsAddedResponse = bagController.addItemToBasket(ITEMS_OF_PANTS);
//        int StatusCodeAfterAdding = PantsAddedResponse.getStatusCode();
//        String ItemID = bagController.getItemsFromBasket().as(BasketResponse.class).getData().getItems().get(0)
//                .getItemId();
//        int actualStatusCode = bagController.deleteItemFromBasket(ItemID).statusCode();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(StatusCodeAfterAdding)
//                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET).isEqualTo(expectedStatusCode);
//        softly.assertThat(actualStatusCode)
//                .as(ITEM_MUST_HAVE_BEEN_DELETED).isEqualTo(expectedStatusCode);
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, delete it, check that there is no deleted item in the basket and status code")
//    @Tags({@Tag("P1"), @Tag("extended")})
//    @Test
//    void addItemDeleteItAndCheckBasket(){
//        int expectedPostAndPatchStatusCode = 202;
//        int expectedGetItemsStatusCode = 200;
//
//        Response WomensJeansAddedResponse = bagController.addItemToBasket(ITEMS_OF_WOMENS_JEANS);
//        int afterAddingStatusCode = WomensJeansAddedResponse.statusCode();
//        String ItemID = bagController.getItemsFromBasket().as(BasketResponse.class).getData().getItems().get(0)
//                .getItemId();
//        int afterDeletingStatusCode = bagController.deleteItemFromBasket(ItemID).statusCode();
//        Response AfterGetItemsResponse = bagController.getItemsFromBasket();
//        int actualStatusCode = AfterGetItemsResponse.statusCode();
//        List <ItemFromBasketResponse> items = AfterGetItemsResponse.as(BasketResponse.class).getData().getItems();
//        boolean isItemInTheBasket = items.stream().anyMatch(item -> ItemID.equals
//                (item.getItemId()));
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(afterAddingStatusCode)
//                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET).isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(afterDeletingStatusCode)
//                .as(ITEM_MUST_HAVE_BEEN_DELETED).isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(actualStatusCode)
//                .as(REQUEST_FOR_GETTING_ITEMS_FROM_BASKET_SHOULD_BEEN_EXECUTED).isEqualTo(expectedGetItemsStatusCode);
//        softly.assertThat(isItemInTheBasket).as("Basket should not contain already deleted item").isFalse();
//        softly.assertAll();
//    }
//
//    @DisplayName("Add item to the basket, delete it using empty itemID and check status code")
//    @Tags({@Tag("P2"), @Tag("negative")})
//    @Test
//    void deleteItemWhichIsNotInTheBasket(){
//        int expectedGetItemsStatusCode = 200;
//        int expectedPostAndPatchStatusCode = 202;
//        int expectedDeleStatusCode = 403;
//        String poloTShirtsSkuID = ITEMS_OF_POLO_T_SHIRTS.getItems().get(0).getSkuId();
//
//        Response poloTShirtAddedResponse = bagController.addItemToBasket(ITEMS_OF_POLO_T_SHIRTS);
//        int afterAddingStatusCode = poloTShirtAddedResponse.statusCode();
//        Response afterGetItemsResponse = bagController.getItemsFromBasket();
//        int afterGetItemsStatusCode = afterGetItemsResponse.getStatusCode();
//        List <ItemFromBasketResponse> items = afterGetItemsResponse.as(BasketResponse.class).getData().getItems();
//        boolean isItemInTheBasket = items.stream().anyMatch(item -> poloTShirtsSkuID.equals
//                (item.getSku()));
//        int afterDeletingStatusCode = bagController.deleteItemFromBasketWithEmptyId().statusCode();
//
//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(afterAddingStatusCode)
//                .as(ITEM_SHOULD_HAVE_BEEN_ADDED_TO_THE_BASKET).isEqualTo(expectedPostAndPatchStatusCode);
//        softly.assertThat(afterGetItemsStatusCode)
//                .as(REQUEST_FOR_GETTING_ITEMS_FROM_BASKET_SHOULD_BEEN_EXECUTED).isEqualTo(expectedGetItemsStatusCode);
//        softly.assertThat(isItemInTheBasket).as("Basket should contain added item").isTrue();
//        softly.assertThat(afterDeletingStatusCode)
//                .as("Item should have been deleted because itemId wasn't applied").isEqualTo(expectedDeleStatusCode);
//        softly.assertAll();
//    }
//}
