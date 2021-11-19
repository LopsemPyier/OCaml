package com.github.lopsempyier.ocaml

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock


class OCamlBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {
    override fun buildChildren(): List<Block> {
        val blocks: MutableList<Block> = ArrayList<Block>()
        var child: ASTNode? = myNode.firstChildNode
        while (child != null) {
            if (child.getElementType() !== TokenType.WHITE_SPACE) {
                val block: Block = OCamlBlock(
                    child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(),
                    spacingBuilder
                )
                blocks.add(block)
            }
            child = child.getTreeNext()
        }
        return blocks
    }

    override fun getIndent(): Indent? {
        return Indent.getNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }
}