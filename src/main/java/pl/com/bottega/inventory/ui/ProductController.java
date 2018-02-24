package pl.com.bottega.inventory.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.dtos.PurchaseDto;
import pl.com.bottega.inventory.domain.commands.InflateCommand;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;

import java.util.Map;

@RestController
public class ProductController {

    private CommandGateway gateway;

    public ProductController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @PostMapping("/inventory")
    public void addProduct(@RequestBody InflateCommand cmd){
        gateway.execute(cmd);
    }

    @PostMapping("/purchase")
    public PurchaseDto purchaseProduct(@RequestBody Map<String, Integer> purchasedProducts){
        PurchaseCommand cmd = new PurchaseCommand();
        cmd.setSkus(purchasedProducts);
        return gateway.execute(cmd);
    }
}
