package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import remote.Giaotiep;

public class App extends JFrame implements ActionListener {
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("GTK+".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
				// System.out.println(info.getName());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		new App();
	}

	protected String status = "";
	protected Giaotiep srmi = null;
	protected boolean run = false;

	public App() {
		menu();
		initAll();
		this.setVisible(true);
		this.setSize(700, 500);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.out.println("close");
				System.exit(1);
				try {
					if (srmi != null)
						srmi.logout();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		// showLich();
		login();
	}

	private void menu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnChucNang = new JMenu("chuc nang");
		menuBar.add(mnChucNang);

		mntmXemLich = new JMenuItem("xem lich");
		mnChucNang.add(mntmXemLich);
		mntmXemLich.addActionListener(this);

		mntmXemEmail = new JMenuItem("xem email");
		mnChucNang.add(mntmXemEmail);
		mntmXemEmail.addActionListener(this);

		GuiEmail = new JMenuItem("gui email");
		mnChucNang.add(GuiEmail);
		GuiEmail.addActionListener(this);

		mntmThemCv = new JMenuItem("them cv");

		mntmThemCv.addActionListener(this);
		mnChucNang.add(mntmThemCv);

		mntmDangKi = new JMenuItem("dang ki");
		mnChucNang.add(mntmDangKi);
		mntmDangKi.addActionListener(this);
		mntmLogout = new JMenuItem("logout");
		mnChucNang.add(mntmLogout);
		mntmLogout.addActionListener(this);
	}

	private void initAll() {
		initView();
		initlich();
		inittask();
		initView();
		initW();
		initguimail();
		initxemmail();
		initdangki();

	}

	void showdangki() {
		signup.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(signup, GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(signup, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE).addContainerGap()));

