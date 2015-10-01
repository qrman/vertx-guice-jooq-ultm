package io.github.qrman.potato.db;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class JooqProvider implements Provider<DSLContext> {

    private final DataSource dataSource;

    @Inject
    public JooqProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DSLContext get() {

        Configuration configuration = new DefaultConfiguration()
          .set(dataSource)
          .set(SQLDialect.POSTGRES);
        return DSL.using(configuration);
    }
}
