package UI.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemData {
    private String name;
    private String salePrice;
    private String regularPrice;
    private String color;
    private String size;
    private String quantity;
}
