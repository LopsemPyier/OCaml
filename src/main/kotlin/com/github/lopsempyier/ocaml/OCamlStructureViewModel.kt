package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.ide.structureView.StructureViewModel.ElementInfoProvider
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.psi.PsiFile


class OCamlStructureViewModel(psiFile: PsiFile?) :
    StructureViewModelBase(psiFile!!, OCamlStructureViewElement(psiFile)), ElementInfoProvider {
    override fun getSorters(): Array<Sorter> {
        return arrayOf<Sorter>(Sorter.ALPHA_SORTER)
    }

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean {
        return false
    }

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
        return element.value is OCamlProperty
    }
}