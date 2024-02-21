package ca.tweetzy.shops.api.shop;

import ca.tweetzy.shops.api.Jsonable;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface ShopDisplay extends Jsonable {

	int getRows();

	void setRows(final int rows);

	int getExitButtonSlot();

	void setExitButtonSlot(final int slot);

	int getPrevPageButtonSlot();

	void setPrevPageButtonSlot(final int slot);

	int getNextPageButtonSlot();

	void setNextPageButtonSlot(final int slot);

	int getSearchButtonSlot();

	void setSearchButtonSlot(final int slot);

	int getFilterButtonSlot();

	void setFilterButtonSlot(final int slot);

	int getCartButtonSlot();

	void setCartButtonSlot(final int slot);

	List<Integer> getFillSlots();

	void setFillSlots(final List<Integer> slots);

	Map<Integer, ItemStack> getDecoration();

	void setDecoration(@NonNull final Map<Integer, ItemStack> decoration);

	ItemStack getBackgroundItem();

	void setBackgroundItem(@NonNull final ItemStack backgroundItem);
}
