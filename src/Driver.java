import javax.swing.JFrame;

public class Driver {
	public static void main (String[] args) {
			Control c = new Control();
			UI gui = new UI();
			gui.setTitle("Schedule Comparator");
			//c.setUI(gui);
			gui.setControl(c);
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
