package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _3_ShopItemBuySellToggleMigration extends DataMigration {

	public _3_ShopItemBuySellToggleMigration() {
		super(3);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD allow_buy BOOLEAN NOT NULL DEFAULT 1");
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD allow_sell BOOLEAN NOT NULL DEFAULT 1");
		}
	}
}
