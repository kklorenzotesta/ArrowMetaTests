package com.kklorenzotesta.arrowmeta.fp

import arrow.meta.*
import arrow.meta.phases.CompilerContext
import arrow.meta.quotes.*
import arrow.meta.quotes.expression.AnnotatedExpression
import org.jetbrains.kotlin.psi.KtAnnotatedExpression
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity

fun Meta.annotatedExpression(
    match: KtAnnotatedExpression.() -> Boolean,
    map: AnnotatedExpression.(KtAnnotatedExpression) -> Transform<KtAnnotatedExpression>
): arrow.meta.phases.ExtensionPhase =
    quote(match, map) { AnnotatedExpression(it) }

val Meta.helloWorld: CliPlugin
    get() =
        "Hello World" {
            meta(
                namedFunction({ name == "helloWorld" }) { c ->
                    Transform.replace(
                        replacing = c,
                        newDeclaration =
                        """|fun helloWorld(): Unit =
                           |  println("Hello ARROW Meta!")
                           |""".function.syntheticScope
                    )
                }
            )
        }

val Meta.firstPlugin: CliPlugin
    get() =
        "First Plugin" {
            meta(
                dotQualifiedExpression({ true }) { funl ->
                    println("Transforming ${funl.text}")
                    Transform.replace(
                        replacing = funl,
                        newDeclaration = funl.scope()
                    )
                },
                returnExpression({ true }) { exp ->
                    Transform.replace(
                        replacing = exp,
                        newDeclarations = listOf(
                            "println(\"Transformed return '${exp.text}' inside ${exp.parents.find { it is org.jetbrains.kotlin.psi.KtNamedFunction }
                                ?.let { it as org.jetbrains.kotlin.psi.KtNamedFunction }?.name}\")".expression,
                            exp.scope()
                        )
                    )
                },
                annotatedExpression({
                    messageCollector?.report(CompilerMessageSeverity.WARNING, "name is $name")
                    println(text)
                    true
                }) { exp ->
                    Transform.replace(
                        replacing = exp,
                        newDeclarations = listOf(
                            "println(\"Transformed annotation '${this.`@annotations`}'\")".expression,
                            exp.scope()
                        )
                    )
                }
            )
        }


class MetaPlugin : Meta {
    override fun intercept(ctx: CompilerContext): List<CliPlugin> = listOf(firstPlugin, helloWorld)
}
