package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import cellularautomata.CellularAutomaton;

public class Main {
	
	//CSV header
	private static final String NUMBER_OF_THREADS = "size,0,1,2,3,4,5";
	private static final int STEPS = 1000;
	private static BufferedWriter quotResults;
	private static BufferedWriter modResults;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		//unique filename
		long resFilename = System.currentTimeMillis();
		
		modResults = new BufferedWriter(new FileWriter("modResults"+resFilename+".csv"));
		quotResults = new BufferedWriter(new FileWriter("quotResults"+resFilename+".csv"));
		
		modResults.write(NUMBER_OF_THREADS+"\n");
		quotResults.write(NUMBER_OF_THREADS+"\n");
		
		Random random = new Random();
		
		for(int m = 5; m <= 24; m++) {
			
			//imprimir primera columna de la fila
			modResults.write(Integer.toString(m));
			quotResults.write(Integer.toString(m));
			
			
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
			
			for(int k = 0; k <=5; k++) {
				long resMod = cellAut.runSimulation(STEPS, (int)Math.pow(2, k), CellularAutomaton.MODULO_PARTITION);
				long resQuot = cellAut.runSimulation(STEPS, (int)Math.pow(2, k), CellularAutomaton.QUOTIENT_PARTITION);
				modResults.write(","+String.valueOf(resMod));
				quotResults.write(","+Long.toString(resQuot));
			}
			modResults.newLine();
			quotResults.newLine();
		}
		
		modResults.close();
		quotResults.close();
		
	}
}
