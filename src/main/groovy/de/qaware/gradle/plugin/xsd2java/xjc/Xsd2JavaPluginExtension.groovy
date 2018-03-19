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
/**
 * The extension object for the build.gradle script to configure the xsd2java task.
 */
class Xsd2JavaPluginExtension {
    /**
     * The base directory where the xsd schemas are located.
     */
    String schemaDirPath = 'src/main/resources/'
    /**
     * The package name for all the generated java sources.
     */
    String packageName
    /**
     * Should add the xjc configuration to the ant runner classpath.
     */
    Boolean extension = false
    /**
     * Additional arguments for the xsd2java ant task.
     */
    def arguments = []
    /**
     * Do not generate these classes. They are user defined.
     */
    String userDefinedClasses = ""
    /**
     * The full base directory where the xsd schemas are located.
     */
    def inputDir = project.file("$project.projectDir/${schemaDirPath}")
    /**
     * The target path write the java sources to.
     */
    def outputDir = project.file("$project.buildDir/generated-sources/xjc")
}
