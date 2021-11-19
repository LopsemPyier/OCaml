package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.github.lopsempyier.ocaml.parser.OCamlParser
import com.github.lopsempyier.ocaml.psi.OCamlFile
import com.github.lopsempyier.ocaml.psi.OCamlTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class OCamlParserDefinition : ParserDefinition {

    val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS = TokenSet.create(OCamlTypes.COMMENT)

    val FILE = IFileElementType(OCamlLanguage)


    override fun createLexer(project: Project?) = OCamlLexerAdapter()

    override fun getWhitespaceTokens() = WHITE_SPACES

    override fun getCommentTokens() = COMMENTS

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createParser(project: Project?) = OCamlParser()

    override fun getFileNodeType() = FILE

    override fun createElement(node: ASTNode?): PsiElement = OCamlTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider) = OCamlFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?) =
        ParserDefinition.SpaceRequirements.MAY

}