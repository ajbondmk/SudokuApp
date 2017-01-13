import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class GridPanelList extends JPanel {

	private Grid mOriginalGrid;
	private Grid mSolvedGrid;
	private JList mList;
	private boolean mSolved;
	public boolean isSolved() { return mSolved; }
	private boolean mHints = false;
	private boolean mHintsForced = false;
	private Random random = new Random();

	public GridPanelList() {
		mOriginalGrid = new Grid();
		mList = new JList();
		mList.addListSelectionListener(new ConditionableListSelectionListener());
		newPuzzle(false);
		mList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		mList.setVisibleRowCount(9);
		add(mList);
	}

	public void display() {
		repaint();
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) { newCellRenderer(); }

	public void newPuzzle(boolean hardMode) {
		mOriginalGrid = new Grid();
		int rand;
		String fileName;
		if (hardMode) {
			fileName = "src/Puzzles/HardPuzzles.txt";
			rand = random.nextInt(2365);
		}
		else {
			fileName = "src/Puzzles/EasyPuzzles.txt";
			rand = random.nextInt(1011);
		}
		try {
			BufferedReader b = new BufferedReader(new FileReader(fileName));
			String line = b.readLine();
			for (int i = 0; i < rand; i++) {
				line = b.readLine();
			}
			mOriginalGrid.populate(line);
			Solver mSolver = new Solver(line);
			mSolver.solve();
			mSolvedGrid = mSolver.getGrid();
			mSolved = false;
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("Invalid file.");
		} catch (FailedSolutionException e) {
			System.out.println("Puzzle unsolveable.");
		}
		resetGrid();
	}

	public void newPuzzleTest() {
		mOriginalGrid = new Grid();
		String puzzle = "16593247872948165343857692164189723529361578458732416985426931731674859297215384.";
		mOriginalGrid.populate(puzzle);
		Solver mSolver = new Solver(puzzle);
		try {
			mSolver.solve();
		}
		catch (FailedSolutionException e) {
			System.out.println("WTF");
		}
		mSolvedGrid = mSolver.getGrid();
		mSolved = false;
		resetGrid();
	}

	public void resetGrid() {
		DefaultListModel<ConditionableItem> listModel = new DefaultListModel();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell thisCell = mOriginalGrid.getCell(i,j);
				if (thisCell.isSolved()) {
					listModel.addElement(new ConditionableItem(thisCell.get(0).toString()));
				}
				else listModel.addElement(new ConditionableItem(""));
			}
		}
		mList.setModel(listModel);
		mSolved = false;
	}

	public void solve() {
		mList.clearSelection();
		mSolved = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				ConditionableItem item = ((DefaultListModel<ConditionableItem>)mList.getModel()).get(i * 9 + j);
				item.setValue(mSolvedGrid.getCell(i,j).get(0).toString());
				item.disable();
			}
		}
		newCellRenderer();
	}

	private void newCellRenderer() {
		MyCellRenderer cellRenderer = new MyCellRenderer(mSolvedGrid, mHints || mHintsForced);
		cellRenderer.resize(Math.min((this.getWidth() - 20) / 9, (this.getHeight() - 45) / 9));
		mList.setCellRenderer(cellRenderer);
	}

	public void assignNumber(String number) {
		int index = mList.getSelectedIndex();
		if (index != -1) {
			ConditionableItem item = ((DefaultListModel<ConditionableItem>)mList.getModel()).get(index);
			if (item.isEnabled()) {
				item.setValue(number);
				if (mHintsForced) mHintsForced = !mHintsForced;
				if (userSolutionComplete()) {
					if (!userSolutionCorrect() && !mHints) mHintsForced = true;
					else mSolved = true;
				}
				newCellRenderer();
			}
		}
	}

	private boolean userSolutionComplete() {
		DefaultListModel<ConditionableItem> listModel = (DefaultListModel<ConditionableItem>)mList.getModel();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (listModel.getElementAt(i*9 + j).toString().equals("")) return false;
			}
		}
		return true;
	}

	private boolean userSolutionCorrect() {
		DefaultListModel<ConditionableItem> listModel = (DefaultListModel<ConditionableItem>)mList.getModel();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!listModel.getElementAt(i*9 + j).toString().equals(mSolvedGrid.getCell(i,j).get(0))) return false;
			}
		}
		return true;
	}

	public void toggleHints() {
		mHints = !mHints;
		newCellRenderer();
	}

}