package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePicker;

public class Act_lich implements ActionListener {
	JTextField t;
	DatePicker dat;
	App ap;
	JButton b1, b2, b3, b4;

	public Act_lich(JTextField a, DatePicker b, App c, JButton e, JButton f, JButton g, JButton d) {
		t = a;
		dat = b;
		ap = c;
		b1 = e;
		b2 = f;
		b3 = g;
		b4 = d;
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String[] col = { "chon", "name", "note", "gio", "ngay" };
		String[][] d = null;
		try {
			if (e.getSource() == b1) {
				String p = dat.getDateStringOrSuppliedString("null");
				d = ap.srmi.viewonday(p);
			} else if (e.getSource() == b2) {
				int i = Integer.parseInt(t.getText());
				d = ap.srmi.dayofgetWeek(i);
			} else if (e.getSource() == b3) {
				d = ap.srmi.getAll();
			} else if (e.getSource() == b4) {
				d = ap.srmi.viewonday(new Date(System.currentTimeMillis()).toString());
			}
			if (d != null) {
				DefaultTableModel model = new DefaultTableModel(convert(d), col) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 3333L;

					@Override
					public Class<?> getColumnClass(int col) {
						return col == 0 ? Boolean.class : String.class;
					}
				};
				ap.xemlich.setVisible(false);
				JTable tb = new JTable(model) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int roe, int col) {

						return col == 0;
					}
				};
				ap.showView(tb);
				ap.status = "lich";
			} else {
				System.out.println("li null");
			}
		} catch (Exception e2) {
			System.out.println("loi Act_lich");

		}

	}

	public static Object[][] convert(String[][] s) {
		int a = s.length;
		int b = s[0].length;
		Object[][] ret = new Object[a][b + 1];
		for (int i = 0; i < a; i++) {
			ret[i][0] = false;
			for (int j = 1; j < b + 1; j++) {
				ret[i][j] = s[i][j - 1];
			}

		}
		return ret;
	}

}
