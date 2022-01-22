package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.enums.TimePeriod;
import ca.tweetzy.shops.api.interfaces.IRefillTime;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.sql.Time;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.util.Date;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 05 2022
 * Time Created: 2:08 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class RefillTime implements IRefillTime, ConfigSerializable {

	private DayOfWeek day;
	private int hour;
	private int minute;
	private TimePeriod timePeriod;

	public RefillTime() {
		this(DayOfWeek.MONDAY, 12, 1, TimePeriod.AM);
	}

	@Override
	public DayOfWeek getDay() {
		return this.day;
	}

	@Override
	public void setDay(@NonNull DayOfWeek day) {
		this.day = day;
	}

	@Override
	public int getHour() {
		return this.hour;
	}

	@Override
	public void setHour(int hour) {
		this.hour = hour;
	}

	@Override
	public int getMinute() {
		return this.minute;
	}

	@Override
	public void setMinute(int minute) {
		this.minute = minute;
	}

	@Override
	public @NonNull TimePeriod getTimePeriod() {
		return this.timePeriod;
	}

	@Override
	public void setTimePeriod(@NonNull TimePeriod period) {
		this.timePeriod = period;
	}


	public Date getDate() {
		return null;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray("day", this.day, "hour", this.hour, "minute", this.minute, "period", this.timePeriod);
	}

	public static RefillTime deserialize(SerializedMap map) {
		return new RefillTime(map.get("day", DayOfWeek.class), map.getInteger("hour"), map.getInteger("minute"), map.get("period", TimePeriod.class));
	}
}
