plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0" }

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // <- match your installed JDK
    }
}

javafx {
    version = "17.0.10"
    modules("javafx.controls", "javafx.fxml")
}

application {
    // If you have module-info.java, also set mainModule below.
    mainClass.set("org.example.App")
    //mainModule.set("org.example")
    // mainModule.set("edu.bsu.cs") // <- uncomment if you have a module-info.java named 'module edu.bsu.cs { ... }'
}