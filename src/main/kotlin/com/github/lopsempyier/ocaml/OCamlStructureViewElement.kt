package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlFile
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.github.lopsempyier.ocaml.psi.impl.OCamlPropertyImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil


class OCamlStructureViewElement(private val myElement: NavigatablePsiElement) : StructureViewTreeElement,
    SortableTreeElement {
    override fun getValue(): Any {
        return myElement
    }

    override fun navigate(requestFocus: Boolean) {
        myElement.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return myElement.canNavigate()
    }

    override fun canNavigateToSource(): Boolean {
        return myElement.canNavigateToSource()
    }

    override fun getAlphaSortKey(): String {
        val name = myElement.name
        return name ?: ""
    }

    override fun getPresentation(): ItemPresentation {
        val presentation = myElement.presentation
        return presentation ?: PresentationData()
    }

    override fun getChildren(): Array<TreeElement> {
        if (myElement is OCamlFile) {
            val properties: List<OCamlProperty> = PsiTreeUtil.getChildrenOfTypeAsList(
                myElement,
                OCamlProperty::class.java
            )
            val treeElements: MutableList<TreeElement> = ArrayList<TreeElement>(properties.size)
            for (property in properties) {
                treeElements.add(OCamlStructureViewElement(property as OCamlPropertyImpl))
            }
            return treeElements.toTypedArray()
        }
        return TreeElement.EMPTY_ARRAY
    }
}