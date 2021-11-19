package com.github.lopsempyier.ocaml.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode


abstract class OCamlNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    OCamlNamedElement