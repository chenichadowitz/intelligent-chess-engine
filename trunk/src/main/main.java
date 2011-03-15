package main;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0){
			if(args[0].equals("xboard")){
				Xboard.run(args);
			} else if(args[0].equals("cli")){
				ice.iceDriver.run(args);
			} 
		} else {
				iceGUI.BoardGUI.run(args);
		}
	}

}
