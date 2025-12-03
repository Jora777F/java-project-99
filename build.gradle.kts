plugins {
	id("application")
	id("checkstyle")
	id("jacoco")
	id("org.sonarqube") version "7.1.0.6387"
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.github.ben-manes.versions") version "0.53.0"
	id("io.freefair.lombok") version "8.14"
	id("io.sentry.jvm.gradle") version "5.12.2"
}

group = "hexlet.code"
version = "1.0.0-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

application {
	mainClass.set("hexlet.code.AppApplication")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

	compileOnly("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.instancio:instancio-junit:5.5.1")
	implementation("net.datafaker:datafaker:2.4.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
}

sonar {
	properties {
		property("sonar.projectKey", "Jora777F_java-project-99")
		property("sonar.organization", "jora777f")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

sentry {
	// Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
	// This enables source context, allowing you to see your source
	// code as part of your stack traces in Sentry.
	includeSourceContext = true

	org = "pokemon-9p"
	projectName = "java-spring-boot"
	authToken = System.getenv("SENTRY_AUTH_TOKEN")
}