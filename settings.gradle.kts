dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

includeBuild("build-logic")
include(":greeting-gradle-plugin")
include(":greeting-annotations")
include(":greeting-compiler")
include(":test")
