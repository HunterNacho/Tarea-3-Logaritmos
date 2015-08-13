package cellularautomata;

import java.util.ArrayList;
import java.util.Arrays;

public class CellularAutomaton {
	public static int QUOTIENT_PARTITION = 1;
	public static int MODULO_PARTITION = 2;
	private boolean[][][] initialStates;
	private boolean[][][] oldStates;
	private boolean[][][] newStates;
	private int[][] hash;
	private int size;
	private final int n1 = 6, n2 = 8, n3 = 6, n4 = 11;
	
	private class PartitionThread extends Thread {
		private int[][] indexes;

		public PartitionThread(int[][] indexes) {
			this.indexes = indexes;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < indexes.length; i++) {
				if (indexes[i] != null)
					computeNextState(indexes[i][0], indexes[i][1], indexes[i][2]);
			}
		}
		
	}
	
	public CellularAutomaton(boolean[][][] matrix) {
		size = matrix.length;
		initialStates = matrix;
		hash = new int[size*size*size][3];
		int index = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					hash[index][0] = i;
					hash[index][1] = j;
					hash[index][2] = k;
					index++;
				}
			}
		}
		newStates = new boolean[size][size][size];
	}
	
	public void computeNextState(int x, int y, int z) {
		int aliveNeighbors = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					if (i == j && j == k && k == 0)
						continue;
					if (oldStates[(x + i + size) % size][(y + j + size) % size][(z + k + size) % size])
						aliveNeighbors++;
				}
			}
		}
		if (!oldStates[x][y][z])
			newStates[x][y][z] = n1 <= aliveNeighbors && aliveNeighbors < n2;
		else
			newStates[x][y][z] = n3 <= aliveNeighbors && aliveNeighbors < n4;
	}
	
	public long runSimulation(int steps, int threads, int partitionType) throws InterruptedException {
		oldStates = initialStates.clone();
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		int[][][] partitions = new int[threads][(int) Math.ceil(Math.pow(size, 3) / threads)][];
		if (partitionType == QUOTIENT_PARTITION) {
			for (int j = 0; j < threads; j++) {
				int lowerBound, upperBound;
				lowerBound = j * size * size * size / threads;
				upperBound = (j + 1) * size * size * size / threads;
				if (j == threads - 1)
					upperBound = hash.length;
				partitions[j] = Arrays.copyOfRange(hash, lowerBound, upperBound - 1);
			}
		}
		else if (partitionType == MODULO_PARTITION) {
			for (int i = 0; i * threads < hash.length; i++) {
				for (int j = 0; j < threads; j++) {
					if (i * j >= hash.length)
						break;
					partitions[j][i] = hash[i * j];
				}
					
			}
		}
		long startTime = System.currentTimeMillis();
		for (int step = 0; step < steps; step++) {
			for (int j = 0; j < threads; j++) {
				Thread newThread;
				newThread = new PartitionThread(partitions[j]);
				threadList.add(newThread);
				newThread.start();
			}
			while (!threadList.isEmpty()) {
				Thread thread = threadList.remove(0);
				thread.join();
			}
			oldStates = newStates.clone();
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
}
