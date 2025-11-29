val springBootVersion = "3.5.7"
val jUnitVersion = "6.0.1"
val datafakerVersion = "2.5.3"
val jUnitAssertjVersion = "5.1.0"
val instancioJunitVersion = "5.5.1"

plugins {
    java
    jacoco
    id("org.sonarqube") version "7.1.0.6387"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.3"
    id("io.freefair.lombok") version "9.0.0-rc2"
}

group = "ru.naissur"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("net.datafaker:datafaker:$datafakerVersion")

    runtimeOnly("com.h2database:h2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(platform("org.junit:junit-bom:$jUnitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:$jUnitAssertjVersion")
    testImplementation("org.instancio:instancio-junit:$instancioJunitVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

sonar {
    properties {
        property("sonar.projectKey", "YmraNaissur_hexlet-spring-blog")
        property("sonar.organization", "ymranaissur")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.test", "src/test")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.exclusions", "**/verification-metadata.xml")
    }
}