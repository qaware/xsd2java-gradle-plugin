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

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Configures a specific schema convertion.
 */
class Xsd2JavaTaskConfig {
    /**
     * The task names
     */
    String name
    /**
     * The base directory where the xsd schemas are located.
     */
    Path schemaDirPath = Paths.get("src/main/resources/")

    /**
     * The package name for all the generated java sources.
     */
    String packageName

    /**
     * Init a new task.
     * @param name the name
     */
    Xsd2JavaTaskConfig(String name) {
        this.name = name
    }

    /**
     * @param schemaDirPath the schema dir
     */
    void schemaDirPath(String schemaDirPath) {
        this.schemaDirPath = Paths.get(schemaDirPath)
    }

    /**
     * @param schemaDirPath the schema dir
     */
    void schemaDirPath(File schemaDirPath) {
        this.schemaDirPath = schemaDirPath.toPath()
    }

    /**
     * @param schemaDirPath the schema dir
     */
    void schemaDirPath(Path schemaDirPath) {
        this.schemaDirPath = schemaDirPath
    }

    /**
     * @param packageName The designated package name for the generated schema.
     */
    void packageName(String packageName) {
        this.packageName = packageName
    }
}