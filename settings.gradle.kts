@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "3.17.5"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

val remoteCacheUrl: String? by extra
if (System.getenv("REMOTE_CACHE_URL") != null || remoteCacheUrl != null) {
    buildCache {
        local {
            removeUnusedEntriesAfterDays = 30
        }
        val cacheUrl = if (System.getenv("REMOTE_CACHE_URL") == null) remoteCacheUrl as String else System.getenv("REMOTE_CACHE_URL")
        remote<HttpBuildCache> {
            url = uri(cacheUrl)
            isEnabled = true
            isPush = true
            isAllowUntrustedServer = true
            isAllowInsecureProtocol = true
            if (isEnabled) {
                println("Using remote build cache: $cacheUrl")
            }

            val remoteCacheUser: String? by extra
            val remoteCachePass: String? by extra
            credentials {
                username = if (System.getenv("REMOTE_CACHE_USER") == null) remoteCacheUser as String else System.getenv("REMOTE_CACHE_USER")
                password = if (System.getenv("REMOTE_CACHE_PASS") == null) remoteCachePass as String else System.getenv("REMOTE_CACHE_PASS")
            }
        }
    }
} else {
    println("Not using remote build cache!")
}

rootProject.name = "Bible"
include(":app")
