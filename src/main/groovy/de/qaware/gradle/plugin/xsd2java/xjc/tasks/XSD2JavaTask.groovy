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
package de.qaware.gradle.plugin.xsd2java.xjc.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.*

/**
 * Generates java sources from a xsd schema.
 */
class XSD2JavaTask extends DefaultTask {

    /**
     * The path were all schemas are located.
     */
    @Input
    String schemaDirPath = 'src/main/resources/'

    /**
     * The package name for the generated java sources.
     */
    @Input
    @Optional
    String packageName

    /**
     * Adds the xjc build configuration to the ant task as extension.
     */
    @Input
    @Optional
    Boolean extension = false

    /**
     * Additionally arguments passed to the ant task.
     */
    @Input
    @Optional
            arguments = []

    /**
     * The full path for the where the input files are located. Also used to identify changes on the schemas.
     */
    @InputDirectory
    def inputDir = project.file("$project.projectDir/$schemaDirPath")

    /**
     * The output directory.
     */
    @OutputDirectory
    def outputDir = project.file("$project.buildDir/generated-sources/xsd2java")

    /**
     * The actually action for generating the java sources.
     */
    @TaskAction
    @SuppressWarnings("GroovyUnusedDeclaration")
    void generateSourcesFromXSD() {
        ant.taskdef(
                name: 'xjc',
                classname: 'com.sun.tools.xjc.XJCTask',
                classpath: project.configurations.xsd2javaCompile.asPath
        )

        logging.level = LogLevel.DEBUG

        ant.xjc(
                removeOldOutput: 'yes',
                destdir: outputDir,
                package: packageName,
                extension: extension,
                encoding: 'UTF-8'
        ) {
            schema(dir: inputDir, includes: '**/*.xsd')

            if (extension) {
                classpath(path: project.configurations.xsd2javaExtension.asPath)
            }

            for (argument in arguments) {
                arg(line: argument)
            }
        }
    }

}
