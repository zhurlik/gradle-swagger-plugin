package com.github.zhurlik

import groovy.util.logging.Slf4j
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A gradle plugin that adds at least the buildscript with required classpath.
 * To be able to use swagger-maven-plugin classes in the gradle script.
 *
 * @author zhurlik@gmail.com
 */
@Slf4j('logger')
class SwaggerPlugin implements Plugin<Project> {
    void apply(final Project project) {
        logger.info "Swagger Plugin..."

        checkRequiredLibs(project)

        logger.info "Swagger libs are loaded"
    }

    /**
     * Swagger libs should be added to buildscript classpath.
     *
     * @param project
     */
    private void checkRequiredLibs(Project project) {
        def swaggerLibs = project.buildscript.configurations.classpath.files.findAll {
            it.name.contains('swagger') && !it.name.contains('gradle-swagger-plugin')
        }

        if (swaggerLibs.isEmpty()) {
            logger.warn """
Looks like there are no required Swagger dependencies. Please update and add to your buildscript block the following lines:

buildscript {
            repositories {
                mavenCentral()
            }

            // to be able to use swagger
            dependencies {
                classpath group: 'io.swagger', name: 'swagger-core', version: '1.5.+'
                classpath group: 'com.github.kongchen', name: 'swagger-maven-plugin', version: '3.1.+'
            }
        }
"""
            throw new GradleException('You have to add Swagger libs into buildscript classpath')
        }
    }
}
