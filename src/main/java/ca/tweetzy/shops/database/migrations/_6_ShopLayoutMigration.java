package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _6_ShopLayoutMigration extends DataMigration {

	public _6_ShopLayoutMigration() {
		super(6);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("ALTER TABLE " + tablePrefix + "shop ADD layout TEXT NOT NULL DEFAULT '{}'");
		}
	}
}
