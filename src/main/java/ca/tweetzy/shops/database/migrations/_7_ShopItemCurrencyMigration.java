package ca.tweetzy.shops.database.migrations;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.database.DataMigration;
import ca.tweetzy.flight.utils.SerializeUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class _7_ShopItemCurrencyMigration extends DataMigration {

	public _7_ShopItemCurrencyMigration() {
		super(7);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			// shop item
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD currency TEXT NOT NULL DEFAULT 'Vault/Vault'");
			statement.execute("ALTER TABLE " + tablePrefix + "shop_content ADD currency_item TEXT NOT NULL DEFAULT '" + SerializeUtil.encodeItem(CompMaterial.AIR.parseItem()) + "'");
		}
	}
}
