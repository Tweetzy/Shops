package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _8_ShopTransactionMigration extends DataMigration {

	public _8_ShopTransactionMigration() {
		super(8);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("CREATE TABLE " + tablePrefix + "transaction (" +
					"id VARCHAR(48) PRIMARY KEY, " +
					"content_id VARCHAR(48) NOT NULL, " +
					"shop_id TEXT NOT NULL, " +
					"shop_name TEXT NOT NULL, " +
					"type VARCHAR(16) NOT NULL, " +
					"content_type VARCHAR(16) NOT NULL, " +
					"player_uuid VARCHAR(48) NOT NULL, " +
					"player_name VARCHAR(16) NOT NULL, " +
					"item TEXT NOT NULL, " +
					"price DOUBLE NOT NULL, " +
					"quantity INT NOT NULL, " +
					"created_at BigInt NOT NULL, " +
					"updated_at BigInt NOT NULL " +
					")");
		}
	}
}
