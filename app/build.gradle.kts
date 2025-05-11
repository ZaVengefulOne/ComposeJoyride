plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.composejoyride"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composejoyride"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }

        ktlint {
            version.set("0.50.0") // Можно явно указать версию CLI
            android.set(true)     // Включает правила для Android
            outputColorName.set("RED")
        }

        detekt {
            buildUponDefaultConfig = true
            allRules = false
            autoCorrect = true
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
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
allprojects {
    configurations.all {
        resolutionStrategy {
            force( "org.xerial:sqlite-jdbc:3.34.0")
        }
    }
}



dependencies {

    // DI
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime.ktx.v281)
    implementation(libs.work.runtime.ktx)
    ksp(libs.hilt.compiler)

    implementation(libs.lifecycle.viewmodel.android)
    
    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.runtime.livedata)

    //Navigation
    implementation(libs.navigation.compose)

    // Network
    implementation(libs.coil.compose)
    implementation (libs.jsoup)

    //Misc
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))



    //UI
    implementation(libs.material3.android)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc12")

    //Core
    implementation(libs.core.ktx)

    //Tests
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom.v20230300))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)




    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)

}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
