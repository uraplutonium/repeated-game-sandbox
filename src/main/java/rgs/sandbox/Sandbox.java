package rgs.sandbox;

import java.util.List;
import java.util.ArrayList;

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

    public static List<Integer[]> getHistory() {
	return history;
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
	return scores;
    }

    private static void repeatedGame(IPlayer p1, IPlayer p2, IStageGame stageGame, int maxTime) {
	clock = 0;
	maxT = maxTime;
	sGame = stageGame;
	history = new ArrayList<Integer[]>();
	for(Sandbox.clock = 0; Sandbox.clock<maxT; Sandbox.clock++) {
	    int[] scores = match(p1, p2);
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

    
    public static void main(String[] args) {
	System.out.println("sandbox start running.");
	IPlayer p1 = new Globalist();
	IPlayer p2 = new Selfishman();
	repeatedGame(p1, p2, new PrisonersDilemma(), 1000);
	

    }

}
