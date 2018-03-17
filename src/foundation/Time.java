package foundation;

import java.util.HashSet;
import java.util.Set;

/**
 * Timekeeping class. Synchronizes all events. Single instance only.
 */
public class Time {

	/** Time instance */
	private static Time instance;
	
	/** Current year. */
	private static int year;
	
	/** Current month, starting at 0. */
	private static int month;
	
	/** Current day of the month, starting at 0. */
	private static int day;
	
	/** Current hour, where 0 is 00:xx */
	private static int hour;
	
	/** The current minute, where 0 is xx:00. */
	private static int minute;
	
	/** Number of days since new year*/
	private static int daysCount;
	
	/** Month names. */
	private static final String[] lMonths = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	
	/** Set of months with 30 days. */
	private static Set<Integer> thirtyDays = new HashSet<Integer>();
	
	/**
	 * Initialises the global clock
	 */
	private Time() {
		
		year = 700;
		month = 8;
		day = 21;
		
		hour = 7;
		minute = 0;
		
		thirtyDays.add(3);
		thirtyDays.add(5);
		thirtyDays.add(8);
		thirtyDays.add(10);
		
		daysCount = 264;
		
	}
	
	/**
	 * Ensures that only one Time instance can exist
	 *
	 * @return instance of Time
	 */
	public static Time getInstance() {
		
		if(instance == null){
			instance = new Time();
		}
		return instance;
	}
		
	/**
	 * Advances the time by one hour.<br>
	 * Handles any necessary changes to the day, month, or year.
	 */
	public static void advanceHour() {
		
		//Advance to nearest hour
		hour++;
		minute = 0;
		
		//advance day if appropriate
		if(hour > 23) {
			
			hour = 0;
			day ++;
			daysCount ++;
			
			//Advance month if appropriate
			if(month == 1 && day > 27) {
				month ++;
				day = 0;
			}
			else if(thirtyDays.contains(month) && day > 29) {
				month ++;
				day = 0;
			}
			else if(day > 30){
				month++;
				day = 0;
			}
			
			//Advance year if appropriate
			if(month > 11) {
				year++;
				month = 0;
				daysCount = 0;
			}
		}
	}
	
	/**
	 * Returns the current year.
	 *
	 * @return Current year
	 */
	public static int getYear() {
		return year;
	}
	
	/**
	 * Returns the id of the current month.
	 *
	 * @return Current month
	 */
	public static int getMonthByID() {
		return month;
	}
	
	/**
	 * Returns the current day.
	 *
	 * @return Current day
	 */
	public static int getDay() {
		return day;
	}
	
	/**
	 * Returns the current hour.
	 *
	 * @return Current hour
	 */
	public static int getHour() {
		return hour;
	}
	
	/**
	 * Returns the current minute.
	 *
	 * @return Current minute
	 */
	public static int getMinute() {
		return minute;
	}	
	
	/**
	 * Returns the name of the current month.
	 *
	 * @return Current month
	 */
	public static String getMonthByName() {
		return lMonths[month];
	}
	
	/**
	 * Returns a formatted date.
	 *
	 * @return Formatted date
	 */
	public static String getFormattedDate() {
		return (day+1) + " " + lMonths[month] + ", " + year;
	}
	
	/**
	 * Returns a formatted time.
	 *
	 * @return Formatted time
	 */
	public static String getFormattedTime() {
		
		if(hour == 0){
			return "12:" + String.format("%02d", minute) + " am";
		}
		else if(hour == 12){
			return "12:" + String.format("%02d", minute) + " pm";
		}
		else if(hour < 12){
			return String.format("%02d", hour) + ":" + String.format("%02d", minute) + " am";
		}
		else {
			return String.format("%02d", hour-12) + ":" + String.format("%02d", minute) + " pm";
		}
	}
	
	/**
	 * Returns the value of daysCount.
	 *
	 * @return daysCount
	 */
	public static int getCurrentDayCount() {
		return daysCount;
	}
	
	/**
	 * Returns the sunrise time in decimal hours.
	 *
	 * @return Sunrise time
	 */
	public static double getSunriseTime() {
		return 2*Math.cos((Math.PI * (double)daysCount)/182.0 + (5.0 * Math.PI)/91.0) + 5.0;
	}
	
}
