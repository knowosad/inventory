package pl.com.bottega.inventory.api.dtos;

public abstract class PurchaseDto {

    private boolean success;

    public PurchaseDto() {
    }

    public PurchaseDto(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
