buildscript {

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
    }
}

plugins {
    id("net.ltgt.errorprone") version "1.2.1"
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
    group = project.property("GROUP") as String
    version = project.property("VERSION_NAME") as String
}

