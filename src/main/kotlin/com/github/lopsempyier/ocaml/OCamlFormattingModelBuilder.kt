package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.github.lopsempyier.ocaml.psi.OCamlTypes
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings


class OCamlFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                formattingContext.containingFile,
                OCamlBlock(
                    formattingContext.node,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(codeStyleSettings)
                ),
                codeStyleSettings
            )
    }

    companion object {
        private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
            return SpacingBuilder(settings, OCamlLanguage)
                .around(OCamlTypes.SEPARATOR)
                .spaceIf(settings.getCommonSettings(OCamlLanguage.id).SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .before(OCamlTypes.PROPERTY)
                .none()
        }
    }
}