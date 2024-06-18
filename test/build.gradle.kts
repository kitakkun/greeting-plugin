plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":greeting-annotations"))
    kotlinCompilerPluginClasspath(project(":greeting-compiler"))
}
