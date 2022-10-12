package com.plutoisnotaplanet.buildsrc

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.2.1"
    const val junit = "junit:junit:4.13"

    const val exoplayer = "com.google.android.exoplayer:exoplayer:2.11.8"

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    const val startup = "androidx.startup:startup-runtime:1.1.1"

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
        private const val sandwichVersion = "1.2.1"
        private const val okhttpVersion = "4.7.2"
        private const val retrofitVersion = "2.9.0"
        const val sandwich = "com.github.skydoves:sandwich:$sandwichVersion"
        const val logInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        const val gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val mock = "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
    }

    object Coil {
        private const val version = "1.3.2"
        const val sdk = "io.coil-kt:coil-compose:$version"
    }


    object Database {
        private const val roomVersion = "2.4.3"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
    }

    object Kotlin {
        private const val version = "1.5.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha01"
        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha02"
        const val material = "com.google.android.material:material:1.2.0"
        const val tooling = "androidx.ui:ui-tooling:1.0.0-alpha07"

        object Lifecycle {
            private const val lifecycleVersion = ""
            implementation "androidx.lifecycle:lifecycle-extensions:$versions.lifecycleVersion"
            implementation "androidx.lifecycle:lifecycle-runtime-ktx:$versions.lifecycleVersion"
            implementation "androidx.lifecycle:lifecycle-livedata-ktx:$versions.lifecycleVersion"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$versions.lifecycleVersion"
        }

        object Compose {
            const val snapshot = ""
            const val version = "1.0.2"
            const val activityComposeVersion = "1.3.0-alpha06"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val compiler = "androidx.compose.compiler:compiler:$version"
            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"
            const val ui = "androidx.compose.ui:ui:${version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val animation = "androidx.compose.animation:animation:${version}"
            const val activity = "androidx.activity:activity-compose:${activityComposeVersion}"
        }

        object Test {
            private const val version = "1.2.0"
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