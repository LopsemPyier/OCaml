package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement


class OCamlRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement,
        context: PsiElement?
    ): Boolean {
        return elementToRename is OCamlProperty
    }
}