package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JTextField;

public class Act_login implements ActionListener {
	JTextField ur, ps, host;
	App ap;
	String s;

	public Act_login(JTextField t1, JTextField ts, JTextField ht, App d) {
		ur = t1;
		ps = ts;
		ap = d;
		host = ht;
	}

	public void actionPerformed(ActionEvent e) {
		String a = ur.getText();
		String b = ps.getText();
		try {
			s = "localhost";
			if (!host.getText().equals(""))
				s = host.getText();

			if (ap.srmi == null) {
				ap.srmi = GetRmi.gets(s);
			}
			boolean t = ap.srmi.login(a, b);
			if (t) {
				ap.run = true;
				ap.login.setVisible(false);
				ap.showLich();
			} else {
				ur.setText("sai thong tin");
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
