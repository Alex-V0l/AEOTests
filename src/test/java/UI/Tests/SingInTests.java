package UI.Tests;

import Steps.UISteps;
import UI.Pages.HomePage;
import UI.Pages.SingInPage;
import Utils.AllureExtension;
import Utils.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static Utils.Constants.VALUES_HAVE_TO_BE_EQUAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("UI Tests")
@ExtendWith(AllureExtension.class)
public class SingInTests extends BaseTest{

    private SingInPage SIPage;
    private HomePage hPage;
    TestPropertiesConfig config;
    private UISteps uIsteps;

    @BeforeEach
    void setup(){
        hPage = new HomePage(driver);
        SIPage = new SingInPage(driver);
        uIsteps = new UISteps(driver);
        config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
        hPage.openHomePage();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname, lastname," +
            " click 'Sign In' button and check that authorization was successful")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void successfulSignIn(){
        String expectedSignInModalsText = "Sign In";
        String usersName = "Jack";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        uIsteps.fillInNecessarySignInFieldsAndClick(config.getLoginForSignIn(), config.getPasswordForSignIn());
        SIPage.waitForUserNameInAccountHeader(usersName);
        boolean isAccountsModalVisible = SIPage.isAccountsModalVisible();
        String AccountsHeadersText = SIPage.getAccountsHeaderText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(isAccountsModalVisible).as("Modal of authorized user should be visible").isTrue();
        softlyAgain.assertThat(AccountsHeadersText).as("Header should contain user's name").contains(usersName);
        softlyAgain.assertAll();

        SIPage.clickSignOut();

        assertThat(SIPage.isAccountsModalVisible()).as("Account's modal should not be visible after signing out")
                .isFalse();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type invalid firstname, valid password," +
            " click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithInvalidFirstName(){
        String expectedSignInModalsText = "Sign In";
        String invalidLogin = "JohnDoe.gmail.com";
        String expectedClueText = "Please enter a valid email address.";
        String expectedAlertsText = "Hold up, there's a problem.";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        uIsteps.fillInNecessarySignInFieldsAndClick(invalidLogin, config.getPasswordForSignIn());
        String alertHeadersText = SIPage.getAlertsHeaderText();
        String cluesErrorText = SIPage.getCluesText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(alertHeadersText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedAlertsText);
        softlyAgain.assertThat(cluesErrorText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
        softlyAgain.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname, invalid password," +
            " click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithInvalidPassword(){
        String expectedSignInModalsText = "Sign In";
        String PasswordOfSevenDigits = "000000000";
        String expectedClueText =
                "Please enter a password that contains 8-25 characters with at least one letter and one number.";
        String expectedAlertsText = "Hold up, there's a problem.";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        uIsteps.fillInNecessarySignInFieldsAndClick(config.getLoginForSignIn(), PasswordOfSevenDigits);
        String alertHeadersText = SIPage.getAlertsHeaderText();
        String cluesErrorText = SIPage.getCluesText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(alertHeadersText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedAlertsText);
        softlyAgain.assertThat(cluesErrorText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
        softlyAgain.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname," +
            " password with less than 8 characters, click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithTooSmallPassword(){
        String expectedSignInModalsText = "Sign In";
        String PasswordOfSevenCharacters = "J0yt1me";
        String expectedClueText = "Your user name and password are incorrect.";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        uIsteps.fillInNecessarySignInFieldsClickAndWait(config.getLoginForSignIn(), PasswordOfSevenCharacters);
        String cluesErrorText = SIPage.getCluesText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(cluesErrorText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
        softlyAgain.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname," +
            " password with more than 25 characters, click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithTooBigPassword(){
        String expectedSignInModalsText = "Sign In";
        String PasswordOf26Characters = "qwe123rty456yui789iop0asd2";
        String expectedClueText =
                "Please enter a password that contains 8-25 characters with at least one letter and one number.";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        uIsteps.fillInNecessarySignInFieldsClickAndWait(config.getLoginForSignIn(), PasswordOf26Characters);
        String cluesErrorText = SIPage.getCluesText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(cluesErrorText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
        softlyAgain.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname," +
            " click 'Forgot Password' button and check message")
    @Tags({@Tag("P0"), @Tag("smoke")})
    @Test
    void forgotPasswordCheckWithValidEmail(){
        String expectedSignInModalsText = "Sign In";
        String expectedHeadersText = "Forgot your password?";
        String login = config.getLoginForSignIn();
        String expectedForgotPasswordMessage =
                "Email sent! Check " + login + " for a link to reset your password. It may take up to 15 minutes.";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        SIPage.typeIntoEmail(login);
        SIPage.clickForgotPassword();
        SIPage.waitForAlertMessage(expectedHeadersText);
        String headersText = SIPage.getAlertsHeaderText();
        String restorePasswordMessage = SIPage.getForgotPasswordText();

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(headersText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedHeadersText);
        softlyAgain.assertThat(restorePasswordMessage).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(expectedForgotPasswordMessage);
        softlyAgain.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid password" +
            ", click 'Forgot Password' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void forgotPasswordCheckWithValidPassword(){
        String expectedSignInModalsText = "Sign In";
        String expectedCluesText = "Please enter your email address.";

        uIsteps.openSignInModal();
        boolean isSignInModalVisible = SIPage.isSignInModalVisible();
        String singInModalsText = SIPage.getSignInModalsHeaderText();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(singInModalsText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedSignInModalsText);
        softly.assertThat(isSignInModalVisible).as("'Sign In' modal should be visible").isTrue();
        softly.assertAll();

        SIPage.typeIntoPassword(config.getPasswordForSignIn());
        SIPage.clickForgotPassword();
        SIPage.waitForError();
        String cluesText = SIPage.getCluesText();

        assertThat(cluesText).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedCluesText);
    }
}
