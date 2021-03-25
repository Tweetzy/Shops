package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.core.database.DataMigration;
import ca.tweetzy.core.database.MySQLConnector;
import ca.tweetzy.shops.Shops;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:57 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class _1_InitialMigration extends DataMigration {

    public _1_InitialMigration() {
        super(1);
    }

    @Override
    public void migrate(Connection connection, String tablePrefix) throws SQLException {
        String autoIncrement = Shops.getInstance().getDatabaseConnector() instanceof MySQLConnector ? " AUTO_INCREMENT" : "";

        try (Statement statement = connection.createStatement()) {
        }
    }
}
