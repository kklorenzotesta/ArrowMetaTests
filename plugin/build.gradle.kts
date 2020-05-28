/*buildscript {
    repositories {
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
    dependencies {
        classpath("io.arrow-kt:gradle-plugin:1.3.61-SNAPSHOT")
    }
}

apply(plugin = "io.arrow-kt.arrow")*/

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("io.arrow-kt:compiler-plugin:1.3.61-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.61")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        configurations = listOf(project.configurations.compileOnly.get())
        dependencies {
            exclude("org.jetbrains.kotlin:kotlin-stdlib")
            exclude("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.61")
        }
    }
}
