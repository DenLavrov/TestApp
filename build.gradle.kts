import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    id("io.gitlab.arturbosch.detekt").version(libs.versions.detekt)
}

buildscript {
    dependencies {
        classpath(libs.detekt.gradlePlugin)
    }
}

tasks.register<Detekt>("detektChanged") {
    val files = project.findProperty("detekt.changedFiles")
        ?.toString()
        ?.split(",")
        ?: emptyList()

    if (files.isEmpty()) {
        enabled = false
        return@register
    }

    setSource(files.map { file(it) })
    config.setFrom(files("$rootDir/detekt.yml"))
}

dependencies {
    detektPlugins(project(":detekt_rules"))
}