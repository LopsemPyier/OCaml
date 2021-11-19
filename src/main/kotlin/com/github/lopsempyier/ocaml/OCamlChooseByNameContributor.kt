package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project


class OCamlChooseByNameContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        val properties: List<OCamlProperty> = OCamlUtils.findProperties(project)
        val names: MutableList<String> = ArrayList(properties.size)
        for (property in properties) {
            if (property.key != null && (property.key as String).length > 0) {
                names.add(property.key as String)
            }
        }
        return names.toTypedArray()
    }

    override fun getItemsByName(
        name: String,
        pattern: String,
        project: Project,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        // TODO: include non project items
        val properties: List<OCamlProperty> = OCamlUtils.findProperties(project, name)
        return properties.map { it as NavigationItem }.toTypedArray()
    }
}