package com.test.detekt_rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtImportDirective

class DateTimeManagerRule(config: Config = Config.empty) : Rule(config) {
    private companion object {
        private val FORBIDDEN = listOf(
            "LocalDateTime",
            "LocalDate",
            "LocalTime",
            "OffsetDateTime",
            "ZonedDateTime",
            "Instant",
            "java.time.LocalDateTime",
            "java.time.LocalDate",
            "java.time.LocalTime",
            "java.time.OffsetDateTime",
            "java.time.ZonedDateTime",
            "java.time.Instant",
        )
    }

    override val issue: Issue = Issue(
        id = "ForbiddenJavaTimeUsage",
        severity = Severity.Defect,
        description = "Use DateTimeManager instead of java.time directly",
        debt = Debt.FIVE_MINS,
    )

    override val autoCorrect = false

    override fun visitImportDirective(importDirective: KtImportDirective) {}

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)
        if (expression.receiverExpression.text in FORBIDDEN) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(expression),
                    message = "Use DateTimeManager instead of java.time",
                ),
            )
        }
    }
}