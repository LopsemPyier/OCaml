package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlFileType
import com.github.lopsempyier.ocaml.psi.OCamlElementFactory
import com.github.lopsempyier.ocaml.psi.OCamlFile
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.lang.ASTNode
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.IncorrectOperationException


internal class OCamlCreatePropertyQuickFix(private val key: String) : BaseIntentionAction() {
    override fun getText(): String {
        return "Create property '$key'"
    }

    override fun getFamilyName(): String {
        return "Create property"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return true
    }

    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        ApplicationManager.getApplication().invokeLater {
            val virtualFiles =
                FileTypeIndex.getFiles(OCamlFileType, GlobalSearchScope.allScope(project))
            if (virtualFiles.size == 1) {
                createProperty(project, virtualFiles.iterator().next())
            } else {
                val descriptor: FileChooserDescriptor =
                    FileChooserDescriptorFactory.createSingleFileDescriptor(OCamlFileType)
                descriptor.setRoots(project.guessProjectDir())
                val file1 = FileChooser.chooseFile(descriptor, project, null)
                file1?.let { createProperty(project, it) }
            }
        }
    }

    private fun createProperty(project: Project, file: VirtualFile) {
        WriteCommandAction.writeCommandAction(project).run<RuntimeException> {
            val simpleFile: OCamlFile? = PsiManager.getInstance(project).findFile(file) as OCamlFile?
            val lastChildNode: ASTNode? = simpleFile?.node?.lastChildNode
            // TODO: Add another check for CRLF
            if (lastChildNode != null /* && !lastChildNode.getElementType().equals(SimpleTypes.CRLF)*/) {
                OCamlElementFactory.createCRLF(project)?.node?.let { simpleFile.node.addChild(it) }
            }
            // IMPORTANT: change spaces to escaped spaces or the new node will only have the first word for the key
            val property: OCamlProperty =
                OCamlElementFactory.createProperty(project, key.replace(" ".toRegex(), "\\\\ "), "")
            simpleFile?.node?.addChild(property.node)
            (property.lastChild.navigationElement as Navigatable).navigate(true)
            FileEditorManager.getInstance(project).selectedTextEditor!!
                .caretModel.moveCaretRelatively(2, 0, false, false, false)
        }
    }
}