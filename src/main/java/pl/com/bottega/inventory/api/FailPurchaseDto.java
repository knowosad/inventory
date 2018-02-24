package pl.com.bottega.inventory.api;

import java.util.Map;

public class FailPurchaseDto extends PurchaseDto{

    private Map<String, Integer> missingProducts;

    public FailPurchaseDto(Map<String, Integer> products, boolean success) {
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
