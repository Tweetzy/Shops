package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ItemUtil;
import ca.tweetzy.flight.utils.MathUtil;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public final class AddCommand extends Command {

	public AddCommand() {
		super(AllowedExecutor.PLAYER, "add");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (!(sender instanceof final Player player)) return ReturnType.FAIL;
		if (args.length < 3) return ReturnType.INVALID_SYNTAX;

		// check for valid add options
		if (!List.of("cmd", "item").contains(args[0].toLowerCase()))
			return ReturnType.INVALID_SYNTAX;

		// lookup the shop id
		final Shop shop = Shops.getShopManager().getById(args[1]);
		// shop not found
		if (shop == null) {
			tell(player, TranslationManager.string(player, Translations.SHOP_NOT_FOUND));
			return ReturnType.FAIL;
		}

		if (args[0].equalsIgnoreCase("item"))
			return handleItemAdd(player, shop, getArrayExceptFromBeginning(args, 2));

		return ReturnType.SUCCESS;
	}

	private ReturnType handleItemAdd(@NonNull final Player player, @NonNull final Shop shop, @NonNull final String... args) {
		// if the args is not 2 then can't create
		 if (args.length < 2) return ReturnType.INVALID_SYNTAX;

		// check if item in hand is air
		if (PlayerUtil.isHandEmpty(player)) {
			tell(player, TranslationManager.string(player, Translations.ITEM_CANNOT_BE_AIR));
			return ReturnType.FAIL;
		}

		// grab the item to add
		final ItemStack itemToAdd = player.getInventory().getItemInMainHand().clone();

		if (!MathUtil.isDouble(args[0])) {
			tell(player, TranslationManager.string(player, Translations.NOT_A_NUMBER, "value", args[0]));
			return ReturnType.FAIL;
		}

		if (!MathUtil.isDouble(args[1])) {
			tell(player, TranslationManager.string(player, Translations.NOT_A_NUMBER, "value", args[1]));
			return ReturnType.FAIL;
		}

		// get the buy/sell values
		final double buyPrice = Double.parseDouble(args[0]);
		final double sellPrice = Double.parseDouble(args[1]);

		// since min buy is optional, check for it but don't error, just provide a default of 1
		final int minBuy = args.length == 3 && MathUtil.isInt(args[2]) ? Integer.parseInt(args[2]) : 1;

		// add the item to the shop, but first update the item to add qty
		itemToAdd.setAmount(1);
		// add item
		shop.addContent(new ItemShopContent(UUID.randomUUID(), shop.getId(), itemToAdd, minBuy, buyPrice, sellPrice));
		// tell them it was added
		tell(player, TranslationManager.string(player, Translations.ADDED_ITEM_TO_SHOP, "item_name", ItemUtil.getItemName(itemToAdd), "shop_id", shop.getId()));

		return ReturnType.SUCCESS;
	}

	private String[] getArrayExceptFromBeginning(String[] originalArray, int numToExclude) {
		// Calculate the length of the new array
		int newArrayLength = Math.max(originalArray.length - numToExclude, 0);

		// Create a new array
		String[] newArray = new String[newArrayLength];

		// Copy elements starting from the specified index
		for (int i = numToExclude; i < originalArray.length; i++) {
			newArray[i - numToExclude] = originalArray[i];
		}

		return newArray;
	}


	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.command.add";
	}

	@Override
	public String getSyntax() {
		return "add <item/cmd> <shop> <buy> <sell> [minBuy]";
	}

	@Override
	public String getDescription() {
		return "Used to add content to a shop";
	}
}
