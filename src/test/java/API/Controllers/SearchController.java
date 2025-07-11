package API.Controllers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static Utils.Constants.BASE_URL_API;
import static Utils.Constants.SEARCH_ENDPOINT;
import static io.restassured.RestAssured.given;

public class SearchController {

    private final RequestSpecification requestSpecification;
    private final String token;

    public SearchController(String token) {
        this.token = token;

        RestAssured.defaultParser = Parser.JSON;
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL_API)
                .setBasePath("cstr/v1/")
                .setContentType("application/json")
                .setAccept("application/json")
                .addHeader("aesite", "AEO_US")
                .addHeader("aelang", "en_US")
                .addFilter(new AllureRestAssured())
                .build();
    }

    private RequestSpecification specWithAuth() {
        return given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token);
    }

    @Step("get items by name")
    public Response getItemsByName(String query){
        return specWithAuth()
                .get(SEARCH_ENDPOINT + query)
                .andReturn();
    }

    @Step("get items by name using invalid path")
    public Response getItemsByNameUsingInvalidPath(String query){
        return specWithAuth()
                .get("tocken" + query)
                .andReturn();
    }

    @Step("get items using empty name")
    public Response getItemsWithEmptyName(){
        return specWithAuth()
                .get(SEARCH_ENDPOINT)
                .andReturn();
    }
}
