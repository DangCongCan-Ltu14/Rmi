package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Act_xemmail implements ActionListener {
	JButton nhan, gui, all;
	JTextField tf;
	App ap;

	public Act_xemmail(JButton n, JButton g, JButton all, JTextField t, App a) {
		nhan = n;
		gui = g;
		this.all = all;
		tf = t;
		ap = a;
		nhan.addActionListener(this);
		gui.addActionListener(this);
		this.all.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		try {

			String[][] ret = null;
			String d = tf.getText();
			if (e.getSource() == nhan) {
				ret = ap.srmi.getmail(d);

			} else if (e.getSource() == gui) {

				ret = ap.srmi.nhanmail(d);
			} else {
				System.out.println("ssssss");
				ret = ap.srmi.allmail();
			}
			if (ret != null) {
				String[] col = { "chon", "nguoi gui", "nguoi nhan", "tieu de", "mess", "ngay", "gio" };
				DefaultTableModel model = new DefaultTableModel(Act_lich.convert(ret), col) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Class<?> getColumnClass(int col) {
						return col == 0 ? Boolean.class : String.class;
					}
				};
				System.out.println(ret.length);
				ap.xemmail.setVisible(false);
				ap.status = "email";
				JTable tb = new JTable(model) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int roe, int col) {
						
						return col==0;
					}
				};
				ap.showView(tb);

			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
