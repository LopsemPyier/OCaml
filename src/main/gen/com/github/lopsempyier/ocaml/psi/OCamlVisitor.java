// This is a generated file. Not intended for manual editing.
package com.github.lopsempyier.ocaml.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public class OCamlVisitor extends PsiElementVisitor {

    public void visitProperty(@NotNull OCamlProperty o) {
        visitNamedElement(o);
    }

    public void visitNamedElement(@NotNull OCamlNamedElement o) {
        visitPsiElement(o);
    }

    public void visitPsiElement(@NotNull PsiElement o) {
        visitElement(o);
    }

}
