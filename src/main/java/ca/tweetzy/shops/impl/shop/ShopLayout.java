package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.SerializeUtil;
import ca.tweetzy.shops.api.shop.ShopDisplay;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public final class ShopLayout implements ShopDisplay {

	private int rows;
	private int exitButtonSlot;
	private int prevPageButtonSlot;
	private int nextPageButtonSlot;
	private int searchButtonSlot;
	private int filterButtonSlot;
	private int cartButtonSlot;
	private int sellButtonSlot;

	private List<Integer> fillSlots;
	private Map<Integer, ItemStack> decorations;
	private ItemStack background;

	public ShopLayout() {
		this(6, 45, 48, 50, 49, 52, 53,47, InventoryBorder.getInsideBorders(6), new HashMap<>(), QuickItem.bg(CompMaterial.BLACK_STAINED_GLASS_PANE.parseItem()));
	}

	@Override
	public int getRows() {
		return this.rows;
	}

	@Override
	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public int getExitButtonSlot() {
		return this.exitButtonSlot;
	}

	@Override
	public void setExitButtonSlot(int slot) {
		this.exitButtonSlot = slot;
	}

	@Override
	public int getPrevPageButtonSlot() {
		return this.prevPageButtonSlot;
	}

	@Override
	public void setPrevPageButtonSlot(int slot) {
		this.prevPageButtonSlot = slot;
	}

	@Override
	public int getNextPageButtonSlot() {
		return this.nextPageButtonSlot;
	}

	@Override
	public void setNextPageButtonSlot(int slot) {
		this.nextPageButtonSlot = slot;
	}

	@Override
	public int getSearchButtonSlot() {
		return this.searchButtonSlot;
	}

	@Override
	public void setSearchButtonSlot(int slot) {
		this.searchButtonSlot = slot;
	}

	@Override
	public int getFilterButtonSlot() {
		return this.filterButtonSlot;
	}

	@Override
	public void setFilterButtonSlot(int slot) {
		this.filterButtonSlot = slot;
	}

	@Override
	public int getCartButtonSlot() {
		return this.cartButtonSlot;
	}

	@Override
	public void setCartButtonSlot(int slot) {
		this.cartButtonSlot = slot;
	}

	@Override
	public int getSellButtonSlot() {
		return this.sellButtonSlot;
	}

	@Override
	public void setSellButtonSlot(int slot) {
		this.sellButtonSlot = slot;
	}

	@Override
	public List<Integer> getFillSlots() {
		return this.fillSlots;
	}

	@Override
	public void setFillSlots(List<Integer> slots) {
		this.fillSlots = slots;
	}

	@Override
	public Map<Integer, ItemStack> getDecoration() {
		return this.decorations;
	}

	@Override
	public void setDecoration(@NonNull Map<Integer, ItemStack> decorations) {
		this.decorations = decorations;
	}

	@Override
	public ItemStack getBackgroundItem() {
		return this.background;
	}

	@Override
	public void setBackgroundItem(@NonNull ItemStack background) {
		this.background = background;
	}

	@Override
	public String getJSONString() {
		final JsonObject object = new JsonObject();

		object.addProperty("exitButtonSlot", this.exitButtonSlot);
		object.addProperty("prevPageButtonSlot", this.prevPageButtonSlot);
		object.addProperty("nextPageButtonSlot", this.nextPageButtonSlot);
		object.addProperty("filterButtonSlot", this.filterButtonSlot);
		object.addProperty("searchButtonSlot", this.searchButtonSlot);
		object.addProperty("cartButtonSlot", this.cartButtonSlot);
		object.addProperty("sellButtonSlot", this.sellButtonSlot);
		object.addProperty("rows", this.rows);

		final JsonArray fillSlotsArray = new JsonArray();
		this.fillSlots.forEach(fillSlotsArray::add);

		object.addProperty("backgroundItem", SerializeUtil.encodeItem(this.background));
		object.add("fillSlots", fillSlotsArray);

		final JsonArray decorationsArray = new JsonArray();
		this.decorations.forEach((slot, item) -> {
			final JsonObject decoration = new JsonObject();

			decoration.addProperty("slot", slot);
			decoration.addProperty("item", SerializeUtil.encodeItem(item));

			decorationsArray.add(decoration);
		});

		object.add("decorations", decorationsArray);

		return object.toString();
	}

	public static ShopLayout decodeJSON(@NonNull final String json) {
		final JsonObject parentObject = JsonParser.parseString(json).getAsJsonObject();
		final JsonArray fillSlotsArray = parentObject.get("fillSlots").getAsJsonArray();
		final JsonArray decorationsArray = parentObject.get("decorations").getAsJsonArray();

		final List<Integer> fillSlots = new ArrayList<>();
		final Map<Integer, ItemStack> decoration = new HashMap<>();

		decorationsArray.forEach(element -> {
			final JsonObject decoObj = element.getAsJsonObject();
			decoration.put(decoObj.get("slot").getAsInt(), SerializeUtil.decodeItem(decoObj.get("item").getAsString()));
		});

		fillSlotsArray.forEach(element -> fillSlots.add(element.getAsInt()));

		return new ShopLayout(
				parentObject.get("rows").getAsInt(),
				parentObject.get("exitButtonSlot").getAsInt(),
				parentObject.get("prevPageButtonSlot").getAsInt(),
				parentObject.get("nextPageButtonSlot").getAsInt(),
				parentObject.get("searchButtonSlot").getAsInt(),
				parentObject.get("filterButtonSlot").getAsInt(),
				parentObject.has("cartButtonSlot") ? parentObject.get("cartButtonSlot").getAsInt() : 53,
				parentObject.has("sellButtonSlot") ? parentObject.get("sellButtonSlot").getAsInt() : 47,
				fillSlots,
				decoration,
				SerializeUtil.decodeItem(parentObject.get("backgroundItem").getAsString())
		);
	}
}
