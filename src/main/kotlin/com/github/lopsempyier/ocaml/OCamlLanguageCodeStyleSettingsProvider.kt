package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlLanguage
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider


class OCamlLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language {
        return OCamlLanguage
    }

    override fun customizeSettings(
        consumer: CodeStyleSettingsCustomizable,
        settingsType: SettingsType
    ) {
        if (settingsType == SettingsType.SPACING_SETTINGS) {
            consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS")
            consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Separator")
        } else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
            consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE")
        }
    }

    override fun getCodeSample(settingsType: SettingsType): String {
        return """# You are reading the ".properties" entry.
                ! The exclamation mark can also mark text as comments.
                website = https://en.wikipedia.org/
                
                language = English
                # The backslash below tells the application to continue reading
                # the value onto the next line.
                message = Welcome to \
                          Wikipedia!
                # Add spaces to the key
                key\ with\ spaces = This is the value that could be looked up with the key "key with spaces".
                # Unicode
                tab : \u0009"""
    }
}