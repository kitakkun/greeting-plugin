package greeting.compiler.fir

import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class GreetingFirExtensionRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::GreetingFirDeclarationGenerationExtension
    }
}
