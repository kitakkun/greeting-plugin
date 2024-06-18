package greeting.compiler.ir

import greeting.compiler.fir.GreetingDeclarationKey
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.createIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.kotlinFqName
import org.jetbrains.kotlin.ir.util.parentClassOrNull
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

@OptIn(UnsafeDuringIrConstructionAPI::class)
class GreetingIrElementTransformer(private val pluginContext: IrPluginContext) : IrElementTransformerVoid() {
    private val printFunctionSymbol = pluginContext.referenceFunctions(
        CallableId(FqName("kotlin.io"), Name.identifier("print")),
    ).first { it.owner.valueParameters.singleOrNull()?.type == pluginContext.irBuiltIns.anyNType }

    private val printlnFunctionSymbol = pluginContext.referenceFunctions(
        CallableId(FqName("kotlin.io"), Name.identifier("println")),
    ).first { it.owner.valueParameters.singleOrNull()?.type == pluginContext.irBuiltIns.anyNType }

    override fun visitSimpleFunction(declaration: IrSimpleFunction): IrStatement {
        val origin = declaration.origin as? IrDeclarationOrigin.GeneratedByPlugin
        if (origin?.pluginKey != GreetingDeclarationKey) return declaration

        val parentClassName = declaration.parentClassOrNull?.kotlinFqName?.asString()

        val irBuilder = pluginContext.irBuiltIns.createIrBuilder(declaration.symbol)
        declaration.body = irBuilder.irBlockBody {
            +irCall(printFunctionSymbol).apply {
                putValueArgument(index = 0, valueArgument = irString("$parentClassName「こんにちは、"))
            }
            +irCall(printFunctionSymbol).apply {
                putValueArgument(index = 0, valueArgument = irGet(declaration.valueParameters.single()))
            }
            +irCall(printlnFunctionSymbol).apply {
                putValueArgument(index = 0, valueArgument = irString("！」"))
            }
        }

        return declaration
    }
}
