package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.api.enums.ShopState;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.impl.*;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.FileUtil;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.remain.Remain;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 16 2022
 * Time Created: 12:16 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandImport extends AbstractSubCommand {

	public CommandImport() {
		super("import");
	}

	@Override
	protected void onCommand() {
		if (args.length == 0) {
			returnTell("&aPlease use &c/shops import &4confirm &aas this action will override shops");
		}

		Common.runAsync(() -> {
			final List<Shop> shops = new ArrayList<>();
			final File v2DataFile = FileUtil.getFile("shops-export.yml");

			if (!v2DataFile.exists())
				returnTell("&cCould not find &4shops-export.yml &cfile used to import v2 data.");

			final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(v2DataFile);
			final ConfigurationSection configurationSection = configuration.getConfigurationSection("Shops");

			if (configurationSection == null || configurationSection.getKeys(false).size() == 0)
				returnTell("&4shops-export.yml &cdoes not contain any shops");

			configurationSection.getKeys(false).forEach(shopId -> {
				final ConfigurationSection shopSection = configurationSection.getConfigurationSection(shopId);
				if (shopSection == null) return;
				final ConfigurationSection shopItemsSection = shopSection.getConfigurationSection("items");
				final List<IShopItem> shopItems = new ArrayList<>();

				if (shopItemsSection != null) {
					shopItemsSection.getKeys(false).forEach(node -> {
						final ConfigurationSection shopItemSection = shopItemsSection.getConfigurationSection(node);
						final ItemStack item = shopItemSection.getItemStack("item");

						final ShopItem shopItem = new ShopItem(
								item,
								ShopItemType.ITEM,
								ShopItemQuantityType.UNLIMITED,
								new ArrayList<>(),
								Shops.getCurrencyManager().getCurrency("Vault"),
								shopItemSection.getDouble("buy price"),
								shopItemSection.getDouble("sell price"),
								item.getAmount(),
								1,
								true,
								true,
								new ArrayList<>(),
								new ArrayList<>()
						);

						Shops.getPriceMapManager().addPriceMap(new PriceMap(item, shopItem.getBuyPrice(), shopItem.getSellPrice(), shopItem.getCurrency()));
						shopItems.add(shopItem);
					});
				}

				final Shop shop = new Shop(
						shopId,
						new SmartItem(shopSection.getItemStack("icon"), shopSection.getItemStack("icon").getType().name()),
						shopSection.getString("display name"),
						shopSection.getString("desc"),
						Shops.getCurrencyManager().getCurrency("Vault"),
						new ShopDisplay(ShopLayout.AUTOMATIC, CompMaterial.BLACK_STAINED_GLASS_PANE, new StrictList<>(IntStream.rangeClosed(0, 44).boxed().collect(Collectors.toList())), new StrictMap<>(), -1, -1),
						new ShopSettings(false, ShopState.BUY_AND_SELL, true, shopId, shopSection.getBoolean("needs see perm"), shopSection.getBoolean("needs sell perm"), shopSection.getBoolean("needs buy perm"),
								shopSection.getString("see perm"), shopSection.getString("buy perm"), shopSection.getString("sell perm")),
						shopItems
				);

				shops.add(shop);
				tell("&aImported shop&f: &2" + shopId);
			});

			ShopsData.getInstance().getShops().addAll(shops);
			ShopsData.getInstance().saveAll();

			Shops.getShopManager().load(loaded -> loaded.forEach(shop -> {
				if (shop.getSettings().isUseOpenCommand() && shop.getSettings().getOpenCommand().length() >= 1)
					Remain.registerCommand(new DynamicShopCommand(shop.getSettings().getOpenCommand()));
			}));

			tell("&aImported a total of &2" + shops.size() + "&a shops");
		});
	}
}
