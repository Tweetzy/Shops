package ca.tweetzy.shops.api;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.shop.Shop;
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

    public boolean exists(String shopId) {
        return Shops.getInstance().getData().contains("shops." + shopId.toLowerCase());
    }

    public void createShop(Shop shop) {
        Shops.getInstance().getData().set("shops." + shop.getId().toLowerCase() + ".data", convertToBase64(shop));
        Shops.getInstance().getData().save();
    }

    public void removeShop(String shopId) {
        Shops.getInstance().getData().set("shops." + shopId.toLowerCase(), null);
        Shops.getInstance().getData().save();
    }

    public void saveCustomGuiItems(CustomGUIItemHolder holder) {
        Shops.getInstance().getData().set("custom gui items." + holder.getGuiName().toLowerCase() + ".data", convertToBase64(holder));
        Shops.getInstance().getData().save();
    }

    /**
     * Used to convert a number into a readable format (commas, suffix)
     *
     * @param original is the original number being converted
     * @return the user friendly number
     */
    public String getFormattedNumber(double original) {
        int power;
        String suffix = " KMBTQ";
        String formatted = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int) StrictMath.log10(original);
        original = original / (Math.pow(10, (power / 3) * 3));
        formatted = formatter.format(original);
        formatted = formatted + suffix.charAt(power / 3);
        return formatted.length() > 4 ? formatted.replaceAll("\\.[0-9]+", "") : formatted;
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
