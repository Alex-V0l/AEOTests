package Steps;

import API.Controllers.AuthorizationController;
import API.Controllers.CartController;
import API.Controllers.SearchController;
import API.Models.CartModels.*;
import API.Models.SearchModels.ItemAfterSearch;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class APISteps {

    private SearchController searchController;
    private AuthorizationController authController;
    private CartController cartController;

    public APISteps(SearchController searchController, AuthorizationController authController, CartController cartController) {
        this.authController = authController;
        String token = authController.getAccessToken();
        this.searchController = searchController;
        this.cartController = cartController;
    }

    @Step("Filter search results to items whose names contain queryText")
    public List<ItemAfterSearch> getFilteredItemsByQueryText(Response response, String queryText) {
        List<ItemAfterSearch> items = response.jsonPath().getList("included", ItemAfterSearch.class);
        return items.stream()
                .filter(item -> item.getAttributes().getDisplayName().toLowerCase().contains(queryText.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Step("Check that all item names in search results contain the query text")
    public void checkSearchResultsContainQuery(List<ItemAfterSearch> items, String queryText) {
        for (ItemAfterSearch item : items) {
            String itemName = item.getAttributes().getDisplayName();
            assertThat(itemName).isNotNull()
                    .as("There must be items with name, corresponding to query, in the search result")
                    .containsIgnoringCase(queryText.toLowerCase());
        }
    }

    @Step("get response body after adding item to cart and then get catID")
    public String getCartIdFromResponseBodyAfterAddingItems(Response afterAddingItemToCartResponse){
        AddToCartResponse bodyResponse = afterAddingItemToCartResponse.andReturn().then().extract().body()
                .as(AddToCartResponse.class);
        return bodyResponse.getCartId();
    }

    @Step("get cartID after getting all items from cart")
    public String getCartIDFromResponseBodyAfterGettingItemsFromCart(){
        CartResponse getItemsResponse = cartController.getItemsFromCart().andReturn().then().extract().body()
                .as(CartResponse.class);
        return getItemsResponse.getData().getId();
    }

    @Step("get response body after getting all items from cart")
    public CartResponse getItemsResponse(){
         return cartController.getItemsFromCart().andReturn().then().extract().body()
                .as(CartResponse.class);
    }

    @Step("Check if item with SKU is present in cart")
    public boolean isItemInCart(CartResponse getItemsResponse, String sku) {
        List<ItemFromCartResponse> itemsInCart = getItemsResponse.getData().getItems();
        return itemsInCart.stream().anyMatch(item -> sku.equals(item.getSku()));
    }

    @Step("get id of item from cart response body")
    public String getItemsIDFromCartResponse(CartResponse response){
        return response.getData().getId();
    }

    @Step("add item to cart get status code after getting item to the cart")
    public int postItemAndGetStatusCode(OrderRequest Items){
         return cartController.addItemToCart(Items).statusCode();
    }

    @Step("get first item's ID after getting all items from cart")
    public String firstItemsIDAfterGettingItems(){
         return cartController.getItemsFromCart().as(CartResponse.class).getData().getItems().get(0)
                .getItemId();
    }

    @Step("Build patch request for itemId: {itemId}, skuId: {skuId}, quantity: {quantity}")
    public ItemsForPatchRequest buildPatchRequest(String itemId, String skuId, int quantity) {
        ItemForPatch itemToPatch = ItemForPatch.builder()
                .skuId(skuId)
                .quantity(quantity)
                .itemId(itemId)
                .build();

        return ItemsForPatchRequest.builder()
                .items(Collections.singletonList(itemToPatch))
                .build();
    }

    @Step("patch item and get status code after patch item")
    public int patchItemAndGetStatusCode(ItemsForPatchRequest patchRequest){
         return cartController.changeItemUsingPatch(patchRequest).statusCode();
    }

    @Step("get status code after getting items from cart")
    public int getStatusCodeAfterGetItems(Response afterNewGetResponse){
        return afterNewGetResponse.statusCode();
    }

    @Step("get quantity of the first item after getting items from cart")
    public int getFirstItemsQuantityFromCart(Response afterNewGetResponse) {
        return afterNewGetResponse.as(CartResponse.class).getData().getItems().get(0).getQuantity();
    }

    @Step("delete item by id and get status code")
    public int deleteItemAndGetStatusCode(String ItemID){
        return cartController.deleteItemFromCart(ItemID).statusCode();
    }

    @Step("delete item with empty id and get status code")
    public int deleteItemWithNoIDAndGetStatusCode(){
        return cartController.deleteItemFromCartWithEmptyId().statusCode();
    }
}
