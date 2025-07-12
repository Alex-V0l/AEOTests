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
    private WebElement pagesHeader;
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
    private WebElement monthSelectorButton;
    @FindBy(xpath = "//option[@value='11' and text()='November']")
    private WebElement novemberOptionFromMonthSelector;
    @FindBy(name = "day")
    private WebElement daySelectorButton;
    @FindBy(name = "acceptTerms")
    private WebElement iAcceptCheckbox;
    @FindBy(xpath = "//button[@data-test-btn='submit']")
    private WebElement createAccountButton;
    @FindBy(xpath = "//div[@class='alert-content']//h6")
    private WebElement successfulCreationText;
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

    @Step("click on 'Account' button")
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
        return pagesHeader.getText();
    }

    @Step("scroll and type into 'Email' field")
    public void scrollAndTypeIntoEmailField(String email) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", emailField);
        emailField.sendKeys(email);
    }

    @Step("scroll and type into 'First name' field")
    public void scrollAndTypeIntoFirstNameField(String firstName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstNameField);
        js.executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                firstNameField, firstName
        );
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                firstName.equals(firstNameField.getDomProperty("value"))
        );
    }

    @Step("scroll and type into 'Last name' field")
    public void scrollAndTypeIntoLastNameField(String lastName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", lastNameField);
        lastNameField.sendKeys(lastName);
    }

    @Step("scroll and type into 'Password' field")
    public void scrollAndTypeIntoPasswordField(String password) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", passwordField);
        passwordField.sendKeys(password);
    }

    @Step("scroll and type into 'Confirm password' field")
    public void scrollAndTypeIntoConfirmPasswordField(String password) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", confirmPasswordField);
        confirmPasswordField.sendKeys(password);
    }

    @Step("scroll and type into 'Postal code' field")
    public void scrollAndTypeIntoPostalCodeField(String code) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", postalCodeField);
        postalCodeField.sendKeys(code);
    }

    @Step("click on 'Month' selector button")
    public void scrollAndClickOnMonthSelector() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", monthSelectorButton);
        monthSelectorButton.click();
    }

    @Step("click on 'November' from month selector")
    public void scrollAndClickOnNovember() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", novemberOptionFromMonthSelector);
        novemberOptionFromMonthSelector.click();
    }

    @Step("click on 'Day' selector")
    public void clickOnDaySelector(String day) {
        Select select = new Select(daySelectorButton);
        select.selectByValue(day);
    }

    @Step("click on 'I accept' checkbox")
    public void scrollAndClickIAcceptCheckbox() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", iAcceptCheckbox);
        js.executeScript("arguments[0].click();", iAcceptCheckbox);
    }

    @Step("click on 'Create Account' button")
    public void scrollAndClickOnCreateAccountButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        Actions actions = new Actions(driver);
        actions.scrollToElement(createAccountButton).moveToElement(createAccountButton)
                .pause(Duration.ofSeconds(2)).click().perform();
    }

    @Step("get successful message's text after creating a new account")
    public String getSuccessfulMessagesAfterCreatingText() {
        return successfulCreationText.getText();
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

    @Step("wait for successful message's text to be visible")
    public void waitForSuccessfulMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(successfulCreationText));
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