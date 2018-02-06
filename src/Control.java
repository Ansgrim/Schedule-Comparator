
public class Control {

	//simple main method - to be deleted upon completion of project
	public static void main(String[] args) throws IOException {
		//file chooser for picking the file - simple, quick for testing purposes - need file restrictions eventually
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fn = file.getPath();
            System.out.println(fn);		//LINE OF CODE FOR TESTING PURPOSES
            
            //Read in the lines of the csv
            BufferedReader br = new BufferedReader(new FileReader(fn));
            String line = "";
            
            //get rid of the top lines of code - currently deletes top 18 lines which works, but NEEDS TO CHANGE IN CASE OF DISASTER
            for(int i = 0; i < 18; i++) {
            	//gonna need the date/time in this area at some point
            	line = br.readLine();
            }
            
            //read in the rest - this should be names in the first 2 blocks, + shifts
            while ((line = br.readLine()) != null) {
                //System.out.println(line);    //LINE OF CODE FOR TESTING PURPOSES
            	String[] shifts = line.split(",");	//csv, so split on commas
            	
            	//This prevents the code from crashing if theres empty lines at the end
                if(shifts.length > 0 ) {
                	//Find the 2-letter designation for a person
                	//The name takes up two indicies cause they separate the last and first names with a comma
                    String id = shifts[0].substring(1, 3);
                	System.out.println(id);		//LINE OF CODE FOR TESTING PURPOSES
                }
                //Parse through the rest of the line, this should be just times
                //The dayofweek represents the day of the week the shift is on
                //the first sunday = 2, first monday = 3, first tuesday = 4, etc.
                //look I know it's weird but its just how its gotta work
                for(int dayofweek = 2; dayofweek < shifts.length;  dayofweek++)
                {
                	System.out.print(shifts[dayofweek] + " ");
                }
                System.out.println();
            }
            
        }
        else {
        	System.out.println("File opening cancelled");
        }
        
        
	}
	
}
