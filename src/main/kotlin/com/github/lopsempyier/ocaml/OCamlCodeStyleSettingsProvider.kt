package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings


class OCamlCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
        return OCamlCodeStyleSettings(settings)
    }

    override fun getConfigurableDisplayName(): String {
        return "OCaml"
    }

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(settings, modelSettings, this.configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return SimpleCodeStyleMainPanel(currentSettings, settings)
            }
        }
    }

    private class SimpleCodeStyleMainPanel(currentSettings: CodeStyleSettings?, settings: CodeStyleSettings?) :
        TabbedLanguageCodeStylePanel(OCamlLanguage, currentSettings, settings)
}