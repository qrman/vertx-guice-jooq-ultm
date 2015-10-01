package io.github.qrman.potato.db;

import com.github.witoldsz.ultm.ULTM;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;

public class ULTMProvider implements Provider<ULTM> {

    private final DataSource dataSource;

    @Inject
    public ULTMProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ULTM get() {
        return new ULTM(dataSource);
    }
}
