package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class PurchaseHandler implements Handler<PurchaseCommand, PurchaseDto> {

    private ProductRepository repository;

    public PurchaseHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public PurchaseDto handle(PurchaseCommand cmd) {
        Map<String, Integer> successMap = new HashMap<>();
        Map<String, Integer> failMap = new HashMap<>();

        validateStockSkuCodeAvailability(cmd);
        validateStockAmountAvailability(cmd, successMap, failMap);

        if (allRequestedProductsInStock(cmd, successMap)) {
            updateAmountsInRepository(cmd);
            return new SuccessPurchaseDto(successMap, true);
        } else
            return new FailPurchaseDto(failMap, false);
    }

    private boolean allRequestedProductsInStock(PurchaseCommand cmd, Map<String, Integer> successMap) {
        return cmd.getSkus().size() == successMap.size();
    }

    private void updateAmountsInRepository(PurchaseCommand cmd) {
        cmd.getSkus().entrySet().stream().forEach((entry) -> {
            Product productToUpdate = getProduct(entry.getKey());
            productToUpdate.reduceTheAmount(entry.getValue());
            repository.save(productToUpdate);
        });
    }

    private void validateStockAmountAvailability(PurchaseCommand cmd, Map<String, Integer> successMap, Map<String, Integer> failMap) {
        cmd.getSkus().entrySet().stream().forEach((entry) -> {
            Product productFromRepo = getProduct(entry.getKey());
            if (productFromRepo.getAmount() < entry.getValue()) {
                failMap.put(entry.getKey(), entry.getValue());
            } else
                successMap.put(entry.getKey(), entry.getValue());
        });
    }

    private Product getProduct(String scuCode) {
        return repository.get(scuCode);
    }

    private void validateStockSkuCodeAvailability(PurchaseCommand cmd) {
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        cmd.getSkus().entrySet().stream().forEach((entry) -> {
            if (!alreadyExist(entry.getKey())) {
                errors.add(entry.getKey(), "no such sku");
            }
        });
        if (!errors.isValid())
            throw new InvalidCommandException(errors);
    }

    private boolean alreadyExist(String skuCode) {
        return repository.ifExist(skuCode);
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return PurchaseCommand.class;
    }

}
