package com.plutoisnotaplanet.buildsrc

object Dependencies {

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.3.0"
    const val junit = "junit:junit:4.13"

    const val exoplayer = "com.google.android.exoplayer:exoplayer:2.11.8"

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    const val startup = "androidx.startup:startup-runtime:1.1.1"

    const val permissions = "com.markodevcic:peko:2.2.0"

    object GoogleServices {
        const val core = "com.google.gms:google-services:4.3.13"
        const val platformBom = "com.google.firebase:firebase-bom:31.0.1"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val auth = "com.google.firebase:firebase-auth"
    }

    object Hilt {
        private const val hiltCoreVersion = "2.44"
        private const val hiltVersion = "1.0.0"
        private const val hiltComposeVersion = "1.0.0"
        const val gradleHilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltCoreVersion"
        const val core = "com.google.dagger:hilt-android:$hiltCoreVersion"
        const val composeNavigation = "androidx.hilt:hilt-navigation-compose:$hiltComposeVersion"
        const val compiler = "com.google.dagger:hilt-compiler:$hiltCoreVersion"
        const val androidXCompiler = "androidx.hilt:hilt-compiler:$hiltVersion"
        const val test = "com.google.dagger:hilt-android-testing:$hiltCoreVersion"
        const val kaptTest = "com.google.dagger:hilt-compiler:$hiltCoreVersion"
    }

    object Network {
        private const val okhttpVersion = "4.7.2"
        private const val retrofitVersion = "2.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val logInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val gson = "com.google.code.gson:gson:$retrofitVersion"
        const val mock = "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
    }

    object Coil {
        private const val version = "1.3.2"
        private const val landscapistVersion = "1.4.3"
        private const val coilVersion = "2.2.2"
        const val sdk = "io.coil-kt:coil-compose:$version"
        const val landscapist = "com.github.skydoves:landscapist-coil:${landscapistVersion}"

        const val coil = "io.coil-kt:coil-compose:$coilVersion"
    }


    object Database {
        private const val roomVersion = "2.4.3"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
    }

    object Kotlin {
        private const val version = "1.7.20"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.5.1"
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val material = "com.google.android.material:material:1.6.1"
        const val tooling = "androidx.ui:ui-tooling:1.0.0-alpha07"

        object Lifecycle {
            private const val lifecycleVersion = "2.5.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        }

        object Compose {
            const val version = "1.3.0"

            const val compilerVersion = "1.3.2"
            const val activityComposeVersion = "1.6.0"
            private const val accompanistVersion = "0.25.1"
            private const val orchestraVersion = "1.1.1"
            private const val navigationComposeVersion = "2.5.1"
            private const val constraintVersion = "1.0.1"

            const val materialIcons = "androidx.compose.material:material-icons-extended:$version"
            const val navigationCompose =  "androidx.navigation:navigation-compose:$navigationComposeVersion"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val compiler = "androidx.compose.compiler:compiler:$compilerVersion"
            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"
            const val ui = "androidx.compose.ui:ui:${version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val animation = "androidx.compose.animation:animation:${version}"
            const val activity = "androidx.activity:activity-compose:${activityComposeVersion}"
            const val constraint = "androidx.constraintlayout:constraintlayout-compose:$constraintVersion"

            const val accompanistInsets  ="com.google.accompanist:accompanist-insets:$accompanistVersion"
            const val accompanistFlow = "com.google.accompanist:accompanist-flowlayout:$accompanistVersion"
            const val orchestra = "com.github.skydoves:orchestra-balloon:$orchestraVersion"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }
}