package io.github.qrman.potato;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.qrman.potato.entity.Potato;
import io.github.qrman.potato.entity.PotatoBag;
import io.github.qrman.potato.guice.GuiceModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import lombok.extern.java.Log;

@Log
public class PotatoVerticle extends AbstractVerticle {
    
    @Inject
    private PotatoBasement potatoBasement;
    
    @Override
    public void start() throws Exception {
        //Binds all dependencies to already initialized vertx instance
        Guice.createInjector(new GuiceModule(vertx)).injectMembers(this);
        
        vertx.eventBus().consumer("potato-bag", (Message<String> transactionMessage) -> {
            log.log(Level.INFO, "Potato bag start processing");
            
            PotatoBag potatoBag = Json.decodeValue(transactionMessage.body(), PotatoBag.class);
            log.log(Level.INFO, "Potato bag received {0}", potatoBag);
            
            potatoBasement.store(potatoBag);
            
            transactionMessage.reply("Potato bag stored in basement");
        });
        
        vertx.eventBus().consumer("potatoes-in-basement", (Message<String> countryMessage) -> {
            
            String potatoOriginCountry = countryMessage.body();
            log.log(Level.INFO, "Searching in basement for potatoes from {0}", potatoOriginCountry);
            
            List<Potato> potatoes = potatoBasement.fetchByOrigin(potatoOriginCountry);
            countryMessage.reply(Json.encode(potatoes));
        });
    }
}
