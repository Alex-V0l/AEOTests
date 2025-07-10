package UI.Tests;

import Steps.UISteps;
import UI.Pages.HomeSearchSectionPage;
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
    private HomeSearchSectionPage hssPage;
    TestPropertiesConfig config;
    private UISteps uIsteps;

    @BeforeEach
    void setup(){
        hssPage = new HomeSearchSectionPage(driver);
        SIPage = new SingInPage(driver);
        uIsteps = new UISteps(driver);
        config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
        hssPage.openHomePage();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname, lastname," +
            " click 'Sign In' button and check that authorization was successful")
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect")})
    @Test
    void successfulSignIn(){
        String usersName = "Jack";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        uIsteps.fillInNecessarySignInFieldsAndClick(config.getLoginForSignIn(), config.getPasswordForSignIn());
        SIPage.waitForUserNameInAccountsHeader(usersName);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SIPage.isAccountsModalVisible()).as("Modal of authorized user should be visible")
                .isTrue();
        softly.assertThat(SIPage.getAccountsHeaderText()).as("Header should contain user's name")
                .contains(usersName);
        softly.assertAll();

        SIPage.clickSignOut();

        assertThat(SIPage.isAccountsModalVisible()).as("Account's modal should not be visible after signing out")
                .isFalse();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type invalid firstname, valid password," +
            " click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithInvalidFirstName(){
        String invalidLogin = "JohnDoe.gmail.com";
        String expectedClueText = "Please enter a valid email address.";
        String expectedAlertsText = "Hold up, there's a problem.";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        uIsteps.fillInNecessarySignInFieldsAndClick(invalidLogin, config.getPasswordForSignIn());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SIPage.getAlertsHeaderText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedAlertsText);
        softly.assertThat(SIPage.getCluesText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
        softly.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname, invalid password," +
            " click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithInvalidPassword(){
        String PasswordOfSevenDigits = "000000000";
        String expectedClueText =
                "Please enter a password that contains 8-25 characters with at least one letter and one number.";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        uIsteps.fillInNecessarySignInFieldsAndClick(config.getLoginForSignIn(), PasswordOfSevenDigits);

        assertThat(SIPage.getCluesText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname," +
            " password with less than 8 characters, click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative"), @Tag("defect")})
    @Test
    void authorizationWithTooSmallPassword(){
        String PasswordOfSevenCharacters = "J0yt1me";
        String expectedClueText = "Your user name and password are incorrect.";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        uIsteps.fillInNecessarySignInFieldsClickAndWait(config.getLoginForSignIn(), PasswordOfSevenCharacters);

        assertThat(SIPage.getCluesText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname," +
            " password with more than 25 characters, click 'Sign In' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void authorizationWithTooBigPassword(){
        String PasswordOf26Characters = "qwe123rty456yui789iop0asd2";
        String expectedClueText =
                "Please enter a password that contains 8-25 characters with at least one letter and one number.";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        uIsteps.fillInNecessarySignInFieldsClickAndWait(config.getLoginForSignIn(), PasswordOf26Characters);

        assertThat(SIPage.getCluesText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedClueText);
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid firstname," +
            " click 'Forgot Password' button and check message")
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect")})
    @Test
    void forgotPasswordCheckWithValidEmail(){
        String expectedHeadersText = "Forgot your password?";
        String login = config.getLoginForSignIn();
        String expectedForgotPasswordMessage =
                "Email sent! Check " + login + " for a link to reset your password. It may take up to 15 minutes.";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        SIPage.typeIntoEmail(login);
        SIPage.clickForgotPassword();
        SIPage.waitForAlertMessage(expectedHeadersText);

        SoftAssertions softlyAgain = new SoftAssertions();
        softlyAgain.assertThat(SIPage.getAlertsHeaderText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedHeadersText);
        softlyAgain.assertThat(SIPage.getForgotPasswordText()).as(VALUES_HAVE_TO_BE_EQUAL)
                .isEqualTo(expectedForgotPasswordMessage);
        softlyAgain.assertAll();
    }

    @DisplayName("Go to 'Sign In' modal inside 'Account' modal, type valid password" +
            ", click 'Forgot Password' button and check message")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void forgotPasswordCheckWithValidPassword(){
        String expectedCluesText = "Please enter your email address.";

        uIsteps.openSignInModalCheckItsVisibilityAndSignInHeaderText();
        SIPage.typeIntoPassword(config.getPasswordForSignIn());
        SIPage.clickForgotPassword();
        SIPage.waitForError();

        assertThat(SIPage.getCluesText()).as(VALUES_HAVE_TO_BE_EQUAL).isEqualTo(expectedCluesText);
    }
}
