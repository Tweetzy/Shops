package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 29 2021
 * Time Created: 1:01 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandSet extends AbstractCommand {

    private final String[] validSetOptions = new String[]{
            "public",
            "sellonly",
            "buyonly",
            "sellbonus",
            "buydiscount",
            "tax",
            "usesellbonus",
            "usebuydiscount",
            "usetax",
            "useseepermission",
            "usesellpermission",
            "usebuypermission",
            "seepermission",
            "sellpermission",
            "buypermission",
            "displayname",
            "description",
            "icon"
    };

    public CommandSet() {
        super(CommandType.PLAYER_ONLY, "set");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length < 2) return ReturnType.SYNTAX_ERROR;
        Player player = (Player) sender;

        Shop shop = Shops.getInstance().getShopManager().getShop(args[0]);
        if (shop == null) {
            Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", args[0]).sendPrefixedMessage(player);
            return ReturnType.FAILURE;
        }

        String setOption = args[1].toLowerCase();
        StringBuilder builder;

        if (args.length == 2 && Arrays.stream(validSetOptions).noneMatch(options -> options.equalsIgnoreCase(setOption))) {
            sendValidSetOptions(player);
            return ReturnType.FAILURE;
        }

        /*
        This is a special case, since the `set icon` option doesn't need a 3rd argument
         */
        if (args.length == 2 && args[1].equalsIgnoreCase("icon")) {
            if (ShopAPI.getInstance().getHeldItem(player) == null || ShopAPI.getInstance().getHeldItem(player).getType() == XMaterial.AIR.parseMaterial()) {
                Shops.getInstance().getLocale().getMessage("general.nothing_in_hand").sendPrefixedMessage(player);
                return ReturnType.FAILURE;
            }
            shop.setDisplayIcon(ShopAPI.getInstance().serializeItemStack(ShopAPI.getInstance().getHeldItem(player)));
            Shops.getInstance().getLocale().getMessage("set_command.changed_shop_icon").sendPrefixedMessage(player);
            return StorageManager.getInstance().updateShop(player, shop);
        }

        if (args.length >= 3) {
            switch (setOption) {
                case "public":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setPublic(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_visibility").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "sellonly":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setSellOnly(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_sell_only").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "buyonly":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setBuyOnly(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_buy_only").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "usesellbonus":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setUseSellDiscount(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_use_sell_bonus").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "usebuydiscount":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setUseBuyDiscount(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_use_buy_discount").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "usetax":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setUseTax(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_use_tax").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "useseepermission":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setRequiresPermissionToSee(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_use_see_permission").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "usesellpermission":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setRequiresPermissionToSell(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_use_sell_permission").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "usebuypermission":
                    if (isValidBooleanOption(player, args[2])) {
                        shop.setRequiresPermissionToBuy(Boolean.parseBoolean(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_use_buy_permission").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "sellbonus":
                    if (isValidPercentage(player, args[2])) {
                        shop.setSellBonus(Double.parseDouble(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_sell_bonus").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "buydiscount":
                    if (isValidPercentage(player, args[2])) {
                        shop.setBuyDiscount(Double.parseDouble(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_buy_discount").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "tax":
                    if (isValidPercentage(player, args[2])) {
                        shop.setTax(Double.parseDouble(args[2]));
                        Shops.getInstance().getLocale().getMessage("set_command.changed_shop_tax").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    }
                    break;
                case "seepermission":
                    shop.setSeePermission(args[2]);
                    Shops.getInstance().getLocale().getMessage("set_command.changed_shop_see_permission").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    break;
                case "sellpermission":
                    shop.setSellPermission(args[2]);
                    Shops.getInstance().getLocale().getMessage("set_command.changed_shop_sell_permission").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    break;
                case "buypermission":
                    shop.setBuyPermission(args[2]);
                    Shops.getInstance().getLocale().getMessage("set_command.changed_shop_buy_permission").processPlaceholder("value", args[2]).sendPrefixedMessage(player);
                    break;
                case "displayname":
                    builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    shop.setDisplayName(builder.toString().trim());
                    Shops.getInstance().getLocale().getMessage("set_command.changed_shop_display_name").processPlaceholder("value", builder.toString().trim()).sendPrefixedMessage(player);
                    break;
                case "description":
                    builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    shop.setDescription(builder.toString().trim());
                    Shops.getInstance().getLocale().getMessage("set_command.changed_shop_description").processPlaceholder("value", builder.toString().trim()).sendPrefixedMessage(player);
                    break;
                default:
                    sendValidSetOptions(player);
                    return ReturnType.FAILURE;
            }
            return StorageManager.getInstance().updateShop(player, shop);
        }
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1)
            return Shops.getInstance().getShopManager().getShops().stream().map(Shop::getId).collect(Collectors.toList());
        if (args.length == 2) return Arrays.asList(validSetOptions);
        if (args.length == 3) {
            switch (args[1].toLowerCase()) {
                case "public":
                case "usesellbonus":
                case "usebuydiscount":
                case "usetax":
                case "useseepermission":
                case "usesellpermission":
                case "usebuypermission":
                case "sellonly":
                case "buyonly":
                    return Arrays.asList("true", "false");
            }
        }
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd.set";
    }

    @Override
    public String getSyntax() {
        return "set <shop> <option> [value]";
    }

    @Override
    public String getDescription() {
        return "Used to set a specific shop setting.";
    }

    private boolean isValidBooleanOption(Player player, String arg) {
        try {
            Boolean.parseBoolean(arg);
        } catch (NumberFormatException e) {
            Shops.getInstance().getLocale().getMessage("general.not_boolean_value").sendPrefixedMessage(player);
            return false;
        }
        return true;
    }

    private boolean isValidPercentage(Player player, String arg) {
        if (!NumberUtils.isDouble(arg)) {
            Shops.getInstance().getLocale().getMessage("general.not_double_value").sendPrefixedMessage(player);
            return false;
        }
        return true;
    }

    private void sendValidSetOptions(Player player) {
        Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&eValid Set Options&F:")).sendPrefixedMessage(player);
        for (String validSetOption : validSetOptions)
            player.sendMessage(TextUtils.formatText("   &f- &e" + validSetOption));
    }
}
