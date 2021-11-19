package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext


class OCamlCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(OCamlTypes.VALUE),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    result.addElement(LookupElementBuilder.create("Hello"))
                }
            }
        )
    }
}