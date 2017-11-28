package rgs.player;

import java.util.List;

import rgs.game.IStageGame;

public abstract class APlayer implements IPlayer {

    private String alias = "abstract player";
    private static int population = 0;
    
    private final int ID; // every player instance has a distinct ID

    public APlayer(String alias) {
	ID = ++APlayer.population;
	this.alias = alias;
    }

    /**
     * @return the ID of a particular player instance.
     */
    @Override
    public final int getID () {
	return ID;
    }
    
    @Override
    public boolean sameTypeWith(IPlayer p) {
	return this.alias.equals(((APlayer)p).alias);
    }
    
    @Override
    public String toString() {
	String str = alias;
	str = str + ", " + ID;
	return str;
    }

}
