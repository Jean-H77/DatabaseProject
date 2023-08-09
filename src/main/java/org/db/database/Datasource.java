package org.db.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Datasource {

    private static Datasource INSTANCE;

    private final HikariDataSource hikariDataSource;

    private Datasource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/comp440");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(255);
        hikariDataSource = new HikariDataSource(config);

        INSTANCE = this;
    }

    public static void init() {
        new Datasource();
    }

    public static Datasource getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
