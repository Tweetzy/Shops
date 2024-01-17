package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _5_ShopCMDNameDescMigration extends DataMigration {

	public _5_ShopCMDNameDescMigration() {
		super(5);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD cmd_name TEXT NULL");
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD cmd_desc TEXT NULL");
		}
	}
}
