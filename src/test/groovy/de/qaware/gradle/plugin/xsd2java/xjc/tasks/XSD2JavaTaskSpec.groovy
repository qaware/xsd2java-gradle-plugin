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

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Unit test for the {@link XSD2JavaTask}.
 */
class XSD2JavaTaskSpec extends Specification {
    Project project
    Path resources

    void setup() {
        def resource = Paths.get(getClass().getResource('/dummySchema.xsd').toURI())
        resources = resource.parent

        project = ProjectBuilder.builder().build()
        project.repositories {
            mavenCentral()
        }
        project.apply plugin: 'de.qaware.gradle.plugin.xsd2java'

    }

    def "Generate Sources From XSD"() {
        setup:
        XSD2JavaTask task = project.task("xsd2JavaTask", type: XSD2JavaTask) {
            schemaDirPath = resources.toFile()
            packageName = 'dummySchema'
            arguments = ['-verbose']
        } as XSD2JavaTask

        when:
        task.generateSourcesFromXSD()

        then:
        assert new File(project.buildDir, '/generated-sources/xsd2java/dummySchema/BookType.java').exists()
        assert new File(project.buildDir, '/generated-sources/xsd2java/dummySchema/BooksType.java').exists()
    }

    def "Generate Sources From XSD with Extension"() {
        setup:
        XSD2JavaTask task = project.task("xsd2JavaTask", type: XSD2JavaTask) {
            schemaDirPath = resources.toFile()
            packageName = 'dummySchema'
            extension = true
        } as XSD2JavaTask

        when:
        task.generateSourcesFromXSD()

        then:
        assert new File(project.buildDir, '/generated-sources/xsd2java/dummySchema/BookType.java').exists()
        assert new File(project.buildDir, '/generated-sources/xsd2java/dummySchema/BooksType.java').exists()
    }
}
