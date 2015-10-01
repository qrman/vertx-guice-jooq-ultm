package io.github.qrman.potato.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.sql.DataSource;

public class DataSourceProvider implements Provider<DataSource> {

    @Inject
    @Named("db.class.name")
    private String dataSourceClassName;
    @Inject
    @Named("db.username")
    private String dbUser;
    @Inject
    @Named("db.password")
    private String dbPassword;
    @Inject
    @Named("db.name")
    private String dbName;

    @Override
    public DataSource get() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceClassName);
        config.addDataSourceProperty("databaseName", dbName);
        config.addDataSourceProperty("user", dbUser);
        config.addDataSourceProperty("password", dbPassword);
        return new HikariDataSource(config);
    }
}
