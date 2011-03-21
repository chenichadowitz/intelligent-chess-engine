package main;

public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0){
			if(args[0].equals("xboard")){
				Xboard.run(args);
			} else if(args[0].equals("cli")){
				gameLogic.ICEDriver.run(args);
			} 
		} else {
				iceGUI.BoardGUI.run(args);
		}
	}

}
