
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id ("kotlin-parcelize")
    kotlin("plugin.serialization") version "2.0.21"
    id ("androidx.navigation.safeargs.kotlin") version "2.8.5"
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.homeworkstbc"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.homeworkstbc"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val nav_version = "2.8.5"
    implementation("androidx.datastore:datastore-preferences:1.1.2")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation (libs.logging.interceptor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    implementation ("com.google.android.material:material:1.9.0")
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}