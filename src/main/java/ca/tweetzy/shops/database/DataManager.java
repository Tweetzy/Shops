package ca.tweetzy.shops.database;

import ca.tweetzy.core.database.DataManagerAbstract;
import ca.tweetzy.core.database.DatabaseConnector;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void removeShop(String shopId, Consumer<Boolean> result) {
        this.async(() -> this.databaseConnector.connect(connection -> {
            String removeShop = "DELETE FROM " + this.getTablePrefix() + "shops WHERE shop_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(removeShop)) {
                statement.setString(1, shopId);
                int status = statement.executeUpdate();
                this.sync(() -> result.accept(status == 0));
            }
        }));
    }

    public void updateShop(Shop shop, Consumer<Boolean> result) {
        this.async(() -> this.databaseConnector.connect(connection -> {
            String updateShop = "UPDATE " + this.getTablePrefix() + "shops SET shop_data = ? WHERE shop_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateShop)) {
                statement.setString(1, ShopAPI.getInstance().convertToBase64(shop));
                statement.setString(2, shop.getId());
                int status = statement.executeUpdate();
                this.sync(() -> result.accept(status == 0));
            }
        }));
    }

    public void updateShops(List<Shop> shops) {
        this.async(() -> this.databaseConnector.connect(connection -> {
            String updateShop = "UPDATE " + this.getTablePrefix() + "shops SET shop_data = ? WHERE shop_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateShop);
            shops.forEach(shop -> {
                try {
                    statement.setString(1, ShopAPI.getInstance().convertToBase64(shop));
                    statement.setString(2, shop.getId());
                    statement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            statement.executeBatch();
            this.async(() -> Shops.getInstance().getShopManager().loadShops(true, true));
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

    public void createCustomGuiItem(CustomGUIItemHolder holder) {
        this.async(() -> this.databaseConnector.connect(connection -> {
            String createCustomItem = "INSERT IGNORE INTO " + this.getTablePrefix() + "custom_gui_items SET gui = ?, data = ?";
            try (PreparedStatement statement = connection.prepareStatement(createCustomItem)) {
                statement.setString(1, holder.getGuiName());
                statement.setString(2, ShopAPI.getInstance().convertToBase64(holder));
                statement.executeUpdate();
                this.async(() -> Shops.getInstance().getShopManager().loadCustomGuiItems(true, true));
            }
        }));
    }

    public void updateCustomGuiItems(CustomGUIItemHolder holder) {
        getCustomGuiItems(results -> {
            if (results.stream().noneMatch(holders -> holders.getGuiName().equalsIgnoreCase(holder.getGuiName()))) {
                this.async(() -> createCustomGuiItem(holder));
            } else {
                this.async(() -> this.databaseConnector.connect(connection -> {
                    String updateCustomItem = "UPDATE " + this.getTablePrefix() + "custom_gui_items SET data = ? WHERE gui = ?";
                    try (PreparedStatement statement = connection.prepareStatement(updateCustomItem)) {
                        statement.setString(1, ShopAPI.getInstance().convertToBase64(holder));
                        statement.setString(2, holder.getGuiName());
                        statement.executeUpdate();
                        this.async(() -> Shops.getInstance().getShopManager().loadCustomGuiItems(true, true));
                    }
                }));
            }
        });
    }

    public void getCustomGuiItems(Consumer<List<CustomGUIItemHolder>> callback) {
        List<CustomGUIItemHolder> customGuiItems = new ArrayList<>();
        this.async(() -> this.databaseConnector.connect(connection -> {
            try (Statement statement = connection.createStatement()) {
                String getCustomItems = "SELECT * FROM " + this.getTablePrefix() + "custom_gui_items";
                ResultSet result = statement.executeQuery(getCustomItems);
                while (result.next()) {
                    customGuiItems.add((CustomGUIItemHolder) ShopAPI.getInstance().convertBase64ToObject(result.getString("data")));
                }
            }
            this.sync(() -> callback.accept(customGuiItems));
        }));
    }

}
