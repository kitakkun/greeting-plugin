plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    implementation(kotlin("compiler-embeddable"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["kotlin"])
        }
    }
}
