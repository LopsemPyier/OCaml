// This is a generated file. Not intended for manual editing.
package com.github.lopsempyier.ocaml.psi.impl;

import com.github.lopsempyier.ocaml.psi.OCamlNamedElementImpl;
import com.github.lopsempyier.ocaml.psi.OCamlProperty;
import com.github.lopsempyier.ocaml.psi.OCamlVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OCamlPropertyImpl extends OCamlNamedElementImpl implements OCamlProperty {

    public OCamlPropertyImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull OCamlVisitor visitor) {
        visitor.visitProperty(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof OCamlVisitor) accept((OCamlVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public String getKey() {
        return OCamlPsiImplUtil.getKey(this);
    }

    @Override
    @Nullable
    public String getValue() {
        return OCamlPsiImplUtil.getValue(this);
    }

    @Override
    @Nullable
    public String getName() {
        return OCamlPsiImplUtil.getName(this);
    }

    @Override
    @Nullable
    public PsiElement setName(@Nullable String newName) {
        return OCamlPsiImplUtil.setName(this, newName);
    }

    @Override
    @Nullable
    public PsiElement getNameIdentifier() {
        return OCamlPsiImplUtil.getNameIdentifier(this);
    }

    @Override
    @NotNull
    public ItemPresentation getPresentation() {
        return OCamlPsiImplUtil.getPresentation(this);
    }

}
