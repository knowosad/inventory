package pl.com.bottega.inventory.api.dtos;

import java.util.Map;

public class FalsePurchaseDto extends PurchaseDto{

    private Map<String, Integer> missingProducts;

    public FalsePurchaseDto(Map<String, Integer> products, boolean success) {
        super(success);
        this.missingProducts = products;
    }

    public Map<String, Integer> getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Map<String, Integer> missingProducts) {
        this.missingProducts = missingProducts;
    }
}
