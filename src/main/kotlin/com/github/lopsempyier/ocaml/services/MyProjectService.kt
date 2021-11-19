package com.github.lopsempyier.ocaml.services

import com.github.lopsempyier.ocaml.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
