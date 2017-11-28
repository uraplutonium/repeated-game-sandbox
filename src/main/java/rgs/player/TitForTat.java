package rgs.player;

import java.util.List;

import rgs.sandbox.Sandbox;
import rgs.game.IStageGame;
import rgs.game.PayoffMatrix;

public class TitForTat extends APlayer {

    private static int commonAction = -1;
    
    public TitForTat() {
	super("TitForTat");
    }
    
    public int act(int opnID) {
	if(commonAction == -1) {
	    IStageGame sGame = Sandbox.getStageGame();
	    PayoffMatrix payoffMatrix = sGame.getPayoffMatrix();
	    int d = sGame.actionDimension();
	    int globalScore[] = new int[d]; // records the scores including mine and my opponent's under each possible action
	    int maxGlobalScore = -1;
	    for(int a=0; a<d; a++) { // try every possible actions
		// if I took action a
		for(int b=0; b<d; b++)
		    globalScore[a] += (payoffMatrix.matrix[a][b][0] + payoffMatrix.matrix[a][b][1]); // we care about all people
		if(globalScore[a] > maxGlobalScore) {
		    commonAction = a;
		    maxGlobalScore = globalScore[a];
		}
	    }
	    System.out.println("TitForTat: I will do '" + commonAction + "' (" + maxGlobalScore + ") for our common benifits at first, and will revenge if you betray me." );
	}
	
	List<Integer[]> history = Sandbox.getHistory(getID());
	if(history.size() == 0) {
	    return commonAction;
	} else {
	    int lastOpnAct = -1;
	    for(int i=history.size() - 1; i>=0; i--) {
		Integer[] result = history.get(i);
		if(result[1] == opnID) {
		    lastOpnAct = result[3];
		    break;
		} else if(result[2] == opnID) {
		    lastOpnAct = result[4];
		    break;
		}
	    }
	    return lastOpnAct == -1 ? commonAction : lastOpnAct;
	}
    }
    
    public IPlayer duplicate() {
	return new TitForTat();
    }
}
