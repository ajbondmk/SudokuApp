import javax.swing.*;
import javax.swing.event.*;

class ConditionableListSelectionListener implements ListSelectionListener {
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		ListModel lm = list.getModel();
		for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
			if (list.getSelectionModel().isSelectedIndex(i)) {
				if (!((ConditionableItem) lm.getElementAt(i)).isEnabled()) {
					list.removeSelectionInterval(i, i);
				}
			}
		}
	}
}

class ConditionableItem {

	private String value;
	private boolean isEnabled;
	private boolean wasOriginal;

	public boolean isEnabled() { return isEnabled; }
	public boolean wasOriginal() { return wasOriginal; }
	public void disable() { isEnabled = false; }
	public void setValue(String str) { value = str; }
	@Override
	public String toString() { return value; }

	public ConditionableItem(String str) {
		this.value = str;
		if (str == "") {
			wasOriginal = false;
			isEnabled = true;
		}
		else {
			wasOriginal = true;
			isEnabled = false;
		}
	}

}