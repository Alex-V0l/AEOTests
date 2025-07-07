package Steps;

import UI.Pages.*;
import Utils.Utils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class UISteps {

    private HomePage hPage;
    private Utils utils;
    private CartAndCheckoutPage cPage;
    private MensJeansPage mJPage;
    private SlimStraightJeansItemsPage sSJIPage;
    private CreateAnAccountPage CAAPage;
    private SingInPage SIPage;

    public UISteps(WebDriver driver) {
        this.hPage = new HomePage(driver);
        this.utils = new Utils(driver);
        this.cPage = new CartAndCheckoutPage(driver);
        this.mJPage = new MensJeansPage(driver);
        this.sSJIPage = new SlimStraightJeansItemsPage(driver);
        this.CAAPage = new CreateAnAccountPage(driver);
        this.SIPage = new SingInPage(driver);
    }

    @Step("Open 'Sign In' modal")
    public void openSignInModal() {
        CAAPage.clickAccount();
        CAAPage.waitForAccountModal();
        SIPage.clickSignInBasic();
    }

    @Step("type first name and last name and click sigh in")
    public void fillInNecessarySignInFieldsAndClick(String firstname, String lastname) {
        SIPage.typeIntoEmail(firstname);
        SIPage.typeIntoPassword(lastname);
        SIPage.clickSignIn();
    }

    @Step("type first name and last name, click sign in and wait for error")
    public void fillInNecessarySignInFieldsClickAndWait(String firstname, String lastname) {
        SIPage.typeIntoEmail(firstname);
        SIPage.typeIntoPassword(lastname);
        SIPage.clickSignIn();
        SIPage.waitForError();
    }

    @Step("Go to 'Men's Jeans' section and close adverts modal")
    public void goToMensJeansAndCloseAdverts() {
        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        hPage.closeModalAdverts();
    }

    @Step("Go to 'Men's Jeans' section, scroll to size and click")
    public void goToMensJeansAndClickSize() {
        hPage.moveToMens();
        hPage.clickOnJeansInsideMens();
        mJPage.scrollAndClickSize();
    }

    @Step("Select size checkbox")
    public void selectSizeCheckboxAndCloseAdverts() {
        mJPage.isCheckboxVisible();
        mJPage.clickOnCheckbox28x30();
        hPage.closeModalAdverts();
    }
}