		getContentPane().setLayout(groupLayout);
	}

	void initdangki() {
		signup = new JPanel();

		JTextField user = new JTextField();
		user.setColumns(10);

		JTextField pass = new JTextField();
		pass.setColumns(10);

		JTextField cpass = new JTextField();
		cpass.setColumns(10);

		JButton sebd = new JButton("send");

		JLabel lblUser = new JLabel("user");

		JLabel lblPass = new JLabel("pass");

		JLabel lblConfirmPass = new JLabel("host");
		GroupLayout gl_signup = new GroupLayout(signup);
		gl_signup.setHorizontalGroup(gl_signup.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_signup.createSequentialGroup().addGap(43).addGroup(gl_signup.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_signup.createSequentialGroup().addComponent(lblPass).addContainerGap())
						.addGroup(gl_signup.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_signup.createSequentialGroup().addComponent(lblUser).addContainerGap())
								.addGroup(gl_signup.createSequentialGroup()
										.addGroup(gl_signup.createParallelGroup(Alignment.TRAILING)
												.addComponent(user, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 355,
														Short.MAX_VALUE)
												.addComponent(pass, 355, 355, 355)
												.addGroup(Alignment.LEADING, gl_signup.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(gl_signup.createParallelGroup(Alignment.LEADING)
																.addComponent(sebd)
																.addComponent(cpass, GroupLayout.DEFAULT_SIZE, 355,
																		Short.MAX_VALUE)
																.addComponent(lblConfirmPass))))
										.addGap(272))))));
		gl_signup.setVerticalGroup(gl_signup.createParallelGroup(Alignment.LEADING).addGroup(gl_signup
				.createSequentialGroup().addGap(35).addComponent(lblUser).addGap(18)
				.addComponent(user, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(36).addComponent(lblPass).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(34).addComponent(lblConfirmPass).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(cpass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(66).addComponent(sebd).addContainerGap(100, Short.MAX_VALUE)));
		signup.setLayout(gl_signup);
		sebd.addActionListener(new Act_dk(user, pass, cpass, this));
	}

	void showXmail() {
		xemmail.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(xemmail, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(xemmail, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE).addContainerGap()));

		getContentPane().setLayout(groupLayout);

	}

	void initxemmail() {
		xemmail = new JPanel();

		JTextField textField = new JTextField();
		textField.setColumns(10);

		JLabel lblNguoiGuinhan = new JLabel("nguoi gui/nhan");

		JButton btnAll = new JButton("all");

		JButton btnGet = new JButton("get");

		JLabel lblNhanTatCa = new JLabel("nhan tat ca mail");

		JLabel lblLayTheoNguoi = new JLabel("lay theo nguoi gui");

		JButton btnNhan = new JButton("nhan");

		JLabel lblLayTheoNgui = new JLabel("lay theo ngui nhan");
		GroupLayout gl_xemmail = new GroupLayout(xemmail);
		gl_xemmail.setHorizontalGroup(gl_xemmail.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_xemmail.createSequentialGroup().addGap(28).addComponent(lblNhanTatCa)
						.addPreferredGap(ComponentPlacement.RELATED, 161, Short.MAX_VALUE).addComponent(lblLayTheoNguoi)
						.addGap(46))
				.addGroup(Alignment.TRAILING,
						gl_xemmail.createSequentialGroup().addContainerGap(182, Short.MAX_VALUE)
								.addComponent(lblLayTheoNgui).addGap(174))
				.addGroup(gl_xemmail.createSequentialGroup().addGap(199).addComponent(btnNhan).addContainerGap(202,
						Short.MAX_VALUE))
				.addGroup(gl_xemmail.createSequentialGroup().addGap(56)
						.addGroup(gl_xemmail.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_xemmail.createSequentialGroup()
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addGroup(gl_xemmail.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_xemmail.createSequentialGroup().addComponent(lblNguoiGuinhan)
												.addContainerGap())
										.addGroup(gl_xemmail.createSequentialGroup().addComponent(btnAll)
												.addPreferredGap(ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
												.addComponent(btnGet).addGap(66))))));
		gl_xemmail.setVerticalGroup(gl_xemmail.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_xemmail.createSequentialGroup().addGap(71)
						.addGroup(gl_xemmail.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_xemmail.createSequentialGroup().addComponent(lblNguoiGuinhan).addGap(18)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(45).addComponent(lblNhanTatCa))
								.addComponent(lblLayTheoNguoi))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_xemmail.createParallelGroup(Alignment.BASELINE).addComponent(btnAll)
								.addComponent(btnGet))
						.addGap(61).addComponent(lblLayTheoNgui).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnNhan).addContainerGap(95, Short.MAX_VALUE)));
		new Act_xemmail(btnNhan, btnGet, btnAll, textField, this);
		xemmail.setLayout(gl_xemmail);
	}

	void showmail() {
		guimail.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(guimail, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(guimail, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE).addContainerGap()));

		getContentPane().setLayout(groupLayout);
	}

	void initguimail() {
		guimail = new JPanel();

		JTextArea mess = new JTextArea();

		JTextField cap = new JTextField();
		cap.setColumns(10);

		JTextField id2 = new JTextField();
		id2.setColumns(10);

		JLabel lblNguoiNhan = new JLabel("nguoi nhan");

		JLabel lblTieuDe = new JLabel("tieu de");

		JButton btnSend = new JButton("send");
		GroupLayout gl_guimail = new GroupLayout(guimail);
		gl_guimail.setHorizontalGroup(gl_guimail.createParallelGroup(Alignment.LEADING).addGroup(gl_guimail
				.createSequentialGroup()
				.addGroup(gl_guimail.createParallelGroup(Alignment.LEADING).addGroup(gl_guimail.createSequentialGroup()
						.addContainerGap().addGroup(gl_guimail.createParallelGroup(Alignment.LEADING)
								.addComponent(mess, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
								.addComponent(cap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(id2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNguoiNhan).addComponent(lblTieuDe)))
						.addGroup(gl_guimail.createSequentialGroup().addGap(189).addComponent(btnSend)))
				.addContainerGap()));
		gl_guimail.setVerticalGroup(gl_guimail.createParallelGroup(Alignment.LEADING).addGroup(gl_guimail
				.createSequentialGroup().addGap(16).addComponent(lblNguoiNhan)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(id2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblTieuDe).addGap(3)
				.addComponent(cap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(mess, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE).addGap(47)
				.addComponent(btnSend).addContainerGap(57, Short.MAX_VALUE)));
		btnSend.addActionListener(new Act_guimail(cap, id2, mess, this));
		guimail.setLayout(gl_guimail);
	}

	protected void showLich() {
		xemlich.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(xemlich, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
						.addComponent(xemlich, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE).addContainerGap()));

		getContentPane().setLayout(groupLayout);
	}

	protected void initlich() {

		DatePicker date = new DatePicker();
		xemlich = new JPanel();

		JLabel lblXemLichTheo = new JLabel("xem lich theo ngay");

		JButton b1 = new JButton("send");

		JLabel lblXemTheoNgay = new JLabel("xem theo ngay trong tuan");

		JTextField text = new JTextField();
		text.setColumns(10);

		JButton b2 = new JButton("send");

		JLabel lblLayTatCa = new JLabel("lay tat ca");

		JButton b3 = new JButton("send");

		JButton b4 = new JButton("send");

		JLabel lblLichHomNay = new JLabel("lich hom nay");
		GroupLayout gl_xemlich = new GroupLayout(xemlich);
		gl_xemlich.setHorizontalGroup(gl_xemlich.createParallelGroup(Alignment.LEADING).addGroup(gl_xemlich
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_xemlich.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING,
								gl_xemlich.createSequentialGroup().addComponent(lblXemLichTheo).addContainerGap(328,
										Short.MAX_VALUE))
						.addGroup(gl_xemlich.createSequentialGroup().addComponent(lblXemTheoNgay).addContainerGap(278,
								Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_xemlich.createSequentialGroup()
								.addGroup(gl_xemlich.createParallelGroup(Alignment.LEADING)
										.addComponent(date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(text, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblLayTatCa).addComponent(lblLichHomNay))
								.addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
								.addGroup(gl_xemlich.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_xemlich.createSequentialGroup().addComponent(b3)
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(gl_xemlich.createSequentialGroup().addComponent(b2)
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(Alignment.TRAILING,
												gl_xemlich.createSequentialGroup().addComponent(b1).addGap(45))
										.addGroup(gl_xemlich.createSequentialGroup().addComponent(b4)
												.addContainerGap()))))));
		gl_xemlich.setVerticalGroup(gl_xemlich.createParallelGroup(Alignment.LEADING).addGroup(gl_xemlich
				.createSequentialGroup().addGap(44).addComponent(lblXemLichTheo).addGap(30)
				.addGroup(gl_xemlich.createParallelGroup(Alignment.BASELINE)
						.addComponent(date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(b1))
				.addGap(27).addComponent(lblXemTheoNgay).addGap(29)
				.addGroup(gl_xemlich.createParallelGroup(Alignment.BASELINE)
						.addComponent(text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(b2))
				.addGap(30)
				.addGroup(gl_xemlich.createParallelGroup(Alignment.BASELINE).addComponent(lblLayTatCa).addComponent(b3))
				.addGap(49)
				.addGroup(
						gl_xemlich.createParallelGroup(Alignment.BASELINE).addComponent(b4).addComponent(lblLichHomNay))
				.addContainerGap(89, Short.MAX_VALUE)));
		xemlich.setLayout(gl_xemlich);
		new Act_lich(text, date, this, b1, b2, b3, b4);
	}

	protected void showTask(JTable table_1) {
		stask.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(stask, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().addComponent(stask, GroupLayout.DEFAULT_SIZE, 440,
						Short.MAX_VALUE)));
		stask.setViewportView(table_1);
		getContentPane().setLayout(groupLayout);

	}

	protected void inittask() {
		stask = new JScrollPane();
		stask.setVisible(false);
	}

	protected void login() {
		login = new JPanel();

		JTextField user = new JTextField();
		user.setColumns(10);

		JTextField pass = new JTextField();
		pass.setColumns(10);

		JLabel lblUser = new JLabel("user");

		JLabel lblL = new JLabel("Login");

		JLabel lblPass = new JLabel("pass");

		JButton btnSubmit_1 = new JButton("submit");

		JTextField host = new JTextField();
		host.setText("");
		host.setColumns(10);

		JLabel lblHost = new JLabel("host");
		GroupLayout gl_login = new GroupLayout(login);
		gl_login.setHorizontalGroup(gl_login.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_login.createSequentialGroup().addContainerGap()
						.addComponent(lblL, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addGroup(gl_login.createParallelGroup(Alignment.LEADING).addComponent(btnSubmit_1)
								.addComponent(lblHost)
								.addGroup(gl_login.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(host, Alignment.LEADING).addComponent(lblPass, Alignment.LEADING)
										.addComponent(lblUser, Alignment.LEADING)
										.addComponent(user, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 289,
												Short.MAX_VALUE)
										.addComponent(pass, Alignment.LEADING)))
						.addContainerGap(268, Short.MAX_VALUE)));
		gl_login.setVerticalGroup(gl_login.createParallelGroup(Alignment.LEADING).addGroup(gl_login
				.createSequentialGroup().addGap(33).addComponent(lblUser).addGap(18)
				.addComponent(user, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(35).addComponent(lblPass).addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(33).addComponent(lblHost).addGap(18)
				.addComponent(host, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE).addComponent(btnSubmit_1).addGap(74))
				.addGroup(gl_login.createSequentialGroup().addContainerGap()
						.addComponent(lblL, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE).addGap(391)));
		login.setLayout(gl_login);

		btnSubmit_1.addActionListener(new Act_login(user, pass, host, this));
		showlogin();
	}

	void showlogin() {
		login.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(login, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(login, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE).addContainerGap()));

		getContentPane().setLayout(groupLayout);
	}

	protected void showView(JTable table) {
		view.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(view, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(view, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE).addContainerGap()));
		tb = table;
		scrolltable.setViewportView(tb);
		getContentPane().setLayout(groupLayout);
	}

	protected void initView() {
		view = new JPanel();
		view.setVisible(true);
		scrolltable = new JScrollPane();

		JButton btnDelete = new JButton("Delete");
		GroupLayout gl_view = new GroupLayout(view);
		gl_view.setHorizontalGroup(
				gl_view.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_view.createSequentialGroup()
								.addGroup(gl_view.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_view.createSequentialGroup().addGap(194).addComponent(btnDelete))
										.addGroup(gl_view.createSequentialGroup().addContainerGap().addComponent(
												scrolltable, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
								.addContainerGap()));
		gl_view.setVerticalGroup(gl_view.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_view.createSequentialGroup().addContainerGap()
						.addComponent(scrolltable, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addComponent(btnDelete)
						.addGap(21)));
		view.setLayout(gl_view);
		view.setVisible(false);
		btnDelete.addActionListener(new Act_view(this));
	}

	protected void initW() {
		DatePicker date = new DatePicker();
		TimePicker time = new TimePicker();
		panel = new JPanel();

		JTextField cv = new JTextField();
		cv.setColumns(10);

		JButton btnSubmit = new JButton("submit");

		JLabel lblThemCongViec = new JLabel("them cong viec");

		JLabel lblTenCv = new JLabel("ten cv");

		JLabel lblNgay = new JLabel("ngay");

		JLabel lblGio = new JLabel("gio");

		JTextArea lydo = new JTextArea();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(169).addComponent(lblThemCongViec)
						.addContainerGap(197, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(cv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(334, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(lblTenCv).addContainerGap(416,
						Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNgay))
						.addPreferredGap(ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGio))
						.addGap(27))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(lydo, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE).addContainerGap())
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addContainerGap(196, Short.MAX_VALUE)
						.addComponent(btnSubmit).addGap(192)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(4).addComponent(lblThemCongViec).addGap(5).addComponent(lblTenCv)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(cv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(36).addComponent(lydo, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE).addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblNgay).addComponent(lblGio))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(63).addComponent(btnSubmit).addContainerGap()));
		panel.setLayout(gl_panel);
		btnSubmit.addActionListener(new Act_W(cv, lydo, date, time, this));
	}

	protected void showW() {
		panel.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap(34, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		getContentPane().setLayout(groupLayout);
	}

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	DatePicker datePicker1;
	TimePicker timePicker1;
	protected JPanel panel;
	protected JPanel view;
	protected JPanel login;
	protected JScrollPane stask;
	protected JScrollPane scrolltable;
	protected JPanel xemlich;
	protected JTable tb;
	private JMenuItem mntmXemLich;
	private JMenuItem mntmThemCv;
	private JMenuItem GuiEmail;
	private JPanel guimail;
	private JMenuItem mntmXemEmail;
	protected JPanel xemmail;
	private JMenuItem mntmLogout;
	private JMenuItem mntmDangKi;
	protected JPanel signup;

	public void closepn() {
		panel.setVisible(false);
		view.setVisible(false);
		xemlich.setVisible(false);
		xemmail.setVisible(false);
		guimail.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.gc();
		closepn();
		if (run) {

			if (e.getSource() == mntmXemLich) {
				showLich();
			} else if (e.getSource() == mntmThemCv) {
				showW();
			} else if (e.getSource() == mntmXemEmail) {
				showXmail();
			} else if (e.getSource() == GuiEmail) {
				showmail();
			} else if (e.getSource() == GuiEmail) {
				showmail();
			} else if (e.getSource() == mntmLogout) {
				run = false;
				showlogin();
			}

		}
		if (e.getSource() == mntmDangKi) {
			System.out.println("s");
			login.setVisible(false);
			showdangki();
		}
	}
}
