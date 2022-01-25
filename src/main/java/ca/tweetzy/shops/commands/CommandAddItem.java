package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.model.PlayerHand;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 12:55 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandAddItem extends AbstractSubCommand {

	public CommandAddItem() {
		super("additem");
		setMinArguments(3);
		setDescription("Used to add a new item to a shop");
		setUsage("<shop> <buyPrice> <sellPrice> [--nosell] [--nobuy]");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();

		if (args.length >= 3) {
			final Shop shop = Shops.getShopManager().getShop(args[0]);

			if (shop == null) {
				returnTell(Localization.Error.INVALID_SHOP_ID.replace("{shop_id}", args[0]));
			}

			final double buyPrice = findNumber(Double.class, 1, Localization.Error.NOT_A_NUMBER.replace("{value}", args[1]));
			final double sellPrice = findNumber(Double.class, 2, Localization.Error.NOT_A_NUMBER.replace("{value}", args[2]));

			final boolean allowSell = Arrays.stream(args).anyMatch(arg -> arg.equalsIgnoreCase("--nosell"));
			final boolean allowBuy = Arrays.stream(args).anyMatch(arg -> arg.equalsIgnoreCase("--nobuy"));

			final ItemStack hand = PlayerHand.get(player);
			if (hand == null || hand.getType() == CompMaterial.AIR.toMaterial()) {
				returnTell(Localization.Error.AIR);
			}

			final ShopItem shopItem = new ShopItem(
					hand,
					ShopItemType.ITEM,
					ShopItemQuantityType.UNLIMITED,
					new ArrayList<>(),
					Shops.getCurrencyManager().getCurrency("Vault"),
					buyPrice,
					sellPrice,
					hand.getAmount(),
					1,
					1,
					!allowBuy,
					!allowSell,
					new ArrayList<>(),
					new ArrayList<>()
			);

			shop.getShopItems().add(shopItem);
			Shops.getPriceMapManager().addPriceMap(new PriceMap(hand.clone(), shopItem.getBuyPrice(), shopItem.getSellPrice(), shopItem.getCurrency()));
			ShopsData.getInstance().saveAll();
		}
	}
}
