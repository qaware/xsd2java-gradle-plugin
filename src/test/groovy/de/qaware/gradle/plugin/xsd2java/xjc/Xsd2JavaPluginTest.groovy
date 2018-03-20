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

import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

/**
 * Unit test for the {@link Xsd2JavaPlugin}.
 */
class Xsd2JavaPluginTest extends Specification {
    def "apply"() {
        setup:
        def resource = Paths.get(getClass().getResource('/dummySchema.xsd').toURI())

        def project = ProjectBuilder.builder().build()
        project.repositories {
            mavenCentral()
        }

        project.apply plugin: 'de.qaware.gradle.plugin.xsd2java'

        project.xsd2java {
            extension = true
            schemas {
                dummySchema {
                    schemaDirPath resource.parent
                    packageName "dummySchema"
                }
            }
        }

        when:
        project.evaluate()

        then:
        assert project.tasks.xsd2javaDummySchema != null

        when:
        executeTask(project.tasks.compileXsd2javaJava)

        then:
        assert new File(project.buildDir, '/classes/java/xsd2java/dummySchema/BookType.class').exists()
        assert new File(project.buildDir, '/classes/java/xsd2java/dummySchema/BooksType.class').exists()
    }

    /**
     * Executes the given task including all dependencies.
     *
     * @param task the task to execute.
     */
    void executeTask(Task task) {
        task.taskDependencies.getDependencies(task).each {
            subTask -> executeTask(subTask)
        }

        task.execute()
    }
}
