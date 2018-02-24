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
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        Map<String, Integer> successMap = new HashMap<>();
        Map<String, Integer> failMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : cmd.getSkus().entrySet()) {
            if (alreadyExist(entry.getKey())) {
                Product productFromRepo = repository.get(entry.getKey());
                if (productFromRepo.getAmount() < entry.getValue()) {
                    failMap.put(entry.getKey(), entry.getValue());
                } else
                    successMap.put(entry.getKey(), entry.getValue());
            } else {
                errors.add(entry.getKey(), "no such sku");
            }
        }
        if (!errors.isValid())
            throw new InvalidCommandException(errors);
        if (failMap.isEmpty()) {
            for (Map.Entry<String, Integer> ent : cmd.getSkus().entrySet()) {
                Product prodToUpdate = repository.get(ent.getKey());
                prodToUpdate.takeOffValues(ent.getValue());
                repository.update(prodToUpdate);
            }
            return new SuccessPurchaseDto(successMap, true);
        } else
            return new FailPurchaseDto(failMap, false);
    }

    private boolean alreadyExist(String skuCode) {
        return repository.ifExist(skuCode);
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return PurchaseCommand.class;
    }

}
