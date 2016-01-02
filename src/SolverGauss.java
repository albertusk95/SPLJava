package bin;

import java.io.IOException;
import java.math.*;

public class SolverGauss {

	public int indexA, indexB, indexC, indexD, indexE, indexF;

	private int row_eff, col_eff, tampil_Proses, jenis_Output;
	private double[][] hasilOutput;

	//KONSTRUKTOR
	public SolverGauss (double[][] matriks, int baris_eff, int kolom_eff, int tampilProses, int jenisOutput) {
		hasilOutput = matriks;
		row_eff = baris_eff;
		col_eff = kolom_eff;
		tampil_Proses = tampilProses;
		jenis_Output = jenisOutput;
	}

	//METHOD INI MEMBUAT 1 UTAMA DARI BARIS DAN KOLOM TERTENTU
	double[][] MakeLeadingOne(double[][] hasilOutput, int row, int col) throws Exception {

		double faktorPembagi;

		faktorPembagi = hasilOutput[row][col];

		/*
			MATRIKS KITA SEKARANG ADALAH :
				4.0		4.0		-3.0	3.0
				2.0		3.0		-1.0	5.0
				-2.0	3.0		-1.0	1.0

			DENGAN NILAI 'row' = 0 dan 'col' = 0

			NILAI faktorPembagi BERDASARKAN KASUS MATRIKS KITA ADALAH hasilOutput[0][0] = 4.0

			JADI, KITA AKAN MEMBAGI SEMUA ELEMEN DI BARIS KE 0 DENGAN 4.0
		*/

		for (indexB = 0; indexB < col_eff; indexB++) {
			hasilOutput[row][indexB] = hasilOutput[row][indexB] / faktorPembagi;
			if (hasilOutput[row][indexB] == -0.0) {
				hasilOutput[row][indexB] = 0.0;
			}
		}

		/*
			JADI, SETELAH PROSES LOOPING FOR DI ATAS, MATRIKS KITA MENJADI
				1.0		1.0		-0.75	0.75
				2.0		3.0		-1.0	5.0
				-2.0	3.0		-1.0	1.0
		*/

		if (tampil_Proses == 1) {
			//JIKA USER MAU MELIHAT SEMUA PROSES ELIMINASI, MAKA AKAN DITAMPILKAN KE LAYAR
			System.out.println();
			if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
			
			System.out.println("R"+row+" "+"/"+" "+faktorPembagi);
			if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println("R"+row+" "+"/"+" "+faktorPembagi);
			
			for (indexE = 0; indexE < row_eff; indexE++) {
				for (indexF = 0; indexF < col_eff; indexF++) {

					//MENGAMBIL 3 DIGIT DI BELAKANG KOMA
					BigDecimal bd = new BigDecimal(hasilOutput[indexE][indexF]);
					bd = bd.round(new MathContext(5));

					hasilOutput[indexE][indexF] = bd.doubleValue();

					System.out.print(hasilOutput[indexE][indexF]+" "+" "+" ");
					if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.print(hasilOutput[indexE][indexF]+" "+" "+" ");
				}
				System.out.println();
				if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
			}
		}

		return hasilOutput; //MENGEMBALIKAN MATRIKS PALING BARU SETELAH PUNYA 1 UTAMA

	}

