import java.util.ArrayList;
import java.util.List;

public class Cell extends ArrayList<Character> {

	public Cell(boolean emptyCell) { }
	public Cell() {
		for (char num = '1'; num <= '9'; num++) {
			add(num);
		}
	}

	public void assign(char num) {
		clear();
		add(num);
	}

	public Cell copyCell() {
		Cell cellCopy = new Cell(true);
		int count = size();
		for (int k = 0; k < count; k++) {
			cellCopy.add(get(k));
		}
		return cellCopy;
	}

	public void remove(char num) {
		remove(indexOf(num));
	}

	public boolean isInList(List<Cell> list) {
		for (Cell square : list) {
			if (square == this) return true;
		}
		return false;
	}

	public boolean isSolved() {
		return (size() == 1);
	}

}