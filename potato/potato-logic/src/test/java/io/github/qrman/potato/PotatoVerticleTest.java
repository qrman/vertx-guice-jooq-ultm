package io.github.qrman.potato;

import com.google.inject.Inject;
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
import org.joda.time.LocalDate;
import org.junit.Test;

public class PotatoVerticleTest extends IntegrationTests {

    @Inject
    private EventBus eb;

    @Test
    public void will_store_potato_bag(TestContext context) {
        Async async = context.async();

        PotatoBag potatoBag = new PotatoBag(
          "Poland",
          Arrays.asList(
            new Potato(10, 100),
            new Potato(7, 100),
            new Potato(5, 100)
          ),
          date("2015-09-21"));

        eb.send("potato-bag", Json.encode(potatoBag), (bagResponse) -> {
            context.assertEquals(bagResponse.result().body(), "Potato bag stored in basement", "Response status should be");
            eb.send("potatoes-in-basement", "Poland", (AsyncResult<Message<String>> potatoesResponse) -> {

                List<Potato> decodeValue = Json.decodeValue(potatoesResponse.result().body(), List.class);

                context.assertEquals(decodeValue.size(), 3, "Potatoes number should be");
                async.complete();
            });
        });
    }

    private static LocalDate date(String date) {
        return LocalDate.parse(date);
    }
}