	//METHOD INI AKAN MENUKAR 2 BUAH BARIS
	double[][] TukarBaris (double[][] matriks, int startRow, int startColumn, double max) {

		double[][] containerSementara = new double[30][30];

		/*
			BERDASARKAN KASUS MATRIKS KITA, YAITU :
			2.0		3.0		-1.0	5.0
			4.0		4.0		-3.0	3.0
			-2.0	3.0		-1.0	1.0

			KITA MENDAPATKAN NILAI MAKSIMUM ABSOLUT NYA 4.0 DAN ADA DI BARIS KE-1 KOLOM KE-0
		*/

		for (indexA = startRow; indexA < row_eff; indexA++) {

			//PROSES 'IF' UNTUK MENCARI ELEMEN DI TIAP BARIS PADA KOLOM startColumn yang
			//BERNILAI SAMA DENGAN 4.0 --- HASIL NYA ADALAH DI BARIS KE-1
			if (Math.abs(matriks[indexA][startColumn]) == max) {

				// ALGORITMA MENUKAR 2 BUAH BARIS. UDAH PADA TAU LAH YA.
				for (indexB = 0; indexB < col_eff; indexB++) {
					containerSementara[indexA][indexB] = matriks[indexA][indexB];
					matriks[indexA][indexB] = matriks[startRow][indexB];
					matriks[startRow][indexB] = containerSementara[indexA][indexB];
				}

				/*
					BERDASARKAN KASUS KITA, MAKA BARIS KE-0 AKAN DITUKAR DENGAN BARIS KE-1

					BEGINI HASIL AKHIRNYA :
					4.0		4.0		-3.0	3.0
					2.0		3.0		-1.0	5.0
					-2.0	3.0		-1.0	1.0
				*/

				if (tampil_Proses == 1) {
					//JIKA USER MAU MELIHAT SEMUA PROSES ELIMINASI, MAKA AKAN DITAMPILKAN KE LAYAR
					System.out.println();
					if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
					
					System.out.println("R"+indexA+" "+"<->"+" "+"R"+startRow);
					if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println("R"+indexA+" "+"<->"+" "+"R"+startRow);
					
					for (indexE = 0; indexE < row_eff; indexE++) {
						for (indexF = 0; indexF < col_eff; indexF++) {

							//MENGAMBIL 3 DIGIT DI BELAKANG KOMA
							BigDecimal bd = new BigDecimal(matriks[indexE][indexF]);
							bd = bd.round(new MathContext(5));

							matriks[indexE][indexF] = bd.doubleValue();


							System.out.print(matriks[indexE][indexF]+" "+" "+" ");
							if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.print(matriks[indexE][indexF]+" "+" "+" ");
						}
						System.out.println();
						if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
					}
				}

				break; //KELUAR DARI LOOP FOR
			}
		}

		return matriks; //MENGEMBALIKAN MATRIKS BARU YANG SUDAH DITUKAR 2 BARIS NYA

	}

