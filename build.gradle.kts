buildscript {

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0")
    }
}

plugins {
    id("net.ltgt.errorprone") version "2.0.1"
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
    group = project.property("GROUP") as String
    version = project.property("VERSION_NAME") as String
}

