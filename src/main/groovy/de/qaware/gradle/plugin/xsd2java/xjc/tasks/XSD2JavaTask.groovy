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

class XSD2JavaTask extends DefaultTask {

    @Input
    String schemaDirPath = 'src/main/resources/'

    @Input
    @Optional
    String packageName

    @Input
    @Optional
    Boolean extension = false

    @Input
    @Optional
            arguments = []

    @InputDirectory
    def inputDir = project.file("$project.projectDir/src/main/resources")

    @OutputDirectory
    def outputDir = project.file("$project.buildDir/generated-sources/xjc")


    @TaskAction
    public void generateSourcesFromXSD() {
        ant.taskdef(
                name: 'xjc',
                classname: 'com.sun.tools.xjc.XJCTask',
                classpath: project.configurations.jaxb.asPath
        )

        logging.level = LogLevel.DEBUG

        ant.xjc(
                removeOldOutput: 'yes',
                destdir: outputDir,
                package: packageName,
                extension: extension,
                encoding: 'UTF-8'
        ) {
            schema(dir: schemaDirPath, includes: '**/*.xsd')

            if (extension) {
                classpath(path: project.configurations.xjc.asPath)
            }

            for (argument in arguments) {
                arg(line: argument)
            }
        }
    }

}
