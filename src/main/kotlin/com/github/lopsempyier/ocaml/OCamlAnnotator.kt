package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralExpression


class OCamlAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // Ensure the Psi Element is an expression
        if (element !is PsiLiteralExpression) {
            return
        }

        // Ensure the Psi element contains a string that starts with the prefix and separator
        val literalExpression = element
        val value = if (literalExpression.value is String) literalExpression.value as String? else null
        if (value == null || !value.startsWith(OCAML_PREFIX_STR + OCAML_SEPARATOR_STR)) {
            return
        }

        // Define the text ranges (start is inclusive, end is exclusive)
        // "simple:key"
        //  01234567890
        val prefixRange = TextRange.from(element.getTextRange().startOffset, OCAML_PREFIX_STR.length + 1)
        val separatorRange = TextRange.from(prefixRange.endOffset, OCAML_SEPARATOR_STR.length)
        val keyRange = TextRange(separatorRange.endOffset, element.getTextRange().endOffset - 1)

        // highlight "simple" prefix and ":" separator
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(separatorRange).textAttributes(OCamlSyntaxHighlighter.SEPARATOR).create()


        // Get the list of properties for given key
        val key = value.substring(OCAML_PREFIX_STR.length + OCAML_SEPARATOR_STR.length)
        val properties: List<OCamlProperty> = OCamlUtils.findProperties(element.getProject(), key)
        if (properties.isEmpty()) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
                .range(keyRange)
                .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                // ** Tutorial step 18.3 - Add a quick fix for the string containing possible properties
                .withFix(OCamlCreatePropertyQuickFix(key))
                .create()
        } else {
            // Found at least one property, force the text attributes to Simple syntax value character
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(keyRange).textAttributes(OCamlSyntaxHighlighter.VALUE).create()
        }
    }

    companion object {
        // Define strings for the Simple language prefix - used for annotations, line markers, etc.
        const val OCAML_PREFIX_STR = "ocaml"
        const val OCAML_SEPARATOR_STR = ":"
    }
}