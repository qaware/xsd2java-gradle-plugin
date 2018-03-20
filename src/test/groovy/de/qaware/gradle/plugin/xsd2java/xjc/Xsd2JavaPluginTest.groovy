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
