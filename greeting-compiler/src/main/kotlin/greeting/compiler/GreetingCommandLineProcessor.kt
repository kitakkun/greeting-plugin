package greeting.compiler

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@OptIn(ExperimentalCompilerApi::class)
class GreetingCommandLineProcessor : CommandLineProcessor {
    companion object {
        val KEY_GREETING_MESSAGE = CompilerConfigurationKey<String>("message")
    }

    override val pluginId: String = "greeting"
    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = "message",
            valueDescription = "<string>",
            description = "Greeting message to be used. The place holder {name} will be replaced with the name of the person being greeted.",
            required = false,
        ),
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option.optionName) {
            "message" -> configuration.put(KEY_GREETING_MESSAGE, value)
            else -> error("Unexpected config option ${option.optionName}")
        }
    }
}
