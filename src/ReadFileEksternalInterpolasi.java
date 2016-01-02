package bin;

import java.nio.charset.Charset;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.*;
import java.math.*;

public class ReadFileEksternalInterpolasi {
	
	private String lokasi;
	
	//KONSTRUKTOR -- METHOD INI AKAN DIJALANKAN PERTAMA KALI SAAT
	//				 CLASS ReadFileEksternal dipanggil
	//METHOD INI MENGAMBIL DIREKTORI "FileInput.txt" BERADA
	public ReadFileEksternalInterpolasi (String lokasi_file) {
		lokasi = lokasi_file;
	}
	
	//METHOD INI MENGHITUNG BANYAK BARIS INPUT DALAM FILE "FileInput.txt"
	int banyakBaris() throws Exception {
		
		FileReader fileTujuan = new FileReader(lokasi);
		BufferedReader bfr = new BufferedReader(fileTujuan);
		
		String baris;
		int jumlahBaris = 0;
		
		while ((baris = bfr.readLine()) != null) {
			jumlahBaris++;
		}
		
		jumlahBaris--;
		
		bfr.close();
		
		return jumlahBaris;		
	
	}
	
	int banyakKolom(int jmlhBaris) throws Exception{
		return (jmlhBaris + 1);
	}
	
	//METHOD INI MENCARI FILE TUJUAN YANG AKAN DIBACA
	double[] bacaFileTujuan(String file, int code) throws Exception {
		
		FileInputStream fis = new FileInputStream (file);

		double[] RV;
		
		//code 0 untuk bacaFileTujuan
		//code 1 untuk sumPercobaan
		
		RV = bacaNilaiDouble(fis, code);
		
		return RV;
	}
	
	//METHOD INI MEMBACA DAN MENGEMBALIKAN NILAI FLOAT DALAM "FileInput.txt"
	double[] bacaNilaiDouble(InputStream in, int code) throws Exception {
	
		String barisSekarang;
		
		String BARIS;
		
		BufferedInputStream s = new BufferedInputStream(in);
		BufferedReader nilaiDouble = new BufferedReader(new InputStreamReader(s));
   
		int j = 0, k, jk;
		
		double[] values01 = new double[5];
	
		int valRow, valCol;
		int dimCode1;
		
		BARIS = nilaiDouble.readLine();
		
		StringTokenizer st = new StringTokenizer(BARIS);
		
		while (st.hasMoreElements()) {
			values01[j++] = Double.valueOf(st.nextToken()).doubleValue();
		}
		
		valRow = (int) values01[0];
		valCol = valRow + 1;
		
		int dimensi = 0;
		
		if (code == 0) {
			dimensi = valRow * 2;
		}
		else {
			jk = 0;
			k = 0;
			
			while ( (barisSekarang = nilaiDouble.readLine()) != null ) {
			
			StringTokenizer st1 = new StringTokenizer(barisSekarang);
			
			if ( jk == valRow + 1 ) {
				while(st1.hasMoreElements()) {
			
					values01[k] = Double.valueOf(st1.nextToken()).doubleValue();
					dimensi = (int) values01[k];

				}
				break;
			}
			
			jk++;
			
			}
		}
		
		double[] values = new double[dimensi];
		
		j = 0;
		k = 0;
		jk = 0;
		
		if (code == 0) {
		while (jk < valRow + 1) {
			
			barisSekarang = nilaiDouble.readLine();
			StringTokenizer st1 = new StringTokenizer(barisSekarang);
			
			while(st1.hasMoreElements()) {
				if ( j != 0 ) {
					if ( k < dimensi ) {
						
						values[k] = Double.valueOf(st1.nextToken()).doubleValue();
						k++;
						
					}
				}
				j++;
			}
			
			jk++;
		}
		}
		
		else {
			//code = 1
			
			while ( (barisSekarang = nilaiDouble.readLine()) != null ) {
			
			StringTokenizer st1 = new StringTokenizer(barisSekarang);
			
				while(st1.hasMoreElements()) {
				
					values[k] = Double.valueOf(st1.nextToken()).doubleValue();
					k++;
						
				}
			
			}
			
			jk++;
		}
				
		
		return values;
    }
	
	//METHOD INI MEMBUAT MATRIKS 2 DIMENSI DARI ANGKA YANG DIAMBIL PADA "FileInput.txt"
	double[][] buatDuaDimensi() throws Exception{
			
			int a = 0, b = 0, i, jj;
			
			double[] results = bacaFileTujuan(lokasi, 0);
			
			int bnykBaris = (results.length) / 2;
			int nilaiKolom = bnykBaris + 1;
			
			double[][] hasilDuaDimensi = new double[bnykBaris][nilaiKolom];
			
			for(i = 0; i < results.length; i++ ) {
				for (jj = 0; jj < bnykBaris; jj++) {
					hasilDuaDimensi[a][jj] = Math.pow(results[i], jj);
				}
				i++;
				hasilDuaDimensi[a][bnykBaris] = results[i];
				a++;
			} 
			
			return hasilDuaDimensi;
		
	}
	
	double[] sumPercobaan() throws Exception{
		
		double[] results = bacaFileTujuan(lokasi, 1);
		
		return results;
	}
	
}