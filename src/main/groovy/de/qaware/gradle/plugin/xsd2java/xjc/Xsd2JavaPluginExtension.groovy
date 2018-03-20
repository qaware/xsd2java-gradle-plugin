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

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * The extension object for the build.gradle script to configure the xsd2java task.
 */
class Xsd2JavaPluginExtension {
    /**
     * Name of this extension for this plugin.
     */
    public static final String NAME = 'xsd2java'

    /**
     * The base project this extension is active in.
     */
    final Project project

    /**
     * The different xsd schema configurations.
     */
    final NamedDomainObjectContainer<Xsd2JavaTaskConfig> schemas

    /**
     * Should add the xjc configuration to the ant runner classpath.
     */
    Boolean extension = false

    /**
     * Additional arguments for the xsd2java ant task.
     */
    List<String> arguments = []

    /**
     * The target path write the java sources to.
     */
    File outputDir = project.file("${project.buildDir}/generated-sources/xsd2java")

    /**
     * Initializes the new xsd2java extension.
     *
     * @param project the project this extension is added to.
     */
    Xsd2JavaPluginExtension(Project project) {
        this(project, project.container(Xsd2JavaTaskConfig.class))
    }

    /**
     * Setup the xsd plugin extension.
     *
     * @param project the project
     * @param schemas the schema container.
     */
    Xsd2JavaPluginExtension(Project project, NamedDomainObjectContainer<Xsd2JavaTaskConfig> schemas) {
        this.project = project
        this.schemas = schemas
    }

    /**
     * @param block the block
     */
    void schemas(Closure<?> block) {
        schemas.configure(block)
    }

    /**
     * @param block the block
     */
    void schemas(Action<NamedDomainObjectContainer<Xsd2JavaTaskConfig>> block) {
        block.execute(this.schemas)
    }

    /**
     * @return the schema container.
     */
    NamedDomainObjectContainer<Xsd2JavaTaskConfig> getSchemas() {
        return schemas
    }

    /**
     * @param extension the new extension value.
     */
    void extension(Boolean extension) {
        this.extension = extension
    }

    /**
     * @param arguments The arguments passed to the task
     */
    void arguments(List<String> arguments) {
        this.arguments = arguments
    }

    /**
     * @param outputDir The new output dir
     */
    void outputDir(File outputDir) {
        this.outputDir = outputDir
    }
}
