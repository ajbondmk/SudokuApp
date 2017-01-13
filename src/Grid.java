import java.util.ArrayList;
import java.util.List;

public class Grid {

	private List<List<Cell>> grid = new ArrayList<>(0);
	private List<List<Cell>> units = new ArrayList<>(9);

	public Grid(boolean emptyGrid) {}
	public Grid() {
		for (int row = 0; row < 9; row++) {
			List<Cell> cols = new ArrayList<>(0);
			for (int col = 0; col < 9; col++) {
				cols.add(new Cell());
			}
			addCols(cols);
		}
		initialiseUnits();
	}

	public Grid copyGrid() {
		Grid gridCopy = new Grid(true);
		for (int row = 0; row < 9; row++) {
			List<Cell> cols = new ArrayList<>(0);
			for (int col = 0; col < 9; col++) {
				cols.add(getCell(row, col).copyCell());
			}
			gridCopy.addCols(cols);
		}
		gridCopy.initialiseUnits();
		return gridCopy;
	}

	public void addCols(List<Cell> cols) {
		grid.add(cols);
	}

	public Cell getCell(int row, int col) {
		return grid.get(row).get(col);
	}

	private void initialiseUnits() {
		for (int i = 0; i < 9; i++) {
			List<Cell> colUnit = new ArrayList<>(9);
			List<Cell> rowUnit = new ArrayList<>(9);
			for (int j = 0; j < 9; j++) {
				colUnit.add(getCell(i, j));
				rowUnit.add(getCell(j, i));
			}
			units.add(colUnit);
			units.add(rowUnit);
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				List<Cell> unit = new ArrayList<>(9);
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						unit.add(getCell(3*i + k, 3*j + l));
					}
				}
				units.add(unit);
			}
		}
	}

	public List<Cell> populate(String initial) {
		char[] nums = initial.toCharArray();
		List<Cell> initialQueue = new ArrayList<>(0);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				char num = nums[9 * i + j];
				if (num != '0' && num != '.') {
					Cell square = getCell(i, j);
					square.assign(num);
					initialQueue.add(square);
				}
			}
		}
		return initialQueue;
	}

	public List<Cell> findPeers(Cell square) {
		List<Cell> peers = new ArrayList<>();
		for (List<Cell> unit : units) {
			if (square.isInList(unit)) {
				for (Cell peer : unit) {
					if (!peer.isInList(peers) && peer != square) peers.add(peer);
				}
			}
		}
		return peers;
	}

	public void print() {
		for (List<Cell> row : grid) {
			for (Cell square : row) {
				if (!square.isSolved()) System.out.print(".");
				else System.out.print(square.get(0));
			}
			System.out.println();
		}
		System.out.println();
	}

}