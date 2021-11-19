package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.OCamlAnnotator.Companion.OCAML_PREFIX_STR
import com.github.lopsempyier.ocaml.OCamlAnnotator.Companion.OCAML_SEPARATOR_STR
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext


class OCamlReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    val literalExpression = element as PsiLiteralExpression
                    val value = if (literalExpression.value is String) literalExpression.value as String? else null
                    if (value != null && value.startsWith(OCAML_PREFIX_STR + OCAML_SEPARATOR_STR)) {
                        val property = TextRange(
                            OCAML_PREFIX_STR.length + OCAML_SEPARATOR_STR.length + 1,
                            value.length + 1
                        )
                        return arrayOf(OCamlReference(element, property))
                    }
                    return PsiReference.EMPTY_ARRAY
                }
            })
    }
}