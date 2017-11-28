package rgs.sandbox;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import rgs.game.*;
import rgs.player.*;

public class Sandbox {
    private static int clock = 0;
    private static int maxT;
    private static IStageGame sGame;
    private static List<Integer[]> history; // time, p1, p2, a1, a2, s1, s2

    public static int getTime() {
	return clock;
    }

    public static int getMaxT() {
	return maxT;
    }

    public static IStageGame getStageGame() {
	return sGame;
    }

    public static List<Integer[]> getHistory(int ID) {
	List<Integer[]> myHistory = new ArrayList<Integer[]>();
	for(Integer[] eachResult : history)
	    if(eachResult[1] == ID || eachResult[2] == ID)
		myHistory.add(eachResult);
	return myHistory;
    }

    private static int[] match(IPlayer p1, IPlayer p2) {
	int a1 = p1.act(p2.getID());
	int a2 = p2.act(p1.getID());
	int[] scores = sGame.getScores(a1, a2);
	Integer[] result = new Integer[7];
	result[0] = Sandbox.clock;
	result[1] = p1.getID();
	result[2] = p2.getID();
	result[3] = a1;
	result[4] = a2;
	result[5] = scores[0];
	result[6] = scores[1];
	history.add(result);
	// System.out.println("T:" + Sandbox.clock + "\t" + p1 + " plays " + a1 + " gets " + scores[0] + "\t" + p2 + " plays " + a2 + " gets " + scores[1]);
	return scores;
    }

    private static void repeatedGame(IPlayer p1, IPlayer p2, IStageGame stageGame, int maxTime) {
	clock = 0;
	maxT = maxTime;
	sGame = stageGame;
	history = new ArrayList<Integer[]>();
	for(Sandbox.clock = 0; Sandbox.clock<maxT; Sandbox.clock++) {
	    match(p1, p2);
	}
	int score1 = 0;
	int score2 = 0;
	for(Integer[] eachResult : history) {
	    score1 += eachResult[5];
	    score2 += eachResult[6];
	}
	System.out.println(p1 + " gets " + score1);
	System.out.println(p2 + " gets " + score2);
    }

    private static Map<IPlayer, Integer[]> roundRobin(IPlayer[] playerPool, int nPlayer, IStageGame stageGame, int maxTime) {
	clock = 0;
	maxT = maxTime;
	sGame = stageGame;
	history = new ArrayList<Integer[]>();

	Map<IPlayer, Integer[]> distribution = new HashMap<IPlayer, Integer[]>();

	for(Sandbox.clock = 0; Sandbox.clock<maxT; Sandbox.clock++) {
	    // System.out.print("t" + Sandbox.clock + "\t");
	    for(int p1=0; p1<nPlayer; p1++) {
		for(int p2=p1+1; p2<nPlayer; p2++) {
		    int[] s = match(playerPool[p1], playerPool[p2]);
		    boolean typeExist = false;
		    for(IPlayer eachType : distribution.keySet()) {
			if(eachType.sameTypeWith(playerPool[p1])) {
			    typeExist = true;
			    Integer[] newInt = new Integer[2];
			    newInt[0] = distribution.get(eachType)[0] + 1; // one more player
			    newInt[1] = distribution.get(eachType)[1] + s[0]; // add its score
			    distribution.put(eachType, newInt);
			    break;
			}
		    }
		    if(!typeExist) {
			Integer[] newInt = new Integer[2];
			newInt[0] = 1;
			newInt[1] = s[0];
			distribution.put(playerPool[p1], newInt);
		    }

		    typeExist = false;
		    for(IPlayer eachType : distribution.keySet()) {
			if(eachType.sameTypeWith(playerPool[p2])) {
			    typeExist = true;
			    Integer[] newInt = new Integer[2];
			    newInt[0] = distribution.get(eachType)[0] + 1; // one more match
			    newInt[1] = distribution.get(eachType)[1] + s[1]; // add its score
			    distribution.put(eachType, newInt);
			    break;
			}
		    }
		    if(!typeExist) {
			Integer[] newInt = new Integer[2];
			newInt[0] = 1;
			newInt[1] = s[1];
			distribution.put(playerPool[p2], newInt);
		    }
		}
	    }
	}
	System.out.println();
	for(IPlayer eachType : distribution.keySet()) {
	    double avgScore = (double)(distribution.get(eachType)[1]) / distribution.get(eachType)[0];
	    System.out.println(eachType + ", "+ avgScore + ", " + (distribution.get(eachType)[0]/maxTime/(nPlayer-1)));
	}
	return distribution;
    }

    private static IPlayer[] evolutionalRobin(IPlayer[] playerPool, int nPlayer, IStageGame stageGame, int maxTime, int nEvo) {
	for(int i=0; i<nEvo; i++) {
	    System.out.println("Round " + i);
	    Map<IPlayer, Integer[]> distribution = roundRobin(playerPool, nPlayer, new PrisonersDilemma(), maxTime);

	    double maxScore = Double.MIN_VALUE;
	    double minScore = Double.MAX_VALUE;
	    IPlayer winner=null, loser=null;
	    for(IPlayer eachType : distribution.keySet()) {
		double avgScore = (double)(distribution.get(eachType)[1]) / distribution.get(eachType)[0];
		if(avgScore > maxScore) {
		    maxScore = avgScore;
		    winner = eachType;
		}
		if(avgScore < minScore) {
		    minScore = avgScore;
		    loser = eachType;
		}
	    }
	    // System.out.println("Winner: " + winner);
	    // System.out.println("Loser:" + loser);
	    for(int p=0; p<nPlayer; p++) {
		if(playerPool[p].sameTypeWith(loser)) {
		    playerPool[p] = winner.duplicate();
		    break;
		}
	    }
	}
	return playerPool;
    }
    
    public static void main(String[] args) {
	System.out.println("sandbox start running.");

	// repeatedGame(new TitForTat(), new Selfishman(), new PrisonersDilemma(), 1000);

	int s = 10;
	int iter = 100;
	IPlayer[] playerPool = new IPlayer[3*s];
	for(int i=0; i<1*s; i++)
	    playerPool[i] = new Globalist();
	for(int i=1*s; i<2*s; i++)
	    playerPool[i] = new Selfishman();
	for(int i=2*s; i<3*s; i++)
	    playerPool[i] = new TitForTat();
	
	
	// roundRobin(playerPool, 3*s, new PrisonersDilemma(), iter);
	

	
	IPlayer[] finalPlayerPool = evolutionalRobin(playerPool, 3*s, new PrisonersDilemma(), iter, 20);
	int n1=0, n2=0, n3=0;
	for(IPlayer eachPlayer : finalPlayerPool)
	    if(eachPlayer instanceof Globalist)
		n1++;
	    else if(eachPlayer instanceof Selfishman)
		n2++;
	    else if(eachPlayer instanceof TitForTat)
		n3++;
	System.out.println("Globalist number: " + n1);
	System.out.println("Selfishman number: " + n2);
	System.out.println("TitForTat number: " + n3);
	

    }

}
