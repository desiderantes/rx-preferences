plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion = Versions.buildToolsVersion

    compileOptions {
        sourceCompatibility = Versions.java8Version
        targetCompatibility = Versions.java8Version
    }

    buildFeatures {
        viewBinding = true
    }


    defaultConfig {
        applicationId = "com.desiderantes.rx.preferences.sample"
        minSdkVersion(Versions.minSdkVersion) // rxBinding has a minimum of 14
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0.0"
    }

    lintOptions {
        textReport = true
        textOutput("stdout")
        isIgnoreWarnings = true
    }
}

dependencies {
    implementation(project(":rx-preferences"))
    implementation(Deps.rxandroid)
    implementation(Deps.rxbinding)
}
