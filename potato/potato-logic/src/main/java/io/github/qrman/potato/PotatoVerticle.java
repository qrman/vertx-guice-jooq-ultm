package io.github.qrman.potato;

import com.google.inject.Guice;
import io.github.qrman.potato.entity.Transaction;
import io.github.qrman.potato.guice.GuiceModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
public class PotatoVerticle extends AbstractVerticle {
    
    @Override
    public void start() throws Exception {
        //Binds all dependencies to already initialized vertx instance
        Guice.createInjector(new GuiceModule(vertx)).injectMembers(this);
        
        vertx.eventBus().consumer("potato-transaction", (Message<String> transactionMessage) -> {
            log.log(Level.INFO, "Potato transaction start processing");
            
            Transaction transaction = Json.decodeValue(transactionMessage.body(), Transaction.class);
            log.log(Level.INFO, "Potato transaction received {0}", transaction);
            
            transactionMessage.reply("Transaction stored");
        });
        
    }
}
