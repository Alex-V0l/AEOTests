package UI.Tests;

import UI.Pages.CreateAnAccountPage;
import UI.Pages.HomePage;
import Utils.TestPropertiesConfig;
import Utils.Utils;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import Utils.AllureExtension;


import static Utils.Constants.CREATE_AN_ACCOUNT_URL;
import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI tests")
@ExtendWith(AllureExtension.class)
public class CreateAnAccountTests extends BaseTest{

    private HomePage hPage;
    private CreateAnAccountPage CAPage;
    TestPropertiesConfig config;
    private Utils utils;

    @BeforeEach
    void setup(){
        hPage = new HomePage(driver);
        CAPage = new CreateAnAccountPage(driver);
        config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
        utils = new Utils(driver);
        hPage.openHomePage();
    }

    @DisplayName("Go to 'Create an Account' page and check url and page's header text")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void transitionToCreateAnAccountPage(){
        String expectedCreateAnAccountHeaderText = "Create an Account";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(CAPage.getCurrentURL())
                .as("Transition to 'Create an Account' page should have been executed").isEqualTo(CREATE_AN_ACCOUNT_URL);
        softly.assertThat(CAPage.getPagesHeaderText())
                .as("Page's header 'Create an Account' should be visible").isEqualTo(expectedCreateAnAccountHeaderText);
        softly.assertAll();
    }

