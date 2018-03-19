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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

/**
 * Configures the XSD2Java gradle plugin.
 */
class Xsd2JavaPlugin implements Plugin<Project> {

    def final static XML_BIND_VERSION = '2.2.11'

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin('java')) {
            project.apply plugin: 'java'
        }

        addConfigurations(project)
        def xjc = addEnvironment(project, 'xsd2java')

        project.dependencies.add(xjc.compileConfigurationName, "com.sun.xml.bind:jaxb-core:$XML_BIND_VERSION")
        project.dependencies.add(xjc.compileConfigurationName, "com.sun.xml.bind:jaxb-impl:$XML_BIND_VERSION")
        project.dependencies.add(xjc.compileConfigurationName, "javax.xml.bind:jaxb-api:$XML_BIND_VERSION")
        project.dependencies.add(xjc.compileConfigurationName, "com.sun.xml.bind:jaxb-xjc:$XML_BIND_VERSION")
        project.dependencies.add(xjc.compileConfigurationName, "javax.activation:activation:1.1.1")

        project.dependencies.add('xsd2javaExtension', "com.github.jaxb-xew-plugin:jaxb-xew-plugin:1.9")
        project.dependencies.add('xsd2javaExtension', "net.java.dev.jaxb2-commons:jaxb-fluent-api:2.1.8")
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

        project.dependencies.add('compile', sourceSet.output)
        sourceSet
    }
}
