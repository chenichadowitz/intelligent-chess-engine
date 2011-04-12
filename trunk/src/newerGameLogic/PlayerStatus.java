package newerGameLogic;

import java.util.HashMap;

public class PlayerStatus {

	private HashMap<WBColor, Boolean> inCheck = new HashMap<WBColor,Boolean>();
	private HashMap<WBColor, Boolean> mated = new HashMap<WBColor,Boolean>();
	
	public PlayerStatus(){
		inCheck.put(WBColor.White, false);
		inCheck.put(WBColor.Black, false);
		mated.put(WBColor.White, false);
		mated.put(WBColor.Black, false);
	}
	
	public boolean isInCheck(WBColor playerColor){
		return inCheck.get(playerColor);
	}
	
	public boolean isMated(WBColor playerColor){
		return mated.get(playerColor);
	}
}
