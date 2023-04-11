import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    //id("org.jetbrains.kotlinx.dataframe") version "0.8.1"
}

group = "es.ivan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    //JSON
    //implementation("org.json:json:20230227")
    implementation("com.google.code.gson:gson:2.9.1")

    //JAXB
    implementation("org.jvnet.jaxb2.maven2:maven-jaxb2-plugin:0.15.1")
    // Dataframes
    implementation("org.jetbrains.kotlinx:dataframe:0.8.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}