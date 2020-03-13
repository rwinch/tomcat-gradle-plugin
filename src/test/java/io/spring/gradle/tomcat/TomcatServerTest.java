package io.spring.gradle.tomcat;

import org.apache.catalina.LifecycleException;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rob Winch
 */
class TomcatServerTest {

	@Test
	void test() throws IOException, LifecycleException {

		TomcatServer server = new TomcatServer();
		server.setHttpPort(0);
		server.setWebapp(new File("/home/rwinch/code/rwinch/hello-security/build/libs/hello-security.war"));

		server.start();
		System.out.println(server);

	}

}