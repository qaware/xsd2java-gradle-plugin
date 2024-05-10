/*
 *    Copyright (C) 2018 QAware GmbH
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package de.qaware.gradle.plugin.xsd2java.xjc

import de.qaware.gradle.plugin.xsd2java.xjc.tasks.XSD2JavaTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.SourceSet

/**
 * Configures the XSD2Java gradle plugin.
 */
class Xsd2JavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin('java')) {
            project.apply plugin: 'java'
        }
        def xsd2JavaExtension = new Xsd2JavaPluginExtension(project)
        project.extensions.add(Xsd2JavaPluginExtension.NAME, xsd2JavaExtension)

        addConfigurations(project)
        def xjc = addEnvironment(project, 'xsd2java')

        addsDependencies(project, xjc)

        project.afterEvaluate {
            xsd2JavaExtension.schemas.asMap.forEach({ name, config ->
                configureXsd2javaTask(project, xsd2JavaExtension, name, config)
            })
        }
    }

    /**
     * Configures the xsd2java tasks for every source directory.
     *
     * @param project The project to configure.
     * @param name The name suffix for the source directory task.
     * @param config The configuration for the source directory.
     */
    private static void configureXsd2javaTask(Project project, Xsd2JavaPluginExtension xsd2JavaExtension, String name, config) {
        Task xsd2Java = project.task("xsd2java${name.capitalize()}", type: XSD2JavaTask) {
            schemaDirPath = config.schemaDirPath.toFile()
            packageName = config.packageName
            outputDir = xsd2JavaExtension.outputDir
            arguments = xsd2JavaExtension.arguments
            extension = xsd2JavaExtension.extension
        }
        project.tasks.compileXsd2javaJava.dependsOn xsd2Java
    }

    /**
     * Adds the required dependencies to the specific configurations.
     *
     * @param project The project to add the dependencies to.
     * @param xjc The source set used to add the dependencies to.
     */
    private static void addsDependencies(Project project, SourceSet xjc) {
        project.dependencies.add(xjc.implementationConfigurationName, "org.glassfish.jaxb:jaxb-core:4.0.5")
        project.dependencies.add(xjc.implementationConfigurationName, "org.glassfish.jaxb:jaxb-runtime:4.0.5")
        project.dependencies.add(xjc.implementationConfigurationName, "jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
        project.dependencies.add(xjc.implementationConfigurationName, "org.glassfish.jaxb:jaxb-xjc:4.0.5")
        project.dependencies.add(xjc.implementationConfigurationName, "javax.activation:activation:1.1.1")

        project.dependencies.add('xsd2javaExtension', "com.github.jaxb-xew-plugin:jaxb-xew-plugin:2.1")
        project.dependencies.add('xsd2javaExtension', "org.jvnet.jaxb2_commons:jaxb2-fluent-api:3.0")
    }

    /**
     * Adds the configurations required by the plugin.
     *
     * @param project the project to add the configurations to.
     */
    private static void addConfigurations(Project project) {
        project.configurations.maybeCreate('xsd2java')
        project.configurations.maybeCreate('xsd2javaExtension')
    }

    /**
     * Initializes the given environment.
     *
     * @param project The project to initialize the environment for.
     * @param environment The environments name.
     * @return The source set for the created environment.
     */
    private static SourceSet addEnvironment(Project project, String environment) {
        def sourceSet = project.sourceSets.findByName(environment)

        if (sourceSet == null) {
            sourceSet = project.sourceSets.create(environment, {
                java.srcDirs += project.file("$project.buildDir/generated-sources/${environment}")
            })
        }

        project.dependencies.add('implementation', sourceSet.output)
        sourceSet
    }
}
