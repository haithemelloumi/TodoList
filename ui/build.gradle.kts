plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Add kapt plugin for annotation processing
    alias(libs.plugins.kotlin.kapt)
    // Add Hilt plugin
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.helloumi.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":domain"))

    //------------------------------------- Jetpack Compose -------------------------------------//
    ///////////////////////////////////////////// BOM /////////////////////////////////////////////
    val composeBom = platform(libs.compose.bom)

    implementation(platform(composeBom))
    androidTestImplementation(platform(composeBom))

    // Ui
    implementation(libs.compose.ui)
    implementation(libs.activity.compose)
    implementation(libs.androidx.compose.runtime)

    // Navigation
    implementation(libs.navigation.compose)

    // ViewModel
    implementation(libs.lifecycle.viewmodel.compose)

    // Material Design 3
    implementation(libs.material3)

    // Tooling
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)

    ///////////////////////////////////////////// BOM /////////////////////////////////////////////

    // Accompanist
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.placeholder.material)

    // Hilt compose
    implementation(libs.hilt.navigation.compose)

    // ConstraintLayout for Compose
    implementation(libs.constraintlayout.compose)

    // Coil (Compose extension lib --> AsyncImage = load image from url)
    implementation(libs.coil.compose)

    //------------------------------------- Jetpack Compose -------------------------------------//

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.kotlinx.coroutines.core)

    // Testing
    implementation(libs.junit.ktx)
    androidTestImplementation(libs.junit.ktx)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.core)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.core.testing)
}