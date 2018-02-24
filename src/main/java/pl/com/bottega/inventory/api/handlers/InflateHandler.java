package pl.com.bottega.inventory.api.handlers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;
import pl.com.bottega.inventory.domain.commands.InflateCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;

@Component
public class InflateHandler implements Handler<InflateCommand, Void> {

    private ProductRepository repository;

    public InflateHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Void handle(InflateCommand cmd) {
        if (alreadyExist(cmd)) {
            updateProduct(cmd);
        } else {
            addProduct(cmd);
        }
        return null;
    }

    private void addProduct(InflateCommand cmd) {
        Product product = new Product(cmd);
        repository.save(product);
    }

    private void updateProduct(InflateCommand cmd) {
        Product product = repository.get(cmd.getSkuCode());
        product.addValues(cmd);
        repository.save(product);
    }

    private boolean alreadyExist(InflateCommand cmd) {
        return repository.ifExist(cmd.getSkuCode());
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return InflateCommand.class;
    }
}
