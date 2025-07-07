package Utils;

import UI.Pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class Utils extends BasePage {

    public Utils(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("scroll with button")
    public void scrollWithButton(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
    }

    @Step("parse string that contains money value into double")
    public double parseToDouble (String valueToParse){
        return Double.parseDouble(valueToParse.replaceAll("[^\\d.]", ""));
    }

    @Step("parse string that contains digits into int")
    public int parseToInt(String valueToParse){
        return Integer.parseInt(valueToParse.replaceAll("\\D", ""));
    }
}
