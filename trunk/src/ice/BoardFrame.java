package ice;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class BoardFrame extends JFrame {

	JTextField jt;
	
	@SuppressWarnings("deprecation")
	public BoardFrame(String title){
		this.setTitle(title);
		jt = new JTextField();
		this.add(jt);
		
		this.show();		
	}
	
	public void setText(String text){
		jt.setText(text);
	}
	
	
}
