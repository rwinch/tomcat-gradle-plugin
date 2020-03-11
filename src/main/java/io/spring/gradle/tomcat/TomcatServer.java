package io.spring.gradle.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

/**
 * @author Rob Winch
 */
public class TomcatServer {

	private File webapp;

	private int httpPort = 8080;

	private Tomcat tomcat;

	private ClassLoader parentLoader;

	public void setWebapp(File webapp) {
		this.webapp = webapp;
	}

	public File getWebapp() {
		return this.webapp;
	}

	public int getHttpPort() {
		return this.httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public Tomcat getTomcat() {
		return this.tomcat;
	}

	public ClassLoader getParentLoader() {
		return this.parentLoader;
	}

	public void setParentLoader(ClassLoader parentLoader) {
		System.out.println("parentLoader " + parentLoader);
		this.parentLoader = parentLoader;
	}

	public Tomcat start() throws LifecycleException, IOException {
		int port = getHttpPort();
		File webapp = getWebapp();
		if (webapp == null) {
			throw new IllegalStateException("webapp cannot be null");
		}
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(createBaseDir(port).getAbsolutePath());
		tomcat.setPort(port);
		tomcat.getConnector(); // must be called to create default connector
		tomcat.getHost().setAppBase(".");
		Context context = tomcat.addWebapp("", webapp.toURI().toURL());
		if (this.parentLoader != null) {
			context.setLoader(new WebappLoader(this.parentLoader));
		}
		System.out.println("Starting...");
		tomcat.start();
		System.out.println("Started");
		this.tomcat = tomcat;
		return tomcat;
	}

	public void stop() throws LifecycleException {
		this.tomcat.stop();
	}

	private static File createBaseDir(int port) throws IOException {
		File tempDir = File.createTempFile("tomcat.", "." + port);
		tempDir.delete();
		tempDir.mkdir();
		tempDir.deleteOnExit();
		return tempDir;
	}
}
