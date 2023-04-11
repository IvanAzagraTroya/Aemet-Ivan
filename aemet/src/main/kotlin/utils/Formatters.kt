package utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTimeParser(hora: String, patron: String): LocalTime{
    val formateador = DateTimeFormatter.ofPattern(patron)
    return LocalTime.parse(hora, formateador)
}