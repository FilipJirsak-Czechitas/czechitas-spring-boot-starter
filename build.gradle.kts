plugins {
    `java-library`
    `maven-publish`
}

group = "dev.czechitas.java2"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.0.11")
    implementation("org.springframework:spring-context-support:6.0.11")
    implementation("org.springframework:spring-webmvc:6.0.11")
    implementation("org.freemarker:freemarker:2.3.32")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.9.2")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/FilipJirsak-Czechitas/spring-freemarker-extension")
            credentials {
                username = project.findProperty("gpr.user") as String?
                password = project.findProperty("gpr.key") as String?
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

