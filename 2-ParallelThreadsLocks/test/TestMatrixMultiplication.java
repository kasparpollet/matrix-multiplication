import com.company.Main;
import com.company.ParallelBlock;
import com.company.ParallelNaive;
import com.company.Sequential;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatrixMultiplication {

    private float[][] mat1;
    private float[][] mat2;

    @BeforeEach
    public void generateMatrices() {
        mat1 = Main.generateMatrix(3000);
        mat2 = Main.generateMatrix(3000);
    }

    @Test
    public void testParallelNaiveWithOneThreads() {
        float[][] resultSequential = Sequential.multiplyMatrices(mat1, mat2);
        ParallelNaive parallelNaive = new ParallelNaive(mat1, mat2, 1);
        float[][] resultParallelNaive = parallelNaive.multiplyMatrices();

        assertEquals(resultSequential.length, resultParallelNaive.length);
        assertEquals(resultSequential[0].length, resultParallelNaive[0].length);

        for (int i = 0; i < resultParallelNaive.length; i++) {
            for (int j = 0; j < resultParallelNaive[0].length; j++) {
                assertEquals(resultSequential[i][j], resultParallelNaive[i][j]);
            }
        }
    }

    @Test
    public void testParallelBlockWithOneThreads() {
        float[][] resultSequential = Sequential.multiplyMatrices(mat1, mat2);
        ParallelBlock parallelBlock = new ParallelBlock(mat1, mat2, 1);
        float[][] resultParallelBlock = parallelBlock.multiplyMatrices();

        assertEquals(resultSequential.length, resultParallelBlock.length);
        assertEquals(resultSequential[0].length, resultParallelBlock[0].length);

        for (int i = 0; i < resultParallelBlock.length; i++) {
            for (int j = 0; j < resultParallelBlock[0].length; j++) {
                assertEquals(resultSequential[i][j], resultParallelBlock[i][j]);
            }
        }
    }

    @Test
    public void testParallelNaiveWithFourThreads() {
        float[][] resultSequential = Sequential.multiplyMatrices(mat1, mat2);
        ParallelNaive parallelNaive = new ParallelNaive(mat1, mat2, 4);
        float[][] resultParallelNaive = parallelNaive.multiplyMatrices();

        assertEquals(resultSequential.length, resultParallelNaive.length);
        assertEquals(resultSequential[0].length, resultParallelNaive[0].length);

        for (int i = 0; i < resultParallelNaive.length; i++) {
            for (int j = 0; j < resultParallelNaive[0].length; j++) {
                assertEquals(resultSequential[i][j], resultParallelNaive[i][j]);
            }
        }
    }

    @Test
    public void testParallelBlockWithFourThreads() {
        float[][] resultSequential = Sequential.multiplyMatrices(mat1, mat2);
        ParallelBlock parallelBlock = new ParallelBlock(mat1, mat2, 4);
        float[][] resultParallelBlock = parallelBlock.multiplyMatrices();

        assertEquals(resultSequential.length, resultParallelBlock.length);
        assertEquals(resultSequential[0].length, resultParallelBlock[0].length);

        for (int i = 0; i < resultParallelBlock.length; i++) {
            for (int j = 0; j < resultParallelBlock[0].length; j++) {
                assertEquals(resultSequential[i][j], resultParallelBlock[i][j]);
            }
        }
    }

    @Test
    public void testCreateSubMatricesAndCreateMatrixAgain() {
        ParallelBlock parallelBlock = new ParallelBlock(mat1, mat2, 4);
        float[][][][] subMatrix1 = parallelBlock.createSubMatrices(mat1);
        float[][] test = parallelBlock.createMatrixFromSubMatrices(subMatrix1);

        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                assertEquals(mat1[i][j], test[i][j]);
            }
        }
    }
}
