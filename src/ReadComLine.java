package bin;


import java.util.Scanner;
import java.io.IOException;

//INPUT DIASUMSIKAN SELALU BENAR

public class ReadComLine {

    private int row_eff, col_eff;

	//KONSTRUKTOR
    public ReadComLine (int baris_eff, int kolom_eff){
        row_eff = baris_eff;
        col_eff = kolom_eff;
    }

	//METHOD ini membaca input user dan me-return dalam bentuk matriks 2-D
    double[][] BacaMatriks () throws Exception {
        int i,j;
        double[][] DuaDimensi = new double [30][30];

        Scanner input_scan = new Scanner(System.in);

        for(i=0; i<row_eff; i++){
            for(j=0; j<col_eff; j++){
                //DuaDimensi[i][j] = input_scan.nextDouble();
                DuaDimensi[i][j] = Double.parseDouble(input_scan.next());
            }
        }
        return DuaDimensi;
    }

}
