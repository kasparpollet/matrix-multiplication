import java.io.Serializable;

public class Result implements Serializable {
    private final int i;
    private final int j;
    private final float sum;

    public Result(int i, int j, float sum) {
        this.i = i;
        this.j = j;
        this.sum = sum;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public float getSum() {
        return sum;
    }
}
