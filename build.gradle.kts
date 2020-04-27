import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("jvm") version "1.3.72"
    id("me.champeau.gradle.jmh") version "0.5.0"
}

repositories {
    jcenter()
}

jmh {
    jmhVersion = "1.23"
    failOnError = true
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.openjdk.jmh:jmh-core:${jmh.jmhVersion}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

sourceSets["jmh"].java.srcDirs("src")

// NOTE: To build benchmarks.jar use: gradlew --no-daemon clean jmhJar
val jmhJar by tasks.getting(Jar::class) {
    archiveBaseName.set("benchmarks")
    archiveClassifier.set("")
    archiveVersion.set("")
    destinationDirectory.set(rootDir)
}