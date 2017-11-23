package rgs.player;

import rgs.sandbox.Sandbox;
import rgs.game.IStageGame;
import rgs.game.PayoffMatrix;

public class Selfishman extends APlayer {
    
    private static int commonAction = -1;

    public Selfishman() {
	super("Selfishman");
    }

    public int act(int opnID) {
	if(commonAction == -1) {
	    IStageGame sGame = Sandbox.getStageGame();
	    PayoffMatrix payoffMatrix = sGame.getPayoffMatrix();
	    int d = sGame.actionDimension();
	    int myScore[] = new int[d]; // records the scores including mine and my opponent's under each possible action
	    int maxBenifit = -1;
	    for(int a=0; a<d; a++) { // try every possible actions
		// if I took action a
		for(int b=0; b<d; b++)
		    myScore[a] += payoffMatrix.matrix[a][b][0]; // I care about only myself
		if(myScore[a] > maxBenifit) {
		    commonAction = a;
		    maxBenifit = myScore[a];
		}
	    }
	    System.out.println("Selfishman: I really think action '" + commonAction + "' (" + maxBenifit + ") does the best for myself." );
	}
	return commonAction;
    }
    
}
