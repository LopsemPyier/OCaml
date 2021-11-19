package com.github.lopsempyier.ocaml.psi

import com.github.lopsempyier.ocaml.language.OCamlFileType
import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class OCamlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, OCamlLanguage) {
    override fun getFileType() = OCamlFileType

    override fun toString() = "OCaml File"
}