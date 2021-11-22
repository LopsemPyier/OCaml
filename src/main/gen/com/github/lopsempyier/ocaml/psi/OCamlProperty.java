// This is a generated file. Not intended for manual editing.
package com.github.lopsempyier.ocaml.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface OCamlProperty extends OCamlNamedElement {

    @Nullable
    String getKey();

    @Nullable
    String getValue();

    @Nullable
    String getName();

    @NotNull
    PsiElement setName(@Nullable String newName);

    @Nullable
    PsiElement getNameIdentifier();

    @NotNull
    ItemPresentation getPresentation();

}
