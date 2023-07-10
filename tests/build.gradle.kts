plugins {
    kotlin("jvm") version "1.8.21"
}

group = "no.tanettrimas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(kotlin("test"))
    testImplementation(project(mapOf("path" to ":")))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}