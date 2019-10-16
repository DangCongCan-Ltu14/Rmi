package server;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import conn.Connect;

public class RMIServer {

	public static void main(String[] args) {

		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		try {

			System.setProperty("java.rmi.server.hostname", host);
			// setSettings();
			SRmi call;
			if (args.length > 1)
				call = new SRmi(Connect.getConn(args[1]));
			else
				call = new SRmi(Connect.getConn());

			// LocateRegistry.createRegistry(8888, new SslRMIClientSocketFactory(),
			// new SslRMIServerSocketFactory(null, null, true));
			// Registry registry = LocateRegistry.getRegistry(host, 8888, new
			// SslRMIClientSocketFactory());

			LocateRegistry.createRegistry(8888);
			Registry registry = LocateRegistry.getRegistry(host, 8888);
			UnicastRemoteObject.exportObject(call, 0);
			registry.bind("Hello", call);
			System.out.println("run server");
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	static void setSettings() {
		String path = new File("").getAbsolutePath();
		System.out.println(path);
		String pass = "dangcongcan"; // ko duoc tu tien thay doi
		System.setProperty("javax.net.ssl.debug", "all");
		System.setProperty("javax.net.ssl.keyStore", path + "/ssl/server/KeyStore.jks");
		System.setProperty("javax.net.ssl.trustStore", path + "/ssl/server/truststore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		System.setProperty("javax.net.ssl.trustStorePassword", pass);
	}
}