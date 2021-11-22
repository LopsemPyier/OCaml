package com.github.lopsempyier.ocaml

import com.github.lopsempyier.ocaml.language.OCamlFileType
import com.github.lopsempyier.ocaml.psi.OCamlProperty
import com.intellij.application.options.CodeStyle
import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import com.intellij.util.containers.ContainerUtil


class OCamlCodeInsightTest : LightJavaCodeInsightFixtureTestCase() {
    /**
     * @return path to test data file directory relative to working directory in the run configuration for this test.
     */
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    /* fun testCompletion() {
        myFixture.configureByFiles("CompleteTestData.java", "DefaultTestData.ml")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertSameElements(lookupElementStrings!!, "key with spaces", "language", "message", "tab", "website")
    } */

    fun testAnnotator() {
        myFixture.configureByFiles("AnnotatorTestData.java", "DefaultTestData.ml")
        myFixture.checkHighlighting(false, false, true, true)
    }

    fun testFormatter() {
        myFixture.configureByFile("FormatterTestData.ml")
        CodeStyle.getLanguageSettings(myFixture.file).SPACE_AROUND_ASSIGNMENT_OPERATORS = true
        CodeStyle.getLanguageSettings(myFixture.file).KEEP_BLANK_LINES_IN_CODE = 2
        WriteCommandAction.writeCommandAction(project).run<RuntimeException> {
            CodeStyleManager.getInstance(project).reformatText(
                myFixture.file,
                ContainerUtil.newArrayList(myFixture.file.textRange)
            )
        }
        myFixture.checkResultByFile("DefaultTestData.ml")
    }

    fun testRename() {
        myFixture.configureByFiles("RenameTestData.java", "RenameTestData.ml")
        myFixture.renameElementAtCaret("websiteUrl")
        myFixture.checkResultByFile("RenameTestData.ml", "RenameTestDataAfter.ml", false)
    }

    /* fun testFolding() {
        myFixture.configureByFile("DefaultTestData.ml")
        myFixture.testFolding("$testDataPath/FoldingTestData.java")
    } */

    /* fun testFindUsages() {
        val usageInfos = myFixture.testFindUsages("FindUsagesTestData.ml", "FindUsagesTestData.java")
        assertEquals(1, usageInfos.size)
    } */

    fun testCommenter() {
        myFixture.configureByText(OCamlFileType, "<caret>website = https://en.wikipedia.org/")
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("#website = https://en.wikipedia.org/")
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("website = https://en.wikipedia.org/")
    }

    fun testReference() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("ReferenceTestData.java", "DefaultTestData.ml")
        val resolvedSimpleProperty: OCamlProperty = assertInstanceOf(
            referenceAtCaret.resolve(),
            OCamlProperty::class.java
        )
        assertEquals("https://en.wikipedia.org/", resolvedSimpleProperty.value)
    }

    /* fun testDocumentation() {
        myFixture.configureByFiles("DocumentationTestData.java", "DocumentationTestData.ml")
        val originalElement = myFixture.elementAtCaret
        var element = DocumentationManager
            .getInstance(project)
            .findTargetElement(myFixture.editor, originalElement.containingFile, originalElement)
        if (element == null) {
            element = originalElement
        }
        val documentationProvider = DocumentationManager.getProviderFromElement(element)
        val generateDoc = documentationProvider.generateDoc(element, originalElement)
        assertNotNull(generateDoc)
        assertSameLinesWithFile(
            "$testDataPath/DocumentationTest.html.expected",
            generateDoc!!
        )
    } */
}