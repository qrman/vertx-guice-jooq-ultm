package io.github.qrman.potato.db.guice;

import com.github.witoldsz.ultm.TxManager;
import com.github.witoldsz.ultm.ULTM;

import io.github.qrman.potato.db.ULTMProvider;
import io.github.qrman.potato.db.JooqProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.github.qrman.potato.db.DataSourceProvider;
import io.github.qrman.potato.db.TxJooq;
import io.github.qrman.potato.db.TxJooqProvider;
import io.github.qrman.potato.db.TxManagerProvider;
import javax.sql.DataSource;
import org.jooq.DSLContext;

public class DbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataSource.class).toProvider(DataSourceProvider.class).in(Singleton.class);
        
        bind(DSLContext.class).toProvider(JooqProvider.class).in(Singleton.class);
        
        bind(DSLContext.class).annotatedWith(TxJooq.class).toProvider(TxJooqProvider.class).in(Singleton.class);

        bind(ULTM.class).toProvider(ULTMProvider.class).in(Singleton.class);
        bind(TxManager.class).toProvider(TxManagerProvider.class).in(Singleton.class);
    }

}
