package consultas.secuencias

import models.Informe
import models.Resultado
import kotlin.math.roundToInt

class Sequences(private val results: List<Resultado>) {
    private val datos = results.map {
        val date = it.date
        val sitio = it.sitio
        val ciudad = it.ciudad
        val tempMax = it.tempMaxima
        val tempMin = it.tempMinima
        val precipitacion = it.precipitacion
        val hora = it.hora
        mapOf("date" to date, "ciudad" to ciudad, "sitio" to sitio,
            "tempMax" to tempMax, "tempMin" to tempMin, "precipitacion" to precipitacion,
            "hora" to hora)
    }

    fun getAllWithSequences() {
        temperaturaMaxDiaSitio()
        temperaturaMinDiaSitio()
        tempMaxProvincia()
        tempMediaProvincia()
        precipitacionMediaDiaProvincia()
        numLugaresLluviaPorDiaYProvincia()
        tempMediaMadrid()
        tempMaxTotal()
        tempMinTotal()
        tempMaxAntesTres()
        tempMinDespuesCinco()
    }

    private fun temperaturaMaxDiaSitio() {
        datos.groupBy { mapOf("date" to it["date"], "sitio" to it["sitio"], "tempMax" to it["tempMax"]) }
            .mapValues { (_, temps) ->
                temps.maxByOrNull { it["tempMax"] as Double }!!["tempMax"] as Double
            }
            .forEach { (key, value) ->
                println("fecha: ${key["date"]}, lugar: ${key["sitio"]}: Temperatura máxima = $value")
            }
    }

    private fun temperaturaMinDiaSitio() {
        datos.groupBy { mapOf("date" to it["date"], "sitio" to it["sitio"], "tempMin" to it["tempMin"]) }
            .mapValues { (_, temps) ->
                temps.minByOrNull { it["tempMin"] as Double }!!["tempMin"] as Double
            }
            .forEach { (key, value) ->
                println("fecha: ${key["date"]}, lugar: ${key["sitio"]}: Temperatura mínima = $value")
            }
    }

    private fun tempMaxProvincia() {
        datos.groupBy { mapOf("date" to it["date"], "ciudad" to it["ciudad"]) }
            .mapValues { (_, temps) ->
                temps.maxByOrNull { it["tempMax"] as Double }!!["tempMax"] as Double
            }
            .forEach { (key, value) ->
                println("fecha: ${key["date"]}, ciudad: ${key["ciudad"]},lugar: ${key["sitio"]}: Temperatura máxima = $value")
            }
    }

    private fun tempMinProvincia() {
        datos.groupBy { mapOf("date" to it["date"], "ciudad" to it["ciudad"]) }
            .mapValues { (_, temps) ->
                temps.minByOrNull { it["tempMax"] as Double }!!["tempMax"] as Double
            }
            .forEach { (key, value) ->
                println("fecha: ${key["date"]}, ciudad: ${key["ciudad"]},lugar: ${key["sitio"]}: Temperatura máxima = $value")
            }
    }

    private fun tempMediaProvincia() {
        datos.groupBy { mapOf("date" to it["date"], "ciudad" to it["ciudad"]) }
            .mapValues { (_, temps) ->
                temps.map { (it["tempMax"] as Double + it["tempMin"] as Double) /2 }.average()}
                    .forEach { (key, value) ->
                        println("fecha: ${key["date"]}, ciudad: ${key["ciudad"]},lugar: ${key["sitio"]}: Temperatura media = $value")
                    }
    }
    private fun precipitacionMediaDiaProvincia(){
        datos.groupBy { mapOf("date" to it["date"], "ciudad" to it["ciudad"]) }
            .mapValues { (_, prec) ->
                prec.map { it["precipitacion"] as Double }.average()}
            .forEach { (key, value) ->
                println("fecha: ${key["date"]}, ciudad: ${key["ciudad"]}: precipitacion media = $value")
            }
    }
    private fun numLugaresLluviaPorDiaYProvincia() {
        datos.groupBy { mapOf("date" to it["date"], "ciudad" to it["ciudad"]) }
            .forEach { (key, value) ->
                val numLugares = value.map { it["sitio"] }.distinct().count()
                println("Fecha: ${key["date"]}, Provincia: ${key["ciudad"]}, Número de lugares con lluvia: $numLugares")
            }
    }
    private fun tempMediaMadrid(){
        val filtrado = datos.filter { it["ciudad"] == "Madrid" }
        val avg = filtrado.map { (it["tempMax"] as Double +it["tempMin"] as Double)/2 }.average()
        println("Temperatura media de la provincia de Madrid: $avg")
    }
    private fun tempMaxTotal(){
        val temps = datos.maxByOrNull { it["tempMax"] as Double }
        println("Temperatura máxima media total: $temps")
    }
    private fun tempMinTotal(){
        val temps = datos.minByOrNull { it["tempMin"] as Double }
        println("Temperatura mínima media total: $temps")
    }
    private fun tempMaxAntesTres(){
        datos.groupBy { it["date"] }
            .forEach { (dia, temps) ->
                val lugares = temps.filter { it["hora"]!!.toString().substringBefore(":").toInt() < 15 }
                    .map { it["sitio"] }
                println("$dia, máxima antes de las 15:00: $lugares")
            }
    }
    private fun tempMinDespuesCinco(){
        datos.groupBy { it["date"] }
            .forEach { (dia, temps) ->
                val lugares = temps.filter { it["hora"]!!.toString().substringBefore(":").toDouble() > 17.30 }
                    .map { it["sitio"] }
                println("$dia, mínima después de las 17:30: $lugares")
            }
    }

    fun generateMadridLogSequences(): Informe {
        val tempMedia = datos.filter { it["ciudad"] == "Madrid" }
            .map { it["tempMax"] as Double }
            .average().roundToInt().toString()

        val tempMaxima = datos.filter { it["ciudad"] == "Madrid" }
            .maxByOrNull { it["tempMax"] as Double }!!.let {
                val sitio = it["sitio"]
                val hora = it["hora"]
                val fecha = it["date"]
                val tempMax = it["tempMax"]
                "lugar: $sitio hora: $hora fecha: $fecha temperatura: $tempMax"
            }


        val tempMinima = datos.filter { it["ciudad"] == "Madrid" }
            .minByOrNull { it["tempMin"] as Double }!!.let {
                val sitio = it["sitio"]
                val hora = it["hora"]
                val fecha = it["date"]
                val tempMin = it["tempMin"]
                "lugar: $sitio hora: $hora fecha: $fecha temperatura: $tempMin"
            }

        val precipitation = datos.filter { it["ciudad"] == "Madrid" }
            .sumOf { it["precipitacion"] as Double }.toString()
        val prep = if(precipitation == "0.0") "No hubo precipitación" else "Ha habido precipitación"

        return Informe(tempMedia, tempMaxima, tempMinima, prep)
    }
}