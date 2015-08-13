package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

import cellularautomata.CellularAutomaton;

public class Main {
	
	//CSV header
	private static final String NUMBER_OF_THREADS = "size,1,2,3,4,5";
	private static final int STEPS = 10;
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		
		//unique filename
		long resFilename = System.currentTimeMillis();
		
		PrintStream out = new PrintStream(new FileOutputStream("/home/ekauffma/Documents/2015-1/logaritmos/Tarea-3-Logaritmos/results"+resFilename+".csv"));
		System.setOut(out);
		
		System.out.println(NUMBER_OF_THREADS);
		
		Random random = new Random();
		
		for(int m = 5; m <= 24; m++) {
			
			//imprimir primera columna de la fila
			System.out.print(m);
			
			//generar matriz de tamaño m con valores aleatorios
			boolean matrix[][][] = new boolean[m][m][m];
			
			for(int i = 0; i < m; i++){
				for(int j = 0; j < m; j++) {
					for (int k = 0; k < m; k++) {
						matrix[i][j][k] = random.nextBoolean();
					}
				}
			}
			
			CellularAutomaton cellAut = new CellularAutomaton(matrix);
			
			for(int k = 1; k <=5; k++) {
				long res = cellAut.runSimulation(STEPS, k);
				System.out.print(","+res);
			}
			System.out.println();
		}
		
		

	

		
	}
}
