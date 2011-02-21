package ice;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HumanPlayer hp = new HumanPlayer(true);
		int[] move = hp.getMove();
		for(int i : move) System.out.println(i);
		
	}

}
