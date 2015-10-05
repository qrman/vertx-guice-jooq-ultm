package io.github.qrman.potato.verticle;

import com.google.inject.Inject;
import io.github.qrman.potato.IntegrationTests;
import io.github.qrman.potato.entity.PotatoBag;
import io.github.qrman.potato.entity.Potato;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class PotatoVerticleTest extends IntegrationTests {

    @Inject
    private EventBus eb;

    @Test
    public void will_store_potato_bag_with_healthy_potatoes(TestContext context) {
        Async async = context.async();

        PotatoBag potatoBag = PotatoBag.builder()
          .origin("Poland")
          .items(Arrays.asList(
              new Potato(quality(100)),
              new Potato(quality(100)),
              new Potato(quality(100))
            ))
          .build();

        eb.send("potato-bag", Json.encode(potatoBag), bagResponse -> {
            eb.send("potatoes-in-basement", "Poland", (AsyncResult<Message<String>> potatoesResponse) -> {

                @SuppressWarnings("unchecked")
                List<Potato> decodeValue = Json.decodeValue(potatoesResponse.result().body(), List.class);

                context.assertEquals(decodeValue.size(), 3, "Potatoes number should be");
                async.complete();
            });
        });
    }

    @Test
    public void will_refuse_to_store_potato_bag_with_at_least_one_rotten_potato(TestContext context) {
        Async async = context.async();

        PotatoBag potatoBag = PotatoBag.builder()
          .origin("Poland")
          .items(Arrays.asList(
              new Potato(quality(100)), 
              new Potato(quality(0))   // <-- rotten potato!!!
            ))
          .build();

        eb.send("potato-bag", Json.encode(potatoBag), bagResponse -> {
            eb.send("potatoes-in-basement", "Poland", (AsyncResult<Message<String>> potatoesResponse) -> {
                
                context.assertEquals(bagResponse.result().body(), "Bag with rotten potato cannot be stored", "Response status should be");

                @SuppressWarnings("unchecked")
                List<Potato> decodeValue = Json.decodeValue(potatoesResponse.result().body(), List.class);
                context.assertEquals(decodeValue.size(), 0, "No potato in basement");
                async.complete();
            });
        });

    }

    private int quality(int quality) {
        return quality;
    }
}
