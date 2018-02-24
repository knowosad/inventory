package pl.com.bottega.inventory.domain;

import pl.com.bottega.inventory.domain.commands.InflateCommand;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private String skuCode;
    private Integer amount;

    public Product() {
    }
    public Product(InflateCommand cmd) {
        this.skuCode = cmd.getSkuCode();
        this.amount = cmd.getAmount();
    }

    public void addValues(InflateCommand cmd) {
        this.amount += cmd.getAmount();
    }

    public void reduceTheAmount(Integer toTakeOffValues){
        this.amount -= toTakeOffValues;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
