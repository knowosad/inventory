package pl.com.bottega.inventory.domain;

public interface ProductRepository {

    Product get(String skuCode);

    boolean ifExist(String skuCode);

    void save(Product product);
}
