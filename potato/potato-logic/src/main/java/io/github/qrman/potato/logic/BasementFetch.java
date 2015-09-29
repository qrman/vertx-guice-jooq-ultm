package io.github.qrman.potato.logic;

import static io.github.qrman.potato.db.Tables.POTATO;
import static io.github.qrman.potato.db.Tables.POTATO_BAG;
import io.github.qrman.potato.model.Potato;
import java.util.List;
import javax.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.jooq.Record;

public class BasementFetch {

    private final DSLContext jooq;

    @Inject
    public BasementFetch(DSLContext jooq) {
        this.jooq = jooq;
    }

    public List<Potato> fetchByOrigin(String potatoOriginCountry) {
        return jooq.select()
          .from(POTATO)
          .join(POTATO_BAG, JoinType.JOIN).onKey()
          .where(POTATO_BAG.ORIGIN.eq(potatoOriginCountry))
          .fetch().map((Record record) -> {
              return new Potato(record.getValue(POTATO.QUALITY));
          });
    }
}
