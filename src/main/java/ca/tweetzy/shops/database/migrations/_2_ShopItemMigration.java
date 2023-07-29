package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _2_ShopItemMigration extends DataMigration {

	public _2_ShopItemMigration() {
		super(2);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("CREATE TABLE " + tablePrefix + "shop_content (" +
					"id VARCHAR(48) PRIMARY KEY, " +
					"shop_id VARCHAR(48) NOT NULL, " +
					"type VARCHAR(16) NOT NULL, " +
					"buy_price DOUBLE NOT NULL, " +
					"sell_price DOUBLE NOT NULL, " +
					"purchase_qty INT NOT NULL, " +
					"item TEXT NULL, " +
					"command TEXT NULL " +
					")");
		}
	}
}
