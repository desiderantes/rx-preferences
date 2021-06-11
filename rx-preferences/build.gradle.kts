plugins {
    id("com.android.library")
    `maven-publish`
    signing
}


android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion = Versions.buildToolsVersion

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
    }

    lintOptions {
        textReport = true
        textOutput("stdout")
    }
}

fun isReleaseBuild(): Boolean {
    return (project.findProperty("VERSION_NAME") as String?)?.contains("SNAPSHOT")?.not() ?: false
}



fun getStringProperty(propertyName: String) : String? {
    return project.findProperty(propertyName) as String?
}

dependencies {
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
                    username =
                        getStringProperty("REGISTRY_USERNAME") ?: System.getenv("REGISTRY_USERNAME")
                    password = getStringProperty("REGISTRY_TOKEN") ?: System.getenv("REGISTRY_TOKEN")
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
