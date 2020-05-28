package com.kklorenzotesta.arrowmeta.fu

@kotlin.annotation.Target(AnnotationTarget.EXPRESSION)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class Ann

object Test {
    fun bar(): String = "FOO!"
}

fun foo(): String {
    return Test.bar()
}

fun helloWorld(): Unit = TODO()

fun main() {
    println("##START")
    println(foo())
    @Ann helloWorld()
    println("##END")
}