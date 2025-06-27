package API.Controllers;

import API.Models.AuthResponse;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import static API.TestData.TestData.VALID_AUTH_HEADER_VALUE;
import static Utils.Constants.*;
import static io.restassured.RestAssured.given;

@Getter
public class AuthorizationController {

    private final RequestSpecification requestSpecification;
    private String accessToken;

    public AuthorizationController() {
        RestAssured.defaultParser = Parser.JSON;
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL_API)
                .setBasePath("auth/oauth/v5/")
                .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
                .setAccept("application/vnd.oracle.resource+json")
                .addFilter(new AllureRestAssured())
                .build();

        initializeToken();
    }

    @Step("Get guest token from response")
    private void initializeToken() {
        Response response = given(requestSpecification)
                .header("Authorization", VALID_AUTH_HEADER_VALUE)
                .formParam("grant_type", "client_credentials")
                .log().all()
                .post(TOKEN_ENDPOINT)
                .andReturn();

        this.accessToken = response.as(AuthResponse.class).getAccessToken();
    }


    @Step("request for guest token with value of Authorization header")
    public Response getGuestTokenWithAuthHeaderValue(String AuthHeaderValue){
        return given(requestSpecification)
                .header("Authorization", AuthHeaderValue)
                .formParam("grant_type", "client_credentials")
                .post(TOKEN_ENDPOINT)
                .andReturn();
    }

    @Step("request for guest token using invalid value for grant type")
    public Response getGuestTokenWithContentTypeValue(String grantTypeValue){
        return given(requestSpecification)
                .header("Authorization", VALID_AUTH_HEADER_VALUE)
                .formParam("grant_type", grantTypeValue)
                .post(TOKEN_ENDPOINT)
                .andReturn();
    }
}
