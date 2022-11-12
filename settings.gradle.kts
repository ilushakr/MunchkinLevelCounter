pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MunchkinLevelCounter"
include(":androidApp")
include(":sharedbase")
include(":sharedmainfeature")
