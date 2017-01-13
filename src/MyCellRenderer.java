import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {

	private int mSize;
	private Grid mSolvedGrid;
	private boolean mHints;

	public MyCellRenderer(Grid solvedGrid, boolean hints) {
		setOpaque(true);
		mSolvedGrid = solvedGrid;
		mHints = hints;
	}

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		setPreferredSize(new Dimension(mSize, mSize));
		if (((ConditionableItem)list.getModel().getElementAt(index)).wasOriginal()) setBackground(new Color(220,220,220));
		else if (mHints && !value.toString().equals("") && !value.toString().equals(mSolvedGrid.getCell(index / 9, index % 9).get(0).toString())) setBackground(Color.RED);
		else setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setFont(new Font("Calibri", 1, mSize * 2 / 3));
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		addBorder(index, isSelected);
		return this;
	}

	public void resize(int size) {
		mSize = size;
	}

	@SuppressWarnings("Duplicates")
	private void addBorder(int index, boolean isSelected) {
		int top = 1;
		int left = 1;
		int bottom = 1;
		int right = 1;
		switch (index % 9) {
			case 0: left = 6; break;
			case 2: right = 3; break;
			case 3: left = 3; break;
			case 5: right = 3; break;
			case 6: left = 3; break;
			case 8: right = 6; break;
		}
		switch (index / 9) {
			case 0: top = 6; break;
			case 2: bottom = 3; break;
			case 3: top = 3; break;
			case 5: bottom = 3; break;
			case 6: top = 3; break;
			case 8: bottom = 6; break;
		}
		Border border = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
		if (isSelected) {
			Border highlight = new MatteBorder(5, 5, 5, 5, Color.BLUE);
			setBorder(new CompoundBorder(border, highlight));
		}
		else setBorder(border);
	}

}