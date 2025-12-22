package com.test.detekt_rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtImportDirective
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeManagerRule(config: Config = Config.empty) : Rule(config) {
    private companion object {
        private val FORBIDDEN_DOT_RECEIVERS = setOf(
            LocalDateTime::class.simpleName,
            LocalDate::class.simpleName,
            LocalTime::class.simpleName,
            OffsetDateTime::class.simpleName,
            ZonedDateTime::class.simpleName,
            Instant::class.simpleName,
            DateTimeFormatter::class.simpleName,
            LocalDateTime::class.qualifiedName,
            LocalDate::class.qualifiedName,
            LocalTime::class.qualifiedName,
            OffsetDateTime::class.qualifiedName,
            ZonedDateTime::class.qualifiedName,
            Instant::class.qualifiedName,
            DateTimeFormatter::class.qualifiedName,
        )

        private val FORBIDDEN_CALLEES = setOf(
            SimpleDateFormat::class.qualifiedName,
            SimpleDateFormat::class.simpleName
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
        if (expression.receiverExpression.text in FORBIDDEN_DOT_RECEIVERS) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(expression),
                    message = expression.receiverExpression.text,
                ),
            )
        }
    }

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        val callee = expression.calleeExpression?.text ?: return
        if (callee in FORBIDDEN_CALLEES) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(expression),
                    message = callee,
                ),
            )
        }
    }
}