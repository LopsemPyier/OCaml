package com.github.lopsempyier.ocaml.language

import com.intellij.openapi.fileTypes.LanguageFileType

object OCamlFileType : LanguageFileType(OCamlLanguage) {
    override fun getName() = "OCaml File"

    override fun getDescription() = "OCaml source file"

    override fun getDefaultExtension() = "ml"

    override fun getIcon() = OCamlIcon.ICON
}