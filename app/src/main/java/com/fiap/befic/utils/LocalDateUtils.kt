package com.fiap.befic.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateUtils {

    companion object {

        fun getDate(date: String): LocalDate {
            var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            formatter =
                formatter.withLocale(Locale.getDefault())

            return LocalDate.parse(date, formatter)
        }
    }
}