plugins {
    kotlin("jvm") version "1.3.61" apply false
    id("com.github.johnrengelman.shadow") version "5.2.0" apply false
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
}
