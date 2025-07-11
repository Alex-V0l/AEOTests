package UI.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricesCheckoutInfo {
    private String standardShippingPriceText;
    private String currentShippingCost;
    private String currentOrderTotal;
    private String twoDayShippingCost;
    private String currentTaxValue;
}
