package io.spring.gradle.tomcat

import io.spring.gradle.tomcat.TomcatPlugin.TOMCAT_RUN_TASK_NAME
import io.spring.gradle.testkit.junit.TestKit
import org.junit.jupiter.api.Test

/**
 * @author Rob Winch
 */
internal class TomcatPluginITest {
    @Test
    fun tomcatStartThenSuccess() {
        TestKit().use { testKit ->
            val task = ":$TOMCAT_RUN_TASK_NAME"
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

    private fun aggregateJavadocPath(path: String) = "aggregator/build/docs/javadoc/${path}.html"

}