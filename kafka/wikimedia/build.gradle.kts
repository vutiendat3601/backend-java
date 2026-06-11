plugins {
	java
}

group = "vn.io.vutiendat3601"
version = "1.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.kafka:kafka-clients:4.3.0")
  implementation("com.squareup.okhttp3:okhttp:5.4.0")
  implementation("com.launchdarkly:okhttp-eventsource:4.3.0")
  implementation("org.slf4j:slf4j-api:2.0.18")
  implementation("org.slf4j:slf4j-simple:2.0.18")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
