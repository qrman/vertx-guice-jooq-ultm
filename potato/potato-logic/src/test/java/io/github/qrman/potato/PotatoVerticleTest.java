package io.github.qrman.potato;

import io.github.qrman.potato.entity.Transaction;
import io.github.qrman.potato.entity.TransactionPosition;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import java.math.BigDecimal;
import java.util.Arrays;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PotatoVerticleTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    @Before
    public void deployVerticle(TestContext context) {
        rule.vertx().deployVerticle(new PotatoVerticle(), context.asyncAssertSuccess());
    }

    @Test
    public void will_receive_message_and_response(TestContext context) {

        Transaction transaction = new Transaction(
          "Poland",
          Arrays.asList(
            new TransactionPosition(150L, new BigDecimal("100")),
            new TransactionPosition(300L, new BigDecimal("200")),
            new TransactionPosition(450L, new BigDecimal("350"))
          ), 
          new LocalDate(2015, 9, 21));

        Async async = context.async();
        rule.vertx().eventBus().send("potato-transaction", Json.encode(transaction), (response) -> {

            context.assertEquals(response.result().body(), "Transaction stored", "Response status should be");
            async.complete();
        });
    }
}
