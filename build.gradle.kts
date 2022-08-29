plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://papermc.io/repo/repository/maven-public/")
        }
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
        implementation("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
        kapt("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

gradle.buildFinished {
    project.buildDir.deleteRecursively()
}
