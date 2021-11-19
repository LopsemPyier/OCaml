package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlFileType
import com.github.lopsempyier.ocaml.psi.OCamlFile
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import java.util.*


class OCamlUtils {
    companion object {

        /**
         * Searches the entire project for OCaml language files with instances of the OCaml property with the given key.
         *
         * @param project current project
         * @param key     to check
         * @return matching properties
         */
        @JvmStatic
        fun findProperties(project: Project, key: String): List<OCamlProperty> {
            val result: MutableList<OCamlProperty> = ArrayList<OCamlProperty>()
            val virtualFiles = FileTypeIndex.getFiles(OCamlFileType, GlobalSearchScope.allScope(project))
            for (virtualFile in virtualFiles) {
                val ocamlFile: OCamlFile? = PsiManager.getInstance(project).findFile(virtualFile!!) as OCamlFile?
                if (ocamlFile != null) {
                    val properties: Array<OCamlProperty> =
                        PsiTreeUtil.getChildrenOfType(ocamlFile, OCamlProperty::class.java) as Array<OCamlProperty>
                    for (property in properties) {
                        if (key == property.key) {
                            result.add(property)
                        }
                    }
                }
            }
            return result
        }

        @JvmStatic
        fun findProperties(project: Project): List<OCamlProperty> {
            val result: MutableList<OCamlProperty> = ArrayList<OCamlProperty>()
            val virtualFiles = FileTypeIndex.getFiles(OCamlFileType, GlobalSearchScope.allScope(project))
            for (virtualFile in virtualFiles) {
                val ocamlFile: OCamlFile? = PsiManager.getInstance(project).findFile(virtualFile!!) as OCamlFile?
                if (ocamlFile != null) {
                    val properties: Array<OCamlProperty> =
                        PsiTreeUtil.getChildrenOfType(ocamlFile, OCamlProperty::class.java) as Array<OCamlProperty>
                    result.addAll(properties)
                }
            }
            return result
        }

        /**
         * Attempts to collect any comment elements above the OCaml key/value pair.
         */
        @JvmStatic
        fun findDocumentationComment(property: OCamlProperty): String {
            val result: MutableList<String> = LinkedList()
            var element: PsiElement = property.prevSibling
            while (element is PsiComment || element is PsiWhiteSpace) {
                if (element is PsiComment) {
                    val commentText = element.getText().replaceFirst("[!# ]+".toRegex(), "")
                    result.add(commentText)
                }
                element = element.prevSibling
            }
            return result.reversed().joinToString("\n ")
        }
    }
}