buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.6")
    }
}

plugins {
    alias(libs.plugins.android).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.ksp).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.google.services).apply(false)
    alias(libs.plugins.gradle.cachefix).apply(false)
}

tasks.wrapper {
    gradleVersion = "8.1.1"
    distributionType = Wrapper.DistributionType.BIN
}
