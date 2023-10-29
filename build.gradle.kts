plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

allprojects {
    group = "kr.cosine.home"
    version = "1.0.0"

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.hqservice.kr/repository/maven-public/")
    }
}

subprojects {
    val subProjectName = name
    val buildableProject = subProjectName.contains("bukkit") || subProjectName.contains("bungee")

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
    }

    dependencies {
        compileOnly("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
        compileOnly("kr.hqservice", "hqframework-global-core", "1.0.1-SNAPSHOT")

        testImplementation(kotlin("test"))
    }

    if (buildableProject) {
        dependencies {
            api(project(":core"))
        }
        tasks.shadowJar {
            archiveFileName.set("${rootProject.name}-$subProjectName-${rootProject.version}.jar")
            if (subProjectName == "bukkit") {
                destinationDirectory.set(file("D:\\서버\\1.20.1 - 번지\\로비1\\plugins"))
            }
            if (subProjectName == "bungee") {
                destinationDirectory.set(file("D:\\서버\\1.20.1 - 번지\\프록시\\plugins"))
            }
            // destinationDirectory.set(file(rootProject.projectDir.path + "/build_output"))
        }
    }

    tasks {
        test {
            useJUnitPlatform()
        }
        build {
            dependsOn(shadowJar)
        }
    }
}