package io.github.qrman.potato.logic;

import com.github.witoldsz.ultm.TxManager;
import com.github.witoldsz.ultm.UnitOfWork;
import static io.github.qrman.potato.db.Tables.POTATO;
import static io.github.qrman.potato.db.Tables.POTATO_BAG;
import io.github.qrman.potato.db.TxJooq;
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

    private final TxManager txManager;
    private final DSLContext jooq;

    private final DSLContext txJooq;
    private final PotatoQualityChecker qualityChecker;

    @Inject
    public PotatoBasement(DSLContext jooq, @TxJooq DSLContext txJooq, TxManager txManager, PotatoQualityChecker qualityChecker) {
        this.jooq = jooq;
        this.txJooq = txJooq;
        this.txManager = txManager;
        this.qualityChecker = qualityChecker;
    }

    public void store(PotatoBag potatoBag) {

        txManager.begin();
        try {

            PotatoBagRecord potatoBagRecord = txJooq.newRecord(POTATO_BAG);
            potatoBagRecord.setOrigin(potatoBag.getOrigin());
            potatoBagRecord.store();

            potatoBag.getItems().stream()
              .forEach((Potato potato) -> {
                  qualityChecker.checkPotato(potato);
                  storePotato(potato, potatoBagRecord);
              });

            txManager.commit();
        } catch (Exception ex) {
            txManager.rollback();
            throw ex; // or whatever you find appropriate
        }

    }

    private void storePotato(Potato potato, PotatoBagRecord potatoBagRecord) {
        PotatoRecord potatoRecord = txJooq.newRecord(POTATO);
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
