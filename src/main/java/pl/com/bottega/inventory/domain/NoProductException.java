package pl.com.bottega.inventory.domain;

public class NoProductException extends RuntimeException {

    public NoProductException(String s) {
        super(s);
    }

}
