package ca.tweetzy.shops.api;

import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Base64;

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
        if (amount <= 0) return;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;
            if (!item.isSimilar(stack)) continue;

            int updatedQty = item.getAmount() - amount;
            if (updatedQty > 0) {
                item.setAmount(updatedQty);
                break;
            } else {
                player.getInventory().clear(i);
                amount -= updatedQty;
                if (amount == 0) break;
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

}
