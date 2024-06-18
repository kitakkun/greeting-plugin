package greeting.compiler.fir

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
import org.jetbrains.kotlin.fir.extensions.MemberGenerationContext
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider
import org.jetbrains.kotlin.fir.plugin.createMemberFunction
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.Name

class GreetingFirDeclarationGenerationExtension(session: FirSession) : FirDeclarationGenerationExtension(session) {
    override fun getCallableNamesForClass(classSymbol: FirClassSymbol<*>, context: MemberGenerationContext): Set<Name> {
        return if (session.predicateBasedProvider.matches(greetablePredicate, classSymbol)) {
            setOf(Name.identifier("greet"))
        } else {
            emptySet()
        }
    }

    override fun generateFunctions(callableId: CallableId, context: MemberGenerationContext?): List<FirNamedFunctionSymbol> {
        return listOf(
            createMemberFunction(
                owner = context?.owner ?: return emptyList(),
                key = GreetingDeclarationKey,
                name = callableId.callableName,
                returnType = session.builtinTypes.unitType.type,
                config = {
                    valueParameter(Name.identifier("name"), session.builtinTypes.stringType.type)
                },
            ).symbol,
        )
    }

    override fun FirDeclarationPredicateRegistrar.registerPredicates() {
        register(greetablePredicate)
    }
}
