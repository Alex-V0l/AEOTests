package API.Tests;

import API.Controllers.AuthorizationController;
import API.Models.AuthResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static API.TestData.TestData.*;
import static Utils.Constants.TOKEN_SHOULD_HAVE_BEEN_RECEIVED;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("API tests")
public class AuthorizationTests {

    AuthorizationController authController = new AuthorizationController();

    @DisplayName("Post request for guest token for authorization and check status code")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void postForValidGuestToken(){
        int expectedStatusCode = 200;

        int actualStatusCode = authController.getGuestTokenWithAuthHeaderValue(VALID_AUTH_HEADER_VALUE).statusCode();

        assertThat(actualStatusCode)
                .as(VALUES_HAVE_TO_BE_EQUAL, TOKEN_SHOULD_HAVE_BEEN_RECEIVED).isEqualTo(expectedStatusCode);
    }

    @DisplayName
            ("Post request for guest token for authorization using invalid Authorization header value and check status code")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void postGuestTokenWithInvalidAuthValue(){
        int expectedStatusCode = 401;

        int actualStatusCode = authController.getGuestTokenWithAuthHeaderValue(INVALID_AUTH_HEADER_VALUE).statusCode();

        assertThat(actualStatusCode)
                .as(VALUES_HAVE_TO_BE_EQUAL, "Request should not have been executed").isEqualTo(expectedStatusCode);
    }

    @DisplayName("Post request for guest token for authorization, check status code and response body")
    @Tags({@Tag("P1"), @Tag("extended")})
    @Test
    void postForValidGuestTokenCheckBody(){
        int expectedStatusCode = 200;

        Response tokenResponse = authController.getGuestTokenWithAuthHeaderValue(VALID_AUTH_HEADER_VALUE);
        int actualStatusCode = tokenResponse.statusCode();
        AuthResponse actualResponse = tokenResponse.as(AuthResponse.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualStatusCode)
                .as(VALUES_HAVE_TO_BE_EQUAL, TOKEN_SHOULD_HAVE_BEEN_RECEIVED).isEqualTo(expectedStatusCode);
        softly.assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("accessToken").as("Response bodies must be equal").isEqualTo(VALID_TOKEN_RESPONSE);
        softly.assertAll();
    }

    @DisplayName("Post request for guest token for authorization using invalid value for grant type and check status code")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void postGuestTokenWithInvalidContentType(){
        int expectedStatusCode = 403;
        String expectedMessage = "You don't have permission to access";

        Response actualResponse = authController.getGuestTokenWithContentTypeValue(INVALID_GRANT_TYPE_VALUE);
        int actualStatusCode = actualResponse.statusCode();
        String actualMessage = actualResponse.asPrettyString();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualStatusCode)
                .as(VALUES_HAVE_TO_BE_EQUAL, "Access must have been forbidden").isEqualTo(expectedStatusCode);
        softly.assertThat(actualMessage).as("Message should represent an error").contains(expectedMessage);
        softly.assertAll();
    }
}
