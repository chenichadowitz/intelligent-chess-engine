package main;

public interface NetworkNode {

	public void setConnectionType(ConnectionType type);
	public ConnectionType getConnectionType();
	public int hashCode();
	public boolean equals(Object obj);
	
}
