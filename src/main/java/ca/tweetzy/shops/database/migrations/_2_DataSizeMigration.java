package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.core.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 03 2021
 * Time Created: 2:47 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class _2_DataSizeMigration extends DataMigration {

	public _2_DataSizeMigration() {
		super(2);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.execute("ALTER TABLE " + tablePrefix + "shops MODIFY COLUMN shop_data LONGTEXT");
		}
	}
}
