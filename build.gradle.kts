import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "ru.cool"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    shadowJar {
        archiveFileName.set("app.jar")
        manifest{
            attributes["Main-Class"] = "ru.cool.sectorsite.SectorSiteApplicationKt"
        }
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("buildFrontend", Exec::class) {
    group = "frontend"
    workingDir("src/frontend/sector-frontend")
    commandLine = listOf("cmd.exe", "/c", "npm", "run", "build")
}

tasks.register("runFrontend", Exec::class){
    group = "frontend"
    workingDir("src/frontend/sector-frontend")
    commandLine = listOf("cmd.exe", "/c", "npm", "start")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveBaseName.set("sector-backend")
    archiveVersion.set("")
    archiveClassifier.set("")
}

tasks.register<Exec>("containerize"){
    dependsOn("bootJar", "buildFrontend")

    commandLine = listOf("docker-compose", "up", "--build")

}
