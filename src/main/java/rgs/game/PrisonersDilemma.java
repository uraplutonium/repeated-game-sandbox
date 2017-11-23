package rgs.game;

public class PrisonersDilemma extends AStageGame {

    private PayoffMatrix payoffMatrix;

    public PrisonersDilemma() {
	actionDimension = 2;
	payoffMatrix = new PayoffMatrix(actionDimension);
	payoffMatrix.matrix[0][0][0] = 3;
	payoffMatrix.matrix[0][0][1] = 3;
	payoffMatrix.matrix[0][1][0] = 0;
	payoffMatrix.matrix[0][1][1] = 5;
	payoffMatrix.matrix[1][0][0] = 5;
	payoffMatrix.matrix[1][0][1] = 0;
	payoffMatrix.matrix[1][1][0] = 1;
	payoffMatrix.matrix[1][1][1] = 1;
    }

    @Override
    public int[] getScores(int a1, int a2) {
	int[] scores = new int[2];
	scores[0] = payoffMatrix.matrix[a1][a2][0];
	scores[1] = payoffMatrix.matrix[a1][a2][1];
	return scores;
    }

    @Override
    public PayoffMatrix getPayoffMatrix() {
	return payoffMatrix;
    }
}
