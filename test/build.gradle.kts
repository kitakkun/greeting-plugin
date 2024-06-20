plugins {
    kotlin("jvm")
    id("greeting")
}

dependencies {
    testImplementation(kotlin("test"))
}

greeting {
    message = "Bonjour. {name}!"
}
