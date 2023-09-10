import java.net.URI

plugins {
    kotlin("jvm") version "1.9.0"
    application
    signing
    `maven-publish`
}

group = "org.yeungyeah"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.yeungyeah"
            artifactId = "clipboard-jvm"
            version = version

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = URI.create("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}