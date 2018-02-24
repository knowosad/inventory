package pl.com.bottega.inventory.domain.commands;

import java.util.Map;

public class PurchaseCommand implements Validatable {

    private Map<String, Integer> skus;
    private Integer ZERO = 0;
    private Integer THOUSAND = 1000;

    @Override
    public void validate(ValidationErrors errors) {
        if (skus == null || skus.isEmpty()) {
            errors.add("skus", "are required");
        }
        skus.entrySet().stream().forEach((entry) -> {
            if (entry.getValue() <= ZERO || entry.getValue() >= THOUSAND)
                errors.add(entry.getKey(), "must be between 1 and 999");
        });
    }

    public Map<String, Integer> getSkus() {
        return skus;
    }

    public void setSkus(Map<String, Integer> skus) {
        this.skus = skus;
    }
}
