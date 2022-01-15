package ca.tweetzy.shops.api.interfaces;

import ca.tweetzy.shops.api.enums.TimePeriod;
import lombok.NonNull;

import java.time.DayOfWeek;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 05 2022
 * Time Created: 2:04 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IRefillTime {

	/**
	 * Get the day of the week
	 *
	 * @return
	 */
	DayOfWeek getDay();

	void setDay(@NonNull final DayOfWeek day);

	/**
	 * Get the hour
	 *
	 * @return the hour of the refill
	 */
	int getHour();

	void setHour(final int hour);

	/**
	 * Get the minute
	 *
	 * @return the minute of the refill
	 */
	int getMinute();

	void setMinute(final int minute);

	/**
	 * Get the time period (am/pm)
	 *
	 * @return the {@link TimePeriod} for the refill
	 */
	@NonNull TimePeriod getTimePeriod();

	void setTimePeriod(@NonNull final TimePeriod period);
}
