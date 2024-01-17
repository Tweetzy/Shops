package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _4_ShopCMDIconMigration extends DataMigration {

	public _4_ShopCMDIconMigration() {
		super(4);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD cmd_icon TEXT NULL");
		}
	}
}
