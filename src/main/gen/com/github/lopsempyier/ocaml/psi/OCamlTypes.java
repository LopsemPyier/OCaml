// This is a generated file. Not intended for manual editing.
package com.github.lopsempyier.ocaml.psi;

import com.github.lopsempyier.ocaml.psi.impl.OCamlPropertyImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public interface OCamlTypes {

    IElementType PROPERTY = new OCamlElementType("PROPERTY");

    IElementType COMMENT = new OCamlTokenType("COMMENT");
    IElementType CRLF = new OCamlTokenType("CRLF");
    IElementType KEY = new OCamlTokenType("KEY");
    IElementType SEPARATOR = new OCamlTokenType("SEPARATOR");
    IElementType VALUE = new OCamlTokenType("VALUE");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == PROPERTY) {
                return new OCamlPropertyImpl(node);
            }
            throw new AssertionError("Unknown element type: " + type);
        }
    }
}
