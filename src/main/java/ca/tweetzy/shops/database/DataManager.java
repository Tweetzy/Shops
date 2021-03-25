package ca.tweetzy.shops.database;

import ca.tweetzy.core.database.DataManagerAbstract;
import ca.tweetzy.core.database.DatabaseConnector;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
            String createShop = "INSERT IGNORE INTO " + this.getTablePrefix() + "shops SET shop_id = ?, shop_data = ?";
            try (PreparedStatement statement = connection.prepareStatement(createShop)) {
                statement.setString(1, shop.getId());
                statement.setString(2, ShopAPI.getInstance().convertToBase64(shop));

                int status = statement.executeUpdate();
                this.sync(() -> result.accept(status == 0));
            }
        }));
    }

    public void getShops(Consumer<List<Shop>> callback) {
        List<Shop> shops = new ArrayList<>();
        this.async(() -> this.databaseConnector.connect(connection -> {
            try (Statement statement = connection.createStatement()) {
                String getShops = "SELECT * FROM " + this.getTablePrefix() + "shops";
                ResultSet result = statement.executeQuery(getShops);
                while (result.next()) {
                    shops.add((Shop) ShopAPI.getInstance().convertBase64ToObject(result.getString("shop_data")));
                }
            }
            this.sync(() -> callback.accept(shops));
        }));
    }
}
