package com.test.app.features.profile.presentation.utils

import com.test.app.R
import com.test.app.features.profile.domain.models.ZodiacSign

fun ZodiacSign.toStringRes() = when (this) {
    ZodiacSign.Aries -> R.string.zodiac_aries
    ZodiacSign.Taurus -> R.string.zodiac_taurus
    ZodiacSign.Twins -> R.string.zodiac_twins
    ZodiacSign.Cancer -> R.string.zodiac_cancer
    ZodiacSign.Lion -> R.string.zodiac_lion
    ZodiacSign.Virgo -> R.string.zodiac_virgo
    ZodiacSign.Scales -> R.string.zodiac_scales
    ZodiacSign.Scorpion -> R.string.zodiac_scorpion
    ZodiacSign.Sagittarius -> R.string.zodiac_sagittarius
    ZodiacSign.Capricorn -> R.string.zodiac_capricorn
    ZodiacSign.Aquarius -> R.string.zodiac_aquarius
    ZodiacSign.Fish -> R.string.zodiac_fish
}