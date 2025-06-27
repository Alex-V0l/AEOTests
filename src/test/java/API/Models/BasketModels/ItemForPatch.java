package API.Models.BasketModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemForPatch {
    private String skuId;
    private int quantity;
    private String itemId;
}
