public class TimeSlot {

	private int baseline;
	private int comparison;
	private int difference;

	public TimeSlot() {
		baseline = 0;
		comparison = 0;
		difference = 0;
	}

	public void addBaseline() {
		baseline++;
		difference--;
	}

	public void addComparison() {
		comparison++;
		difference++;
	}

	public int getDifference() {
		return difference;
	}

	//given the index the time slot is in, what time is that?
	//this will probably only be called while drawing the bar graph
	//@param index is index of the TimeSlot
	//@return a String that represents a time - format: "Day, XX:XX"  ***may change in the future to add specific date***
	public static String getTime(int index) {
		//add 1/4 of an hour per index
		double t = 0.00 + .25 * index;
		//the day is how many times 24 fits into the time
		int day = ((int)(t/24));
		//the hour is the remainder of t/24
		int hour = ((int)t) % 24;
		//the minute is 3/5ths of the decimal portion of t
		int min = ((int)(t * 100)) % 100 * 3/5;

		//convert the int day into the correct day of the week String
		String time = "";
		switch(day) {
		case 0: time = "Sunday";
		break;
		case 1: time = "Monday";
		break;
		case 2: time = "Tuesday";
		break;
		case 3: time = "Wednesday";
		break;
		case 4: time = "Thursday";
		break;
		case 5: time = "Friday";
		break;
		case 6: time = "Saturday";
		break;
		case 7: time = "2nd Sunday";
		break;
		case 8: time = "2nd Monday";
		break;
		case 9: time = "2nd Tuesday";
		break;
		case 10: time = "2nd Wednesday";
		break;
		case 11: time = "2nd Thursday";
		break;
		case 12: time = "2nd Friday";
		break;
		case 13: time = "2nd Saturday";
		break;
		default: break;
		}

		//return the combo of it all for a info-heavy string
		String ret = time + ", " + hour + ":";
		if(min < 10)
			ret = ret + "0" + min;
		else
			ret = ret + min;
		return ret;
	}

	//given the day and time, what index is that?
	//this will probably only be called while parsing the data and populating the list of TimeSlots
	//@param day is the day of the week in integer format: 0 = first Sunday, 1 = first Monday, 7 = second Sunday, 8 = second Monday, etc.
	//@param time is a String to represent the numerical time - format: "XX:XX" so "02:15" is valid
	//@return the index corresponding to this day/time
	public static int getIndex(int day, String time) {
		//time should be in a XX:XX format
		int hour = Integer.parseInt(time.substring(0, 2));
		int min = Integer.parseInt(time.substring(3));
		//Rounding to nearest 15 minutes
		if(min >= 8 && min <= 22)
			min = 15;
		else if(min >= 23 && min <= 37)
			min = 30;
		else if(min >= 38 && min <= 52)
			min = 45;
		else if(min >= 53) {
			min = 0;	//When rounding up to nearest hour
			hour++;		//Don't forget to increment hour
		}
		else
			min = 0;
		//1 index per 15 minutes
		int index = min/15;
		//4 indices per hour
		index += 4*hour;
		//96 indices per day
		index += 96*day;

		//return the correct index for this time
		return index;
	}

	//toString method, prints comparison, baseline, and difference of this TimeSlot
	//NOTE: the TimeSlot does not know what time it resides in
	public String toString() {
		return "comparison: " + comparison + "   baseline: " + baseline + "   difference: " + difference;
	}
}
