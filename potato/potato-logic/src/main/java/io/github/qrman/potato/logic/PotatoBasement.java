package io.github.qrman.potato.logic;

import static io.github.qrman.potato.db.Tables.POTATO;
import static io.github.qrman.potato.db.Tables.POTATO_BAG;
import io.github.qrman.potato.db.tables.records.PotatoBagRecord;
import io.github.qrman.potato.db.tables.records.PotatoRecord;
import io.github.qrman.potato.model.Potato;
import io.github.qrman.potato.model.PotatoBag;
import java.util.List;
import javax.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.jooq.Record;

public class PotatoBasement {
    
    private final DSLContext jooq;
    private final PotatoQualityChecker qualityChecker;

    @Inject
    public PotatoBasement(DSLContext jooq, PotatoQualityChecker qualityChecker) {
        this.jooq = jooq;
        this.qualityChecker = qualityChecker;
    }
    
    public void store(PotatoBag potatoBag) {
        PotatoBagRecord potatoBagRecord = jooq.newRecord(POTATO_BAG);
        potatoBagRecord.setOrigin(potatoBag.getOrigin());
        potatoBagRecord.store();
        
        potatoBag.getItems().stream()
          .forEach((Potato potato) -> {
              qualityChecker.checkPotato(potato);
              storePotato(potato, potatoBagRecord);
        });
    }

    private void storePotato(Potato potato, PotatoBagRecord potatoBagRecord) {
        PotatoRecord potatoRecord = jooq.newRecord(POTATO);
        potatoRecord.setBag(potatoBagRecord.getId());
        potatoRecord.setQuality(potato.getQuality());
        potatoRecord.store();
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
