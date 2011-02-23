package ice;

public class Player {
	protected boolean color;
	protected boolean checkStatus = false;
	public boolean getCheckStatus(){ return checkStatus; }
	public void setCheckStatus(boolean inCheck){ checkStatus = inCheck;}
}
