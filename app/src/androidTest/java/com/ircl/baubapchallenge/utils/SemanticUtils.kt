package com.ircl.baubapchallenge.utils

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher

object SemanticUtils {

    fun hasError(): SemanticsMatcher = SemanticsMatcher(
        "hasError",
    ) { node ->
        val isError = node.config.getOrNull(SemanticsProperties.Error)
        return@SemanticsMatcher isError?.let { true } ?: false
    }

    fun doesNotHaveError(): SemanticsMatcher = SemanticsMatcher(
        "doesNotHaveError",
    ) { node ->
        val isError = node.config.getOrNull(SemanticsProperties.Error)
        return@SemanticsMatcher isError?.let { false } ?: true
    }

}