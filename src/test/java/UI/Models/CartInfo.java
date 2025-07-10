package UI.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartInfo {
    private String shippingMessage;
    private String shippingProgressValue;
    private  String shippingProgressStatus;
    private  String maxShippingCostBeforeFree;
    private  String addedItemsName;
    private  String addedItemsSalePrice;
    private String addedItemsQuantity;
    private  String subTotal;
    private  String shippingPrice;
}
