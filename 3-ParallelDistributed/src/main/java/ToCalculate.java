import java.io.Serializable;

public class ToCalculate implements Serializable {
    private final float[] col;
    private final float[] row;
    private final int i;
    private final int j;
    private final int sizeOfResult;

    public ToCalculate(float[] col, float[] row, int i, int j, int sizeOfResult) {
        this.col = col;
        this.row = row;
        this.i = i;
        this.j = j;
        this.sizeOfResult = sizeOfResult;
    }

    public float calculate() {
        float sum = 0;
        for (int k = 0; k < sizeOfResult; k++) {
            sum += col[k] * row[k];
        }
        return sum;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}