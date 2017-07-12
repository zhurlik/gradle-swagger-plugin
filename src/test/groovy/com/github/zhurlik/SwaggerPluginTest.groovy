package com.github.zhurlik

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.internal.plugins.PluginApplicationException
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


/**
 * A set of unit tests to check {@link SwaggerPlugin}.
 *
 * @author zhurlik@gmail.com
 */
class SwaggerPluginTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none()

    @Test
    void testWithSwaggerDependencies() {
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

    }

    @Test
    void testNoSwaggerDependencies() {
        expectedEx.expect(PluginApplicationException.class)
        expectedEx.expectMessage("Failed to apply plugin [id 'com.github.zhurlik.swagger']")
        expectedEx.expectCause(CoreMatchers.instanceOf(GradleException.class))

        final Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.github.zhurlik.swagger'
    }
}
