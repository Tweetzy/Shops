package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _1_InitialMigration extends DataMigration {

	public _1_InitialMigration() {
		super(1);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop
			statement.execute("CREATE TABLE " + tablePrefix + "shop (" +
					"id VARCHAR(48) PRIMARY KEY, " +
					"display_name VARCHAR(64) NOT NULL, " +
					"description TEXT NOT NULL, " +
					"icon VARCHAR(40) NOT NULL, " +
					"created_at BigInt NOT NULL, " +
					"updated_at BigInt NOT NULL " +
					")");
		}
	}
}
