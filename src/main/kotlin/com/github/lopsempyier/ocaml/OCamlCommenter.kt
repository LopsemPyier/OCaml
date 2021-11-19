package com.github.lopsempyier.ocaml

import com.intellij.lang.Commenter


class OCamlCommenter : Commenter {
    override fun getLineCommentPrefix(): String {
        return "#"
    }

    override fun getBlockCommentPrefix(): String {
        return ""
    }

    override fun getBlockCommentSuffix(): String? {
        return null
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }
}