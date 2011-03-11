package iceGUI;


import javax.swing.*;

public class BoardGUI{

	public static void main(String[] args){
		DisplayWindow dw = new DisplayWindow("ICE", 800, 600);
		JMenuBar menuBar = new JMenuBar();
	    dw.setJMenuBar(menuBar);
		BoardPanel bp = new BoardPanel(menuBar);
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
	}
	
}
