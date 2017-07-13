package com.github.zhurlik

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * For testing the gradle task {@link SwaggerTask}.
 *
 * @author zhurlik@gmail.com
 */
class SwaggerTaskTest {

    @Test
    void testEmptyTask() throws Exception {
        final Project project = ProjectBuilder.builder().build()

        // required libs
        project.buildscript {
            repositories {
                mavenCentral()
            }

            // to be able to use swagger
            dependencies {
                classpath group: 'io.swagger', name: 'swagger-core', version: '1.5.10'
                classpath group: 'com.github.kongchen', name: 'swagger-maven-plugin', version: '3.1.4'
            }
        }

        project.pluginManager.apply 'com.github.zhurlik.swagger'

        SwaggerTask task = project.task('customTask', type: SwaggerTask)

        project['customTask'].execute()
    }
}
