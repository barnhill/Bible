import org.gradle.internal.extensions.core.extra

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
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.gradle.cachefix) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.android.navsafe.args) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization.plugin) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
}

tasks {
    wrapper {
        gradleVersion = libs.versions.gradle.get()
        distributionType = Wrapper.DistributionType.BIN
    }
}

rootProject.extra.set("gitVersionName", fetchGitVersionName())
rootProject.extra.set("gitVersionCode", fetchGitVersionCode())

fun fetchGitVersionCode(): String = "git rev-list HEAD --count".execute()

fun fetchGitVersionName(): String = "git describe HEAD".execute()

fun String.execute(): String {
    val process = ProcessBuilder(*split(" ").toTypedArray())
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    process.waitFor()
    return process.inputStream.bufferedReader().readText().trim()
}

