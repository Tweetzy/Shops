package ca.tweetzy.shops.helpers;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.TextUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 12:11 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ConfigurationItemHelper {

    public static ItemStack build(ItemStack stack, String title, List<String> lore, HashMap<String, Object> replacements) {
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(TextUtils.formatText(title));

        if (replacements != null) {
            for (String key : replacements.keySet()) {
                if (title.contains(key)) title = title.replace(key, String.valueOf(replacements.get(key)));
            }

            for (int i = 0; i < lore.size(); i++) {
                for (String key : replacements.keySet()) {
                    if (lore.get(i).contains(key)) {
                        lore.set(i, lore.get(i).replace(key, String.valueOf(replacements.get(key))));
                    }
                }
            }
        }

        meta.setDisplayName(TextUtils.formatText(title));
        meta.setLore(lore.stream().map(TextUtils::formatText).collect(Collectors.toList()));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack build(String item, String title, List<String> lore, HashMap<String, Object> replacements) {
        ItemStack stack = XMaterial.matchXMaterial(item.toUpperCase()).orElse(XMaterial.RED_STAINED_GLASS_PANE).parseItem();
        assert stack != null;
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(TextUtils.formatText(title));

        if (replacements != null) {
            for (String key : replacements.keySet()) {
                if (title.contains(key)) title = title.replace(key, String.valueOf(replacements.get(key)));
            }

            for (int i = 0; i < lore.size(); i++) {
                for (String key : replacements.keySet()) {
                    if (lore.get(i).contains(key)) {
                        lore.set(i, lore.get(i).replace(key, String.valueOf(replacements.get(key))));
                    }
                }
            }
        }

        meta.setDisplayName(TextUtils.formatText(title));
        meta.setLore(lore.stream().map(TextUtils::formatText).collect(Collectors.toList()));
        stack.setItemMeta(meta);
        return stack;
    }
}
