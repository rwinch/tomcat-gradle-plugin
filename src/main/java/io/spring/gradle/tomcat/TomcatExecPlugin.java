package io.spring.gradle.tomcat;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.tasks.JavaExec;

/**
 * @author Rob Winch
 */
public class TomcatExecPlugin implements Plugin<Project> {
	public static final String TOMCAT_EXEC_TASK_NAME = "tomcatExec";

	@Override
	public void apply(Project project) {
		NamedDomainObjectProvider<Configuration> tomcatClasspath = project
				.getConfigurations()
				.register("tomcatClasspath", new Action<Configuration>() {
					@Override
					public void execute(Configuration files) {
						files.defaultDependencies(dependencies -> {
							dependencies.add(project.getDependencies()
									.create("org.apache.tomcat.embed:tomcat-embed-core:9.+"));
							dependencies.add(project.getDependencies()
									.create("org.apache.tomcat.embed:tomcat-embed-jasper:9.+"));
							dependencies.add(project.getDependencies()
									.create("javax.servlet.jsp.jstl:javax.servlet.jsp.jstl-api:1.+"));
							dependencies.add(project.getDependencies()
									.create("javax.servlet.jsp.jstl:javax.servlet.jsp.jstl-api:1.+"));
						});
					}
				});
		project.getTasks().register(TOMCAT_EXEC_TASK_NAME, JavaExec.class).configure(new Action<JavaExec>() {
			@Override
			public void execute(JavaExec javaExec) {
				javaExec.setMain(TomcatMain.class.getName());
				javaExec.classpath(tomcatClasspath);
			}
		});
	}
}
