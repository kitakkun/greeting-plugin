dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

includeBuild("build-logic")
include(":greeting-gradle-plugin")
include(":greeting-annotations")
include(":greeting-compiler")
include(":test")
