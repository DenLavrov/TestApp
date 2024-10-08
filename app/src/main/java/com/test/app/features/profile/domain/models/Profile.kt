package com.test.app.features.profile.domain.models

import com.test.app.R
import com.test.app.core.di.BASE_URL
import com.test.app.features.profile.data.models.AvatarData
import com.test.app.features.profile.data.models.ProfileData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class Profile(
    val avatar: AvatarData?,
    val userName: String,
    val birthday: String?,
    val phone: String,
    val name: String?,
    val zodiacSign: Int?,
    val about: String?,
    val city: String?
)

fun ProfileData.toDomain() = Profile(
    avatar = avatars?.let { AvatarData("$BASE_URL/${it.avatar}", "") },
    userName = userName,
    birthday = birthday,
    phone = phone,
    city = city,
    about = about,
    zodiacSign = birthday?.let { getZodiacSign(it) },
    name = name
)

private fun getZodiacSign(date: String): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse(date, formatter)
    val month = dateTime.month.value
    val day = dateTime.dayOfMonth
    return when {
        month == 3 && day >= 21 || month == 4 && day <= 20 -> R.string.zodiac_aries
        month == 4 || month == 5 && day <= 20 -> R.string.zodiac_taurus
        month == 5 || month == 6 && day <= 21 -> R.string.zodiac_twins
        month == 6 || month == 7 && day <= 22 -> R.string.zodiac_cancer
        month == 7 || month == 8 && day <= 21 -> R.string.zodiac_lion
        month == 8 || month == 9 && day <= 21 -> R.string.zodiac_virgo
        month == 9 || month == 10 && day <= 21 -> R.string.zodiac_scales
        month == 10 || month == 11 && day <= 21 -> R.string.zodiac_scorpion
        month == 11 || month == 12 && day <= 21 -> R.string.zodiac_sagittarius
        month == 12 || month == 1 && day <= 21 -> R.string.zodiac_capricorn
        month == 1 || month == 2 && day <= 21 -> R.string.zodiac_aquarius
        else -> R.string.zodiac_fish
    }
}

