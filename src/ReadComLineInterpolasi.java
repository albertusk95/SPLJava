package bin;

import java.util.Scanner;
import java.io.IOException;
import java.math.*;

//INPUT DIASUMSIKAN SELALU BENAR

public class ReadComLineInterpolasi {

    private int row_eff, col_eff, n_data;

	//KONSTRUKTOR
    public ReadComLineInterpolasi (int baris_eff, int kolom_eff, int ndatainterpolasi){
        row_eff = baris_eff;
        col_eff = kolom_eff;
        n_data = ndatainterpolasi;
    }

	//METHOD ini membaca input user dan me-return dalam bentuk matriks 2-D
    double[][] BacaMatriks () throws Exception {
        int i,j,k;
        double[][] DuaDimensi = new double [30][30];
        double[][] DuaDimensixy = new double [30][30]; //data x dan y yang dimasukkan

        Scanner input_scan = new Scanner(System.in);

        for(k=0; k < n_data; k++){
            DuaDimensixy[k][0] = Double.parseDouble(input_scan.next()); //membaca nilai x0 dan dimasukkan ke matriks
            DuaDimensixy[k][1] = Double.parseDouble(input_scan.next()); //membaca nilai y0 dan dimasukkan ke matriks
        }


        for(i=0; i<row_eff; i++){
            for(j=0; j<col_eff; j++){
                //DuaDimensi[i][j] = input_scan.nextDouble();
                if(j!=col_eff-1){
                    DuaDimensi[i][j] = Math.pow(DuaDimensixy[i][0],j);
                }
                else {
                    DuaDimensi[i][j] = DuaDimensixy[i][1];
                }
            }
        }
        return DuaDimensi;
    }

}
