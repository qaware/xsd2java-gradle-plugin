[![Download](https://api.bintray.com/packages/qaware-oss/gradle-plugins/xsd2java-gradle-plugin/images/download.svg) ](https://bintray.com/qaware-oss/gradle-plugins/xsd2java-gradle-plugin/_latestVersion)
[![Apache License 2](http://img.shields.io/badge/license-ASF2-blue.svg)](https://github.com/qaware/xsd2java-gradle-plugin/blob/master/LICENSE)

# XSD2Java Gradle Plugin

The XSD2Java Gradle Plugin generates java classes from an existing XSD schema. For this it uses the existing ANT task.

## Usage

Build script snippet for use in all Gradle versions:
```groovy
buildscript {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath 'de.qaware.gradle.plugin:xsd2java-gradle-plugin:2.0.0'
    }
}

apply plugin: 'de.qaware.gradle.plugin.xsd2java'
```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:
```groovy
plugins {
    id 'de.qaware.gradle.plugin.xsd2java' version '2.0.0'
}
```
## Tasks

The plugin defines the following tasks:

Task name | Depends on | Type | Description
--------- | ---------- | ---- | ---
`xsd2java`| -          | `XSD2JavaTask` | Generates all the types as java classes from a xsd schema.

## Configurations

The plugin defines the following configurations:

Configuration Name  | Description
------------------- | ---
`xsd2java`          | Used for dependencies that needed by the generated sources.
`xsd2javaExtension` | Used for extensions for the XJC ANT task.

## Extension Properties

The plugin defines the following extension properties in the `xsd2java` closure:

Property name | Type   | Default value | Description
------------- | ------ | - | ---
`schemas`     | `Container<Xsd2JavaTaskConfig>` | - | Contains the configurations for every base directory with schemas.
`extension`   | `Boolean` | - | Should the ant task load extensions defined in the configuration `xsd2javaExtension`. Default false
`arguments`   | `List<String>` | - | A list of arguments passed to the ant task
`outputDir`   | `File` | - | The output directory for the generated sources.

### Example

The following example show the full extension configuration:
```groovy
plugins {
    id 'de.qaware.gradle.plugin.xsd2java' version '2.0.0'
}

xsd2java {
    schemas {
        dummy {
            schemaDirPath 'src/main/resources/xsd'
            packageName 'de.qaware.gradle.plugin.xsd2java.dummy.xsd'
        }
    }
    
    extension true
    arguments ['-verbose']
    outputDir "${project.buildDir}/generated-sources/xsd2java"
}
```

In case you are using the Kotlin DSL for you build script, the above example looks as follows:

```kotlin
plugins {
    id("de.qaware.gradle.plugin.xsd2java") version "2.0.0"
}

xsd2java {
    schemas {
        create("dummy") {
            schemaDirPath = file("src/main/resources/xsd").toPath()
            packageName = "de.qaware.gradle.plugin.xsd2java.dummy.xsd"
        }
    }
    
    extension = true
    arguments = listOf("-verbose")
    outputDir = file("${project.buildDir}/generated-sources/xsd2java")
}
```

## Maintainer

Christian Fritz (@chrfritz)

## License

This software is provided under the Apache License, Version 2.0 license. See the `LICENSE` file for details.
