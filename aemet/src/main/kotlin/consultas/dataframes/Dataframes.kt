package consultas.dataframes

import models.Informe
import models.Resultado
import org.jetbrains.kotlinx.dataframe.api.*
import utils.LocalTimeParser
import java.time.LocalTime
import kotlin.math.roundToInt

class Dataframes(resultados: List<Resultado>) {
    private val resDataframes = resultados.toDataFrame()
    private val resMadrid = resultados.filter { it.ciudad == "Madrid" }

    fun generateMadridLogDataframes(): Informe {
        val res = resMadrid.toDataFrame()
        val tempMedia = res.groupBy { it["ciudad"] }
            .aggregate { mean("tempMaxima", "tempMinima") into "Temperatura media" }.first().toString()
        val tempMaxima = res.groupBy("sitio", "date")
            .aggregate { max("tempMaxima") into "Temperatura máxima" }.first().toString()
        val tempMinima = res.groupBy("sitio", "date")
            .aggregate { max("tempMinima") into "Temperatura mínima" }.first().toString()
        val precipitation = res["precipitacion"].convertToDouble()
            .map { if (it!!.toDouble() > 0) "Llueve precipitación: ${it.toDouble()}" else "No llueve precipitación: ${it.toDouble()}" }.first()
        return Informe(tempMedia, tempMaxima, tempMinima, precipitation)
    }

    fun getDataframes(){
        println("------------------------------DATAFRAMES------------------------------")
        resDataframes.schema().print()
        println("Temperatura máxima por día y lugar\n")
        resDataframes.groupBy("tempMaxima", "date", "sitio").print()
        println("Temperatura mínima por día y lugar\n")
        resDataframes.groupBy("tempMinima", "date", "sitio").print()
        println("Temperatura máxima por provincia\n")
        resDataframes.groupBy("sitio", "tempMaxima").print()
        println("Temperatura mínima por provincia\n")
        resDataframes.groupBy("sitio", "tempMinima").print()
        println("Temperatura media por provincia\n")
        resDataframes.groupBy("sitio", "tempMaxima", "tempMinima")
            .aggregate { mean("tempMaxima", "tempMinima") into "Temperatura media" }.print()
        println("Precipitación media por día y provincia\n")
        resDataframes.groupBy("sitio", "date", "precipitacion")
            .aggregate { mean("precipitacion") into "Precipitación media" }.print()
        println("Número de lugares en los que ha llovido por día y provincia\n")
        println("Ha llovido en: ${
            resDataframes.filter { it["precipitacion"].toString() != "0.0" }
            .groupBy("sitio", "date", "precipitacion")
                .aggregate { max("precipitacion").takeIf { it > 0.0 } into "Lugares con lluvia" }
                .count()
        } lugares.")
        println("Temperatura media en madrid\n")
        resDataframes.filter { it["ciudad"] == "Madrid" }
            .groupBy("ciudad")
            .aggregate { mean("tempMaxima", "tempMinima").roundToInt() into "temperatura media" }.print()
        println("Temperatura media máxima total \n")
        resDataframes.groupBy("tempMaxima")
            .aggregate { mean("tempMaxima") }.print()
        println("Temperatura media mínima total \n")
        resDataframes.groupBy("tempMinima")
            .aggregate { mean("tempMinima") into "Temperatura media Mínima"}.print()
        println("Lugares con temperatura máxima antes de las 15 por día")
        resDataframes.filter { val hora = LocalTimeParser(it["hora"].toString(), "H:m")
            hora.isBefore(LocalTime.of(15, 0)) }
            .groupBy("sitio", "tempMaxima", "hora", "date").print()
        println("Lugares con temperatura mínima después de las 17:30 por día\n")
        resDataframes.filter { val hora = LocalTimeParser(it["hora"].toString(), "H:m")
            hora.isAfter(LocalTime.of(17, 30)) }
            .groupBy("sitio", "tempMinima", "hora", "date").print()

    }



}
