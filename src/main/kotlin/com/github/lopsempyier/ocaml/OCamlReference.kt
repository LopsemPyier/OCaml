package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlIcon
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.util.containers.toArray


class OCamlReference(element: PsiElement, textRange: TextRange) :
    PsiReferenceBase<PsiElement?>(element, textRange), PsiPolyVariantReference {
    private val key: String


    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult?> {
        val project: Project = myElement!!.project
        val properties: List<OCamlProperty> = OCamlUtils.findProperties(project, key)
        val results: MutableList<ResolveResult> = ArrayList()
        for (property in properties) {
            results.add(PsiElementResolveResult(property))
        }
        return results.toArray(arrayOfNulls(results.size))
    }


    override fun resolve(): PsiElement? {
        val resolveResults: Array<ResolveResult?> = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0]?.element else null
    }


    override fun getVariants(): Array<Any> {
        val project: Project = myElement!!.project
        val properties: List<OCamlProperty> = OCamlUtils.findProperties(project)
        val variants: MutableList<LookupElement> = ArrayList()
        for (property in properties) {
            if (property.key != null && (property.key as String).isNotEmpty()) {
                variants.add(
                    LookupElementBuilder
                        .create(property).withIcon(OCamlIcon.ICON)
                        .withTypeText(property.containingFile.name)
                )
            }
        }
        return variants.toTypedArray()
    }

    init {
        key = element.text.substring(textRange.startOffset, textRange.endOffset)
    }
}