	/*	METHOD INI MELAKUKAN PROSES ELIMINASI GAUSS DENGAN TATANCANG PEMOROSAN / PIVOTING
		UNTUK MENDAPATKAN MATRIKS E S E L O N

		PARAMETER NYA ADALAH 'MakeMoreRight_value' YANG MERUPAKAN MATRIKS INPUT USER
	*/
	double[][] PivotingPart (double[][] MakeMoreRight_value) throws Exception {

		/*
			MISALKAN MATRIKS 'MakeMoreRight_value' ADALAH SEBAGAI BERIKUT :

				2.0		3.0		-1.0	5.0
				4.0		4.0		-3.0	3.0
				-2.0	3.0		-1.0	1.0

			INDEKS BARIS DAN KOLOM DIMULAI DARI 0 SAMPAI (barisEff - 1) dan (kolomEff - 1)
		*/

		double[][] MakeLeadingOne_value01;
		double[][] TukarBaris_value01;
		int found = 0, count, startingRow, startingColumn;
		double temp, max;

		startingRow = 0;	//	BARIS AWAL PIVOTING
		startingColumn = 0; //	KOLOM AWAL PIVOTING

		while (startingColumn < col_eff-1) {

			found = 0;
			count = 0;

			//	MENCARI ELEMEN TIDAK NOL PADA SETIAP BARIS DI KOLOM 'startingColumn'
			for (indexA = startingRow; indexA < row_eff; indexA++) {
				if (MakeMoreRight_value[indexA][startingColumn] != 0) {
					count ++;
				}
			}

			if (count > 0) {

				if (tampil_Proses == 1) {
					//JIKA USER MAU MELIHAT PROSES ELIMINASI GAUSS, MAKA AKAN DITAMPILKAN SEMUA PROSES NYA
					
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.println();
					if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
					
					System.out.println("Pivoting elemen ["+startingRow+","+" "+startingColumn+"]");
					if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println("Pivoting elemen ["+startingRow+","+" "+startingColumn+"]");
				}

				//	MENCARI NILAI ABSOLUT MAKSIMUM TIAP BARIS DALAM SATU KOLOM
				max = Math.abs(MakeMoreRight_value[startingRow][startingColumn]);

				for (indexA = startingRow; indexA < row_eff; indexA++) {
					if (max >= Math.abs(MakeMoreRight_value[indexA][startingColumn])) {
						max = max;
					}
					else {
						max = Math.abs(MakeMoreRight_value[indexA][startingColumn]);
					}
				}

				/*
					MISALKAN KITA AMBIL KASUS DIMANA NILAI startingRow = startingColumn = 0

					MAKA, SETELAH PROSES MENCARI NILAI ABSOLUT MAKSIMUM TIAP BARIS DALAM SATU KOLOM DI ATAS,
					DENGAN NILAI KOLOM = startingColumn = 0

						2.0
						4.0		--> elemen-elemen di kolom ke - 0
						-2.0

					NILAI MAKSIMUM ABSOLUT DARI KE-3 ELEMEN ITU ADALAH 4.0
				*/



				/*
					MENCARI INDEKS BARIS NILAI ABSOLUT MAKSIMUM TERSEBUT (MISALKAN ROW 1)
					MENUKAR KEDUA BARIS, YAITU ANTARA BARIS 'startingRow' DENGAN 'ROW 1'

					DARI KASUS SEBELUMNYA, NILAI ABSOLUT MAKSIMUM YAITU 4.0 ADA DI BARIS KE-1
					KITA AKAN MENUKAR BARIS KE-1 DENGAN BARIS KE-0

					HAL INI TERCAPAI BERKAT FUNGSI 'TukarBaris' DI BAWAH

					PAREMETER 'MakeMoreRight_value' ADALAH MATRIKS KITA, YAITU :
						2.0		3.0		-1.0	5.0
						4.0		4.0		-3.0	3.0
						-2.0	3.0		-1.0	1.0

					PARAMETER startingRow = 0 DAN startingColumn = 0

					PARAMETER max ADALAH 4.0
				*/

				TukarBaris_value01 = TukarBaris(MakeMoreRight_value, startingRow, startingColumn, max);

				/*
					MEMBUAT 1 UTAMA UNTUK BARIS 'startingRow',
					DIMANA DALAM KASUS MATRIKS KITA, NILAI 'startingRow = 0'

					BAGAIMANA CARA MENDAPATKAN 1 UTAMA DI BARIS KE - 0?

					CARANYA ADALAH DENGAN MEMBAGI SEMUA ELEMEN DI BARIS KE-0 DENGAN ELEMEN
					DI KOORDINAT (startingRow, startingColumn). DALAM KASUS KITA BERARTI (0, 0)

					INGAT ! MATRIKS KITA SEKARANG ADALAH :
						4.0		4.0		-3.0	3.0
						2.0		3.0		-1.0	5.0
						-2.0	3.0		-1.0	1.0

					PARAMETER TukarBaris_value01 = MATRIKS KITA SEKARANG
					PARAMETER startingRow = startingColumn = 0
				*/
				MakeLeadingOne_value01 = MakeLeadingOne(TukarBaris_value01, startingRow, startingColumn);

				/*
					ASSIGN HASIL MATRIKS YANG SUDAH DITUKAR BARIS NYA DAN PUNYA 1 UTAMA KE VARIABEL MakeMoreRight_value

					JADI, SETELAH MELEWATI FUNGSI 'MakeLeadingOne', MATRIKS KITA MENJADI :
						1.0		1.0		-0.75	0.75
						2.0		3.0		-1.0	5.0
						-2.0	3.0		-1.0	1.0

					NILAI MATRIKS INI DI-ASSIGN KE VARIABEL 'MakeMoreRight_value'
				*/
				MakeMoreRight_value = MakeLeadingOne_value01;

				//PROSES TATANCANG PEMOROSAN / PIVOTING DIMULAI

				for (indexA = startingRow; indexA < row_eff; indexA++) {

					/*
						PROSES MEMBUAT SEMUA ELEMEN DI BAWAH 1 UTAMA MENJADI NOL

						DALAM KASUS MATRIKS KITA, BERARTI MEMBUAT SEMUA ELEMEN DI BARIS
						KE-1 DAN KE-2 UNTUK KOLOM KE-0 MENJADI 0
					*/
					if (indexA != startingRow) {
						if (MakeMoreRight_value[indexA][startingColumn] != 0) {

							temp = MakeMoreRight_value[indexA][startingColumn];

							/*
								CARA MEMBUAT NYA MENJADI NOL ADALAH DENGAN RUMUS :

								R1 = R1 - (2.0 * R0); DIMANA 2.0 ADALAH ELEMEN DI BARIS KE-1 KOLOM KE-0
								R2 = R2 - (-2.0 * R0); DIMANA -2.0 ADALAH ELEMEN DI BARIS KE-2 KOLOM KE-0
							*/
							for (indexD = 0; indexD < col_eff; indexD++) {
								MakeMoreRight_value[indexA][indexD] = MakeMoreRight_value[indexA][indexD] - (temp*MakeMoreRight_value[startingRow][indexD]);
							}

							if (tampil_Proses == 1) {
								//JIKA USER MAU MELIHAT PROSES ELIMINASI GAUSS, MAKA AKAN DITAMPILKAN SEMUA PROSES NYA
									
								//OUTPUT KE COMMAND LINE
								System.out.println();
								if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
								
								if (temp>=0){
									System.out.println("R"+indexA+" "+"-"+" "+temp+"*"+"R"+startingRow);
									if (HalamanUtama.jenis_output==1) 
										HalamanUtama.tulisKeFile.println("R"+indexA+" "+"-"+" "+temp+"*"+"R"+startingRow);
								}
								else{
									System.out.println("R"+indexA+" "+"+"+" "+(-1)*temp+"*"+"R"+startingRow);
									if (HalamanUtama.jenis_output==1) 
										HalamanUtama.tulisKeFile.println("R"+indexA+" "+"+"+" "+(-1)*temp+"*"+"R"+startingRow);
								}
								

								for (indexE = 0; indexE < row_eff; indexE++) {
									for (indexF = 0; indexF < col_eff; indexF++) {

										//MENGAMBIL 3 DIGIT DI BELAKANG KOMA
										BigDecimal bd = new BigDecimal(MakeMoreRight_value[indexE][indexF]);
										bd = bd.round(new MathContext(5));

										MakeMoreRight_value[indexE][indexF] = bd.doubleValue();

										System.out.print(MakeMoreRight_value[indexE][indexF]+" "+" "+" ");
										if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.print(MakeMoreRight_value[indexE][indexF]+" "+" "+" ");
									}
									System.out.println();
									if (HalamanUtama.jenis_output==1) HalamanUtama.tulisKeFile.println();
								}

							}

						}
					}

				}

				/*
					SETELAH PROSES FOR DI ATAS, MATRIKS KITA MENJADI :
						1.0		1.0		-0.75	0.75
						0.0		1.0		0.5		3.5
						0.0		5.0		0.5		2.5
				*/

				startingRow++;	//DAN PROSES BERLANJUT UNTUK BARIS KE-1 DST...
			}

			startingColumn++; //DAN PROSES BERLANJUT UNTUK KOLOM KE-1 DST...

		}

		for (indexA = 0; indexA < row_eff; indexA++) {
			for (indexB = 0; indexB < col_eff; indexB++) {

				//MENGAMBIL 3 DIGIT DI BELAKANG KOMA
				BigDecimal bd = new BigDecimal(MakeMoreRight_value[indexA][indexB]);
				bd = bd.round(new MathContext(5));

				MakeMoreRight_value[indexA][indexB] = bd.doubleValue();

			}
		}

		return MakeMoreRight_value; //MENGEMBALIKAN MATRIKS PALING BARU SETELAH DI PIVOT
	}

