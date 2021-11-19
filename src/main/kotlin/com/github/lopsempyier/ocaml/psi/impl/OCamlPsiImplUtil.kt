package com.github.lopsempyier.ocaml.psi.impl

import com.github.lopsempyier.ocaml.language.OCamlIcon
import com.github.lopsempyier.ocaml.psi.OCamlElementFactory
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.github.lopsempyier.ocaml.psi.OCamlTypes
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon


class OCamlPsiImplUtil {
    companion object {
        @JvmStatic
        fun getKey(element: OCamlProperty): String? {
            return element.node.findChildByType(OCamlTypes.KEY)?.text?.replace("\\\\ ", " ")
        }

        @JvmStatic
        fun getValue(element: OCamlProperty): String? {
            return element.node.findChildByType(OCamlTypes.VALUE)?.text
        }

        @JvmStatic
        fun getName(element: OCamlProperty): String? {
            return getKey(element)
        }

        @JvmStatic
        fun setName(element: OCamlProperty, newName: String?): PsiElement {
            val keyNode: ASTNode? = element.node.findChildByType(OCamlTypes.KEY)
            if (keyNode != null) {
                val property: OCamlProperty = OCamlElementFactory.createProperty(element.project, newName)
                val newKeyNode: ASTNode = property.firstChild.node
                element.node.replaceChild(keyNode, newKeyNode)
            }
            return element
        }

        @JvmStatic
        fun getNameIdentifier(element: OCamlProperty): PsiElement? {
            return element.node.findChildByType(OCamlTypes.KEY)?.psi
        }

        @JvmStatic
        fun getPresentation(element: OCamlProperty): ItemPresentation {
            return object : ItemPresentation {
                override fun getPresentableText(): String? {
                    return element.key
                }

                override fun getLocationString(): String {
                    val containingFile: PsiFile = element.containingFile
                    return containingFile.name
                }

                override fun getIcon(unused: Boolean): Icon {
                    return OCamlIcon.ICON
                }
            }
        }


    }
}