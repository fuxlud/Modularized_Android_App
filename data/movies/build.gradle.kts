import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use(::load)
    }
}

val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY", "")

android {
    namespace = "com.example.movies.data.movies"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        buildConfigField(
            type = "String",
            name = "TMDB_API_KEY",
            value = "\"${tmdbApiKey.replace("\\", "\\\\").replace("\"", "\\\"")}\""
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain:movies"))
    implementation(libs.koin.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)

    testImplementation(libs.junit)
}
