import java.io.*;
import javax.swing.JFileChooser;

public class Control {

	public static TimeSlot[] shifts = new TimeSlot[1334];

	//simple main method - to be deleted upon completion of project
	public static void main(String[] args) throws IOException {

		parse("test", "dont throw an error");
	}

	public static TimeSlot[] parse(String opas, String mou) throws IOException {
		//file chooser for picking the file - simple, quick for testing purposes - need file restrictions eventually
		/*JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fn = file.getPath();
            System.out.println(fn);		//LINE OF CODE FOR TESTING PURPOSES
		 */
		//Read in the lines of the csv
		BufferedReader br = new BufferedReader(new FileReader(opas));
		String line = "";

		//get rid of the top lines of code - parse until you find the first quote
		boolean gate = true;
		while(gate) {
			line = br.readLine();
			if(line.length() > 0 && line.charAt(0) == '\"')
				gate = false;
		}

		//read in the rest - this should be names in the first 2 blocks, followed by lots of shifts
		while (line != null) {
			//System.out.println(line);    //LINE OF CODE FOR TESTING PURPOSES
			String[] sh = line.split(",");	//csv, so split on commas

			//This prevents the code from crashing if theres empty lines at the end
			if(sh.length > 0 ) {
				//Find the 2-letter designation for a person
				//The name takes up two indicies cause they separate the last and first names with a comma
				String id = sh[0].substring(1, 3);
				System.out.println(id);		//LINE OF CODE FOR TESTING PURPOSES
			}
			
			int shiftLength = 8;	//default 8 hour shifts
			int shiftCount = 0;
			//Count the number of shifts this employee has
			for(int dayofweek = 2; dayofweek < sh.length; dayofweek++)
			{
				//If the string is longer than length 0, then its a shift
				if(sh[dayofweek].length() > 0)
				{
					shiftCount++;
				}
			}
			//If they have only 8 shifts, they are 10 hour shifts
			if(shiftCount == 8)
				shiftLength = 10;
			//Generally most will have 10 8-hour shifts
				
			//Parse through the rest of the line, this should be just times
			//The dayofweek represents the day of the week the shift is on
			//the first sunday = 2, first monday = 3, first tuesday = 4, etc.
			//look I know it's weird but its just how its gotta work
			for(int dayofweek = 2; dayofweek < sh.length;  dayofweek++)
			{
				System.out.print(sh[dayofweek] + " ");
				//If the shift exists for this day, add it
				if(sh[dayofweek].length() > 0)
				{
					//We increment the TimeSlot associated with the shift - addBaseline for first schedule, addComparison for second schedule
					//We know what TimeSlot it is via the TimeSlot method
					int index = TimeSlot.getIndex(dayofweek-2,  sh[dayofweek]);
					//Also gotta addBaseline to all other shifts hit by this start time
					for(int i = 0; i < shiftLength*4; i++)
					{
						shifts[index].addBaseline();
					}
				}
			}
			System.out.println();
			//Get the next line
			line = br.readLine();
		}


		//Once more, for the second schedule
		//Read in the lines of the csv
		br = new BufferedReader(new FileReader(mou));
		line = "";

		//get rid of the top lines of code - parse until you find the first quote
		gate = true;
		while(gate) {
			line = br.readLine();
			if(line.length() > 0 && line.charAt(0) == '\"')
				gate = false;
		}

		//read in the rest - this should be names in the first 2 blocks, followed by lots of shifts
		while (line != null) {
			//System.out.println(line);    //LINE OF CODE FOR TESTING PURPOSES
			String[] sh = line.split(",");	//csv, so split on commas

			//This prevents the code from crashing if theres empty lines at the end
			if(sh.length > 0 ) {
				//Find the 2-letter designation for a person
				//The name takes up two indicies cause they separate the last and first names with a comma
				String id = sh[0].substring(1, 3);
				System.out.println(id);		//LINE OF CODE FOR TESTING PURPOSES
			}
			
			int shiftLength = 8;	//default 8 hour shifts
			int shiftCount = 0;
			//Count the number of shifts this employee has
			for(int dayofweek = 2; dayofweek < sh.length; dayofweek++)
			{
				//If the string is longer than length 0, then its a shift
				if(sh[dayofweek].length() > 0)
				{
					shiftCount++;
				}
			}
			//If they have only 8 shifts, they are 10 hour shifts
			if(shiftCount == 8)
				shiftLength = 10;
			//Generally most will have 10 8-hour shifts
			
			//Parse through the rest of the line, this should be just times
			//The dayofweek represents the day of the week the shift is on
			//the first sunday = 2, first monday = 3, first tuesday = 4, etc.
			//look I know it's weird but its just how its gotta work
			for(int dayofweek = 2; dayofweek < sh.length;  dayofweek++)
			{
				System.out.print(sh[dayofweek] + " ");
				//If the shift exists for this day, add it
				if(sh[dayofweek].length() > 0)
				{
					//We increment the TimeSlot associated with the shift - addBaseline for first schedule, addComparison for second schedule
					//We know what TimeSlot it is via the TimeSlot method
					int index = TimeSlot.getIndex(dayofweek-2, sh[dayofweek]);
					//Also gotta addBaseline to all other shifts hit by this start time
					for(int i = 0; i < shiftLength*4; i++)
					{
						shifts[index].addComparison();
					}				
				}
			}
			System.out.println();
			//Get the next line
			line = br.readLine();
		}

		return shifts;
		//}
		//else {
		//System.out.println("File opening cancelled");
	}
}


class TimeSlot {

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
