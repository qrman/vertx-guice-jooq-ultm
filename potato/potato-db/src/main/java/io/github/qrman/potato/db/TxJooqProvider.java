package io.github.qrman.potato.db;

import com.github.witoldsz.ultm.ULTM;
import javax.inject.Inject;
import javax.inject.Provider;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class TxJooqProvider implements Provider<DSLContext> {

    private final ULTM ultm;

    @Inject
    public TxJooqProvider(ULTM ultm) {
        this.ultm = ultm;
    }

    @Override
    public DSLContext get() {
        Configuration configuration = new DefaultConfiguration()
          .set(ultm.getManagedDataSource())
          .set(SQLDialect.POSTGRES);
        return DSL.using(configuration);
    }
}
