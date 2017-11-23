package rgs.player;

import rgs.sandbox.Sandbox;
import rgs.game.IStageGame;
import rgs.game.PayoffMatrix;

public class TitForTat extends APlayer {

    public TitForTat() {
	super("TitForTat");
    }
    
    public int act(int opnID) {
	List<Integer[]> history = Sandbox.getHistory();
	
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
	    System.out.println("Globalist: The Global Union conclude that action '" + commonAction + "' (" + maxGlobalScore + ") fits the common wealth of all people." );
	}
	return commonAction;
    }
    
}
