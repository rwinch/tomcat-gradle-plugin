package io.spring.gradle.tomcat;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.War;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * @author Rob Winch
 */
public class TomcatPlugin implements Plugin<Project> {
	public static final String TOMCAT_RUN_TASK_NAME = "tomcatRun";
	public static final String TOMCAT_STOP_TASK_NAME = "tomcatStop";

	@Override
	public void apply(Project project) {
		TaskContainer tasks = project.getTasks();
		TaskProvider<TomcatRunTask> tomcatRun = tasks.register(TOMCAT_RUN_TASK_NAME, TomcatRunTask.class, new Action<TomcatRunTask>() {
			@Override
			public void execute(TomcatRunTask tomcatTask) {
				tomcatTask.setGroup("Tomcat");
				tomcatTask.setDescription("Runs Tomcat");
				tomcatTask.dependsOn(tasks.named("war"));
				tomcatTask.getConventionMapping().map("parentLoader", new Callable<ClassLoader>() {
					@Override
					public ClassLoader call() throws Exception {
						// FIXME: Can I somehow use a different classloader?
						ClassLoader classLoader = TomcatServer.class.getClassLoader();
						return classLoader;
					}
				});
				tomcatTask.getConventionMapping().map("webapp", new Callable<File>() {
					@Override
					public File call() throws Exception {
						War war = (War) project.getTasks().getAt(WarPlugin.WAR_TASK_NAME);
						File webappFile = war.getArchiveFile().get().getAsFile();
						return webappFile;
					}
				});
			}
		});
		TaskProvider<TomcatStopTask> tomcatStop = tasks.register(TOMCAT_STOP_TASK_NAME, TomcatStopTask.class, new Action<TomcatStopTask>() {
			@Override
			public void execute(TomcatStopTask tomcatTask) {
				tomcatTask.setGroup("Tomcat");
				tomcatTask.setDescription("Stops Tomcat");
				tomcatTask.getConventionMapping().map("server", new Callable<TomcatServer>() {
					@Override
					public TomcatServer call() throws Exception {
						return tomcatRun.get().getServer();
					}
				});
			}
		});
	}
}
