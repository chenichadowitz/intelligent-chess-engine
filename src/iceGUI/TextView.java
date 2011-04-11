package iceGUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextView extends JPanel {
	
	private JTextArea textArea;	
	
	public TextView(int rows, int cols){
		super();
		textArea = new JTextArea(rows, cols);
		JScrollPane scroller = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textArea.setEditable(false);
		textArea.setCaretPosition(0);
		this.add(scroller);
	}
	
	public void setRows(int rows){
		textArea.setRows(rows);
	}
	
	public void setColumns(int columns){
		textArea.setColumns(columns);
	}
	
	public void append(String str){
		textArea.append(str);
		textArea.setCaretPosition(textArea.getText().length());
	}
	
	public void clear(){
		textArea.setText("");
	}
}
