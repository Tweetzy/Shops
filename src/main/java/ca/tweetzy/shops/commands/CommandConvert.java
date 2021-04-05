package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.shop.Shop;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 11:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandConvert extends AbstractCommand {

    public CommandConvert() {
        super(CommandType.CONSOLE_OK, "convert");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length == 0) {
            Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&cPlease &econfirm &cthe convert command. By doing so you acknowledge that data may be lost/the conversion might not even work. IT IS HIGHLY SUGGESTED YOU BACK UP YOUR ORIGINAL &6Shops.yml &cFile.")).sendPrefixedMessage(sender);
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("confirm")) {
            Config oldShopFile = new Config(Shops.getInstance(), "Shops.yml");
            oldShopFile.load();

            if (!oldShopFile.getFile().exists()) {
                Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&cThe &6Shops.yml &cfile could not be found, is this a fresh install / have you converted already?")).sendPrefixedMessage(sender);
                return ReturnType.FAILURE;
            }

            ConfigurationSection section = oldShopFile.getConfigurationSection("shops");
            if (section == null || section.getKeys(false).size() == 0) {
                Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&cCould not find any shops in the &6Shops.yml &cfile.")).sendPrefixedMessage(sender);
                return ReturnType.FAILURE;
            }

            List<Shop> shops = new ArrayList<>();

            Bukkit.getServer().getScheduler().runTaskLater(Shops.getInstance(), () -> {
                long start = System.currentTimeMillis();

                oldShopFile.getConfigurationSection("shops").getKeys(false).forEach(shopNode -> {
                    Shop shop = new Shop(
                            shopNode,
                            oldShopFile.getString("shops." + shopNode + ".title"),
                            oldShopFile.getItemStack("shops." + shopNode + ".icon"),
                            oldShopFile.getBoolean("shops." + shopNode + ".public"),
                            oldShopFile.getBoolean("shops." + shopNode + ".sellonly"),
                            oldShopFile.getBoolean("shops." + shopNode + ".buyonly"),
                            oldShopFile.getBoolean("shops." + shopNode + ".discount.enabled"),
                            oldShopFile.getDouble("shops." + shopNode + ".discount.amount")
                    );

                    if (oldShopFile.getConfigurationSection("shops." + shopNode + ".items") != null && oldShopFile.getConfigurationSection("shops." + shopNode + ".items").getKeys(false).size() != 0) {
                        for (String shopItem : oldShopFile.getConfigurationSection("shops." + shopNode + ".items").getKeys(false)) {
                            ItemStack itemstack = oldShopFile.getItemStack("shops." + shopNode + ".items." + shopItem + ".material");
                            if (itemstack == null || itemstack.getType() == XMaterial.AIR.parseMaterial()) continue;
                            shop.getShopItems().add(new ShopItem(
                                    shopNode,
                                    itemstack,
                                    oldShopFile.getDouble("shops." + shopNode + ".items." + shopItem + ".sell-price"),
                                    oldShopFile.getDouble("shops." + shopNode + ".items." + shopItem + ".buy-price")
                            ));
                        }
                    }
                    shops.add(shop);
                });

                StorageManager.getInstance().convertShops(sender, shops);
                long end = System.currentTimeMillis() - start;
                Shops.getInstance().getLocale().newMessage(TextUtils.formatText(String.format("&aConversion process has been completed (%d)", end))).sendPrefixedMessage(sender);
            }, 1L);
        }

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
