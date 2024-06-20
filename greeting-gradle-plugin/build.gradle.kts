plugins {
    kotlin("jvm")
    `java-gradle-plugin`
}

dependencies {
    compileOnly(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    plugins {
        create("greeting") {
            id = "greeting"
            implementationClass = "greeting.gradle.GreetingPlugin"
        }
    }
}
