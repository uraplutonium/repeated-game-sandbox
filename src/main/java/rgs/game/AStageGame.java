package rgs.game;

public abstract class AStageGame implements IStageGame {

    protected int actionDimension = 0;

    @Override
    public final int actionDimension() {
	return actionDimension;
    }

}
