package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Act_view implements ActionListener {

	App ap;

	public Act_view(App s) {
		ap = s;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			JTable tb = ap.tb;
			int row = tb.getRowCount();
			ArrayList<Integer> ip = new ArrayList<Integer>();
			for (int i = row - 1; i > -1; i--) {
				if ((boolean) tb.getValueAt(i, 0))
					ip.add(i);
			}
			row = ip.size();
			if (ap.status == "lich") {
				System.out.println("sssad");
				String[][] cl = new String[row][3];
				for (int i = 0; i < row; i++) {
					int k = ip.get(i);
					cl[i][0] = (String) tb.getValueAt(k, 1);
					cl[i][1] = (String) tb.getValueAt(k, 3);
					cl[i][2] = (String) tb.getValueAt(k, 4);
				}
				ap.srmi.clearList(cl);
				DefaultTableModel model = (DefaultTableModel) tb.getModel();
				for (int i : ip) {
					model.removeRow(i);
				}
				JTable ts = new JTable(model) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int roe, int col) {

						return col == 0;
					}
				};
				ap.showView(ts);
			} else if (ap.status == "email") {
				// String[] col = { "chon", "nguoi gui", "nguoi nhan", "tieu de", "mess",
				// "ngay", "gio" };
				String[][] cl = new String[row][4];
				for (int i = 0; i < row; i++) {
					int k = ip.get(i);
					cl[i][0] = (String) tb.getValueAt(k, 2);
					cl[i][1] = (String) tb.getValueAt(k, 3);
					cl[i][2] = (String) tb.getValueAt(k, 5);
					cl[i][3] = (String) tb.getValueAt(k, 6);
				}
				ap.srmi.rmEmail(cl);
				DefaultTableModel model = (DefaultTableModel) tb.getModel();
				for (int i : ip) {
					model.removeRow(i);
				}
				JTable ts = new JTable(model) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int roe, int col) {

						return col == 0;
					}
				};
				ap.showView(ts);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Object[][] convert(String[][] s) {
		int a = s.length;
		int b = s[0].length;
		Object[][] ret = new Object[a][b + 1];
		for (int i = 0; i < a; i++) {
			ret[i][0] = false;
			for (int j = 1; j < b + 1; j++) {
				ret[i][j] = s[i][j];
			}

		}
		return ret;
	}
}
