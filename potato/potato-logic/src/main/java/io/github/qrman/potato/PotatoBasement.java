package io.github.qrman.potato;

import static io.github.qrman.potato.db.Tables.POTATO;
import static io.github.qrman.potato.db.Tables.POTATO_BAG;
import io.github.qrman.potato.db.tables.records.PotatoBagRecord;
import io.github.qrman.potato.db.tables.records.PotatoRecord;
import io.github.qrman.potato.entity.Potato;
import io.github.qrman.potato.entity.PotatoBag;
import java.util.List;
import javax.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.jooq.Record;

public class PotatoBasement {
    
    private final DSLContext jooq;

    @Inject
    public PotatoBasement(DSLContext jooq) {
        this.jooq = jooq;
    }
    
    public void store(PotatoBag potatoBag) {
        PotatoBagRecord potatoBagRecord = jooq.newRecord(POTATO_BAG);
        potatoBagRecord.setDeliveryDate(potatoBag.getDeliveryDate());
        potatoBagRecord.setOrigin(potatoBag.getOrigin());
        potatoBagRecord.store();
        
        potatoBag.getItems().stream()
          .forEach((Potato potato) -> {
              storePotato(potato, potatoBagRecord);
        });
    }

    private void storePotato(Potato potato, PotatoBagRecord potatoBagRecord) {
        PotatoRecord potatoRecord = jooq.newRecord(POTATO);
        potatoRecord.setBag(potatoBagRecord.getId());
        potatoRecord.setDurability(potato.getDurability());
        potatoRecord.setQuality(potato.getQuality());
        potatoRecord.store();
    }

    List<Potato> fetchByOrigin(String potatoOriginCountry) {
        return jooq.select()
          .from(POTATO)
          .join(POTATO_BAG, JoinType.JOIN).onKey()
          .where(POTATO_BAG.ORIGIN.eq(potatoOriginCountry))
          .fetch().map((Record record) -> {
            return new Potato(record.getValue(POTATO.DURABILITY), record.getValue(POTATO.QUALITY));
        });
    }
}
