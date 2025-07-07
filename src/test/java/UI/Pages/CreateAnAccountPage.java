package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateAnAccountPage extends BasePage {

    //locators
    @FindBy(xpath = "//a[@class='clickable qa-show-sidetray-account sidetray-account']")
    private WebElement accountButton;
    @FindBy(xpath = "//div[normalize-space(@class) = 'modal-content']")
    private WebElement accountModal;
    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAnAccountButton;
    @FindBy(xpath = "//h1[@class='page-header qa-page-header _page-header_190u4w']")
    private WebElement pageHeader;
    @FindBy(xpath = "//input[@placeholder='Email' and @type='email']")
    private WebElement emailField;
    @FindBy(name = "firstname")
    private WebElement firstNameField;
    @FindBy(name = "lastname")
    private WebElement lastNameField;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordField;
    @FindBy(name = "confirm_password")
    private WebElement confirmPasswordField;
    @FindBy(name = "postalCode")
    private WebElement postalCodeField;
    @FindBy(name = "month")
    private WebElement mothSelectorButton;
    @FindBy(xpath = "//option[@value='11' and text()='November']")
    private WebElement novemberOptionFromMonthSelector;
    @FindBy(name = "day")
    private WebElement daySelectorButton;
    @FindBy(name = "acceptTerms")
    private WebElement iAcceptCheckbox;
    @FindBy(xpath = "//button[@data-test-btn='submit']")
    private WebElement createAccountButton;
    @FindBy(xpath = "//div[@class='alert-content']//h6")
    private WebElement alertText;
    @FindBy(xpath = "//div[normalize-space(@class) = 'text-bold qa-list-menu-header']")
    private WebElement menuHeaderAfterCreating;
    @FindBy(xpath = "//div[contains (@data-test-form-error, 'error.account.password.invalid')]")
    private WebElement errorPassword;
    @FindBy(xpath = "//div[contains (@data-test-form-error, 'error.account.confirmPassword.doesNotMatchPassword')]")
    private WebElement errorConfirmPassword;
    @FindBy(xpath = "//div[contains (@data-test-form-error, 'error.account.email.invalid')]")
    private WebElement errorEmail;

    // methods
    public CreateAnAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("click on account button")
    public void clickAccount() {
        accountButton.click();
    }

    @Step("wait until 'Account' modal becomes visible")
    public void waitForAccountModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountModal));
    }

    @Step("click on 'Create an Account' link")
    public void clickCreateAnAccount() {
        createAnAccountButton.click();
    }

    @Step("get text of the page's header")
    public String getPagesHeaderText() {
        return pageHeader.getText();
    }

    @Step("type into 'Email' field")
    public void typeIntoEmailField(String email) {
        emailField.sendKeys(email);
    }

    @Step("type into 'First name' field")
    public void typeIntoFirstNameField(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    @Step("type into 'Last name' field")
    public void typeIntoLastNameField(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    @Step("type into 'Password' field")
    public void typeIntoPasswordField(String password) {
        passwordField.sendKeys(password);
    }

    @Step("type into 'Confirm password' field")
    public void typeIntoConfirmPasswordField(String password) {
        confirmPasswordField.sendKeys(password);
    }

    @Step("type into 'Postal code' field")
    public void typeIntoPostalCodeField(String code) {
        postalCodeField.sendKeys(code);
    }

    @Step("click on 'Month' selector button")
    public void clickOnMonthSelector() {
        mothSelectorButton.click();
    }

    @Step("click on 'November' from month selector")
    public void clickOnNovember() {
        novemberOptionFromMonthSelector.click();
    }

    @Step("click on 'Day' selector")
    public void clickOnDaySelector(String day) {
        Select select = new Select(daySelectorButton);
        select.selectByValue(day);
    }

    @Step("click on 'I accept' checkbox via JS")
    public void clickIAcceptCheckbox() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", iAcceptCheckbox);
    }

    @Step("click on create account button")
    public void clickOnCreateAccountButton() {
        Actions actions = new Actions(driver);
        actions.moveToElement(createAccountButton).pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("get alert's text after creating a new account")
    public String getAlertsAfterCreatingText() {
        return alertText.getText();
    }

    @Step("ge text of the header menu after creating a new account")
    public String getMenuHeadersAfterCreationText() {
        return menuHeaderAfterCreating.getText();
    }

    @Step("wait for 'Create Account' button to be clickable")
    public void waitForCreateAccountButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> createAccountButton.isEnabled());
    }

    @Step("wait for alert's text to be visible")
    public void waitForAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(alertText));
    }

    @Step("get error's text after using invalid password")
    public String getErrorsPasswordText() {
        return errorPassword.getText();
    }

    @Step("get error's text after using different password in 'Password' and 'Confirm Password' fields")
    public String getErrorsConfirmPasswordText() {
        return errorConfirmPassword.getText();
    }

    @Step("check that 'Create Account' button is enabled")
    public boolean isCreateAccountEnabled() {
        return createAccountButton.isEnabled();
    }

    @Step("get text from error caused by using invalid email")
    public String getErrorEmailText() {
        return errorEmail.getText();
    }

    @Step("get text from error caused by using already used email")
    public String getErrorAlreadyUsedEmailText() {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", emailField);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@data-label-code, 'error.account.email.alreadyExists')]")
            ));
            return element.getText();
        } catch (TimeoutException e) {
            throw new NoSuchElementException
                    ("Expected error message for already used email did not appear within timeout", e);
        }
    }
}