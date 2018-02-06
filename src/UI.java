import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UI extends JFrame{

	private Control c;
	
	public UI(){
		JPanel	panel = new JPanel();

		this.getContentPane().add(panel);
		this.setSize(500, 500);;
		this.validate();
		
		this.setVisible(true);
	}
	public void setControl(Control c) {
		this.c = c;
	}
		
}
