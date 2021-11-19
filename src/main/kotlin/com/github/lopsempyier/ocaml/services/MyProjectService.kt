package com.github.lopsempyier.ocaml.services

import com.intellij.openapi.project.Project
import com.github.lopsempyier.ocaml.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
