package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Giaotiep extends Remote {
	public boolean send(String id2, String cap, String mess) throws RemoteException;

	public void logout() throws RemoteException;

	public String[][] getmail(String a) throws RemoteException;

	public String[][] allmail() throws RemoteException;

	public boolean rmEmail(String[][] a) throws RemoteException;

	public String[][] nhanmail(String a) throws RemoteException;

	public String[][] dayofgetWeek(int a) throws RemoteException;

	public String[][] viewonday(String a) throws RemoteException;

	public String[][] getAll() throws RemoteException;

	public void clearList(String[] a) throws RemoteException;

	public void adduser(String a, String b) throws RemoteException;

	/**
	 * 
	 * @param a
	 *            a1= ten a2 =gio
	 * @throws RemoteException
	 */
	public void clearList(String[][] a) throws RemoteException;

	public boolean login(String user, String pass) throws RemoteException;

	public int setCv(String a, String ngay, String gio, String ghichu) throws RemoteException;
}
