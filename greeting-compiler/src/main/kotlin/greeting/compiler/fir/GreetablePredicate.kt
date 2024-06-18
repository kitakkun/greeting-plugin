package greeting.compiler.fir

import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate
import org.jetbrains.kotlin.name.FqName

val greetablePredicate = DeclarationPredicate.create {
    annotated(FqName("greeting.annotations.Greetable"))
}
