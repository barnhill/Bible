@file:Suppress("UnstableApiUsage")

import org.gradle.internal.extensions.core.extra

plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization.plugin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.google.services)
    alias(libs.plugins.toml.version.checker)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.android.navsafe.args)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.gradle.cachefix)
    id("com.google.android.gms.oss-licenses-plugin")
}

val gitVersionName: String by rootProject.extra
val gitVersionCode: String by rootProject.extra
android {
    namespace = "com.pnuema.bible.android"
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        applicationId = "com.pnuema.bible.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = gitVersionCode.toInt()
        versionName = gitVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
                argument("room.incremental", "true")
                argument("room.expandProjection", "true")
            }
        }
    }

    signingConfigs {
        val bibleCertLocation: String? by rootProject.extra
        val certLocation: String = if (System.getenv("bibleCertLocation") == null) bibleCertLocation!! else System.getenv("bibleCertLocation")

        val bibleKeystorePassword: String? by rootProject.extra
        val certPass: String = if (System.getenv("bibleKeystorePassword") == null) bibleKeystorePassword!! else System.getenv("bibleKeystorePassword")

        register("release") {
            storeFile = file(certLocation)
            storePassword = certPass
            keyAlias = "bible"
            keyPassword = certPass
        }
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            enableUnitTestCoverage = false
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            enableUnitTestCoverage = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    sourceSets {
        getByName("test") {
            kotlin.directories += "src/test/java"
            kotlin.directories += "src/test/kotlin"
        }
        getByName("androidTest") {
            kotlin.directories += "src/androidTest/java"
            kotlin.directories += "src/androidTest/kotlin"
        }
    }
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_21.toString().toInt())
}

tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates").configure {
    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "html"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.ksp.symbol.processing)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.extensions)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    ksp(libs.room.compiler)
    implementation(libs.datastore)
    implementation(libs.material)
    implementation(libs.licenses)
    implementation(libs.kotlin.serialization)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.lifecycle)
    implementation(libs.androidx.compose.viewmodel.lifecycle)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(libs.square.retrofit)
    implementation(libs.converter.moshi)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}

apply(plugin = "com.google.gms.google-services")
