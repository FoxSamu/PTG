/*
 * Copyright 2020-2026 O. W. Nankman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
 * AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

plugins {
    java

    `maven-publish`
}

group = "dev.runefox"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("it.unimi.dsi:fastutil:8.2.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = "ptg"
            version = "${project.version}"

            from(components["java"])
        }
    }
    repositories {
        if ("rfxMavenUser" in properties && "rfxMavenPass" in properties) {
            maven {
                name = "RunefoxMaven"
                url = uri("https://mvn.runefox.dev/releases")
                credentials {
                    username = properties["rfxMavenUser"].toString()
                    password = properties["rfxMavenPass"].toString()
                }
            }
        }
    }
}
