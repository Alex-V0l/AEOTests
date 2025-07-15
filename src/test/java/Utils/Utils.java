package Utils;

import UI.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Utils extends BasePage {

    public Utils(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public double parseToDouble (String valueToParse){
        return Double.parseDouble(valueToParse.replaceAll("[^\\d.]", ""));
    }

    public int parseToInt(String valueToParse){
        return Integer.parseInt(valueToParse.replaceAll("\\D", ""));
    }

    public String formatSearchQueryForUrl(String query) {
        if (query == null) {
            return "";
        }
        return query.trim().replaceAll("\\s+", "+");
    }
}
