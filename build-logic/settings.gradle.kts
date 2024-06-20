dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":greeting-gradle-plugin")
project(":greeting-gradle-plugin").projectDir = file("../greeting-gradle-plugin")
