package API.Controllers;

import API.Models.BasketModels.ItemsForPatchRequest;
import API.Models.BasketModels.OrderRequest;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static Utils.Constants.*;
import static io.restassured.RestAssured.given;

public class BagController {

    private final RequestSpecification requestSpecification;
    private final String token;

    public BagController(String token){
        this.token = token;

        RestAssured.defaultParser = Parser.JSON;
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL_API)
                .setBasePath("bag/v1")
                .setContentType("application/json")
                .addHeader("aecountry", "US")
                .addHeader("aelang", "en_US")
                .addHeader("aesite", "AEO_US")
                .addFilter(new AllureRestAssured())
                .build();
    }

    private RequestSpecification SpecWithAuth() {
        return given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token)
                .log().all();
    }

    @Step("add item to the basket")
    public Response addItemToBasket(OrderRequest orderRequest){
        return SpecWithAuth()
                .body(orderRequest)
                .post(BAG_ENDPOINT)
                .andReturn();
    }

    @Step("add item to the basket using invalid path")
    public Response addItemToBasketUsingInvalidPath(OrderRequest orderRequest){
        return SpecWithAuth()
                .body(orderRequest)
                .post("/iitem")
                .andReturn();
    }

    @Step("get items from basket")
    public Response getItemsFromBasket() {
        return SpecWithAuth()
                .get()
                .andReturn();
    }

    @Step("change item in the basket with patch method")
    public Response changeItemUsingPatch(ItemsForPatchRequest changedItems){
        return SpecWithAuth()
                .body(changedItems)
                .patch(BAG_ENDPOINT)
                .andReturn();
    }

    @Step("delete item from the basket")
    public Response deleteItemFromBasket(String ItemID){
        return SpecWithAuth()
                .delete("items?itemIds=" + ItemID)
                .andReturn();
    }

    @Step("delete item from the basket with empty id")
    public Response deleteItemFromBasketWithEmptyId(){
        return SpecWithAuth()
                .delete()
                .andReturn();
    }
}
