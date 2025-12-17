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

detekt {
    config.setFrom("$projectDir/detekt.yml")
    source.setFrom(
        "app/src/main/java",
        "app/src/main/kotlin"
    )
}

dependencies {
    detektPlugins(project(":detekt_rules"))
}