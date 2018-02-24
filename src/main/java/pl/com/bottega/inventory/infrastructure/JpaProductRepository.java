package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;

import javax.persistence.EntityManager;

@Component
public class JpaProductRepository implements ProductRepository {

    private EntityManager entityManager;

    public JpaProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Product get(String skuCode) {
        Product product = entityManager.find(Product.class, skuCode);
        return product;
    }

    @Override
    public boolean ifExist(String skuCode) {
        Product product = entityManager.find(Product.class, skuCode);
        if (product != null) {
            return true;
        }
        return false;
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }
}
