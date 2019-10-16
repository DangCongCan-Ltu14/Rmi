package server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import remote.Giaotiep;
import sql.security.BCrypt;
import sql.table.Mail;
import sql.table.Thongtin;
import sql.table.User;

public class SRmi implements Giaotiep {

	Connection con;
	protected String ten = "test";

	public SRmi(Connection c) {
		con = c;
		System.out.println("start new rmi");
	}

	public String[][] viewonday(String a) throws RemoteException {
		try {
			String sql = "select " + Thongtin.cv + "," + Thongtin.note + "," + Thongtin.gio + "," + Thongtin.ngay
					+ " from " + Thongtin.name + " where " + Thongtin.ngay + " =?  and " + Thongtin.user + " =? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, a);
			ps.setString(2, ten);
			// System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.last();
			int h = rs.getRow();
			if (h == 0)
				return null;
			String[][] p = new String[h][4];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				p[i][0] = rs.getString(1);
				p[i][1] = rs.getString(2);
				p[i][2] = rs.getString(3);
				p[i][3] = rs.getString(4);
				i++;
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	public String[][] dayofgetWeek(int a) throws RemoteException {
		try {
			String sql = "select " + Thongtin.cv + "," + Thongtin.note + "," + Thongtin.gio + "," + Thongtin.ngay
					+ " from " + Thongtin.name + " where WEEKOFYEAR(" + Thongtin.ngay
					+ " ) =  WEEKOFYEAR( NOW()) and DAYOFWEEK(" + Thongtin.ngay + ") = ? and " + Thongtin.user + " =? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, a);
			ps.setString(2, ten);
			// System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.last();
			int h = rs.getRow();
			if (h == 0)
				return null;
			String[][] p = new String[h][4];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				p[i][0] = rs.getString(1);
				p[i][1] = rs.getString(2);
				p[i][2] = rs.getString(3);
				p[i][3] = rs.getString(4);
				i++;
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	public void clearList(String[] a) throws RemoteException {
		try {
			String sql = "DELETE from " + Thongtin.name + " where " + Thongtin.cv + "=? and " + Thongtin.gio + "=? and "
					+ Thongtin.user + " = ? and " + Thongtin.ngay + " = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, a[0]);
			ps.setString(2, a[1]);
			ps.setString(3, ten);
			ps.setString(4, a[2]);
			// System.out.println(ps.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void clearList(String[][] as) throws RemoteException {
		try {
			String sql = "DELETE from " + Thongtin.name + " where " + Thongtin.cv + "=? and " + Thongtin.gio + "=? and "
					+ Thongtin.user + " = ? and " + Thongtin.ngay + " = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(3, ten);
			for (String[] a : as) {
				ps.setString(1, a[0]);
				ps.setString(2, a[1]);
				ps.setString(4, a[2]);
				// System.out.println(ps.toString());
				ps.executeUpdate();
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void adduser(String a, String b)throws RemoteException {
		try {
			String sql = "insert into " + User.name + " values (?, ? )";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, a);
			ps.setString(2, BCrypt.hashpw(b, BCrypt.gensalt()));
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
		}
	}

	public boolean login(String user, String pass) throws RemoteException {
		String sql = "select * from " + User.name + " where " + User.name + " = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			boolean p = rs.next();
			if (!p) {
				rs.close();
				ps.close();
				return p;
			}
			String st = rs.getString(2);
			p = BCrypt.checkpw(pass, st);
			if (p)
				ten = user;
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int setCv(String a, String ngay, String gio, String ghichu) throws RemoteException {
		boolean p = checkdulicate(a, ngay, gio);
		// System.out.println(p);
		if (p) {
			//System.out.println(p);
			return 2;
		}
		String sql = "INSERT INTO " + Thongtin.name + " values( ?,?,?,?,?)";
		try {
			// System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(5, ten);
			ps.setString(1, a);
			ps.setString(2, ngay);
			ps.setString(3, gio);
			ps.setString(4, ghichu);
			ps.executeUpdate();
			ps.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	private boolean checkdulicate(String a, String ngay, String gio) {
		try {
			String sql = " select * from " + Thongtin.name + " where " + Thongtin.user + " =? and " + Thongtin.cv
					+ " =? and " + Thongtin.ngay + " = ? and " + Thongtin.gio + " =?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ten);
			ps.setString(2, a);
			ps.setString(3, ngay);
			ps.setString(4, gio);
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			boolean c = rs.next();
			rs.close();
			ps.close();
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public String[][] getAll() throws RemoteException {
		try {
			String sql = "select " + Thongtin.cv + "," + Thongtin.note + "," + Thongtin.gio + "," + Thongtin.ngay
					+ " from " + Thongtin.name + " where " + Thongtin.user + " =? ";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ten);
			//System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.last();
			int h = rs.getRow();
			if (h == 0)
				return null;
			String[][] p = new String[h][4];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				p[i][0] = rs.getString(1);
				p[i][1] = rs.getString(2);
				p[i][2] = rs.getString(3);
				p[i][3] = rs.getString(4);
				i++;
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean send(String id2, String cap, String mess) throws RemoteException {
		String time = new Time(System.currentTimeMillis()).toString();
		String date = new Date(System.currentTimeMillis()).toString();
		try {
			String sql = "select * from " + User.name + " where " + User.name + " = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id2);
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			boolean p = rs.next();
			if (!p) {
				rs.close();
				ps.close();
				//System.out.println("khong co user");
				return p;
			}
			sql = " insert into " + Mail.name + " values(?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, ten);
			ps.setString(2, id2);
			ps.setString(3, cap);
			ps.setString(4, mess);
			ps.setString(5, date);
			ps.setString(6, time);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	/**
	 * mail theo nguoi nhan
	 */
	public String[][] getmail(String a) throws RemoteException {
		try {
			String sql;
			PreparedStatement ps;
			if (a.equals("")) {
				sql = "select * from  " + Mail.name + " where " + Mail.id1 + " =? ";
				ps = con.prepareStatement(sql);
				ps.setString(1, ten);
			} else {
				sql = "select * from  " + Mail.name + " where " + Mail.id1 + " =? and " + Mail.id2 + " =?";
				ps = con.prepareStatement(sql);
				ps.setString(1, ten);
				ps.setString(2, a);
			}
			ResultSet rs = ps.executeQuery();
			rs.last();
			int h = rs.getRow();
			if (h == 0)
				return null;
			String[][] p = new String[h][6];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < 6; j++) {
					p[i][j] = rs.getString(j + 1);
				}
				i++;
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * id2 ,cap , date ,time
	 */
	public boolean rmEmail(String[][] a) throws RemoteException {
		try {
			String sql = String.format("delete from %s where %s = ?" + " and %s =? and %s =? and %s = ? and %s = ? ; ",
					Mail.name, Mail.id1, Mail.id2, Mail.cap, Mail.ngay, Mail.gio);
			//System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ten);
			for (String[] p : a) {
				ps.setString(2, p[0]);
				ps.setString(3, p[1]);
				ps.setString(4, p[2]);
				ps.setString(5, p[3]);
				//System.out.print(ps.toString());
				ps.executeUpdate();
			}
			ps.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public String[][] nhanmail(String a) throws RemoteException {

		try {
			String sql;
			if (a.equals(""))
				sql = "select * from  " + Mail.name + " where " + Mail.id2 + " =? ; ";
			else
				sql = "select * from  " + Mail.name + " where " + Mail.id2 + " =? and " + Mail.id1 + " = ?";
			;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ten);
			if (!a.equals(""))
				ps.setString(2, a);
			ResultSet rs = ps.executeQuery();
			rs.last();
			int h = rs.getRow();
			if (h == 0)
				return null;
			String[][] p = new String[h][6];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < 6; j++) {
					p[i][j] = rs.getString(j + 1);
				}
				i++;
			}
			return p;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[][] allmail() throws RemoteException {
		try {
			String sql;
			sql = "select * from  " + Mail.name + " where " + Mail.id2 + " =? or " + Mail.id1 + " = ?";
			;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ten);
			ps.setString(2, ten);

			ResultSet rs = ps.executeQuery();
			rs.last();
			int h = rs.getRow();
			if (h == 0)
				return null;
			// System.out.println(ps.toString());
			String[][] p = new String[h][6];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < 6; j++) {
					p[i][j] = rs.getString(j + 1);
				}
				i++;
			}
			return p;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void logout() throws RemoteException {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
