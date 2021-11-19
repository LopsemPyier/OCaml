package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.editor.Editor
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.presentation.java.SymbolPresentationUtil
import com.intellij.psi.util.PsiTreeUtil


class OCamlDocumentationProvider : AbstractDocumentationProvider() {


    /**
     * Extracts the key, value, file and documentation comment of a Simple key/value entry and returns
     * a formatted representation of the information.
     */
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        if (element is OCamlProperty) {
            val key: String? = element.key
            val value: String? = element.value
            val file = SymbolPresentationUtil.getFilePathPresentation(element.containingFile)
            val docComment: String = OCamlUtils.findDocumentationComment(element)
            return renderFullDoc(key ?: "", value ?: "", file, docComment)
        }
        return null
    }

    /**
     * Creates the formatted documentation using [DocumentationMarkup]. See the Java doc of
     * [com.intellij.lang.documentation.DocumentationProvider.generateDoc] for more
     * information about building the layout.
     */
    private fun renderFullDoc(key: String, value: String, file: String, docComment: String): String {
        val sb = StringBuilder()
        sb.append(DocumentationMarkup.DEFINITION_START)
        sb.append("Simple Property")
        sb.append(DocumentationMarkup.DEFINITION_END)
        sb.append(DocumentationMarkup.CONTENT_START)
        sb.append(value)
        sb.append(DocumentationMarkup.CONTENT_END)
        sb.append(DocumentationMarkup.SECTIONS_START)
        addKeyValueSection("Key:", key, sb)
        addKeyValueSection("Value:", value, sb)
        addKeyValueSection("File:", file, sb)
        addKeyValueSection("Comment:", docComment, sb)
        sb.append(DocumentationMarkup.SECTIONS_END)
        return sb.toString()
    }

    /**
     * Creates a key/value row for the rendered documentation.
     */
    private fun addKeyValueSection(key: String, value: String, sb: StringBuilder) {
        sb.append(DocumentationMarkup.SECTION_HEADER_START)
        sb.append(key)
        sb.append(DocumentationMarkup.SECTION_SEPARATOR)
        sb.append("<p>")
        sb.append(value)
        sb.append(DocumentationMarkup.SECTION_END)
    }

    /**
     * Provides documentation when a Simple Language element is hovered with the mouse.
     */
    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return generateDoc(element, originalElement)
    }

    /**
     * Provides the information in which file the Simple language key/value is defined.
     */
    override fun getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement?): String? {
        if (element is OCamlProperty) {
            val key: String? = element.key
            val file = SymbolPresentationUtil.getFilePathPresentation(element.containingFile)
            return "\"$key\" in $file"
        }
        return null
    }

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int
    ): PsiElement? {
        if (contextElement != null) {
            // In this part the SimpleProperty element is extracted from inside a Java string
            if (contextElement is PsiJavaToken && contextElement.tokenType == JavaTokenType.STRING_LITERAL) {
                val parent = contextElement.getParent()
                val references = parent.references
                for (ref in references) {
                    if (ref is OCamlReference) {
                        val property = ref.resolve()
                        if (property is OCamlProperty) {
                            return property
                        }
                    }
                }
            } else if (contextElement.language === OCamlLanguage) {
                return PsiTreeUtil.getParentOfType(contextElement, OCamlProperty::class.java) as PsiElement
            }
        }
        return super.getCustomDocumentationElement(editor, file, contextElement, targetOffset)
    }
}