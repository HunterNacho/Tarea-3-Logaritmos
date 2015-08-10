package cellularautomata;

import java.util.ArrayList;
import java.util.Arrays;

public class CellularAutomaton {
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
				computeNextState(hash[i][0], hash[i][1], hash[i][2]);
			}
		}
		
	}
	
	public CellularAutomaton(boolean[][][] matrix) {
		size = matrix.length;
		oldStates = matrix;
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
					if (oldStates[(x + i) % size][(y + j) % size][(z + k) % size])
						aliveNeighbors++;
				}
			}
		}
		if (!oldStates[x][y][z])
			newStates[x][y][z] = n1 <= aliveNeighbors && aliveNeighbors < n2;
		else
			newStates[x][y][z] = n3 <= aliveNeighbors && aliveNeighbors < n4;
	}
	
	public long runSimulation(int steps, int threads) throws InterruptedException {
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		long startTime = System.currentTimeMillis();
		for (int step = 0; step < steps; step++) {
			for (int j = 0; j < threads; j++) {
				int lowerBound, upperBound;
				lowerBound = j * size * size * size / threads;
				upperBound = (j + 1) * size * size * size / threads;
				Thread newThread = new PartitionThread(Arrays.copyOfRange(hash, lowerBound, upperBound - 1));
				threadList.add(newThread);
				newThread.run();
			}
			for (int j = 0; j < threads; j++) {
				Thread thread = threadList.remove(0);
				thread.join();
			}
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
}
