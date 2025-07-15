package UI.Tests;

import Steps.UISteps;
import UI.Models.TestUser;
import UI.Pages.CreateAnAccountPage;
import UI.Pages.HomeSearchSectionPage;
import Utils.CredentialsGenerator;
import Utils.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import Utils.AllureExtension;

import static Utils.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI tests")
@ExtendWith(AllureExtension.class)
public class CreateAnAccountTests extends BaseTest{

    private HomeSearchSectionPage homeSearchPage;
    private CreateAnAccountPage createAccountPage;
    TestPropertiesConfig config;
    private UISteps uiSteps;


    @BeforeEach
    void setup(){
        homeSearchPage = new HomeSearchSectionPage(driver);
        createAccountPage = new CreateAnAccountPage(driver);
        config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
        uiSteps = new UISteps(driver);
        homeSearchPage.openHomePage();
    }

    @DisplayName("Go to 'Create an Account' page and check url and page's header text")
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect")})
    @Test
    void transitionToCreateAnAccountPage(){
        String expectedCreateAnAccountHeaderText = "Create an Account";

        uiSteps.goToCreateAnAccount();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createAccountPage.getCurrentURL())
                .as("Transition to 'Create an Account' page should have been executed").isEqualTo(CREATE_AN_ACCOUNT_URL);
        softly.assertThat(createAccountPage.getPagesHeaderText())
                .as("Page's header 'Create an Account' should be visible").isEqualTo(expectedCreateAnAccountHeaderText);
        softly.assertAll();
    }

    @DisplayName("Successful creation of an account test " +
            "(fill all necessary field with valid fields pick birth date and agree with policies)")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void successfulCreation() {
        String expectedMessage = "Account created";
        String dayToSelect = "10";
        TestUser user = CredentialsGenerator.generateTestUser();
        String password = user.getPassword();
        String firstName = user.getFirstName();

        uiSteps.goToCreateAnAccount();
        uiSteps.fillInRegistrationForm
                (user, firstName, password, dayToSelect);
        uiSteps.waitForSuccessfulUrlAndMessage();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createAccountPage.getCurrentURL())
                .as("Transition to account's page should have been executed")
                .isEqualTo(SUCCESSFUL_CREATION_OF_ACCOUNT_URL);
        softly.assertThat(createAccountPage.getSuccessfulMessagesAfterCreatingText())
                .as("User should have received message about successful creation").isEqualTo(expectedMessage);
        softly.assertThat(createAccountPage.getMenuHeadersAfterCreationText())
                .as("Header's text should contain user's name after successful creation of an account")
                .contains(firstName);
        softly.assertAll();
    }

    @DisplayName("Type valid email, first name, last name, invalid password, confirm invalid password," +
            " pick birth date, click 'I accept' checkbox and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithTooBigPassword() {
        String passwordOf26Characters = "qwe123rty456yui789iop0asd2";
        TestUser user = CredentialsGenerator.generateTestUser();
        String dayToSelect = "10";
        String expectedErrorMessage =
                "Please enter a password that contains letters + numbers and is 8-25 characters long.";

        uiSteps.goToCreateAnAccount();
        uiSteps.fillInRegistrationFormWithoutFinalClick
                (user, user.getEmail(), passwordOf26Characters, passwordOf26Characters, dayToSelect);
        String errorPasswordMessage = createAccountPage.getErrorsPasswordText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorPasswordMessage)
                .as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedErrorMessage);
        softly.assertThat(createAccountPage.isCreateAccountEnabled())
                .as("'Create Account' button should not be enabled until valid password would be used").isFalse();
        softly.assertAll();
    }

    @DisplayName("Type invalid email, valid first name, valid last name, password, confirm password," +
            " pick birth date, click 'I accept' checkbox and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithInvalidEmail() {
        TestUser user = CredentialsGenerator.generateTestUser();
        String invalidLogin = "SamNewman@@gmail.ru";
        String password = user.getPassword();
        String dayToSelect = "10";
        String expectedErrorMessage = "Please enter a valid email address.";

        uiSteps.goToCreateAnAccount();
        uiSteps.fillInRegistrationFormWithoutFinalClick
                (user, invalidLogin, password, password, dayToSelect);
        String emailErrorText = createAccountPage.getErrorEmailText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(emailErrorText)
                .as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedErrorMessage);
        softly.assertThat(createAccountPage.isCreateAccountEnabled())
                .as("'Create Account' button should not be enabled until valid email would be used").isFalse();
        softly.assertAll();
    }

    @DisplayName("Type valid email, first name, last name, password, confirm password," +
            " pick birth date and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithoutBirthDate() {
        TestUser user = CredentialsGenerator.generateTestUser();
        String password = user.getPassword();

        uiSteps.goToCreateAnAccount();
        uiSteps.fillInRegistrationFormWithoutFinalClickAndBirthDate
                (user, password);

       assertThat(createAccountPage.isCreateAccountEnabled())
                .as("'Create Account' button should not be enabled until birth date would be picked").isFalse();
    }

    @DisplayName("Type valid email, first name, last name, valid password, confirm password incorrectly," +
            " pick birth date, click 'I accept checkbox' and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithWrongConfirmPassword() {
        TestUser user = CredentialsGenerator.generateTestUser();
        String password = user.getPassword();
        String incorrectPassword = password + "...";
        String dayToSelect = "10";
        String expectedErrorMessage =
                "Please confirm the two passwords you've entered match.";

        uiSteps.goToCreateAnAccount();
        uiSteps.fillInRegistrationFormWithoutFinalClick
                (user, user.getEmail(), password, incorrectPassword, dayToSelect);
        String errorConfirmPasswordMessage = createAccountPage.getErrorsConfirmPasswordText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorConfirmPasswordMessage)
                .as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedErrorMessage);
        softly.assertThat(createAccountPage.isCreateAccountEnabled())
                .as("'Create Account' button should not be enabled " +
                        "until 'Password' and 'Confirm Password' fields values would be equal").isFalse();
        softly.assertAll();
    }

    @DisplayName("Type email of another user, valid first name, last name, valid password, confirm password," +
            " pick birth date, click 'I accept checkbox' and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative"), @Tag("defect")})
    @Test
    void CreationWithWrongAlreadyUsedEmail() {
        TestUser user = CredentialsGenerator.generateTestUser();
        String alreadyUsedLogin = config.getLoginForSignIn();
        String password = user.getPassword();
        String dayToSelect = "10";
        String expectedErrorMessage =
                "This email address is already in use.";

        uiSteps.goToCreateAnAccount();
        uiSteps.fillInRegistrationFormWithoutFinalClick
                (user, alreadyUsedLogin, password, password, dayToSelect);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createAccountPage.getErrorAlreadyUsedEmailText())
                .as("Error message should contain warning").contains(expectedErrorMessage);
        softly.assertThat(createAccountPage.isCreateAccountEnabled())
                .as("'Create Account' button should not be enabled until valid email would be used").isFalse();
        softly.assertAll();
    }
}
