package com.github.lopsempyier.ocaml.psi

import com.github.lopsempyier.ocaml.language.OCamlFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory


class OCamlElementFactory {
    companion object {
        @JvmStatic
        fun createProperty(project: Project?, name: String?): OCamlProperty {
            val file: OCamlFile = createFile(project, name)
            return file.firstChild as OCamlProperty
        }

        @JvmStatic
        private fun createFile(project: Project?, text: String?): OCamlFile {
            val name = "dummy.ml"
            return text?.let {
                PsiFileFactory.getInstance(project).createFileFromText(name, OCamlFileType, it)
            } as OCamlFile
        }

        @JvmStatic
        fun createProperty(project: Project?, name: String, value: String): OCamlProperty {
            val file: OCamlFile = createFile(project, "$name = $value")
            return file.firstChild as OCamlProperty
        }

        @JvmStatic
        fun createCRLF(project: Project?): PsiElement? {
            val file: OCamlFile = createFile(project, "\n")
            return file.firstChild
        }
    }
}
