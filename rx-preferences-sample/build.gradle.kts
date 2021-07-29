plugins {
    id("com.android.application")
}

android {
    compileSdk = Versions.compileSdkVersion
    buildToolsVersion = Versions.buildToolsVersion

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    buildFeatures {
        viewBinding = true
    }


    defaultConfig {
        applicationId = "com.desiderantes.rx.preferences.sample"
        minSdk = Versions.minSdkVersion // rxBinding has a minimum of 14
        targetSdk = Versions.targetSdkVersion
        versionCode = 1
        versionName = "1.0.0"
    }

    lint {
        textReport = true
        textOutput("stdout")
        isIgnoreWarnings = false
    }
}

dependencies {
    implementation(project(":rx-preferences"))
    implementation(Deps.rxandroid)
    implementation(Deps.rxbinding)
}
