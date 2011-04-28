package main;

import java.util.HashMap;
import java.util.LinkedList;

public class PiecePositionMoveNetwork {
	
	HashMap<NetworkNode, LinkedList<NetworkNode>> network = new HashMap<NetworkNode, LinkedList<NetworkNode>>();
	
	public LinkedList<NetworkNode> getNodes(NetworkNode node) throws IllegalArgumentException {
		return network.get(node);
	}

	public NetworkNode fromPositionGetPrimaryPiece(NetworkNode node) throws IllegalArgumentException {
		for(NetworkNode nn : network.get(node)){
			if(nn.getConnectionType() == ConnectionType.Primary) return nn;
		}
		return null;
	}
}
