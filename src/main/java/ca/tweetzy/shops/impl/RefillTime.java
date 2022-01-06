package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.enums.TimePeriod;
import ca.tweetzy.shops.api.interfaces.IRefillTime;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 05 2022
 * Time Created: 2:08 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class RefillTime implements IRefillTime, ConfigSerializable {

	private final int hour;
	private final int minute;
	private final TimePeriod timePeriod;

	@Override
	public int getHour() {
		return this.hour;
	}

	@Override
	public int getMinute() {
		return this.minute;
	}

	@Override
	public @NonNull TimePeriod getTimePeriod() {
		return this.timePeriod;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray("hour", this.hour, "minute", this.minute, "period", this.timePeriod);
	}

	public static RefillTime deserialize(SerializedMap map) {
		return new RefillTime(map.getInteger("hour"), map.getInteger("minute"), map.get("period", TimePeriod.class));
	}
}
