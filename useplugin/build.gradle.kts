plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    application
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "com.kklorenzotesta.arrowmeta.fu.FirstUseKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("io.arrow-kt:arrow-annotations:0.10.5")
    api(project(":plugin", configuration = "shadow"))
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xplugin=${project.rootDir.absolutePath}/plugin/build/libs/plugin-all.jar")
        }
    }
}
