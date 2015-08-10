package cellularautomata;

public class CellularAutomaton {
	private boolean[][][] oldStates;
	private boolean[][][] newStates;
	private int size;
	private int n1, n2, n3, n4;
	
	public CellularAutomaton(int size) {
		this.size = size;
		oldStates = new boolean[size][size][size];
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
	
}
