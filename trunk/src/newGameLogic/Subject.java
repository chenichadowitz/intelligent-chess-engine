package newGameLogic;

public interface Subject {

	/**
	 * registers an observer to this event
	 * @param thisObserver the observer to register
	 */
	public void registerObserver(Observer thisObserver);
	/**
	 * removes an observer from this event
	 * @param thisObserver the observer to remove
	 */
	public void removeObserver(Observer thisObserver);
	/**
	 * calls update on all observers who have registered
	 */
	public void notifyObservers();
}
