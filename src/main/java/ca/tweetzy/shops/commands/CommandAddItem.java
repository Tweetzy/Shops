package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.shop.Shop;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 29 2021
 * Time Created: 2:58 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandAddItem extends AbstractCommand {

	public CommandAddItem() {
		super(CommandType.PLAYER_ONLY, "additem");
	}

	@Override
	protected ReturnType runCommand(CommandSender sender, String... args) {
		if (args.length < 3) return ReturnType.SYNTAX_ERROR;
		Player player = (Player) sender;

		ItemStack toAdd = ShopAPI.getInstance().getHeldItem(player);
		if (toAdd == null || toAdd.getType() == XMaterial.AIR.parseMaterial()) {
			Shops.getInstance().getLocale().getMessage("general.nothing_in_hand").sendPrefixedMessage(player);
			return ReturnType.FAILURE;
		}

		Shop shop = Shops.getInstance().getShopManager().getShop(args[0]);

		if (shop == null) {
			Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", args[0]).sendPrefixedMessage(player);
			return ReturnType.FAILURE;
		}

		if (!NumberUtils.isDouble(args[1]) || !NumberUtils.isDouble(args[2])) {
			Shops.getInstance().getLocale().getMessage("general.not_double_value").sendPrefixedMessage(player);
			return ReturnType.FAILURE;
		}

		if (args.length == 3) {
			shop.getShopItems().add(new ShopItem(shop.getId(), toAdd, Double.parseDouble(args[1]), Double.parseDouble(args[2])));
		} else {
			// they're passing in the additional arguments to state whether the item is buy/sell only
			shop.getShopItems().add(new ShopItem(shop.getId(), toAdd, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Boolean.parseBoolean(args[3]), args.length == 5 && Boolean.parseBoolean(args[4])));
		}

		Shops.getInstance().getLocale().getMessage("shop.add_new_item").processPlaceholder("shop_id", args[0]).processPlaceholder("sell_price", args[1]).processPlaceholder("buy_price", args[2]).sendPrefixedMessage(player);
		StorageManager.getInstance().updateShop(player, shop);
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> onTab(CommandSender sender, String... args) {
		if (args.length == 1)
			return Shops.getInstance().getShopManager().getShops().stream().map(Shop::getId).collect(Collectors.toList());
		if (args.length == 2 || args.length == 3) return Arrays.asList("1", "2", "3", "4", "5");
		if (args.length == 4 || args.length == 5) return Arrays.asList("true", "false");
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.cmd.additem";
	}

	@Override
	public String getSyntax() {
		return Shops.getInstance().getLocale().getMessage("commands.syntax.additem").getMessage();
	}

	@Override
	public String getDescription() {
		return Shops.getInstance().getLocale().getMessage("commands.description.additem").getMessage();
	}
}
