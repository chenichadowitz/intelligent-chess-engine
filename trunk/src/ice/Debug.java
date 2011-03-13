package ice;

public class Debug {
	private static int maxLevel = 5;
	
	public static void setDebugLevel(int lvl){
		maxLevel = lvl;
	}
	
	public static void debug(String action, int level){
		if(level <= maxLevel){
			System.out.println(action);
			//possibly write to file
		}
	}
	
}
