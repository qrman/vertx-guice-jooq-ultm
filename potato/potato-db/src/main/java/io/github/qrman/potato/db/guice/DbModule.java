package io.github.qrman.potato.db.guice;


import javax.sql.DataSource;

import io.github.qrman.potato.db.DataSourceProvider;
import io.github.qrman.potato.db.JooqProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.jooq.DSLContext;

public class DbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataSource.class).toProvider(DataSourceProvider.class);
        bind(DSLContext.class).toProvider(JooqProvider.class).in(Singleton.class);
    }

}


