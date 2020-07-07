package io.spring.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import java.io.File
import java.io.IOException
import java.nio.file.Paths

/**
 * @author Rob Winch
 */
class GradleApi: AutoCloseable {
    val projectDir: File = createTempDir("testkit")

    fun withProjectResource(projectResourceName: String): ProjectConnection {
        val classLoader = javaClass.classLoader
        val resourceUrl = classLoader.getResource(projectResourceName)
        if (resourceUrl == null) {
            throw IOException("Cannot find resource '$projectResourceName' with $classLoader")
        }
        val projectDir = Paths.get(resourceUrl.toURI()).toFile()
        return withProjectDir(projectDir)
    }

    fun withProjectDir(projectDir: File): ProjectConnection {
        projectDir.copyRecursively(this.projectDir)
        return GradleConnector.newConnector()
                .forProjectDirectory(this.projectDir)
                .connect()
    }

    override fun close() {
        projectDir.deleteRecursively()
    }
}