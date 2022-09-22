import java.util.Arrays;
public class Lab_MatrixMul {
    public static void main(String[] args) {
        int[][] inputA = { {5,6,7} , {4,8,9} };
        int[][] inputB = { {6,4} , {5,7} , {1,1}};
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);
        int matC_r = matA.data.length;
        int matC_c = matB.data[0].length;
        MyData matC = new MyData(matC_r, matC_c);
        
        //Q4
        Thread[][] threads = new Thread[matC_c][matC_r];

        for (int i = 0; i < matC_r; i++) {
            Thread matrix = new Thread(new MatrixMulThread(i, i, matA, matB, matC));
            matrix.start();
            for (int j = 0; j < matC_c; j++) {
                threads[j][i] = matrix;
            }
        }
        
        //Q5
        for(Thread t : threads[0]) {
            try {
                t.join();
            } catch (Exception e) { System.out.println(e); }
        }
        matC.show(); 
    }
}

class MatrixMulThread implements Runnable {
    int processing_row; int processing_col;
    MyData datA; MyData datB; MyData datC;

    MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c) {
        //Q1
        this.datA = a;
        this.datB = b;
        this.datC = c;
        this.processing_col = tCol;
        this.processing_row = tRow;
    } 

    /*Q2*/ public void run() {
        //Q3
        for (int i = 0; i < datA.data.length; i++) {
            for (int j = 0; j < datB.data.length; j++){
                datC.data[processing_row][i] += datA.data[processing_row][j] * datB.data[j][i];
            }
        }
    }
}

class MyData {
    int[][] data;
    MyData(int[][] m ) { data = m; }

    MyData(int r, int c) {
        data = new int[r][c];
        for (int[] aRow : data) 
            Arrays.fill(aRow, 0);
    }
    void show() {
        System.out.println(Arrays.deepToString(data));
    }
}
