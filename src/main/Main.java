package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import cellularautomata.CellularAutomaton;

public class Main {
	
	//CSV header
	private static final String NUMBER_OF_THREADS = "size,0,1,2,3,4,5";
	private static final int STEPS = 10;
	private static BufferedWriter quotResults;
	private static BufferedWriter modResults;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		//unique filename
		long resFilename = System.currentTimeMillis();
		
		modResults = new BufferedWriter(new PrintWriter("modResults"+resFilename+".csv", "UTF-8"));
		quotResults = new BufferedWriter(new PrintWriter("quotResults"+resFilename+".csv", "UTF-8"));
		
		modResults.write(NUMBER_OF_THREADS+"\n");
		quotResults.write(NUMBER_OF_THREADS+"\n");
		
		Random random = new Random();
		
		for(int m = 5; m <= 24; m++) {
			
			//imprimir primera columna de la fila
			modResults.write(m);
			quotResults.write(m);
			
			
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
				//VER SI HAY QUE RESETEAR LA MATRIZ A COMO ESTABA ANTES
				long resMod = cellAut.runSimulation(STEPS, (int)Math.pow(2, k), CellularAutomaton.MODULO_PARTITION);
				long resQuot = cellAut.runSimulation(STEPS, (int)Math.pow(2, k), CellularAutomaton.QUOTIENT_PARTITION);
				modResults.write(","+resMod);
				quotResults.write(","+resQuot);
			}
			modResults.newLine();
			quotResults.newLine();
		}
		
		

	

		
	}
}
