package client;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import conn.Connect;
import remote.Giaotiep;
import server.SRmi;

public class GetRmi {
	static boolean rmi = true;

	public static Giaotiep gets(String hosts) {
		if (!rmi)
			return new SRmi(Connect.getConn());
		Registry registry;
		try {
			hosts = "localhost";
			registry = LocateRegistry.getRegistry(hosts, 8888);
			Giaotiep c = (Giaotiep) registry.lookup("Hello");
			System.out.println("tao thanh cong");
			return c;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Giaotiep getSSL() {
		setSettings();
		Registry registry;
		try {
			String hosts = "localhost";
			registry = LocateRegistry.getRegistry(hosts, 8888, new SslRMIClientSocketFactory());
			// registry = LocateRegistry.getRegistry(hosts, 8888);
			Giaotiep c = (Giaotiep) registry.lookup("Hello");
			System.out.println("tao thanh cong");
			return c;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void setSettings() {
		String path = new File("").getAbsolutePath();
		String pass = "dangcongcan"; // ko duoc tu tien thay doi
		System.setProperty("javax.net.ssl.debug", "all");
		System.setProperty("javax.net.ssl.keyStore", path + "/ssl/client/KeyStore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		System.setProperty("javax.net.ssl.trustStore", path + "/ssl/client/truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", pass);
	}
}
