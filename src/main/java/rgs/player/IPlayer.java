package rgs.player;


public interface IPlayer {

    public int getID(); // a player must tell other who he is

    public int act(int opnID); // at the time tick, do an action to a opponent

    public IPlayer duplicate(); // generate another player

    public boolean sameTypeWith(IPlayer p); // decide whether we are the same type of players
}
