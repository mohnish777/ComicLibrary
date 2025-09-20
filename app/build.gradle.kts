import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.2.10"
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

}

val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties().apply {
    apikeyPropertiesFile.inputStream().use { stream ->
        load(stream)
    }
}

android {
    namespace = "com.example.comicslibrary"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.comicslibrary"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val movieAccessToken = apikeyProperties.getProperty("MOVIE_ACCESS_TOKEN") ?: ""
        val apiKey = apikeyProperties.getProperty("API_KEY") ?: ""
        buildConfigField("String", "MOVIE_ACCESS_TOKEN", movieAccessToken)
        buildConfigField("String", "API_KEY", apiKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //room dependencies
    implementation(libs.androidx.room.runtime)
    implementation(libs.material3)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    // dagger-hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    //navigation
    implementation(libs.androidx.navigation.compose)
    // viewmodel life cycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //retrofit
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.converter.gson)
    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
