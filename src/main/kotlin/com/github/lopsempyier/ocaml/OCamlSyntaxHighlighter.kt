package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType


class OCamlSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return OCamlLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey?> {
        return if (tokenType == OCamlTypes.SEPARATOR) {
            SEPARATOR_KEYS
        } else if (tokenType == OCamlTypes.KEY) {
            KEY_KEYS
        } else if (tokenType == OCamlTypes.VALUE) {
            VALUE_KEYS
        } else if (tokenType == OCamlTypes.COMMENT) {
            COMMENT_KEYS
        } else if (tokenType == TokenType.BAD_CHARACTER) {
            BAD_CHAR_KEYS
        } else {
            EMPTY_KEYS
        }
    }

    companion object {
        val SEPARATOR: TextAttributesKey =
            createTextAttributesKey("OCaml_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val KEY: TextAttributesKey = createTextAttributesKey("OCaml_KEY", DefaultLanguageHighlighterColors.KEYWORD)
        val VALUE: TextAttributesKey = createTextAttributesKey("OCaml_VALUE", DefaultLanguageHighlighterColors.STRING)
        val COMMENT: TextAttributesKey =
            createTextAttributesKey("OCaml_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("OCaml_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
        private val KEY_KEYS = arrayOf(KEY)
        private val VALUE_KEYS = arrayOf(VALUE)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val EMPTY_KEYS = arrayOfNulls<TextAttributesKey>(0)
    }
}