package io.github.qrman.potato.db;

import javax.inject.Inject;
import org.jooq.DSLContext;
import static io.github.qrman.potato.db.Tables.POTATO;
import static io.github.qrman.potato.db.Tables.POTATO_BAG;

public class DBCleaner {

    @Inject
    private DSLContext jooq;

    public void apply() {
        jooq.delete(POTATO).execute();
        jooq.delete(POTATO_BAG).execute();
    }
}
