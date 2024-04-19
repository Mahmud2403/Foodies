plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.foodies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodies"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.paging)
    implementation(libs.google.material)

    //Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.material3)
    implementation(libs.material)
    implementation(libs.animation)
    implementation(libs.ui)
    implementation(libs.activity)
    implementation(libs.graphics)
    implementation(libs.tooling.preview)
    implementation(libs.material.icons.extended)
    implementation(libs.util)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.tooling)
    debugImplementation(libs.test.manifest)


    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Hilt
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.startup)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.landscapist.coil)


    //OkHttps
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //System UI controller
    implementation(libs.accompanist.systemuicontroller)

    //LottieAnimation
    implementation(libs.lottie.compose.v400)

    //Flow
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    //BottomSheetDialog
    implementation(libs.bottomsheetdialog.compose)


}