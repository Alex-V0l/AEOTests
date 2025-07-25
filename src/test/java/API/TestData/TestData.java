package API.TestData;

import API.Models.AuthResponseModel;
import API.Models.CartModels.OrderItem;
import API.Models.CartModels.OrderRequest;

import java.util.List;


public class TestData {

    public static final String INVALID_AUTH_HEADER_VALUE = "qwerty1234";
    public static final String VALID_AUTH_HEADER_VALUE =
            "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==";
    public static final String INVALID_GRANT_TYPE_VALUE = "admin_credentials";
    public static final String NEW_YORK_YANKEES_QUERY = "?query=new%20york%20yankees&resultsPerPage=60&us=ae";
    public static final OrderRequest ITEMS_OF_JEANS = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0041370875")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_11_JEANS = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0041370875")
                            .quantity(11)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_JACKET = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0042421008")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_WITH_WRONG_ID = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("1Q")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_POLO = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0043256270")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_SHIRT = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0043043678")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_DRESSES = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0043656941")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_T_SHIRTS = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0043222983")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_PANTS = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0042942870")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_WOMENS_JEANS = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0042444331")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final OrderRequest ITEMS_OF_POLO_T_SHIRTS = OrderRequest.builder()
            .items(List.of(
                    OrderItem.builder()
                            .skuId("0043256395")
                            .quantity(1)
                            .build()
            ))
            .build();
    public static final AuthResponseModel VALID_TOKEN_RESPONSE = AuthResponseModel.builder()
            .expiresIn(1800)
            .refreshExpiresIn(0)
            .tokenType("Bearer")
            .notBeforePolicy(0)
            .scope("guest")
            .build();
}
