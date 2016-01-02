package bin;

import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;				// BIAR BISA NULIS KE FILE
import java.math.*;

//INPUT DIASUMSIKAN SELALU BENAR

public class HalamanUtama {

	//DEKLARASI VARIABEL GLOBAL
	static PrintWriter tulisKeFile;
	static int jenis_output;
	
	public static void main(String[] args) throws Exception{
		
	
		//DEKLARASI VARIABEL DAN SCANNER INPUT
		Scanner input_scan = new Scanner(System.in);
		int jenis_input, jenis_materi, jenis_metode, tampil_proses;
		int baris_eff, kolom_eff, ndatainterpolasi;


		int a, b;
		double[][] output;
		double[] relatePercobaan;
		
		int rowEff, colEff;

		int CekStatusSolusi;
		double[][] ResultGauss;
		double[] SolusiGaussUnik;
		String[] SolusiGaussBanyak;
		
		double[] nilaix = new double[50];
		double nilaifungsi;
		int banyakPercobaan = 0;
		int xy;
		
		
		//TAMPILAN WELCOME
		System.out.println("        Welcome!");
		System.out.println("Sistem Persamaan Linear");
		System.out.println("-----------------------\n");

		//INPUT JENIS MATERI
		System.out.println("PILIH MATERI");
		System.out.println("1. SPL umum (SPL biasa, Hk. Newton, Rangkaian Listrik)");
		System.out.println("2. Interpolasi");
		System.out.print("Anda pilih: ");

		jenis_materi = input_scan.nextInt();
		System.out.println();
		
		//INPUT JENIS INPUT
		System.out.println("JENIS INPUT:");
		System.out.println("1. File");
		System.out.println("2. Command Line");
		System.out.print("Anda pilih: ");

		jenis_input = input_scan.nextInt();
		System.out.println();

		
		//PILIH METODE PENYELESAIAN
		System.out.println("Metode Penyelesaian :");
		System.out.println("1. Eliminasi Gauss dengan Pivoting"); //[DONE]
		System.out.println("2. Eliminasi Gauss-Jordan dengan Pivoting"); //[DONE]
		System.out.print("Anda pilih : ");

		jenis_metode = input_scan.nextInt();
		System.out.println();
		
		//INPUT JENIS O U T P U T
		System.out.println("PILIH OUTPUT:");
		System.out.println("1. Command Line + File");
		System.out.println("2. Command Line (Only)");
		System.out.print("Anda pilih: ");
		
		jenis_output = input_scan.nextInt();
		//INISIASI NAMA FILE OUTPUT
		if (jenis_output==1){			//TAMPIL KE COMMAND LINE dan TULIS KE FILE
			if (jenis_materi==1){				//Materi = SPL
				tulisKeFile = new PrintWriter("C:/Users/Reaver/Desktop/algeo/ak82/FileOutput_SPL.txt", "UTF-8");
			}
			else{								//Materi = Interpolasi
				tulisKeFile = new PrintWriter("C:/Users/Reaver/Desktop/algeo/ak82/FileOutput_Interpolasi.txt", "UTF-8");
			}
		}

		//INPUT Tampilkan proses eliminasi atau tidak? 1 = ya ; 0 = tidak
		System.out.println();
		System.out.print("Tampilkan proses eliminasi? [1 / 0] : ");

		tampil_proses = input_scan.nextInt();
		
		System.out.println();
		//PROSES SOLVING BERDASARKAN JENIS MATERI
		if (jenis_materi == 1) {
			// SPL umum (SPL biasa, Hk. Newton, Rangkaian Listrik)
			
			String nama_file = "C:/Users/Reaver/Desktop/algeo/ak82/FileInput_SPL.txt";
				//SEKARANG FILNYA TERPISAH KARENA KITA BIKIN SISTIM INPUTNYA BEDA, LEBIH USER FRIENDLY (tuple (x,y) yang dibikin kharis)

            if (jenis_input == 1) {
                //MEMAKAI CLASS ReadFileEksternal -- khusus untuk baca input dari file eksternal
				
				
				//INI BIAR ReadFileEksternal NYA CUMA DIJALANIN KALAU USERNYA MILIH INPUT DARI FILE EKSTERNAL
			    ReadFileEksternal RFE = new ReadFileEksternal(nama_file);
				ReadFileEksternalInterpolasi RFEI = new ReadFileEksternalInterpolasi(nama_file);
				
				output = RFE.buatDuaDimensi();

                for (a=0; a<RFE.banyakBaris(); a++) {
                    for (b=0; b<output[0].length; b++) {

                        //MENGAMBIL 3 DIGIT DI BELAKANG KOMA
                        BigDecimal bd = new BigDecimal(output[a][b]);
                        bd = bd.round(new MathContext(5));

                        output[a][b] = bd.doubleValue();

                    }
                }
				
				
                //MENAMPILKAN INPUT USER KE LAYAR UNTUK KONFIRMASI
                System.out.println();
                System.out.println("Tampilan Input User:");
				if (jenis_output==1) tulisKeFile.println("MATRIKS INPUT:");

                for (a=0; a<RFE.banyakBaris(); a++) {
                    for (b=0; b<output[0].length; b++) {
                        System.out.print(output[a][b]+" ");
						if (jenis_output==1) tulisKeFile.print(output[a][b]+" ");
                    }
                    System.out.println();
					if (jenis_output==1) tulisKeFile.println();
                }
                System.out.println();
				if (jenis_output==1) tulisKeFile.println();

                rowEff = RFE.banyakBaris();
                colEff = output[0].length;
            }

            else {
                //MEMAKAI CLASS ReadComLine -- khusus untuk baca input dari CMD
				System.out.println();
                System.out.print("Banyak baris: ");
                baris_eff = input_scan.nextInt();

                System.out.print("Banyak kolom: ");
                kolom_eff = input_scan.nextInt();

				ReadComLine RCL = new ReadComLine(baris_eff, kolom_eff);

                output = RCL.BacaMatriks();

                for (a=0; a < baris_eff; a++) {
                    for (b=0; b < kolom_eff; b++) {

                        //MENGAMBIL 3 DIGIT DI BELAKANG KOMA
                        BigDecimal bd = new BigDecimal(output[a][b]);
                        bd = bd.round(new MathContext(5));

                        output[a][b] = bd.doubleValue();

                    }
                }

                //MENAMPILKAN INPUT USER KE LAYAR UNTUK KONFIRMASI
                System.out.println();
                System.out.println("Tampilan Input User:");
				if (jenis_output==1) tulisKeFile.println("MATRIKS INPUT:");

                for (a=0; a < baris_eff; a++) {
                    for (b=0; b < kolom_eff; b++) {
                        System.out.print(output[a][b]+" ");
						if (jenis_output==1){
								tulisKeFile.print(output[a][b]+" ");
						}
                    }
                    System.out.println();
					if (jenis_output==1) tulisKeFile.println();
                }
                System.out.println();
				if (jenis_output==1) tulisKeFile.println();

                rowEff = baris_eff;
                colEff = kolom_eff;
            }
			
			
			if (jenis_output==1 && tampil_proses==1){
				tulisKeFile.println();
				tulisKeFile.println("LANGKAH PENYELESAIAN:");
			}
			
			if (jenis_metode == 1) {

				//	MEMAKAI CLASS SolverGauss untuk Eliminasi G A U S S
				//	GAUSS itu membentuk matriks ESELON SAJA

				/* 	MEMBUAT OBJEK BARU BERNAMA 'SG' DARI CLASS SolverGauss
					Parameter yang dilempar yaitu output (hasil return matriks 2D dari user),
					baris dan kolom efektif, dan keterangan ingin menampilkan proses eliminasi atau tidak
				*/
				SolverGauss SG = new SolverGauss(output, rowEff, colEff, tampil_proses, jenis_output);

				/* 	OBJEK 'SG' MEMANGGIL FUNGSI BERNAMA 'HasilGaussPivoting()'
					YANG AKAN ME-RETURN NILAI BERTIPE DOUBLE[][] (ARRAY DOUBLE 2 DIMENSI)

					FUNGSI 'HasilGaussPivoting()' ADA DI BAGIAN PALING BAWAH DI FILE 'SolverGauss.java'
				*/
				ResultGauss = SG.HasilGaussPivoting(); // ---> SEMUA PROSES ELIMINASI MEMBENTUK MATRIKS ESELON ADA DI SINI

				/*	MENAMPILKAN MATRIKS AKHIR HASIL ELIMINASI GAUSS DENGAN TATANCANG PEMOROSAN
					INI BARU HASIL ELIMINASI DOANG BIAR DAPETIN MATRIKS ESELON
					BELUM TERMASUK SOLUSI PERSAMAAN NYA
				*/
				
				
				//OUTPUT PERSAMAAN (COMMAND LINE / dan FILE)
				//IF JENIS_OUTPUTNYA GW HILANGIN YA
				
				System.out.println();
				System.out.println();
				if (jenis_output==1){
					tulisKeFile.println();
					tulisKeFile.println();
				}
				
				if (jenis_metode==1){
					System.out.println("MATRIKS HASIL ELIMINASI GAUSS DENGAN PIVOTING");
					if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS DENGAN PIVOTING");
				}
				else{
					System.out.println("MATRIKS HASIL ELIMINASI GAUSS-JORDAN DENGAN PIVOTING");
					if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS-JORDAN DENGAN PIVOTING");
				}
				
				
				for (a=0; a<rowEff; a++) {
					for (b=0; b<colEff; b++) {
						System.out.print(ResultGauss[a][b]+" "+" "+" ");
						if (jenis_output==1) tulisKeFile.print(ResultGauss[a][b]+" "+" "+" ");
					}
					System.out.println();
					if (jenis_output==1) tulisKeFile.println();
				}
				
			
				System.out.println();
				if (jenis_output==1){
					tulisKeFile.println();
					tulisKeFile.println();
				}
				
				/*	MENGECEK JENIS SOLUSI -- UNIK, BANYAK, TIDAK ADA
					OBJEK 'SG' MEMANGGIL FUNGSI 'CekStatusSolusiAkhir(ResultGauss)'
					DAN ME-RETURN NILAI BERTIPE INTEGER

					PARAMETER NYA ADALAH 'ResultGauss' YANG MERUPAKAN MATRIKS ESELON YANG DIDAPAT
					SEBELUMNYA
				*/
				CekStatusSolusi = SG.CekStatusSolusiAkhir(ResultGauss);

				if (CekStatusSolusi == 1) {
					/* 	SOLUSI UNIK -- MENCARI SOLUSI SPL
						OBJEK 'SG' MEMANGGIL FUNGSI 'SolusiGaussPivotingUnik(ResultGauss)'
						DAN ME-RETURN NILAI BERTIPE DOUBLE[] (ARRAY DOUBLE 1 DIMENSI)

						PARAMETER NYA ADALAH 'ResultGauss' YANG MERUPAKAN MATRIKS ESELON YANG DIDAPAT
						SEBELUMNYA
					*/
					SolusiGaussUnik = SG.SolusiGaussPivotingUnik(ResultGauss);

					/* 	SETELAH SOLUSI DIDAPATKAN, LALU KITA MENAMPILKAN SOLUSI ITU KE LAYAR
						CONTOH : x0 = 2.5
								 x1 = 3.0
								 x2 = 3.5
								 dst...
					*/
					
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.println();
					System.out.println("SOLUSI:");
					if (jenis_output==1) tulisKeFile.println("SOLUSI:");
					
					for (a=0; a<rowEff; a++) {
						System.out.println("x"+a+" "+"="+" "+SolusiGaussUnik[a]);
						if (jenis_output==1) tulisKeFile.println("x"+a+" "+"="+" "+SolusiGaussUnik[a]);
					}
				}

				else if (CekStatusSolusi == 0) {
					//	SOLUSI TIDAK ADA
					
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.println("\nTidak Ada Solusi\n");
					if (jenis_output==1) tulisKeFile.println("\nTidak Ada Solusi\n");
				}

				else {
					//	SOLUSI BANYAK -- MENCARI SOLUSI SPL DALAM PARAMETER

					/* 	OBJEK 'SG' MEMANGGIL FUNGSI 'SolusiGaussPivotingBanyak(ResultGauss)'
						DAN ME-RETURN NILAI BERTIPE STRING[] (ARRAY STRING 1 DIMENSI)

						KENAPA STRING? KARENA HASIL NYA DALAM PARAMETER, JADI AGAK SUSAH
						KALO DIJADIIN DOUBLE

						PARAMETER NYA ADALAH 'ResultGauss' YANG MERUPAKAN MATRIKS ESELON YANG DIDAPAT
						SEBELUMNYA
					*/
					SolusiGaussBanyak = SG.SolusiGaussPivotingBanyak(ResultGauss);

					/* 	SETELAH SOLUSI DIDAPATKAN, LALU KITA MENAMPILKAN SOLUSI ITU KE LAYAR
						CONTOH : x0 = (2.5 - (2 * (3.0 - (A)))) // TIPE STRING LHO
								 x1 = (3.0 - (A)) //TIPE STRING LHO
								 x2 = (A) // TIPE STRING LHO
								 dst...

						(A) ADALAH PARAMETER
						PARAMETER DIMULAI DARI A - Z DENGAN ASUMSI KALAU JUMLAH VARIABEL YANG
						DI BERIKAN TIDAK LEBIH DARI 26
					*/
					
						//OUTPUT KE COMMAND LINE / dan FILE
						System.out.println("SOLUSI:");
						if (jenis_output==1) tulisKeFile.println("SOLUSI:");
						
						for (a=0; a<rowEff; a++) {
							System.out.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
							if (jenis_output==1) tulisKeFile.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
							
							System.out.println();
							if (jenis_output==1) tulisKeFile.println();
						}
						System.out.println();
						if (jenis_output==1) tulisKeFile.println();
				}
			}

			else {

				// M U L A I   D A R I   S I N I   N I H   -   G A U S S   J O R D A N

				//GAUSS - JORDAN itu membentuk matriks ESELON TEREDUKSI (baca dulu pengertian eselon tereduksi kalau belum tahu)
				//BACA JUGA TENTANG TATANCANG PEMOROSAN (PIVOTING)
				//SEARCH AJA DI GOOGLE : GaussJordan with Pivoting ATAU APALAH...

				/*	MEMAKAI CLASS SolverGaussJordan untuk Eliminasi G A U S S - J O R D A N
					BERARTI BUAT FILE BARU NAMANYA 'SolverGaussJordan.java'
				*/

				// I K U T I N   A J A   L A N G K A H - L A N G K A H    G A U S S    DI   ATAS

				// MENURUT GW SIH COPAS AJA SEMUA KODE DI FILE 'SolverGauss.java' ke 'SolverGaussJordan.java'
				// TAPI MODIFIKASI BAGIAN FUNGSI 'double[][] PivotingPart (double[][] MakeMoreRight_value) throws Exception'

				//	MEMAKAI CLASS SolverGauss untuk Eliminasi G A U S S
				//	GAUSS itu membentuk matriks ESELON SAJA

				/* 	MEMBUAT OBJEK BARU BERNAMA 'SG' DARI CLASS SolverGauss
					Parameter yang dilempar yaitu output (hasil return matriks 2D dari user),
					baris dan kolom efektif, dan keterangan ingin menampilkan proses eliminasi atau tidak
				*/
				SolverGaussJordan SG = new SolverGaussJordan(output, rowEff, colEff, tampil_proses, jenis_output);

				/* 	OBJEK 'SG' MEMANGGIL FUNGSI BERNAMA 'HasilGaussPivoting()'
					YANG AKAN ME-RETURN NILAI BERTIPE DOUBLE[][] (ARRAY DOUBLE 2 DIMENSI)

					FUNGSI 'HasilGaussPivoting()' ADA DI BAGIAN PALING BAWAH DI FILE 'SolverGauss.java'
				*/
				ResultGauss = SG.HasilGaussPivoting(); // ---> SEMUA PROSES ELIMINASI MEMBENTUK MATRIKS ESELON ADA DI SINI

				/*	MENAMPILKAN MATRIKS AKHIR HASIL ELIMINASI GAUSS DENGAN TATANCANG PEMOROSAN
					INI BARU HASIL ELIMINASI DOANG BIAR DAPETIN MATRIKS ESELON
					BELUM TERMASUK SOLUSI PERSAMAAN NYA
				*/
				
				//OUTPUT KE COMMAND LINE / dan FILE
				System.out.println();
				if (jenis_output==1) tulisKeFile.println();
				
				if (jenis_metode==1){
					System.out.println("MATRIKS HASIL ELIMINASI GAUSS DENGAN PIVOTING");
					if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS DENGAN PIVOTING");
				}
				else{
					System.out.println("MATRIKS HASIL ELIMINASI GAUSS-JORDAN DENGAN PIVOTING");
					if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS-JORDAN DENGAN PIVOTING");
				}
				
				
				for (a=0; a<rowEff; a++) {
					for (b=0; b<colEff; b++) {
						System.out.print(ResultGauss[a][b]+" "+" "+" ");
						if (jenis_output==1) tulisKeFile.print(ResultGauss[a][b]+" "+" "+" ");
					}
					System.out.println();
					if (jenis_output==1) tulisKeFile.println();
				}
				System.out.println();
				if (jenis_output==1) tulisKeFile.println();
				
				
				/*	MENGECEK JENIS SOLUSI -- UNIK, BANYAK, TIDAK ADA
					OBJEK 'SG' MEMANGGIL FUNGSI 'CekStatusSolusiAkhir(ResultGauss)'
					DAN ME-RETURN NILAI BERTIPE INTEGER

					PARAMETER NYA ADALAH 'ResultGauss' YANG MERUPAKAN MATRIKS ESELON YANG DIDAPAT
					SEBELUMNYA
				*/
				CekStatusSolusi = SG.CekStatusSolusiAkhir(ResultGauss);

				if (CekStatusSolusi == 1) {
					/* 	SOLUSI UNIK -- MENCARI SOLUSI SPL
						OBJEK 'SG' MEMANGGIL FUNGSI 'SolusiGaussPivotingUnik(ResultGauss)'
						DAN ME-RETURN NILAI BERTIPE DOUBLE[] (ARRAY DOUBLE 1 DIMENSI)

						PARAMETER NYA ADALAH 'ResultGauss' YANG MERUPAKAN MATRIKS ESELON YANG DIDAPAT
						SEBELUMNYA
					*/
					SolusiGaussUnik = SG.SolusiGaussPivotingUnik(ResultGauss);

					/* 	SETELAH SOLUSI DIDAPATKAN, LALU KITA MENAMPILKAN SOLUSI ITU KE LAYAR
						CONTOH : x0 = 2.5
								 x1 = 3.0
								 x2 = 3.5
								 dst...
					*/
					
					//OUTPUT KE COMMAND LINE / dan FILE
					for (a=0; a<rowEff; a++) {
						System.out.println("x"+a+" "+"="+" "+SolusiGaussUnik[a]);
						if (jenis_output==1) tulisKeFile.println("x"+a+" "+"="+" "+SolusiGaussUnik[a]);
					}
					
					
				}

				else if (CekStatusSolusi == 0) {
					//	SOLUSI TIDAK ADA
					
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.println("\nTidak Ada Solusi\n");
					if (jenis_output==1) tulisKeFile.println("\nTidak Ada Solusi\n");
					
				}

				else {
					//	SOLUSI BANYAK -- MENCARI SOLUSI SPL DALAM PARAMETER

					/* 	OBJEK 'SG' MEMANGGIL FUNGSI 'SolusiGaussPivotingBanyak(ResultGauss)'
						DAN ME-RETURN NILAI BERTIPE STRING[] (ARRAY STRING 1 DIMENSI)

						KENAPA STRING? KARENA HASIL NYA DALAM PARAMETER, JADI AGAK SUSAH
						KALO DIJADIIN DOUBLE

						PARAMETER NYA ADALAH 'ResultGauss' YANG MERUPAKAN MATRIKS ESELON YANG DIDAPAT
						SEBELUMNYA
					*/
					SolusiGaussBanyak = SG.SolusiGaussPivotingBanyak(ResultGauss);

					/* 	SETELAH SOLUSI DIDAPATKAN, LALU KITA MENAMPILKAN SOLUSI ITU KE LAYAR
						CONTOH : x0 = (2.5 - (2 * (3.0 - (A)))) // TIPE STRING LHO
								 x1 = (3.0 - (A)) //TIPE STRING LHO
								 x2 = (A) // TIPE STRING LHO
								 dst...

						(A) ADALAH PARAMETER
						PARAMETER DIMULAI DARI A - Z DENGAN ASUMSI KALAU JUMLAH VARIABEL YANG
						DI BERIKAN TIDAK LEBIH DARI 26
					*/
					
					//OUTPUT KE COMMAND LINE / dan FILE
					for (a=0; a<rowEff; a++) {
						System.out.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
						if (jenis_output==1) tulisKeFile.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
						
						System.out.println();
						if (jenis_output==1) tulisKeFile.println();
					}
					System.out.println();
					
					

				}





			}

		}

		else {

			//SEKARANG FILNYA TERPISAH KARENA KITA BIKIN SISTIM INPUTNYA BEDA, LEBIH USER FRIENDLY (tuple (x,y) yang dibikin kharis)
            String nama_file = "C:/Users/Reaver/Desktop/algeo/ak82/FileInput_Interpolasi.txt";
				
		
			
			//J E N I S   M A T E R I   I N T E R P O L A S I ----->>> INI SEKARANG
            if (jenis_input == 1) {
                //MEMAKAI CLASS ReadFileEksternalInterpolasi -- khusus untuk baca input dari file eksternal
				
				
				//INI BIAR ReadFileEksternal NYA CUMA DIJALANIN KALAU USERNYA MILIH INPUT DARI FILE EKSTERNAL
			    ReadFileEksternal RFE = new ReadFileEksternal(nama_file);
				ReadFileEksternalInterpolasi RFEI = new ReadFileEksternalInterpolasi(nama_file);
			   
                output = RFEI.buatDuaDimensi();

                for (a=0; a<output[0].length - 1; a++) {
                    for (b=0; b<output[0].length; b++) {

                        //MENGAMBIL 3 DIGIT DI BELAKANG KOMA
                        BigDecimal bd = new BigDecimal(output[a][b]);
                        bd = bd.round(new MathContext(5));

                        output[a][b] = bd.doubleValue();

                    }
                }
				
                //MENAMPILKAN INPUT USER KE LAYAR UNTUK KONFIRMASI
                System.out.println();
                System.out.println("Tampilan Input User:");
				if (jenis_output==1) tulisKeFile.println("MATRIKS INPUT:");
				

                for (a=0; a<output[0].length - 1; a++) {
                    for (b=0; b<output[0].length; b++) {
                        System.out.print(output[a][b]+" ");
						if (jenis_output==1) tulisKeFile.print(output[a][b]+" ");
                    }
                    System.out.println();
					if (jenis_output==1) tulisKeFile.println();
                }
                System.out.println();
				if (jenis_output==1) tulisKeFile.println();

                rowEff = output[0].length - 1;
                colEff = output[0].length;
				
				relatePercobaan = RFEI.sumPercobaan();
				
				banyakPercobaan = relatePercobaan.length;
				
				for (a=1; a<=banyakPercobaan; a++) {
					nilaix[a] = relatePercobaan[a-1];
				}
            }

            else{
                //MEMAKAI CLASS ReadComLineInterpolasi -- khusus untuk baca input dari CMD
                System.out.println();
				System.out.print("Banyak data x dan y: ");
                ndatainterpolasi = input_scan.nextInt();

                baris_eff = ndatainterpolasi;
                kolom_eff = ndatainterpolasi + 1;

                ReadComLineInterpolasi RCL = new ReadComLineInterpolasi(baris_eff, kolom_eff, ndatainterpolasi);

                output = RCL.BacaMatriks();

                for (a=0; a < baris_eff; a++) {
                    for (b=0; b < kolom_eff; b++) {

                        //MENGAMBIL 3 DIGIT DI BELAKANG KOMA
                        BigDecimal bd = new BigDecimal(output[a][b]);
                        bd = bd.round(new MathContext(5));

                        output[a][b] = bd.doubleValue();

                    }
                }

                //MENAMPILKAN INPUT USER KE LAYAR UNTUK KONFIRMASI
                System.out.println();
                System.out.println("Tampilan Input User:");
				if (jenis_output==1) tulisKeFile.println("MATRIKS INPUT:");

                for (a=0; a < baris_eff; a++) {
                    for (b=0; b < kolom_eff; b++) {
                        System.out.print(output[a][b]+" ");
						if (jenis_output==1) tulisKeFile.print(output[a][b]+" ");
                    }
                    System.out.println();
					if (jenis_output==1) tulisKeFile.println();
                }
                System.out.println();
				if (jenis_output==1) tulisKeFile.println();

                rowEff = baris_eff;
                colEff = kolom_eff;

            }


			if (jenis_metode == 1) {

				SolverGauss SG = new SolverGauss(output, rowEff, colEff, tampil_proses, jenis_output);

				ResultGauss = SG.HasilGaussPivoting(); // ---> SEMUA PROSES ELIMINASI MEMBENTUK MATRIKS ESELON ADA DI SINI

				
				//OUTPUT KE COMMAND LINE / dan FILE
				System.out.println();
				System.out.println();
				if (jenis_output==1){
					tulisKeFile.println();
					tulisKeFile.println();
				}
				
				if(jenis_metode==1){
					System.out.println("MATRIKS HASIL ELIMINASI GAUSS DENGAN PIVOTING");
					if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS DENGAN PIVOTING");
				}
				else{
					System.out.println("MATRIKS HASIL ELIMINASI GAUSS-JORDAN DENGAN PIVOTING");
					if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS-JORDAN DENGAN PIVOTING");
				}
				
				
				for (a=0; a<rowEff; a++) {
					for (b=0; b<colEff; b++) {
						System.out.print(ResultGauss[a][b]+" "+" "+" ");
						if (jenis_output==1) tulisKeFile.print(ResultGauss[a][b]+" "+" "+" ");
					}
					System.out.println();
					if (jenis_output==1) tulisKeFile.println();
				}
				
				System.out.println();
				System.out.println();
				if (jenis_output==1){
					tulisKeFile.println();
					tulisKeFile.println();
				}
				
				System.out.println("SOLUSI:");
				if (jenis_output==1) tulisKeFile.println("SOLUSI:");
				
				CekStatusSolusi = SG.CekStatusSolusiAkhir(ResultGauss);

				if (CekStatusSolusi == 1) {

					SolusiGaussUnik = SG.SolusiGaussPivotingUnik(ResultGauss);

					
					//OUTPUT KE COMMAND LINE / dan FILE--- PERSAMAAN INTERPOLASI YANG DIDAPAT 
					System.out.print("f(x) = ");
					for (a=0; a<rowEff; a++) {

						BigDecimal bd = new BigDecimal(SolusiGaussUnik[a]);
						bd = bd.round(new MathContext(5));

						SolusiGaussUnik[a] = bd.doubleValue();

						if(a==0){
							System.out.print(SolusiGaussUnik[a]);
							if (jenis_output==1) tulisKeFile.print(SolusiGaussUnik[a]);
						}
						else{
							if(SolusiGaussUnik[a]>=0){
								System.out.print(" + "+SolusiGaussUnik[a]+"x^"+a);
								if (jenis_output==1) tulisKeFile.print(" + "+SolusiGaussUnik[a]+"x^"+a);
							}
							else {
								System.out.print(" "+SolusiGaussUnik[a]+"x^"+a);
								if (jenis_output==1) tulisKeFile.print(" "+SolusiGaussUnik[a]+"x^"+a);
							}
						}
					}
					System.out.println();
					System.out.println();
					if (jenis_output==1){
						tulisKeFile.println();
						tulisKeFile.println();
					} 
					
					
					//MELAKUKAN PERCOBAAN NILAI X INTERPOLASI
					System.out.print("Banyak percobaan nilai x : ");
					banyakPercobaan = input_scan.nextInt();
					if (jenis_output==1) tulisKeFile.print("BANYAK PERCOBAAN NILAI X : "+banyakPercobaan);
					
					System.out.println();
					
					for (xy = 1; xy <= banyakPercobaan; xy++) {
						
						System.out.print("Masukkan nilai x ke-"+xy+" : ");
						nilaix[xy] = Double.parseDouble(input_scan.next());
						
						nilaifungsi = 0;
						
					
						for (a=0; a<rowEff; a++) {
							nilaifungsi = nilaifungsi + SolusiGaussUnik[a] * Math.pow(nilaix[xy], a);
						}

						BigDecimal bd = new BigDecimal(nilaifungsi);
						bd = bd.round(new MathContext(5));

						nilaifungsi = bd.doubleValue();
						
						System.out.println("f("+nilaix[xy]+") = " + nilaifungsi);
						if (jenis_output==1) tulisKeFile.println("f("+nilaix[xy]+") = " + nilaifungsi);
					}
				}

				else if (CekStatusSolusi == 0) {
				//	SOLUSI TIDAK ADA
			
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.println("\nTidak Ada Solusi\n");
					if (jenis_output==1) tulisKeFile.println("\nTidak Ada Solusi\n");
				}

				else {
					SolusiGaussBanyak = SG.SolusiGaussPivotingBanyak(ResultGauss);

			
				//OUTPUT KE COMMAND LINE / dan FILE
				for (a=0; a<rowEff; a++) {
					System.out.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
					if (jenis_output==1) tulisKeFile.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
					System.out.println();
					if (jenis_output==1) tulisKeFile.println();
				}
				System.out.println();
				if (jenis_output==1) tulisKeFile.println();
				}

			}

			else {

				SolverGaussJordan SG = new SolverGaussJordan(output, rowEff, colEff, tampil_proses, jenis_output);

				ResultGauss = SG.HasilGaussPivoting(); // ---> SEMUA PROSES ELIMINASI MEMBENTUK MATRIKS ESELON ADA DI SINI

				
				//OUTPUT KE COMMAND LINE / dan FILE

				System.out.println();
				System.out.println();
				if (jenis_output==1){
					tulisKeFile.println();
					tulisKeFile.println();
				}
				
				System.out.println("MATRIKS HASIL ELIMINASI GAUSS JORDAN - PIVOTING");
				if (jenis_output==1) tulisKeFile.println("MATRIKS HASIL ELIMINASI GAUSS JORDAN - PIVOTING");
				
				for (a=0; a<rowEff; a++) {
					for (b=0; b<colEff; b++) {
						System.out.print(ResultGauss[a][b]+" "+" "+" ");
						if (jenis_output==1) tulisKeFile.print(ResultGauss[a][b]+" "+" "+" ");
					}
					System.out.println();
					if (jenis_output==1) tulisKeFile.println();
				}
				System.out.println();
				if (jenis_output==1) tulisKeFile.println();

				

				CekStatusSolusi = SG.CekStatusSolusiAkhir(ResultGauss);

				if (CekStatusSolusi == 1) {

					SolusiGaussUnik = SG.SolusiGaussPivotingUnik(ResultGauss);

					
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.print("f(x) = ");
					if (jenis_output==1) tulisKeFile.print("f(x) = ");
					for (a=0; a<rowEff; a++) {

						BigDecimal bd = new BigDecimal(SolusiGaussUnik[a]);
						bd = bd.round(new MathContext(5));

						SolusiGaussUnik[a] = bd.doubleValue();

						if(a==0){
							System.out.print(SolusiGaussUnik[a]);
							if (jenis_output==1) tulisKeFile.print(SolusiGaussUnik[a]);
						}
						else{
							if(SolusiGaussUnik[a]>=0){
								System.out.print(" + "+SolusiGaussUnik[a]+"x^"+a);
								if (jenis_output==1) tulisKeFile.print(" + "+SolusiGaussUnik[a]+"x^"+a);
							}
							else {
								System.out.print(" "+SolusiGaussUnik[a]+"x^"+a);
								if (jenis_output==1) tulisKeFile.print(" "+SolusiGaussUnik[a]+"x^"+a);
							}
						}
					}
					
					System.out.println("\n");
					if (jenis_output==1){
						tulisKeFile.println("");
						tulisKeFile.println("");
					}
					
					
					
					System.out.print("Banyak percobaan nilai x : ");
					banyakPercobaan = input_scan.nextInt();
					if (jenis_output==1) tulisKeFile.println("BANYAK PERCOBAAN NILAI X : "+banyakPercobaan);
					System.out.println();
					
					for (xy = 1; xy <= banyakPercobaan; xy++) {
						
						if (jenis_input == 2) {
							System.out.print("Masukkan nilai x ke-"+xy+" : ");
							nilaix[xy] = Double.parseDouble(input_scan.next());
						}
						
						nilaifungsi = 0;
						
						for (a=0; a<rowEff; a++) {
							nilaifungsi = nilaifungsi + SolusiGaussUnik[a] * Math.pow(nilaix[xy], a);
						}
						
						BigDecimal bd = new BigDecimal(nilaifungsi);
						bd = bd.round(new MathContext(5));

						nilaifungsi = bd.doubleValue();
						
						System.out.println("f("+nilaix[xy]+") = " + nilaifungsi);
						if (jenis_output==1) tulisKeFile.println("f("+nilaix[xy]+") = " + nilaifungsi);
					}
						
					
				}

				else if (CekStatusSolusi == 0) {
				//	SOLUSI TIDAK ADA
				
					//OUTPUT KE COMMAND LINE / dan FILE
					System.out.println("\nTidak Ada Solusi\n");
					if (jenis_output==1) tulisKeFile.println("\nTidak Ada Solusi\n");
				}

				else {

					SolusiGaussBanyak = SG.SolusiGaussPivotingBanyak(ResultGauss);
					
					//OUTPUT KE COMMAND LINE
					for (a=0; a<rowEff; a++) {
						System.out.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
						if (jenis_output==1) tulisKeFile.print("x"+a+" "+"="+" "+SolusiGaussBanyak[a]+" ");
						
						System.out.println();
						if (jenis_output==1) tulisKeFile.println();
					}
					System.out.println();
					if (jenis_output==1) tulisKeFile.println();

				}
			}
				
		}
		if (jenis_output==1) tulisKeFile.close();

    }	

}

