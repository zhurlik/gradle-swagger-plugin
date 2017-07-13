package com.github.zhurlik

import com.github.kongchen.swagger.docgen.mavenplugin.ApiDocumentMojo
import com.github.kongchen.swagger.docgen.mavenplugin.ApiSource
import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * A gradle task that uses Swagger maven plugin for building json.
 * Classes for scanning can be passed through a list of dirs where they are located.
 *
 * @author zhurlik@gmail.com
 *
 */
@Slf4j('logger')
class SwaggerTask extends DefaultTask {

    /**
     * A list of directories with java classes for scanning.
     */
    List<File> src = []

    /**
     * A list of ApiSources objects for Swagger maven plugin.
     */
    List<ApiSource> apiSources = []

    @TaskAction
    def apply() {
        logger.info "Making Swagger json..."

        // a trick to have all needed project classes in the classpath
        src.each {
            project.buildscript.classLoader.addURL it.toURI().toURL()
        }

        // maven plugin
        final ApiDocumentMojo mavenTask = Class.forName(
                'com.github.kongchen.swagger.docgen.mavenplugin.ApiDocumentMojo',
                true, project.buildscript.classLoader).newInstance(apiSources: apiSources)

        mavenTask.execute()
    }
}
