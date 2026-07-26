plugins {
  java
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.slf4j:slf4j-api:2.0.18")
  implementation("ch.qos.logback:logback-classic:1.5.38")
  compileOnly("org.projectlombok:lombok:1.18.46")
  annotationProcessor("org.projectlombok:lombok:1.18.46")
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}