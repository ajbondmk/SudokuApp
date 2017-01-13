import java.util.List;
import java.util.ArrayList;

public class Solver {

	private Grid grid = new Grid();
	private List<Cell> queue = new ArrayList<>(9);
	public int guessCount = 0;

	public Solver (String initial) {
		queue = grid.populate(initial);
	}

	public Solver (Grid initial, Cell guess) {
		grid = initial;
		queue.add(guess);
	}

	public Grid solve() throws FailedSolutionException {
		while (queue.size() > 0) {
			Cell peerToCheck = queue.get(0);
			if (peerToCheck.isSolved()) {
				char solution = peerToCheck.get(0);
				for (Cell peer : grid.findPeers(peerToCheck)) {
					if (peer.contains(solution)) {
						if (peer.isSolved()) throw new FailedSolutionException();
						peer.remove(solution);
						if (!peer.isInList(queue) && peer.isSolved()) {
							queue.add(peer);
						}
					}
				}
			}
			queue.remove(0);
		}
		guess();
		if (!isCorrect()) throw new FailedSolutionException();
		//System.out.println("Solution found");
		//System.out.println();
		return grid;
	}

	private void guess() throws FailedSolutionException {
		int minRow = 0;
		int minCol = 0;
		int minSize = 10;
		Cell max = new Cell(true);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell square = grid.getCell(i, j);
				int squareSize = square.size();
				if (!square.isSolved() && squareSize < minSize) {
					minRow = i;
					minCol = j;
					minSize = grid.getCell(i, j).size();
				}
				if (squareSize > max.size()) max = square;
			}
		}
		if (!max.isSolved()) {
			int guessesRemaining = grid.getCell(minRow, minCol).size() - 1;
			while (guessesRemaining >= 0) {
				Grid gridGuess = grid.copyGrid();
				Cell guessSquare = gridGuess.getCell(minRow, minCol);
				guessSquare.clear();
				guessSquare.add(grid.getCell(minRow, minCol).get(guessesRemaining));
				Solver guess = new Solver(gridGuess, gridGuess.getCell(minRow, minCol));
				try {
					grid = guess.solve();
					guessCount += 1 + guess.guessCount;
					return;
				} catch (FailedSolutionException e) {
					guessesRemaining--;
				}
			}
			throw new FailedSolutionException();
		}
	}

	private boolean isCorrect() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell square = grid.getCell(i, j);
				if (square.isSolved()) {
					for (Cell peer : grid.findPeers(square)) {
						if (peer.isSolved() && peer.get(0) == square.get(0)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public Grid getGrid() { return grid; }

	public void print() {
		grid.print();
	}

}