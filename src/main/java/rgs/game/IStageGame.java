package rgs.game;

import rgs.player.IPlayer;

public interface IStageGame {

    public int actionDimension(); // return the max num of action: 0, 1, n-1

    public int[] getScores(int a1, int a2); // return the scores of playerA and playerB if they took actionA and actionB

    public PayoffMatrix getPayoffMatrix(); // return the payoff matrix

}
