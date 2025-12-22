package com.test.detekt_rules

import com.test.detekt_rules.rules.DateTimeManagerRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class DetektCustomRulesSetProvider : RuleSetProvider {
    override val ruleSetId: String = "test-rules"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                DateTimeManagerRule()
            ),
        )
    }
}
