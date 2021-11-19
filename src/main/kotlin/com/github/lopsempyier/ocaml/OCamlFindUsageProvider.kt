package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.github.lopsempyier.ocaml.psi.OCamlTypes
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet


class OCamlFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            OCamlLexerAdapter(),
            TokenSet.create(OCamlTypes.KEY),
            TokenSet.create(OCamlTypes.COMMENT),
            TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }

    override fun getType(element: PsiElement): String {
        return if (element is OCamlProperty) {
            "ocaml property"
        } else {
            ""
        }
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return if (element is OCamlProperty) {
            element.key ?: ""
        } else {
            ""
        }
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return if (element is OCamlProperty) {
            element.key + OCamlAnnotator.OCAML_SEPARATOR_STR + element.value
        } else {
            ""
        }
    }
}