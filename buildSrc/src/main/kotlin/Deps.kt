import org.gradle.api.Project

object Deps {
    const val desugaring = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val annotations =  "androidx.annotation:annotation:1.5.0"
    const val rxjava3 =  "io.reactivex.rxjava3:rxjava:3.1.5"
    const val rxandroid =  "io.reactivex.rxjava3:rxandroid:3.0.0"
    const val rxbinding =  "com.jakewharton.rxbinding4:rxbinding:4.0.0"
    const val junit =  "junit:junit:4.12"
    const val assertj =  "org.assertj:assertj-core:3.23.1"
    const val robolectric =  "org.robolectric:robolectric:4.9"
    const val testcore =  "androidx.test:core:1.3.0"
}

fun Project.getStringProperty(propertyName: String) : String? {
    return findProperty(propertyName) as String? ?: System.getenv(propertyName) ?: "undefined"
}

fun Project.isReleaseBuild(): Boolean {
    return (findProperty("VERSION_NAME") as String?)?.contains("SNAPSHOT")?.not() ?: false
}