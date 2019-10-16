package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class Act_dk implements ActionListener {

	App ap;
	JTextField u, c, p;

	public Act_dk(JTextField a, JTextField b, JTextField c, App as) {
		u = a;
		this.c = c;
		p = b;
		ap = as;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String user = u.getText();
		String pass = p.getText();
		String host = c.getText();
		String s = "localhost";
		if (!host.equals(""))
			s = host;
		
		if (ap.srmi == null) {
			ap.srmi = GetRmi.gets(s);
		}
		try {
			//System.out.println("ss");
			if ((!pass.equals("")) && (!user.equals(""))) {
				
				ap.srmi.adduser(user, pass);
				ap.signup.setVisible(false);
				ap.showlogin();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
