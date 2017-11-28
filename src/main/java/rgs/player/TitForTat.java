package rgs.player;

import java.util.List;

import rgs.sandbox.Sandbox;
import rgs.game.IStageGame;
import rgs.game.PayoffMatrix;

public class TitForTat extends Globalist {

    public TitForTat() {
	super("TitForTat");
    }
    
    public int act(int opnID) {
	if(commonAction == -1) {
	    computeCommonAction();
	    System.out.println("TitForTat: I will do '" + commonAction + "' for our common benifits at first, and will revenge if you betray me." );
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
