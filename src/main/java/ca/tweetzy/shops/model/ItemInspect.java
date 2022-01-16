package ca.tweetzy.shops.model;

import ca.tweetzy.tweety.ItemUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 16 2022
 * Time Created: 2:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class ItemInspect {

	/**
	 * Get the name of an item stack
	 *
	 * @param stack is the item you want to get name from
	 * @return the item name
	 */
	public String getItemName(@NonNull final ItemStack stack) {
		return stack.getItemMeta().hasDisplayName() ? stack.getItemMeta().getDisplayName() : ItemUtil.bountifyCapitalized(stack.getType());
	}

	/**
	 * Used to get the lore from an item stack
	 *
	 * @param stack is the item being checked
	 * @return the item lore if available
	 */
	public List<String> getItemLore(@NonNull final ItemStack stack) {
		final List<String> lore = new ArrayList<>();
		if (stack.hasItemMeta()) {
			if (stack.getItemMeta().hasLore() && stack.getItemMeta().getLore() != null) {
				lore.addAll(stack.getItemMeta().getLore());
			}
		}
		return lore;
	}

	/**
	 * Used to get the names of all the enchantments on an item
	 *
	 * @param stack is the itemstack being checked
	 * @return a list of all the enchantment names
	 */
	public List<String> getItemEnchantments(@NonNull final ItemStack stack) {
		final List<String> enchantments = new ArrayList<>();
		if (!stack.getEnchantments().isEmpty()) {
			stack.getEnchantments().forEach((k, i) -> {
				enchantments.add(k.getName());
			});
		}
		return enchantments;
	}

	/**
	 * Used to match patterns
	 *
	 * @param pattern  is the keyword being searched for
	 * @param sentence is the sentence you're checking
	 * @return whether the keyword is found
	 */
	public boolean match(@NonNull final String pattern, @NonNull final String sentence) {
		final Pattern patt = Pattern.compile(ChatColor.stripColor(pattern), Pattern.CASE_INSENSITIVE);
		final Matcher matcher = patt.matcher(sentence);
		return matcher.find();
	}

	/**
	 * @param pattern is the keyword that you're currently searching for
	 * @param lines   is the lines being checked for the keyword
	 * @return whether the keyword was found in any of the lines provided
	 */
	public boolean match(@NonNull final String pattern, @NonNull final List<String> lines) {
		for (String line : lines) {
			if (match(pattern, line)) {
				return true;
			}
		}
		return false;
	}
}
