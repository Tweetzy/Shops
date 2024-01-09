package ca.tweetzy.shops.database;

import ca.tweetzy.flight.database.Callback;
import ca.tweetzy.flight.database.DataManagerAbstract;
import ca.tweetzy.flight.database.DatabaseConnector;
import ca.tweetzy.flight.database.UpdateCallback;
import ca.tweetzy.flight.utils.SerializeUtil;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.impl.shop.*;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DataManager extends DataManagerAbstract {

	public DataManager(DatabaseConnector databaseConnector, Plugin plugin) {
		super(databaseConnector, plugin);
	}

	public void insertServerShop(@NonNull final Shop shop, final Callback<Shop> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "INSERT INTO " + this.getTablePrefix() + "shop (id, display_name, description, icon, open, requires_permission, permission, uses_command, command, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			final String fetchQuery = "SELECT * FROM " + this.getTablePrefix() + "shop WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				final PreparedStatement fetch = connection.prepareStatement(fetchQuery);

				fetch.setString(1, shop.getId().toLowerCase());

				preparedStatement.setString(1, shop.getId().toLowerCase());
				preparedStatement.setString(2, shop.getDisplayName());
				preparedStatement.setString(3, String.join(";;;", shop.getDescription()));
				preparedStatement.setString(4, SerializeUtil.encodeItem(shop.getShopOptions().getDisplayIcon()));
				preparedStatement.setBoolean(5, shop.getShopOptions().isOpen());
				preparedStatement.setBoolean(6, shop.getShopOptions().isRequiresPermission());
				preparedStatement.setString(7, shop.getShopOptions().getPermission());
				preparedStatement.setBoolean(8, shop.getShopOptions().isUsingCommand());
				preparedStatement.setString(9, shop.getShopOptions().getCommand());
				preparedStatement.setLong(10, shop.getTimeCreated());
				preparedStatement.setLong(11, shop.getLastUpdated());

				preparedStatement.executeUpdate();

				if (callback != null) {
					final ResultSet res = fetch.executeQuery();
					res.next();
					callback.accept(null, extractServerShop(res));
				}

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void updateServerShop(@NonNull final Shop shop, final Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "UPDATE " + this.getTablePrefix() + "shop SET display_name = ?, description = ?, icon = ?, open = ?, requires_permission = ?, permission = ?, uses_command = ?, command = ?, updated_at = ? WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

				preparedStatement.setString(1, shop.getDisplayName());
				preparedStatement.setString(2, String.join(";;;", shop.getDescription()));
				preparedStatement.setString(3, SerializeUtil.encodeItem(shop.getShopOptions().getDisplayIcon()));
				preparedStatement.setBoolean(4, shop.getShopOptions().isOpen());
				preparedStatement.setBoolean(5, shop.getShopOptions().isRequiresPermission());
				preparedStatement.setString(6, shop.getShopOptions().getPermission());
				preparedStatement.setBoolean(7, shop.getShopOptions().isUsingCommand());
				preparedStatement.setString(8, shop.getShopOptions().getCommand());
				preparedStatement.setLong(9, System.currentTimeMillis());
				preparedStatement.setString(10, shop.getId().toLowerCase());

				int result = preparedStatement.executeUpdate();

				if (callback != null)
					callback.accept(null, result > 0);

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void getServerShops(@NonNull final Callback<List<Shop>> callback) {
		final List<Shop> shops = new ArrayList<>();
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + this.getTablePrefix() + "shop")) {
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					final Shop shop = extractServerShop(resultSet);
					shops.add(shop);
				}

				callback.accept(null, shops);
			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	private Shop extractServerShop(final ResultSet resultSet) throws SQLException {
		return new ServerShop(
				resultSet.getString("id"),
				resultSet.getString("display_name"),
				List.of(resultSet.getString("description").split(";;;")),
				new ShopSettings(
						SerializeUtil.decodeItem(resultSet.getString("icon")),
						new ShopLayout(),
						resultSet.getBoolean("open"),
						resultSet.getBoolean("requires_permission"),
						resultSet.getBoolean("uses_command"),
						resultSet.getString("permission"),
						resultSet.getString("command")
				),
				new ArrayList<>(),
				resultSet.getLong("created_at"),
				resultSet.getLong("updated_at")
		);
	}

	public void insertServerShopContent(@NonNull final ShopContent content, final Callback<ShopContent> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "INSERT INTO " + this.getTablePrefix() + "shop_content (id, shop_id, type, buy_price, sell_price, purchase_qty, allow_buy, allow_sell, item, command) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			final String fetchQuery = "SELECT * FROM " + this.getTablePrefix() + "shop_content WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				final PreparedStatement fetch = connection.prepareStatement(fetchQuery);

				fetch.setString(1, content.getId().toString());

				preparedStatement.setString(1, content.getId().toString());
				preparedStatement.setString(2, content.getShopId().toLowerCase());
				preparedStatement.setString(3, content.getType().name());
				preparedStatement.setDouble(4, content.getBuyPrice());
				preparedStatement.setDouble(5, content.getSellPrice());
				preparedStatement.setInt(6, content.getMinimumPurchaseQty());
				preparedStatement.setBoolean(7, content.isAllowBuy());
				preparedStatement.setBoolean(8, content.isAllowSell());

				if (content instanceof final ItemShopContent itemShopContent)
					preparedStatement.setString(9, SerializeUtil.encodeItem(itemShopContent.getItem()));
				else
					preparedStatement.setString(9, null);

				if (content instanceof final CommandShopContent commandShopContent)
					preparedStatement.setString(10, commandShopContent.getCommand());
				else
					preparedStatement.setString(10, null);

				preparedStatement.executeUpdate();

				if (callback != null) {
					final ResultSet res = fetch.executeQuery();
					res.next();
					callback.accept(null, extractServerShopContent(res));
				}

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void updateServerShopContent(@NonNull final ShopContent content, final Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "UPDATE " + this.getTablePrefix() + "shop_content SET buy_price = ?, sell_price = ?, purchase_qty = ?, item = ?, command = ?, allow_buy = ?, allow_sell = ? WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

				preparedStatement.setDouble(1, content.getBuyPrice());
				preparedStatement.setDouble(2, content.getSellPrice());
				preparedStatement.setInt(3, content.getMinimumPurchaseQty());

				if (content instanceof final ItemShopContent itemShopContent)
					preparedStatement.setString(4, SerializeUtil.encodeItem(itemShopContent.getItem()));
				else
					preparedStatement.setString(4, null);

				if (content instanceof final CommandShopContent commandShopContent)
					preparedStatement.setString(5, commandShopContent.getCommand());
				else
					preparedStatement.setString(5, null);

				preparedStatement.setBoolean(6, content.isAllowBuy());
				preparedStatement.setBoolean(7, content.isAllowSell());
				preparedStatement.setString(8, content.getId().toString());

				int result = preparedStatement.executeUpdate();

				if (callback != null)
					callback.accept(null, result > 0);

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void getServerShopContents(@NonNull final Callback<List<ShopContent>> callback) {
		final List<ShopContent> contents = new ArrayList<>();
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + this.getTablePrefix() + "shop_content")) {
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					final ShopContent shopContent = extractServerShopContent(resultSet);
					contents.add(shopContent);
				}

				callback.accept(null, contents);
			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	public void getServerShopContentsById(@NonNull final String shopId, @NonNull final Callback<List<ShopContent>> callback) {
		final List<ShopContent> contents = new ArrayList<>();
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + this.getTablePrefix() + "shop_content WHERE shop_id = ?")) {

				statement.setString(1, shopId.toLowerCase());

				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					final ShopContent shopContent = extractServerShopContent(resultSet);
					contents.add(shopContent);
				}

				callback.accept(null, contents);
			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	private ShopContent extractServerShopContent(final ResultSet resultSet) throws SQLException {
		ShopContent shopContent  =  Enum.valueOf(ShopContentType.class, resultSet.getString("type").toUpperCase()) == ShopContentType.ITEM ? new ItemShopContent(
				UUID.fromString(resultSet.getString("id")),
				resultSet.getString("shop_id"),
				SerializeUtil.decodeItem(resultSet.getString("item")),
				resultSet.getInt("purchase_qty"),
				resultSet.getDouble("buy_price"),
				resultSet.getDouble("sell_price")
		) : new CommandShopContent(
				UUID.fromString(resultSet.getString("id")),
				resultSet.getString("shop_id"),
				resultSet.getString("command"),
				resultSet.getInt("purchase_qty"),
				resultSet.getDouble("buy_price")
		);

		shopContent.setAllowBuy(resultSet.getBoolean("allow_buy"));
		shopContent.setAllowSell(resultSet.getBoolean("allow_sell"));

		return shopContent;
	}


	private void resolveUpdateCallback(@Nullable UpdateCallback callback, @Nullable Exception ex) {
		if (callback != null) {
			callback.accept(ex);
		} else if (ex != null) {
			ex.printStackTrace();
		}
	}

	private void resolveCallback(@Nullable Callback<?> callback, @NotNull Exception ex) {
		if (callback != null) {
			callback.accept(ex, null);
		} else {
			ex.printStackTrace();
		}
	}
}
