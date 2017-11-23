package rgs.game;

public class PayoffMatrix {
    public int[][][] matrix;

    public PayoffMatrix(int actionDimension) {
	matrix = new int[actionDimension][actionDimension][2];
	for(int i=0; i<actionDimension; i++) {
	    for(int j=0; j<actionDimension; j++) {
		matrix[i][j][0] = 0;
		matrix[i][j][1] = 0;		
	    }
	}
    }
}
