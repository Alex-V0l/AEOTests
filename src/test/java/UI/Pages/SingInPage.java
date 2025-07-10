package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SingInPage extends BasePage{

    //locators
    @FindBy (xpath = "//h2[@data-test='sidetray-title']")
    private WebElement signInModalHeader;
    @FindBy(xpath = "//div[@data-id='modalSidetrayAccountLogin']")
    private WebElement singInModal;
    @FindBy (name = "username")
    private WebElement loginField;
    @FindBy (name = "password")
    private WebElement passwordField;
    @FindBy (xpath = "//button[@data-test-btn='submit']")
    private WebElement signInButton;
    @FindBy (xpath = "//div[@data-id='modalSidetrayAccount']")
    private WebElement accountsModal;
    @FindBy (className = "modal-title")
    private WebElement accountsHeader;
    @FindBy (xpath = "//button[@data-test-btn='sign-out']")
    private WebElement signOutButton;
    @FindBy (xpath = "//button[@data-test-btn='signin']")
    private WebElement signInBasicButton;
    @FindBy (xpath = "//h6[contains (@class, 'alert-header')]")
    private WebElement alertHeader;
    @FindBy (xpath = "//div[contains (@class, 'qa-error-help-block')]")
    private WebElement clueElement;
    @FindBy (xpath = "//button[contains (@class, 'qa-link-forgot-password')]")
    private WebElement forgotPasswordButton;
    @FindBy (xpath = "//li[@data-label-code='forgotPassword.notification.message.new']")
    private WebElement restorePasswordMessage;

    //methods
    public SingInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("get text from 'Sign In' modal header")
    public String getSignInModalsHeaderText(){
        return signInModalHeader.getText();
    }

    @Step("check if 'Sign In' modal visible")
    public boolean isSignInModalVisible(){
        return singInModal.isDisplayed();
    }

    @Step("type into 'Login' field")
    public void typeIntoEmail(String login){
        loginField.sendKeys(login);
    }

    @Step("type into 'Password' field")
    public void typeIntoPassword(String password){
        passwordField.sendKeys(password);
    }

    @Step("click on 'Sign In' button")
    public void clickSignIn(){
        signInButton.click();
    }

    @Step("get text from 'Account' modal header")
    public String getAccountsHeaderText(){
        return accountsHeader.getText();
    }

    @Step("check if 'Account' modal visible")
    public boolean isAccountsModalVisible(){
        return accountsModal.isDisplayed();
    }

    @Step("click on 'Sign Out' button")
    public void clickSignOut(){
        signOutButton.click();
    }

    @Step("click on 'Sign In' basic button")
    public void clickSignInBasic(){
        signInBasicButton.click();
    }

    @Step("wait for 'Account' modal's header to contain user's name")
    public void waitForUserNameInAccountsHeader(String userName) {
        new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(ExpectedConditions.textToBePresentInElement(accountsHeader, userName));
    }

    @Step("get text from alert in 'Sign In' modal header")
    public String getAlertsHeaderText(){
        return alertHeader.getText();
    }

    @Step("get text from clue under 'Email' field inside 'Sign In' modal")
    public String getCluesText(){
        return clueElement.getText();
    }

    @Step("wait for error message")
    public void waitForError(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(clueElement));
    }

    @Step("click in 'Forgot Password' basic button")
    public void clickForgotPassword(){
        forgotPasswordButton.click();
    }

    @Step("get text from 'Forgot password' field inside 'Sign In' modal")
    public String getForgotPasswordText(){
        return restorePasswordMessage.getText();
    }

    @Step("wait for alert message")
    public void waitForAlertMessage(String expectedText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(alertHeader, expectedText));
    }
}
