package pl.com.bottega.inventory.api.dtos;

import java.util.Map;

public class TruePurchaseDto extends PurchaseDto{


    private Map<String, Integer> purchasedProducts;

    public TruePurchaseDto(Map<String, Integer> products, boolean success) {
        super(success);
        this.purchasedProducts = products;
    }

    public Map<String, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(Map<String, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}
