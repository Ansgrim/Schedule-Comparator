import java.io.*;
import javax.swing.JFileChooser;

public class Control {

	public static TimeSlot[] shifts = new TimeSlot[1334];

	//simple main method - to be deleted upon completion of project
	public static void main(String[] args) throws IOException {

		parse("test", "dont throw an error");
	}

	public static TimeSlot[] parse(String mou, String opas) throws IOException {
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
					//We increment the TimeSlot associated with the shift - addNeeded for OPAS schedules, addScheduled for MOU schedules
					//We know what TimeSlot it is via the TimeSlot method
					shifts[TimeSlot.getIndex(dayofweek-2, sh[dayofweek])].addNeeded();
				}
			}
			System.out.println();
			//Get the next line
			line = br.readLine();
		}


		//Once more, for the MOU
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
					//We increment the TimeSlot associated with the shift - addNeeded for OPAS schedules, addScheduled for MOU schedules
					//We know what TimeSlot it is via the TimeSlot method
					shifts[TimeSlot.getIndex(dayofweek-2, sh[dayofweek])].addScheduled();
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

	private int needed;
	private int scheduled;
	private int coverage;

	public TimeSlot() {
		needed = 0;
		scheduled = 0;
		coverage = 0;
	}

	public void addNeeded() {
		needed++;
		coverage--;
	}

	public void addScheduled() {
		scheduled++;
		coverage++;
	}

	public int getCoverage() {
		return coverage;
	}

	///TODO: update this so it differentiates between the first and second instance of a day of the week
	//i.e. which sunday is it? theres 2 options - need to know specific date/time
	//given the index the time slot is in, what time is that?
	//this will probably only be called while drawing the bar graph
	//@param index is index of the TimeSlot
	//@return a String that represents a time - format: "Day, XX:XX"  ***probably will change in the future to add specific date***
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

	///TODO: fix the problems with times that aren't exactly 15 minute increval
	//should round to nearest 15-minute increval
	//given the day and time, what index is that?
	//this will probably only be called while parsing the data and populating the list of TimeSlots
	//@param day is the day of the week in integer format: 0 = first Sunday, 1 = first Monday, 7 = second Sunday, 8 = second Monday, etc.
	//@param time is a String to represent the numerical time - format: "XX:XX" so "02:15" is valid
	//@return the index corresponding to this day/time
	public static int getIndex(int day, String time) {
		//time should be in a XX:XX format
		int hour = Integer.parseInt(time.substring(0, 2));
		int min = Integer.parseInt(time.substring(3));
		//1 index per 15 minutes
		int index = min/15;
		//4 indices per hour
		index += 4*hour;
		//96 indices per day
		index += 96*day;

		//return the correct index for this time
		return index;
	}

	//toString method, prints scheduled, needed, and coverage of this TimeSlot
	//NOTE: the TimeSlot does not know what time it resides in
	public String toString() {
		return "Scheduled: " + scheduled + "   Needed: " + needed + "   Coverage: " + coverage;
	}
}
