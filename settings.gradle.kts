pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SpaceX"
include (":app")
include (":core:data")
include (":core:network")
include (":core:database")
include (":feature:rocketlaunches")
include (":feature:rocketlaunch_details")
include (":core:model")
include(":core:common")
