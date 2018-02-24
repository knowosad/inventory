package pl.com.bottega.inventory.domain.commands;

import java.util.Map;

public class PurchaseCommand implements Validatable {

    private Map<String, Integer> skus;

    @Override
    public void validate(ValidationErrors errors) {
        if (skus == null || skus.isEmpty()) {
            errors.add("skus", "are required");
        }
        for (Map.Entry<String, Integer> entry : skus.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value <= 0 || value >= 1000)
                errors.add(key, "must be between 1 and 999");
        }
    }

    public Map<String, Integer> getSkus() {
        return skus;
    }

    public void setSkus(Map<String, Integer> skus) {
        this.skus = skus;
    }
}
