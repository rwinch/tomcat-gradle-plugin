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
public class TomcatTask extends ConventionTask {
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

	@TaskAction
	public Tomcat start() throws IOException, LifecycleException {
		Tomcat tomcat = this.server.start();
		tomcat.getServer().await();
		return tomcat;
	}

	public void stop() throws LifecycleException {
		this.server.stop();
	}
}
