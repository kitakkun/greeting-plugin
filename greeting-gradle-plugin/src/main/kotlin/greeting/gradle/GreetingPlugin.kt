package greeting.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class GreetingPlugin : KotlinCompilerPluginSupportPlugin {
    override fun getCompilerPluginId(): String = "greeting"

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        return kotlinCompilation.target.project.plugins.hasPlugin(GreetingPlugin::class.java)
    }

    override fun getPluginArtifact(): SubpluginArtifact {
        return SubpluginArtifact(
            groupId = "greeting",
            artifactId = "greeting-compiler",
            version = "1.0.0",
        )
    }

    override fun apply(target: Project) {
        target.extensions.create("greeting", GreetingExtension::class.java)
        target.dependencies.add("testCompileOnly", "greeting:greeting-annotations:1.0.0")
    }

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val extension = kotlinCompilation.target.project.extensions.getByType(GreetingExtension::class.java)

        val options = mutableListOf<SubpluginOption>()
        extension.message?.let {
            options.add(SubpluginOption(key = "message", value = it))
        }

        return kotlinCompilation.target.project.provider { options }
    }
}
