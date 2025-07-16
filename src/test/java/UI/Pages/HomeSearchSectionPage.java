package UI.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class HomeSearchSectionPage extends BasePage{

    //locators
    @FindBy(xpath = "//div[@class='modal-content _modal-content_1vao1q']")
    private WebElement searchModal;
    @FindBy(xpath = "//input[@data-test='form-control-input']")
    private WebElement searchField;
    @FindBy (name = "submit")
    private WebElement loupeButton;
    @FindBy(id = "num-results")
    private WebElement subtitleAfterSearch;
    @FindBy(xpath = "//ul[@data-test-suggestions]")
    private WebElement suggestionsDropdown;
    @FindBy(xpath = "//button[@aria-label='search for raincoat instead']")
    private WebElement theOnlySuggestionAfterRainciatType;
    @FindBy(xpath = "//h1[@class='qa-search-match-error _search-match-error_10fauf']")
    private WebElement failedSearchMessage;
    @FindBy (xpath = "//a[text()='Men']")
    private WebElement mensMenuAbove;
    @FindBy (xpath = "//a[contains(@class, 'bag-button')]")
    private WebElement bagButton;

    //methods
    public HomeSearchSectionPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("click on 'Search' button")
    public void clickSearch(){
        By searchButtonLocator = By.xpath("//button[@data-test-btn='search-cta']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
        searchButton.click();
    }

    @Step("check if 'Search' modal is visible")
    public boolean isSearchModalVisible(){
        return searchModal.isDisplayed();
    }

    @Step("type text into the 'Search' field")
    public void typeIntoSearchField(String textToSearch){
        searchField.sendKeys(textToSearch);
    }

    @Step("click 'Submit' loupe-like button")
    public void clickLoupeSubmit(){
        loupeButton.click();
    }

    @Step("get text of the subtitle")
    public String getSubtitleAfterSearchText(){
        return subtitleAfterSearch.getText();
    }

    @Step("wait until 'Search' modal becomes visible")
    public void waitForSearchModal(){
        By searchModalLocator = By.xpath("//div[@class='modal-content _modal-content_1vao1q']");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until
                (ExpectedConditions.visibilityOfElementLocated(searchModalLocator));
    }

    @Step("wait until 'Search' field becomes clickable")
    public void waitForSearchField(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(searchField));
    }

    @Step("check if suggestions after typing into 'Search' field becomes visible")
    public boolean isSuggestionsDropdownVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(suggestionsDropdown));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("click on the last suggestion after dropdown has appeared")
    public void clickOnSuitableSuggestion(){
        WebElement suggestionAfterYankeesType = driver.findElement
                (By.xpath("(//ul[@data-test-suggestions]//button[contains(., 'new york')])[1]"));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until
                (ExpectedConditions.elementToBeClickable(suggestionAfterYankeesType));
        suggestionAfterYankeesType.click();
    }

    @Step("wait for par of known url")
    public void waitForUrlContains(String partialUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.getCurrentUrl().contains(partialUrl));
    }

    @Step("wait for suggestion after typing \"rainciat\" to appear")
    public void waitForRainciatSuggestion(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(theOnlySuggestionAfterRainciatType));
    }

    @Step("get text of the only suggestion after typing \"rainciat\" in the 'Search' field")
    public String getSuggestionRainciatText(){
        return theOnlySuggestionAfterRainciatType.getText();
    }

    @Step("get text of the message that appears if item wasn't found")
    public String getTextOfErrorSearchMessage(){
        return failedSearchMessage.getText();
    }

    @Step("move to 'Men's' section")
    public void moveToMens(){
        Actions actions = new Actions(driver);
        actions.moveToElement(mensMenuAbove).pause(Duration.ofSeconds(2)).perform();
    }

    @Step("click on 'Jeans' inside 'Men's' section with waiting for presence ofd element")
    public void clickOnJeansInsideMens() {
        By jeansLinkLocator = By.xpath
                ("//div[h6[text()='Categories']]//a[@href='/us/en/c/ae/men/bottoms/jeans/cat6430041?pagetype=plp']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement jeansLink = wait.until(ExpectedConditions.presenceOfElementLocated(jeansLinkLocator));
        jeansLink.click();
    }

    @Step("click on 'Bag' button")
    public void clickOnBagButton(){
        bagButton.click();
    }
}
