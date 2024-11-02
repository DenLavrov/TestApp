package com.test.app.features.profile.domain.models

import com.test.app.core.Constants.BASE_URL
import com.test.app.features.profile.domain.models.response.ProfileData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Profile(
    val avatar: AvatarData?,
    val userName: String,
    val birthday: String?,
    val phone: String,
    val name: String?,
    val zodiacSign: ZodiacSign?,
    val about: String?,
    val city: String?
)

enum class ZodiacSign {
    Aries,
    Taurus,
    Twins,
    Cancer,
    Lion,
    Virgo,
    Scales,
    Scorpion,
    Sagittarius,
    Capricorn,
    Aquarius,
    Fish;

    companion object {
        fun fromDate(date: String): ZodiacSign {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateTime = LocalDate.parse(date, formatter)
            val month = dateTime.month.value
            val day = dateTime.dayOfMonth
            return when {
                month == 3 && day >= 21 || month == 4 && day <= 20 -> Aries
                month == 4 || month == 5 && day <= 20 -> Taurus
                month == 5 || month == 6 && day <= 21 -> Twins
                month == 6 || month == 7 && day <= 22 -> Cancer
                month == 7 || month == 8 && day <= 21 -> Lion
                month == 8 || month == 9 && day <= 21 -> Virgo
                month == 9 || month == 10 && day <= 21 -> Scales
                month == 10 || month == 11 && day <= 21 -> Scorpion
                month == 11 || month == 12 && day <= 21 -> Sagittarius
                month == 12 || month == 1 && day <= 21 -> Capricorn
                month == 1 || month == 2 && day <= 21 -> Aquarius
                else -> Fish
            }
        }
    }
}

fun ProfileData.toDomain() = Profile(
    avatar = avatars?.let { AvatarData("$BASE_URL/${it.avatar}", "") },
    userName = userName,
    birthday = birthday,
    phone = phone,
    city = city,
    about = about,
    zodiacSign = birthday?.let { ZodiacSign.fromDate(it) },
    name = name
)

