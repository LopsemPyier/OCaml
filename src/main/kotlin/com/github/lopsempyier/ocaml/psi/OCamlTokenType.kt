package com.github.lopsempyier.ocaml.psi

import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.intellij.psi.tree.IElementType

class OCamlTokenType(debugName: String) : IElementType(debugName, OCamlLanguage) {
    override fun toString(): String {
        return "OCamlTokenType." + super.toString()
    }
}