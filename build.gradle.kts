buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.oss.licenses.plugin)
    }
}

plugins {
    alias(libs.plugins.android) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.gradle.cachefix) apply false
    alias(libs.plugins.hilt.plugin) apply false
}

tasks.wrapper {
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.BIN
}