    @DisplayName("Successful creation of an account test " +
            "(fill all necessary field with valid fields pick birth date and agree with policies)")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void successfulCreation() {
        String login = config.getLogin();
        String password = config.getPassword();
        String firstName = "Sam";
        String lastName = "Newman";
        String zipCode = "46385";
        String expectedUrl = "https://www.ae.com/us/en/myaccount/real-rewards/account-summary";
        String expectedMessage = "Account created";
        String expectedHeadersText = "Sam's account";
        String dayToSelect = "10";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
        CAPage.typeIntoEmailField(login);
        CAPage.typeIntoFirstNameField(firstName);
        CAPage.typeIntoLastNameField(lastName);
        utils.scrollWithButton();
        CAPage.typeIntoPasswordField(password);
        CAPage.typeIntoConfirmPasswordField(password);
        CAPage.typeIntoPostalCodeField(zipCode);
        CAPage.clickOnMonthSelector();
        CAPage.clickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.clickIAcceptCheckbox();
        CAPage.waitForCreateAccountButton();
        CAPage.clickOnCreateAccountButton();
        CAPage.waitForUrl(expectedUrl);
        CAPage.waitForAlert();
        String actualUrl = CAPage.getCurrentURL();
        String actualAlertsText = CAPage.getAlertsAfterCreatingText();
        String actualHeadersText = CAPage.getMenuHeadersAfterCreationText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualUrl)
                .as("Transition to account's page should have been executed").isEqualTo(expectedUrl);
        softly.assertThat(actualAlertsText)
                .as("User should have received message about successful creation").isEqualTo(expectedMessage);
        softly.assertThat(actualHeadersText)
                .as("User's name should be visible after successful creation of an account")
                .isEqualTo(expectedHeadersText);
        softly.assertAll();
    }

    @DisplayName("Type valid email, first name, last name, invalid password, confirm invalid password," +
            " pick birth date, click 'I accept' checkbox and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithTooBigPassword() {
        String login = config.getLogin();
        String passwordOf26Characters = "qwe123rty456yui789iop0asd2";
        String firstName = "Sam";
        String lastName = "Newman";
        String zipCode = "46385";
        String dayToSelect = "10";
        String expectedErrorMessage =
                "Please enter a password that contains letters + numbers and is 8-25 characters long.";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
        CAPage.typeIntoEmailField(login);
        CAPage.typeIntoFirstNameField(firstName);
        CAPage.typeIntoLastNameField(lastName);
        utils.scrollWithButton();
        CAPage.typeIntoPasswordField(passwordOf26Characters);
        CAPage.typeIntoConfirmPasswordField(passwordOf26Characters);
        CAPage.typeIntoPostalCodeField(zipCode);
        String errorPasswordMessage = CAPage.getErrorsPasswordText();
        CAPage.clickOnMonthSelector();
        CAPage.clickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.clickIAcceptCheckbox();
        boolean  isCreateAccountButtonEnabled = CAPage.isCreateAccountEnabled();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorPasswordMessage)
                .as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedErrorMessage);
        softly.assertThat(isCreateAccountButtonEnabled)
                .as("'Create Account' button should not be enabled until valid password would be used").isFalse();
        softly.assertAll();
    }

    @DisplayName("Type invalid email, valid first name, valid last name, password, confirm password," +
            " pick birth date, click 'I accept' checkbox and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithInvalidEmail() {
        String login = "SamNewman@@gmail.ru";
        String password = config.getPassword();
        String firstName = "Sam";
        String lastName = "Newman";
        String zipCode = "46385";
        String dayToSelect = "10";
        String expectedErrorMessage = "Please enter a valid email address.";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
        CAPage.typeIntoEmailField(login);
        CAPage.typeIntoFirstNameField(firstName);
        CAPage.typeIntoLastNameField(lastName);
        String emailErrorText = CAPage.getErrorEmailText();
        utils.scrollWithButton();
        CAPage.typeIntoPasswordField(password);
        CAPage.typeIntoConfirmPasswordField(password);
        CAPage.typeIntoPostalCodeField(zipCode);
        CAPage.clickOnMonthSelector();
        CAPage.clickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.clickIAcceptCheckbox();
        boolean  isCreateAccountButtonEnabled = CAPage.isCreateAccountEnabled();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(emailErrorText)
                .as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedErrorMessage);
        softly.assertThat(isCreateAccountButtonEnabled)
                .as("'Create Account' button should not be enabled until valid email would be used").isFalse();
        softly.assertAll();
    }

    @DisplayName("Type valid email, first name, last name, password, confirm password," +
            " pick birth date and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithoutBirthDate() {
        String login = config.getLogin();
        String password = config.getPassword();
        String firstName = "Sam";
        String lastName = "Newman";
        String zipCode = "46385";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
        CAPage.typeIntoEmailField(login);
        CAPage.typeIntoFirstNameField(firstName);
        CAPage.typeIntoLastNameField(lastName);
        utils.scrollWithButton();
        CAPage.typeIntoPasswordField(password);
        CAPage.typeIntoConfirmPasswordField(password);
        CAPage.typeIntoPostalCodeField(zipCode);
        CAPage.clickIAcceptCheckbox();
        boolean  isCreateAccountButtonEnabled = CAPage.isCreateAccountEnabled();

       assertThat(isCreateAccountButtonEnabled)
                .as("'Create Account' button should not be enabled until birth date would be picked").isFalse();
    }

    @DisplayName("Type valid email, first name, last name, valid password, confirm password incorrectly," +
            " pick birth date, click 'I accept checkbox' and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithWrongConfirmPassword() {
        String login = config.getLogin();
        String password = config.getPassword();
        String incorrectPassword = password + "...";
        String firstName = "Sam";
        String lastName = "Newman";
        String zipCode = "46385";
        String dayToSelect = "10";
        String expectedErrorMessage =
                "Please confirm the two passwords you've entered match.";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
        CAPage.typeIntoEmailField(login);
        CAPage.typeIntoFirstNameField(firstName);
        CAPage.typeIntoLastNameField(lastName);
        utils.scrollWithButton();
        CAPage.typeIntoPasswordField(password);
        CAPage.typeIntoConfirmPasswordField(incorrectPassword);
        CAPage.typeIntoPostalCodeField(zipCode);
        String errorConfirmPasswordMessage = CAPage.getErrorsConfirmPasswordText();
        CAPage.clickOnMonthSelector();
        CAPage.clickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.clickIAcceptCheckbox();
        boolean  isCreateAccountButtonEnabled = CAPage.isCreateAccountEnabled();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorConfirmPasswordMessage)
                .as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedErrorMessage);
        softly.assertThat(isCreateAccountButtonEnabled)
                .as("'Create Account' button should not be enabled " +
                        "until 'Password' and 'Confirm Password' fields values would be equal").isFalse();
        softly.assertAll();
    }

    @DisplayName("Type email of another user, valid first name, last name, valid password, confirm password," +
            " pick birth date, click 'I accept checkbox' and check message and 'Create Account' button status")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void CreationWithWrongAlreadyUsedEmail() {
        String alreadyUsedLogin = config.getLoginForSignIn();
        String password = config.getPassword();
        String firstName = "Sam";
        String lastName = "Newman";
        String zipCode = "46385";
        String dayToSelect = "10";
        String expectedErrorMessage =
                "This email address is already in use.";

        CAPage.clickAccount();
        CAPage.waitForAccountModal();
        CAPage.clickCreateAnAccount();
        CAPage.waitForUrl(CREATE_AN_ACCOUNT_URL);
        CAPage.typeIntoEmailField(alreadyUsedLogin);
        CAPage.typeIntoFirstNameField(firstName);
        CAPage.typeIntoLastNameField(lastName);
        utils.scrollWithButton();
        CAPage.typeIntoPasswordField(password);
        CAPage.typeIntoConfirmPasswordField(password);
        CAPage.typeIntoPostalCodeField(zipCode);
        CAPage.clickOnMonthSelector();
        CAPage.clickOnNovember();
        CAPage.clickOnDaySelector(dayToSelect);
        CAPage.clickIAcceptCheckbox();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(CAPage.getErrorAlreadyUsedEmailText())
                .as("Error message should contain warning").contains(expectedErrorMessage);
        softly.assertThat(CAPage.isCreateAccountEnabled())
                .as("'Create Account' button should not be enabled until valid email would be used").isFalse();
        softly.assertAll();
    }
}
