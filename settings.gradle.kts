pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.13.4"
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

val remoteCacheFromProperties: String? by extra
if (System.getenv("REMOTE_CACHE_URL") != null || remoteCacheFromProperties != null) {
    buildCache {
        local {
            removeUnusedEntriesAfterDays = 30
        }
        val cacheUrl = if (System.getenv("REMOTE_CACHE_URL") == null) remoteCacheFromProperties as String else System.getenv("REMOTE_CACHE_URL")
        remote<HttpBuildCache> {
            url = uri(cacheUrl)
            isEnabled = true
            isPush = true
            isAllowUntrustedServer = true
            isAllowInsecureProtocol = true
            if (isEnabled) {
                println("Using remote build cache: $cacheUrl")
            }

            val remoteCacheUserFromProperties: String? by extra
            val remoteCachePassFromProperties: String? by extra
            credentials {
                username = if (System.getenv("REMOTE_CACHE_USER") == null) remoteCacheUserFromProperties as String else System.getenv("REMOTE_CACHE_USER")
                password = if (System.getenv("REMOTE_CACHE_PASS") == null) remoteCachePassFromProperties as String else System.getenv("REMOTE_CACHE_PASS")
            }
        }
    }
} else {
    println("Not using remote build cache!")
}

rootProject.name = "Bible"
include(":app")
