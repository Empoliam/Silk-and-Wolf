package foundation;

import java.util.HashSet;
import java.util.Set;

public class Time {

	private static Time instance;
	
	private static int year;
	private static int month;
	private static int day;
	
	private static int hour;
	private static int minute;
	
	private static final String[] lMonths = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	private static Set<Integer> thirtyDays = new HashSet<Integer>();
	
	private Time() {
		
		year = 700;
		//Index of month
		month = 8;
		day = 22;
		
		hour = 22;
		minute = 0;
		
		thirtyDays.add(3);
		thirtyDays.add(5);
		thirtyDays.add(8);
		thirtyDays.add(10);
		
	}
	
	public static Time getInstance() {
		
		if(instance == null){
			instance = new Time();
		}
		return instance;
	}
		
	public static void advanceHour() {
		
		//Advance to nearest hour
		hour++;
		minute = 0;
		
		//advance day if appropriate
		if(hour > 23) {
			
			hour = 0;
			day ++;
			
			//Advance month if appropriate
			if(month == 1 && day > 28) {
				month ++;
				day = 1;
			}
			else if(thirtyDays.contains(month) && day > 30) {
				month ++;
				day = 1;
			}
			else if(day > 31){
				month++;
				day = 1;
			}
			
			//Advance year if appropriate
			if(month > 11) {
				year++;
				month = 0;
			}
		}
	}
	
	public static int getYear() {
		return year;
	}
	public static int getMonthByID() {
		return month;
	}
	public static int getDay() {
		return day;
	}
	public static int getHour() {
		return hour;
	}
	public static int getMinute() {
		return minute;
	}
	
	public static String getMonthByName() {
		return lMonths[month];
	}
	public static String getFormattedDate() {
		return day + " " + lMonths[month] + ", " + year;
	}
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
	
}
