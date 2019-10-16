package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class Act_W implements ActionListener {

	JTextField ten;
	JTextArea lydo;
	DatePicker dat;
	TimePicker tim;
	App as;

	public Act_W(JTextField ue, JTextArea pa, DatePicker date, TimePicker time, App a) {
		ten = ue;
		lydo = pa;
		dat = date;
		tim = time;
		as = a;
	}

	public void actionPerformed(ActionEvent e) {
		String na = ten.getText();
		String nb = lydo.getText();
		if (na.equals("") || nb.equals(""))
			return;
		String p = dat.getDateStringOrSuppliedString("null");
		String t = tim.getTimeStringOrSuppliedString("") + ":00";
		try {
			int k = as.srmi.setCv(na, p, t, nb);
			ten.setText("");
			lydo.setText("");
			if(k==1) lydo.setText("loi thuc thi");
			if(k==2) lydo.setText("loi trung lap");
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
