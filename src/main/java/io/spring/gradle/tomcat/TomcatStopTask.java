package io.spring.gradle.tomcat;

import org.apache.catalina.LifecycleException;
import org.gradle.api.DefaultTask;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Rob Winch
 */
public class TomcatStopTask extends ConventionTask {
	private TomcatServer server;

	@TaskAction
	public void stop() throws LifecycleException {
		getServer().stop();
	}

	public void setServer(TomcatServer server) {
		this.server = server;
	}

	public TomcatServer getServer() {
		return this.server;
	}
}
