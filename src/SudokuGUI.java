import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SudokuGUI extends JFrame {

	private GridPanelList mGridPanel;
	private Font buttonFont = new Font("Calibri", 1, 20);

	public static void main(String[] args) {
		SudokuGUI gui = new SudokuGUI();
		gui.setVisible(true);
	}

	public SudokuGUI() {
		super("Sudoku");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(905,600);
		add(createSudokuPanel(), BorderLayout.CENTER);
		add(createNumbersPanel(), BorderLayout.WEST);
		add(createControlsPanel(), BorderLayout.EAST);
		mGridPanel.display();
	}

	private JPanel createSudokuPanel() {
		mGridPanel = new GridPanelList();
		addBorder(mGridPanel, "Current game");
		return mGridPanel;
	}

	private JPanel createNumbersPanel() {
		JPanel numbers = new JPanel();
		addBorder(numbers, "Number selection");
		GridLayout layout = new GridLayout(5, 2);
		layout.setVgap(10);
		layout.setHgap(10);
		numbers.setLayout(layout);
		for (int num = 1; num <= 9; num++) {
			String numString = Integer.toString(num);
			JButton btnNum = new JButton(numString);
			btnNum.setFont(buttonFont);
			btnNum.addActionListener(e -> mGridPanel.assignNumber(numString));
			numbers.add(btnNum);
		}
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(buttonFont);
		btnClear.addActionListener(e -> mGridPanel.assignNumber(""));
		numbers.add(btnClear);
		return numbers;
	}

	private JPanel createControlsPanel() {
		JPanel controls = new JPanel();
		addBorder(controls, "Controls");
		GridLayout layout = new GridLayout(5, 1);
		layout.setVgap(10);
		layout.setHgap(10);
		controls.setLayout(layout);
		JButton btnSolve = new JButton("Solve puzzle");
		btnSolve.setFont(buttonFont);
		JButton btnHints = new JButton("Toggle hints");
		btnHints.setFont(buttonFont);
		JButton btnRestart = new JButton("Restart game");
		btnRestart.setFont(buttonFont);
		JButton btnNewEasy = new JButton("New easy game");
		btnNewEasy.setFont(buttonFont);
		JButton btnNewHard = new JButton("New hard game");
		btnNewHard.setFont(buttonFont);
		btnSolve.addActionListener(e -> { if (!mGridPanel.isSolved()) mGridPanel.solve(); });
		btnHints.addActionListener(e -> mGridPanel.toggleHints() );
		btnRestart.addActionListener(e -> mGridPanel.resetGrid() );
		btnNewEasy.addActionListener(e -> mGridPanel.newPuzzle(false) );
		btnNewHard.addActionListener(e -> mGridPanel.newPuzzle(true) );
		controls.add(btnSolve);
		controls.add(btnHints);
		controls.add(btnRestart);
		controls.add(btnNewEasy);
		controls.add(btnNewHard);
		return controls;
	}

	private void addBorder (JPanel panel, String title) {
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title);
		border.setTitleFont(buttonFont);
		Border margin = new EmptyBorder(5,5,5,5);
		panel.setBorder(new CompoundBorder(border, margin));
	}

}