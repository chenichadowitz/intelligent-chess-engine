package main;
import ice.iceDriver;
import iceGUI.BoardGUI;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0){
			if(args[0].equals("xboard")){
				Xboard.run(args);
			} else if(args[0].equals("gui")){
				BoardGUI.run(args);
			} 
		} else {
				iceDriver.run(args);
		}
	}

}
