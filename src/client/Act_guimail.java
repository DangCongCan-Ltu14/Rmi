package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Act_guimail implements ActionListener {

	App ap;
	JTextField cap, id2;
	JTextArea mess;

	public Act_guimail(JTextField cap, JTextField id2, JTextArea mess, App thiss) {
		this.cap = cap;
		this.id2 = id2;
		this.mess = mess;
		this.ap = thiss;
	}

	public void actionPerformed(ActionEvent e) {
		String p = id2.getText();
		if (p.equals(""))
			return;
		String cp=cap.getText() ;
		String ms=mess.getText();
		try {
			boolean s =ap.srmi.send(p, cp, ms);
			if(s)
			{
				cap.setText("");
				mess.setText("gui tc");
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

}
