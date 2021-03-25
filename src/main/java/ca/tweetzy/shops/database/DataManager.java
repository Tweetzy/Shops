package ca.tweetzy.shops.database;

import ca.tweetzy.core.database.DataManagerAbstract;
import ca.tweetzy.core.database.DatabaseConnector;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.util.function.Consumer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:57 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class DataManager extends DataManagerAbstract {

    public DataManager(DatabaseConnector databaseConnector, Plugin plugin) {
        super(databaseConnector, plugin);
    }

    public void createShop(Shop shop, Consumer<Boolean> result) {
        this.async(() -> this.databaseConnector.connect(connection -> {
            String createShop = "INSERT INTO...";
            try (PreparedStatement statement = connection.prepareStatement(createShop)) {
                statement.setString(1, "");
                // other statements

                int status = statement.executeUpdate();
                this.sync(() -> result.accept(status == 0));
            }
        }));
    }
}
