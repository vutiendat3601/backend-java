plugins {
  java
}

group = "vn.io.vutiendat3601"
version = "1.0.0"
description = "RMI hands on"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.slf4j:slf4j-api:2.0.18")
  implementation("ch.qos.logback:logback-classic:1.5.38")
  compileOnly("org.projectlombok:lombok:1.18.46")
  annotationProcessor("org.projectlombok:lombok:1.18.46")

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