	//METHOD ini mengecek apakah SPL punya solusi unik, banyak, atau tidak punya
	int CekStatusSolusiAkhir(double[][] MatriksAkhir) throws Exception {

		int count;
		int tidakAdaSolusi = 0, banyakSolusi = 0;
		int CekStatusSolusiAkhir = 2;

		for (indexA = 0; indexA < row_eff; indexA++) {
			count = 0;
			for (indexB = 0; indexB < col_eff-1; indexB++) {
				if (MatriksAkhir[indexA][indexB] != 0) {
					count ++;
				}
			}
			if (count == 0) {
				//KASUS dimana semua elemen di suatu baris bernilai 0 (tidak termasuk kolom konstanta)
				if (MatriksAkhir[indexA][col_eff - 1] != 0) {
					//KASUS dimana elemen konstanta di baris tersebut tidak nol.
					//Ini berarti SPL tidak punya solusi
					tidakAdaSolusi ++;
				}
				else {
					//KASUS dimana elemen konstanta di baris tersebut bernilai nol.
					//Ini berarti SPL punya banyak solusi
					banyakSolusi ++;
				}
			}
		}

		if (tidakAdaSolusi == 0 && banyakSolusi == 0) {
			CekStatusSolusiAkhir = 1; // unik
		}

		else if ((tidakAdaSolusi > 0 && banyakSolusi == 0) || (tidakAdaSolusi > 0 && banyakSolusi > 0)) {
			CekStatusSolusiAkhir = 0; // tidak ada solusi
		}

		else if (tidakAdaSolusi == 0 && banyakSolusi > 0) {
			CekStatusSolusiAkhir = -1; // banyak solusi
		}

		return CekStatusSolusiAkhir;

	}

	//METHOD ini mencari solusi SPL yang unik
	double [] SolusiGaussPivotingUnik(double[][] MatriksAkhir) throws Exception {

		int counter;
		double[] nilai_variabel = new double[30];
		double startValue;

		//KASUS dimana SPL memiliki solusi unik
		int startColumn = col_eff - 2;

		//MENDAPATKAN nilai variabel terakhir di baris paling bawah
		nilai_variabel[startColumn] = MatriksAkhir[row_eff - 1][col_eff - 1] / MatriksAkhir[row_eff - 1][col_eff - 2];

		//ALGORITMA Teknik Sulih Mundur
		counter = 0;
		for (indexA = row_eff - 2; indexA >= 0; indexA--) {
			counter ++;
			startValue = MatriksAkhir[indexA][col_eff - 1];
			for (indexB = startColumn; indexB > startColumn - counter; indexB--) {
				startValue = startValue - (MatriksAkhir[indexA][indexB] * nilai_variabel[indexB]);
			}
			nilai_variabel[startColumn - counter] = startValue / MatriksAkhir[indexA][startColumn - counter];
		}

		return nilai_variabel;
	}

