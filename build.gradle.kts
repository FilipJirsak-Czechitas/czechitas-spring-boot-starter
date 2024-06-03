plugins {
    `java-library`
    `maven-publish`
}

group = "dev.czechitas.java2"
version = "0.9.9"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.1.6")
    implementation("org.springframework:spring-context-support:6.1.6")
    implementation("org.springframework:spring-webmvc:6.1.6")
    implementation("org.springframework.boot:spring-boot-starter:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.5")
//    implementation("org.springframework.boot:spring-boot-autoconfigure:3.2.5")
    implementation("org.freemarker:freemarker:2.3.32")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
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
            url = uri("https://maven.pkg.github.com/FilipJirsak-Czechitas/czechitas-spring-boot-starter")
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

