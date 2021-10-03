package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: August 05 2021
 * Time Created: 4:13 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandChangeId extends AbstractCommand {

	public CommandChangeId() {
		super(CommandType.PLAYER_ONLY, "changeid");
	}

	@Override
	protected ReturnType runCommand(CommandSender sender, String... args) {
		if (args.length < 2) return ReturnType.SYNTAX_ERROR;
		Player player = (Player) sender;

		String originalShopId = args[0].toLowerCase();
		String newShopId = args[1].toLowerCase();

		Shop originalShop = Shops.getInstance().getShopManager().getShop(originalShopId);
		if (originalShop == null) {
			Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", originalShopId).sendPrefixedMessage(player);
			return ReturnType.FAILURE;
		}

		if (Shops.getInstance().getShopManager().getShops().stream().anyMatch(shops -> shops.getId().equalsIgnoreCase(newShopId))) {
			Shops.getInstance().getLocale().getMessage("shop.already_exists").processPlaceholder("shop_id", newShopId).sendPrefixedMessage(player);
			return ReturnType.FAILURE;
		}

		Shop newShop = new Shop(newShopId);
		newShop.setDisplayName(originalShop.getDisplayName());
		newShop.setDescription(originalShop.getDescription());
		newShop.setDisplayIcon(originalShop.getDisplayIcon());
		newShop.setPage(originalShop.getPage());
		newShop.setSlot(originalShop.getSlot());

		newShop.setUseSellBonus(originalShop.isUseSellBonus());
		newShop.setUseBuyDiscount(originalShop.isUseBuyDiscount());
		newShop.setUseTax(originalShop.isUseTax());
		newShop.setRequiresPermissionToSee(originalShop.isRequiresPermissionToSee());
		newShop.setRequiresPermissionToBuy(originalShop.isRequiresPermissionToBuy());
		newShop.setRequiresPermissionToSell(originalShop.isRequiresPermissionToSell());
		newShop.setSellBonus(originalShop.getSellBonus());
		newShop.setBuyDiscount(originalShop.getBuyDiscount());
		newShop.setTax(originalShop.getTax());
		newShop.setSeePermission(originalShop.getSeePermission());
		newShop.setSellPermission(originalShop.getSellPermission());
		newShop.setBuyPermission(originalShop.getBuyPermission());
		newShop.setShopItems(originalShop.getShopItems());

		Shops.getInstance().getGuiManager().closeAll();
		Shops.newChain().asyncFirst(() -> {
			StorageManager.getInstance().removeShop(player, originalShopId);
			return null;
		}).asyncLast((x) -> StorageManager.getInstance().createShop(player, newShop)).execute();

		return ReturnType.SUCCESS;
	}

	@Override
	public String getPermissionNode() {
		return "shops.cmd.changeid";
	}

	@Override
	public String getSyntax() {
		return Shops.getInstance().getLocale().getMessage("commands.syntax.changeid").getMessage();
	}

	@Override
	public String getDescription() {
		return Shops.getInstance().getLocale().getMessage("commands.description.changeid").getMessage();
	}

	@Override
	protected List<String> onTab(CommandSender sender, String... args) {
		if (args.length == 1 && Shops.getInstance().getShopManager().getShops().size() > 0)
			return Shops.getInstance().getShopManager().getShops().stream().map(Shop::getId).collect(Collectors.toList());
		return null;
	}
}
