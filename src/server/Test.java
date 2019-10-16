package server;

import java.rmi.RemoteException;

import conn.Connect;

public class Test {
	public static void main(String[] args) throws RemoteException {
		SRmi sr = new SRmi(Connect.getConn());
	//	sr.send("can", "rmi", "irm");
		ps(sr.allmail());

	}

	public static void ps(String[][] s)

	{
		if (s == null)
			return;
		for (String[] ss : s) {
			for (String k : ss)
				System.out.print(k + "  ");
			System.out.println();
		}
	}
}
