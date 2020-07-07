package io.spring.gradle.tomcat

import io.spring.gradle.GradleApi
import io.spring.gradle.tomcat.TomcatPlugin.TOMCAT_RUN_TASK_NAME
import io.spring.gradle.TestKit
import org.junit.jupiter.api.Test

/**
 * @author Rob Winch
 */
internal class TomcatPluginITest {
    @Test
    fun tomcatStartThenSuccessWithTestKit() {
        TestKit().use { testKit ->
            val task = ":check"
            val build = testKit
                    .withProjectResource("hello-security")
                    .withArguments(task)
                    .forwardOutput()
                    .build()
//            assertThat(build.task(task)?.outcome).isEqualTo(TaskOutcome.SUCCESS)
//            val m1 =  File(testKit.projectDir, aggregateJavadocPath("module1/M1"))
//            assertThat(m1).exists()
//            val m2 = File(testKit.projectDir, aggregateJavadocPath("module2/M2"))
//            assertThat(m2).exists()
//            val test = File(testKit.projectDir, aggregateJavadocPath("test"))
//            assertThat(test).doesNotExist()
        }
    }

    @Test
    fun tomcatStartThenSuccessWith() {
        GradleApi().use { api ->
            val task = ":$TOMCAT_RUN_TASK_NAME"
            val build = api
                    .withProjectResource("hello-security")
                    .newBuild()
                    .setStandardError(System.err)
                    .setStandardOutput(System.out)
                    .withArguments("--include-build", "/home/rwinch/code/spring-gradle-plugins/tomcat-gradle-plugin")
                    .forTasks("no")
                    .run()
        }
    }

    private fun aggregateJavadocPath(path: String) = "aggregator/build/docs/javadoc/${path}.html"

}