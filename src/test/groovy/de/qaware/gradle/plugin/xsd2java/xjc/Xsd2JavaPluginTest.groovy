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
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.internal.impldep.org.junit.Rule
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Paths

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

/**
 * Unit test for the {@link Xsd2JavaPlugin}.
 */
class Xsd2JavaPluginTest extends Specification {

    @Rule
    org.gradle.internal.impldep.org.junit.rules.TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        def resource = Paths.get(getClass().getResource('/dummySchema.xsd').toURI())

        buildFile = testProjectDir.newFile('build.gradle')
        buildFile << """
            plugins {
                id 'de.qaware.gradle.plugin.xsd2java'
            }
            
            repositories {
                mavenCentral()
            }

            xsd2java {
                extension = true
                schemas {
                    dummySchema {
                        schemaDirPath "${resource.parent}"
                        packageName "dummySchema"
                    }
                }
            }           
        """
    }

    def "apply"() {
        when:
        def result = GradleRunner.create()
                        .withProjectDir(testProjectDir.root)
                        .withPluginClasspath()
                        .withArguments("compileXsd2javaJava")
                        .build()

        then:
        result.task(":compileXsd2javaJava").outcome == SUCCESS
        result.task(":xsd2javaDummySchema").outcome == SUCCESS
    }
}
