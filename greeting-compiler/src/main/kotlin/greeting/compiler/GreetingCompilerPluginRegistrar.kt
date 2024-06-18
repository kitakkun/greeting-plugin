package greeting.compiler

import greeting.compiler.fir.GreetingFirExtensionRegistrar
import greeting.compiler.ir.GreetingIrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

@OptIn(ExperimentalCompilerApi::class)
class GreetingCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true
    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        FirExtensionRegistrarAdapter.registerExtension(GreetingFirExtensionRegistrar())
        IrGenerationExtension.registerExtension(GreetingIrGenerationExtension())
    }
}
