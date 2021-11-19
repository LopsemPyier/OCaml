package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlIcon
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl


class OCamlLineMakerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>?>
    ) {
        // This must be an element with a literal expression as a parent
        if (element !is PsiJavaTokenImpl || element.getParent() !is PsiLiteralExpression) {
            return
        }

        // The literal expression must start with the Simple language literal expression
        val literalExpression = element.getParent() as PsiLiteralExpression
        val value = if (literalExpression.value is String) literalExpression.value as String? else null
        if (value == null ||
            !value.startsWith(OCamlAnnotator.OCAML_PREFIX_STR + OCamlAnnotator.OCAML_SEPARATOR_STR)
        ) {
            return
        }

        // Get the Simple language property usage
        val project: Project = element.getProject()
        val possibleProperties: String = value.substring(
            OCamlAnnotator.OCAML_PREFIX_STR.length + OCamlAnnotator.OCAML_SEPARATOR_STR.length
        )
        val properties: List<OCamlProperty> = OCamlUtils.findProperties(project, possibleProperties)
        if (properties.isNotEmpty()) {
            // Add the property to a collection of line marker info
            val builder = NavigationGutterIconBuilder.create(OCamlIcon.ICON)
                .setTargets(properties)
                .setTooltipText("Navigate to Simple language property")
            result.add(builder.createLineMarkerInfo(element))
        }
    }
}