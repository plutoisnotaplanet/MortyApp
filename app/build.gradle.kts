import com.plutoisnotaplanet.buildsrc.Dependencies

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.gms.google-services")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId =  "com.plutoisnotaplanet.mortyapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName  = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.compilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //core
    implementation (Dependencies.Kotlin.stdlib)
    implementation (Dependencies.AndroidX.coreKtx)
    implementation (Dependencies.AndroidX.appcompat)
    implementation (Dependencies.AndroidX.material)
    implementation (Dependencies.AndroidX.tooling)

    //compose
    implementation (Dependencies.AndroidX.Compose.runtime)
    implementation (Dependencies.AndroidX.Compose.compiler)
    implementation (Dependencies.AndroidX.Compose.foundation)
    implementation (Dependencies.AndroidX.Compose.layout)
    implementation (Dependencies.AndroidX.Compose.ui)
    implementation (Dependencies.AndroidX.Compose.material)
    implementation (Dependencies.AndroidX.Compose.animation)
    implementation (Dependencies.AndroidX.Compose.tooling)
    implementation (Dependencies.AndroidX.Compose.activity)
    implementation (Dependencies.AndroidX.Compose.navigationCompose)
    implementation (Dependencies.AndroidX.Compose.constraint)
    implementation (Dependencies.AndroidX.Compose.materialIcons)

    implementation (Dependencies.AndroidX.Compose.accompanistInsets)
    implementation (Dependencies.AndroidX.Compose.accompanistFlow)
    implementation (Dependencies.AndroidX.Compose.orchestra)

    //google services
    implementation (platform(Dependencies.GoogleServices.platformBom))
    implementation (Dependencies.GoogleServices.analytics)
    implementation (Dependencies.GoogleServices.auth)

    //permissions
    implementation (Dependencies.permissions)

    //image loading
    implementation (Dependencies.Coil.landscapist)

    // network
    implementation (Dependencies.Network.retrofit)
    implementation (Dependencies.Network.gson)
    implementation (Dependencies.Network.okhttp)
    implementation (Dependencies.Network.logInterceptor)
    implementation (Dependencies.Network.gsonConverter)
    testImplementation (Dependencies.Network.mock)

    //room
    implementation (Dependencies.Database.runtime)
    implementation (Dependencies.Database.ktx)
    kapt (Dependencies.Database.compiler)

    //lifecycle
    implementation (Dependencies.AndroidX.Lifecycle.runtime)
    implementation (Dependencies.AndroidX.Lifecycle.liveData)
    implementation (Dependencies.AndroidX.Lifecycle.viewModel)

    // coroutines
    implementation (Dependencies.Coroutines.android)
    testImplementation (Dependencies.Coroutines.test)

    // startup
    implementation (Dependencies.startup)

    // hilt
    implementation (Dependencies.Hilt.core)
    implementation (Dependencies.Hilt.composeNavigation)
    kapt (Dependencies.Hilt.compiler)
    kapt (Dependencies.Hilt.androidXCompiler)
    kaptAndroidTest (Dependencies.Hilt.kaptTest)
    androidTestImplementation (Dependencies.Hilt.test)

    //logging
    implementation (Dependencies.timber)

    androidTestImplementation (Dependencies.junit)
    androidTestImplementation (Dependencies.AndroidX.Test.core)
    androidTestImplementation (Dependencies.AndroidX.Test.Ext.junit)
    androidTestImplementation (Dependencies.AndroidX.Test.rules)
}