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

	@Override
	public void apply(Project project) {
		TaskContainer tasks = project.getTasks();
		TaskProvider<TomcatTask> tomcat = tasks.register("tomcat", TomcatTask.class, new Action<TomcatTask>() {
			@Override
			public void execute(TomcatTask tomcatTask) {
				tomcatTask.setGroup("Tomcat");
				tomcatTask.setDescription("Runs Tomcat");
				tomcatTask.dependsOn(tasks.named("war"));
				tomcatTask.getConventionMapping().map("parentLoader", new Callable<ClassLoader>() {
					@Override
					public ClassLoader call() throws Exception {
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
	}
}
