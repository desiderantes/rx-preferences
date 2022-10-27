plugins {
    id("com.android.library")
    `maven-publish`
    signing
}


android {
    compileSdk = Versions.compileSdkVersion
    buildToolsVersion = Versions.buildToolsVersion

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    defaultConfig {
        minSdk = Versions.minSdkVersion
        multiDexEnabled = true
    }

    lint {
        textReport = true
    }
    namespace = "com.desiderantes.rx.preferences3"
}




dependencies {
    coreLibraryDesugaring(Deps.desugaring)
    implementation(Deps.annotations)
    api(Deps.rxjava3)

    testImplementation(Deps.junit)
    testImplementation(Deps.assertj)
    testImplementation(Deps.testcore)
    testImplementation(Deps.robolectric)
}
val sourceJar = tasks.create<Jar>("androidSourcesJar") {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}
afterEvaluate {

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/desiderantes/rx-preferences")
                credentials {
                    username = getStringProperty("REGISTRY_USERNAME")!!
                    password = getStringProperty("REGISTRY_TOKEN")!!
                }
            }
        }
        publications {
            create<MavenPublication>("gpr") {
                from(components["release"])
                pom {
                    name.set(getStringProperty("POM_NAME")!!)
                    packaging = getStringProperty("POM_PACKAGING")!!
                    description.set(getStringProperty("POM_DESCRIPTION")!!)
                    url.set(getStringProperty("POM_URL")!!)
                    groupId = getStringProperty("GROUP")!!
                    artifactId = getStringProperty("POM_ARTIFACT_ID")!!
                    version = getStringProperty("VERSION_NAME")!!
                    scm {
                        url.set(getStringProperty("POM_SCM_URL")!!)
                        connection.set(getStringProperty("POM_SCM_CONNECTION")!!)
                        developerConnection.set(getStringProperty("POM_SCM_DEV_CONNECTION")!!)
                    }

                    licenses {
                        license {
                            name.set(getStringProperty("POM_LICENCE_NAME")!!)
                            url.set(getStringProperty("POM_LICENCE_URL")!!)
                            distribution.set(getStringProperty("POM_LICENCE_DIST")!!)
                        }
                    }

                    developers {
                        (getStringProperty("POM_DEVELOPERS")!!).split(';').forEach {
                            val (devId, devName, devEmail) = it.split(',')
                            developer {
                                id.set(devId)
                                name.set(devName)
                                email.set(devEmail)
                            }
                        }
                    }
                }
                artifactId = getStringProperty("POM_ARTIFACT_ID")!!
                //artifact("$buildDir/outputs/aar/${getStringProperty("POM_ARTIFACT_ID")}-release.aar")
                artifact(sourceJar)
            }
        }
    }

    signing {
        isRequired = isReleaseBuild() && gradle.taskGraph.hasTask("publish")
        sign(publishing.publications["gpr"])
    }
}
