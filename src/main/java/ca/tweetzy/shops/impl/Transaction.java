package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.enums.TransactionType;
import ca.tweetzy.shops.api.interfaces.ITransaction;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 24 2022
 * Time Created: 11:29 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class Transaction implements ITransaction, ConfigSerializable {

	private final UUID id;
	private final UUID player;
	private final String shopId;
	private final ItemStack itemStack;
	private final int quantity;
	private final double total;
	private final TransactionType type;
	private final long date;

	@Override
	public UUID getID() {
		return this.id;
	}

	@Override
	public UUID getPlayer() {
		return this.player;
	}

	@Override
	public String getShopID() {
		return this.shopId;
	}

	@Override
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public double getTotal() {
		return this.total;
	}

	@Override
	public TransactionType getType() {
		return this.type;
	}

	@Override
	public long getDate() {
		return this.date;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"id", this.id,
				"player", this.player,
				"shop id", this.shopId,
				"item", this.itemStack,
				"quantity", this.quantity,
				"total", this.total,
				"type", this.type,
				"date", this.date
		);
	}

	public static Transaction deserialize(SerializedMap map) {
		return new Transaction(
				map.getUUID("id"),
				map.getUUID("player"),
				map.getString("shop id"),
				map.getItem("item"),
				map.getInteger("quantity"),
				map.getDouble("total"),
				map.get("type", TransactionType.class),
				map.getLong("date")
		);
	}
}