	//METHOD ini mencari solusi SPL dalam parameter
	String[] SolusiGaussPivotingBanyak(double[][] MatriksAkhir) throws Exception {

		int[] able_to_find = new int[30];
		int[] rowValue = new int[30];
		int count;
		int numberVar;
		int representasiChar;
		int[] has_Had_value = new int[30];
		double[] variableValue = new double[30];
		char[] parameter_tunggal = new char[30];
		String str_awal;

		String[] value_FormatString = new String[30];

		//MENCARI variabel mana saja yang bisa ditentukan nilainya,
		//		  yaitu variabel yang memiliki 1 utama
		for (indexA = 0; indexA < row_eff; indexA++) {
			able_to_find[indexA] = 0;
			has_Had_value[indexA] = 0;
		}

		for (indexA = 0; indexA < row_eff; indexA++) {
			for (indexB = 0; indexB < col_eff; indexB++) {
				if (MatriksAkhir[indexA][indexB] == 1) {
					able_to_find[indexB] = 1;
					rowValue[indexB] = indexA;
					break;
				}
			}
		}

		for (numberVar = 0; numberVar < col_eff; numberVar++) {
			if (able_to_find[numberVar] == 1) {

				count = 0;
				for (indexB = 0; indexB < col_eff - 1; indexB++) {
					if (MatriksAkhir[rowValue[numberVar]][indexB] != 0 && indexB != numberVar) {
						count++;
					}
				}

				if (count == 0) {
					variableValue[numberVar] = MatriksAkhir[rowValue[numberVar]][col_eff - 1]; //dalam Double
					has_Had_value[numberVar] = 1; //sudah punya nilai unik
					value_FormatString[numberVar] = "("+Double.toString(variableValue[numberVar])+")";
				}

			}
		}

		//MENCARI VARIABEL BERNILAI BEBAS

		representasiChar = 65;

		for (numberVar = 0; numberVar < col_eff; numberVar++) {
			if (able_to_find[numberVar] == 0) { //punya parameter tunggal
				parameter_tunggal [numberVar] = (char) representasiChar;
				representasiChar++;
				value_FormatString[numberVar] = "("+String.valueOf(parameter_tunggal [numberVar])+")";
				has_Had_value[numberVar] = 1; //sudah punya nilai dalam format parameter
			}
		}

		//MENCARI NILAI VARIABEL LAIN YANG BELUM DITENTUKAN

		numberVar = col_eff - 1;
		while (numberVar >= 0) {

			if (has_Had_value[numberVar] == 0) {
				str_awal = Double.toString(MatriksAkhir[rowValue[numberVar]][col_eff - 1]);
				for (indexA = col_eff - 2; indexA > numberVar; indexA--) {
					if (MatriksAkhir[rowValue[numberVar]][indexA] != 0) {
						str_awal = str_awal + " " + "-" + " " + "(" + Double.toString(MatriksAkhir[rowValue[numberVar]][indexA]) + " * " + value_FormatString[indexA] +")";
					}
					else {
						str_awal = str_awal;
					}
				}
				value_FormatString[numberVar] = "("+str_awal+")";
				has_Had_value[numberVar] = 1;
				numberVar = col_eff;
			}

			numberVar--;
		}

		return value_FormatString;
	}

	/*	FUNGSI / METHOD INI ADALAH FUNGSI YANG DIPANGGIL KE - 2 SETELAH KONSTRUKTOR

		FUNGSI / METHOD ini sebagai pusat pemanggilan method-method lainnya
		dan juga sebagai akhir dari hasil matriks eliminasi Gauss-Pivoting (matriks ESELON)
	*/
	double[][] HasilGaussPivoting() throws Exception {

		double[][] PivotingPart_value;

		/*	MEMANGGIL METHOD 'PivotingPart(hasilOutput)' UNTUK MEMULAI
			PROSES ELIMINASI MEMBENTUK MATRIKS ESELON, DITAMBAH DENGAN TATANCANG PEMOROSAN / PIVOTING
		*/
		PivotingPart_value = PivotingPart(hasilOutput);

		return PivotingPart_value;

	}

}
