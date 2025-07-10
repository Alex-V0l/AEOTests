package UI.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsedToDoublePricesCheckoutInfo {
    private double standardShippingPrice;
    private double currentShippingCost;
    private double currentOrderTotal;
    private double twoDayShippingCost;
    private double currentTaxValue;
}
