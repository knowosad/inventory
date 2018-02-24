package pl.com.bottega.inventory.api;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;
import pl.com.bottega.inventory.domain.commands.AddCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;

@Component
public class ProductHandler implements Handler<AddCommand, Void> {

    private ProductRepository repository;

    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Void handle(AddCommand cmd) {
        if (alreadyExist(cmd)) {
            updateProduct(cmd);
        } else {
            addProduct(cmd);
        }
        return null;
    }

    private void addProduct(AddCommand cmd) {
        Product product = new Product(cmd);
        repository.save(product);
    }

    private void updateProduct(AddCommand cmd) {
        Product product = repository.get(cmd.getSkuCode());
        product.addValues(cmd);
        repository.update(product);
    }

    private boolean alreadyExist(AddCommand cmd) {
        return repository.ifExist(cmd.getSkuCode());
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return AddCommand.class;
    }
}
