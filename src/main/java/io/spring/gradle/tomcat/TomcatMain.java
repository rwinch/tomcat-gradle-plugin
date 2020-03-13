package io.spring.gradle.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

/**
 * @author Rob Winch
 */
public class TomcatMain {
	public static void main(String[] args) throws IOException, LifecycleException {
		if (args.length != 3) {
			throw new IllegalArgumentException("Expected to have 3 arguments");
		}
		String webapp = args[0];
		int port = Integer.parseInt(args[1]);
		boolean await = Boolean.parseBoolean(args[2]);

		TomcatServer server = new TomcatServer();
		File webappFile = new File(webapp);
		if (!webappFile.exists()) {
			throw new IllegalArgumentException("The file " + webappFile + " does not exist");
		}
		server.setWebapp(webappFile);
		server.setHttpPort(port);
		Tomcat tomcat = server.start();
		if (await) {
			tomcat.getServer().await();
		}
	}
}
