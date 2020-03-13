package io.spring.gradle.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

/**
 * @author Rob Winch
 */
public class TomcatRunTask extends ConventionTask {
	private TomcatServer server = new TomcatServer();

	public void setWebapp(File webapp) {
		this.server.setWebapp(webapp);
	}

	@Input
	public File getWebapp() {
		return this.server.getWebapp();
	}

	@Input
	public int getHttpPort() {
		return this.server.getHttpPort();
	}

	public void setHttpPort(int httpPort) {
		this.server.setHttpPort(httpPort);
	}

	public ClassLoader getParentLoader() {
		return server.getParentLoader();
	}

	public void setParentLoader(ClassLoader parentLoader) {
		server.setParentLoader(parentLoader);
	}

	public TomcatServer getServer() {
		return this.server;
	}

	@TaskAction
	public Tomcat startAndAwait() throws IOException, LifecycleException {
		Tomcat tomcat = start();
		tomcat.getServer().await();
		return tomcat;
	}

	public Tomcat start() throws IOException, LifecycleException {
		this.server.setParentLoader(getParentLoader());
		this.server.setWebapp(getWebapp());
		return this.server.start();
	}

	public void stop() throws LifecycleException {
		this.server.stop();
	}
}
