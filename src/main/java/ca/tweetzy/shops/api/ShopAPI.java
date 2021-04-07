package ca.tweetzy.shops.api;

import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.events.ShopBuyEvent;
import ca.tweetzy.shops.api.events.ShopSellEvent;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 10:14 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopAPI {

    private static ShopAPI instance;

    private ShopAPI() {
    }

    public static ShopAPI getInstance() {
        if (instance == null) {
            instance = new ShopAPI();
        }
        return instance;
    }

    /**
     * Check if a shop exists on file
     *
     * @param shopId is the shop name used when creating the shop
     * @return true if the shop can be found on file
     */
    public boolean exists(String shopId) {
        return Shops.getInstance().getData().contains("shops." + shopId.toLowerCase());
    }

    /**
     * Used to create a shop on the flat file
     *
     * @param shop is the shop object
     */
    public void createShop(Shop shop) {
        Shops.getInstance().getData().set("shops." + shop.getId().toLowerCase() + ".data", convertToBase64(shop));
        Shops.getInstance().getData().save();
    }

    /**
     * Used to remove an existing shop
     *
     * @param shopId is the id of the shop being removed
     */
    public void removeShop(String shopId) {
        Shops.getInstance().getData().set("shops." + shopId.toLowerCase(), null);
        Shops.getInstance().getData().save();
    }

    /**
     * Used to save custom gui items
     *
     * @param holder is the holder object
     */
    public void saveCustomGuiItems(CustomGUIItemHolder holder) {
        Shops.getInstance().getData().set("custom gui items." + holder.getGuiName().toLowerCase() + ".data", convertToBase64(holder));
        Shops.getInstance().getData().save();
    }

    /**
     * Used to get the item the player is currently holding in their main hand
     *
     * @param player is the player you're trying to get the held item
     * @return the item stack the player is currently holding
     */
    public ItemStack getHeldItem(Player player) {
        return ServerVersion.isServerVersionAbove(ServerVersion.V1_8) ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInHand();
    }

    public int getItemCountInPlayerInventory(Player player, ItemStack stack) {
        int total = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || !item.isSimilar(stack)) continue;
            total += item.getAmount();
        }
        return total;
    }

    public void removeSpecificItemQuantityFromPlayer(Player player, ItemStack stack, int amount) {
        int i = amount;
        for (int j = 0; j < player.getInventory().getSize(); j++) {
            ItemStack item = player.getInventory().getItem(j);
            if (item == null) continue;
            if (!item.isSimilar(stack)) continue;

            if (i >= item.getAmount()) {
                player.getInventory().clear(j);
                i -= item.getAmount();
            } else if (i > 0) {
                item.setAmount(item.getAmount() - i);
                i = 0;
            } else {
                break;
            }
        }
    }

    /**
     * Used to convert an item stack to a byte array
     *
     * @param item is the item you want to serialize
     * @return a byte array (serialized item)
     */
    public byte[] serializeItemStack(ItemStack item) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream(); BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(stream)) {
            bukkitStream.writeObject(item);
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Used to deserialize an item from a byte array
     *
     * @param serializedItem is the byte array (serialize item)
     * @return an item stack from the byte array
     */
    public ItemStack deserializeItem(byte[] serializedItem) {
        ItemStack stack = null;
        try (BukkitObjectInputStream stream = new BukkitObjectInputStream(new ByteArrayInputStream(serializedItem))) {
            stack = (ItemStack) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stack;
    }

    /**
     * Used to convert a serializable object into a base64 string
     *
     * @param object is the class that implements Serializable
     * @return the base64 encoded string
     */
    public String convertToBase64(Serializable object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * Used to convert a base64 string into an object
     *
     * @param string is the base64 string
     * @return an object
     */
    public Object convertBase64ToObject(String string) {
        byte[] data = Base64.getDecoder().decode(string);
        ObjectInputStream objectInputStream;
        Object object = null;
        try {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            object = objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void sendDiscordMessage(String webhook, ShopSellEvent sellEvent, ShopBuyEvent buyEvent) {
        Objects.requireNonNull(webhook, "Webhook cannot be null when sending discord message");

        DiscordWebhook hook = new DiscordWebhook(webhook);
        hook.setUsername(Settings.DISCORD_MSG_USERNAME.getString());
        hook.setAvatarUrl(Settings.DISCORD_MSG_PFP.getString());

        String playerName = sellEvent != null ? sellEvent.getPlayer().getName() : buyEvent.getPlayer().getName();
        String itemName = "???";

        if (sellEvent != null) {
            ItemStack deserializedItem = deserializeItem(sellEvent.getShopItem().getItem());
            itemName = getDiscordFriendlyMessage(deserializedItem);
        }

        if (buyEvent != null) {
            if (buyEvent.isFromCart()) {
                itemName = buyEvent.getCartItems().stream().map(cartItem -> deserializeItem(cartItem.getItem())).map(this::getDiscordFriendlyMessage).collect(Collectors.joining(", "));
            } else {
                ItemStack deserializedItem = deserializeItem(buyEvent.getShopItem().getItem());
                itemName = getDiscordFriendlyMessage(deserializedItem);
            }
        }

        int quantity = sellEvent != null ? sellEvent.getQuantity() : buyEvent.getQuantity();
        double price = sellEvent != null ? sellEvent.getQuantity() * sellEvent.getShopItem().getSellPrice() : buyEvent.isFromCart() ? buyEvent.getCartItems().stream().mapToDouble(item -> item.getQuantity() * item.getBuyPrice()).sum() : buyEvent.getQuantity() * buyEvent.getShopItem().getBuyPrice();

        String[] possibleColours = Settings.DISCORD_MSG_DEFAULT_COLOUR.getString().split("-");
        Color colour = Settings.DISCORD_MSG_USE_RANDOM_COLOUR.getBoolean()
                ? Color.getHSBColor(ThreadLocalRandom.current().nextFloat() * 360F, ThreadLocalRandom.current().nextFloat() * 101F, ThreadLocalRandom.current().nextFloat() * 101F)
                : Color.getHSBColor(Float.parseFloat(possibleColours[0]), Float.parseFloat(possibleColours[1]), Float.parseFloat(possibleColours[2]));

        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
        embedObject.setTitle(sellEvent != null ? Settings.DISCORD_MSG_SELL_TITLE.getString() : buyEvent.isFromCart() ? Settings.DISCORD_MSG_BUY_CART_TITLE.getString() : Settings.DISCORD_MSG_BUY_TITLE.getString());
        embedObject.setColor(colour);

        embedObject.addField(Settings.DISCORD_MSG_FIELD_PLAYER_NAME.getString(), Settings.DISCORD_MSG_FIELD_PLAYER_VALUE.getString().replace("%player%", playerName), Settings.DISCORD_MSG_FIELD_PLAYER_INLINE.getBoolean());
        embedObject.addField(Settings.DISCORD_MSG_FIELD_QTY_NAME.getString(), Settings.DISCORD_MSG_FIELD_QTY_VALUE.getString().replace("%quantity%", String.valueOf(quantity)), Settings.DISCORD_MSG_FIELD_QTY_INLINE.getBoolean());
        embedObject.addField(Settings.DISCORD_MSG_FIELD_PRICE_NAME.getString(), Settings.DISCORD_MSG_FIELD_PRICE_VALUE.getString().replace("%price%", String.format("%,.2f", price)), Settings.DISCORD_MSG_FIELD_PRICE_INLINE.getBoolean());

        if (sellEvent != null) {
            embedObject.addField(Settings.DISCORD_MSG_FIELD_ITEM_NAME.getString(), Settings.DISCORD_MSG_FIELD_ITEM_VALUE.getString().replace("%item_name%", itemName), Settings.DISCORD_MSG_FIELD_ITEM_INLINE.getBoolean());
        }

        if (buyEvent != null) {
            embedObject.addField(buyEvent.isFromCart() ? Settings.DISCORD_MSG_FIELD_ITEMS_NAME.getString() : Settings.DISCORD_MSG_FIELD_ITEM_NAME.getString(), buyEvent.isFromCart() ? Settings.DISCORD_MSG_FIELD_ITEMS_VALUE.getString().replace("%purchased_items%", itemName) : Settings.DISCORD_MSG_FIELD_ITEM_VALUE.getString().replace("%item_name%", itemName), buyEvent.isFromCart() ? Settings.DISCORD_MSG_FIELD_ITEMS_INLINE.getBoolean() : Settings.DISCORD_MSG_FIELD_ITEM_INLINE.getBoolean());
        }

        hook.addEmbed(embedObject);

        try {
            hook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDiscordFriendlyMessage(ItemStack stack) {
        return stack.hasItemMeta() ? stack.getItemMeta().hasDisplayName() ? ChatColor.stripColor(stack.getItemMeta().getDisplayName()) : WordUtils.capitalize(stack.getType().name().toLowerCase().replace("_", " ")) : WordUtils.capitalize(stack.getType().name().toLowerCase().replace("_", " "));
    }
}
