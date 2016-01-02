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

public class ReadFileEksternal {
	
	private String lokasi;
	
	//KONSTRUKTOR -- METHOD INI AKAN DIJALANKAN PERTAMA KALI SAAT
	//				 CLASS ReadFileEksternal dipanggil
	//METHOD INI MENGAMBIL DIREKTORI "FileInput.txt" BERADA
	public ReadFileEksternal (String lokasi_file) {
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
	
	int banyakKolom(int panjangResults, int jmlhBaris) throws Exception{
		return (panjangResults / jmlhBaris);
	}
	
	
	//METHOD INI MENCARI FILE TUJUAN YANG AKAN DIBACA
	double[] bacaFileTujuan(String file) throws Exception {
		
		FileInputStream fis = new FileInputStream (file);

		double[] RV;
		
		RV = bacaNilaiDouble(fis);
		
		return RV;
	}

	//METHOD INI MEMBACA DAN MENGEMBALIKAN NILAI FLOAT DALAM "FileInput.txt"
	double[] bacaNilaiDouble(InputStream in) throws Exception {
	
		String barisSekarang;
		
		String BARIS;
		
		BufferedInputStream s = new BufferedInputStream(in);
		BufferedReader nilaiDouble = new BufferedReader(new InputStreamReader(s));
   
		int j = 0, k;
		int bnykBaris = banyakBaris();
		
		double[] values01 = new double[5];
	
		int valRow, valCol;
		
		BARIS = nilaiDouble.readLine();
		
		StringTokenizer st = new StringTokenizer(BARIS);
		
		while (st.hasMoreElements()) {
			values01[j++] = Double.valueOf(st.nextToken()).doubleValue();
		}
		
		valRow = (int) values01[0];
		valCol = (int) values01[1];
		
		int dimensi = valCol * valRow;
		double[] values = new double[dimensi];
		
		j = 0;
		k = 0;
		
		while ((barisSekarang = nilaiDouble.readLine()) != null) {  
			
			StringTokenizer st1 = new StringTokenizer(barisSekarang);
			
			while(st1.hasMoreElements()) {
				if ( j != 0 && j != 1 ) { 
					values[k++] = Double.valueOf(st1.nextToken()).doubleValue();
				}
				j++;
			}
			
		}
		
		return values;
    }
	
	//METHOD INI MEMBUAT MATRIKS 2 DIMENSI DARI ANGKA YANG DIAMBIL PADA "FileInput.txt"
	double[][] buatDuaDimensi() throws Exception{
			
			int a = 0, b = 0, i;
			int bnykBaris = banyakBaris();
			double[] results = bacaFileTujuan(lokasi);
			int nilaiKolom = banyakKolom(results.length, bnykBaris);
			double[][] hasilDuaDimensi = new double[bnykBaris][nilaiKolom];
			
			for(i = 0; i < results.length; i++ ) {
				
				hasilDuaDimensi[a][b] = results[i];
				b++;
				if (b >= nilaiKolom) {
					b = 0;
					a++;
				}
			} 
			
			return hasilDuaDimensi;
		
	}

}

