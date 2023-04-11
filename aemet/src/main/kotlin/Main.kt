import consultas.dataframes.Dataframes
import consultas.secuencias.Sequences
import lector.Lector
import log.JsonLogger
import log.XmlLogger
import models.Resultados
import utils.ObtenerProperties
import java.nio.file.Path
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val resources = ClassLoader.getSystemResource("app.properties").file
    val properties = ObtenerProperties(resources)
    val fuente = Path.of(properties.getProperties().getProperty("fuente"))
    val destino = Path.of(properties.getProperties().getProperty("destino"))
    val logs = Path.of(properties.getProperties().getProperty("logs"))
    val lector = Lector
    val json = JsonLogger(logs)
    val xml = XmlLogger(logs)
    val results = Resultados()

    args.takeIf { args.size == 1 }?.let {
        when(it[0]){
            "dataframes" -> {
                lector.escribirCsv(fuente, destino)
                val resultados = lector.leerCsv(destino, ";")
                val dataframes = Dataframes(resultados)
                dataframes.getDataframes()
                val informeDataframe = dataframes.generateMadridLogDataframes()
                json.informeMadrid(informeDataframe)
                results.resultados.addAll(resultados)
                xml.resultsXml(results)
                json.resultsJson(results)
            }
            "secuencias" -> {
                lector.escribirCsv(fuente, destino)
                val resultados = lector.leerCsv(destino, ";")
                val sequences = Sequences(resultados)
                sequences.getAllWithSequences()
                val informeSecuencias = sequences.generateMadridLogSequences()
                json.informeMadrid(informeSecuencias)
                results.resultados.addAll(resultados)
                xml.resultsXml(results)
                json.resultsJson(results)
            }
            "informe" -> {
                val resultados = lector.leerCsv(destino, ";")
                results.resultados.addAll(resultados)
                xml.resultsXml(results)
                json.resultsJson(results)
            }
            else -> {
                println("Parámetros admitidos: dataframes, secuencias o informe")
                exitProcess(0)
            }
        }
    } ?: println("Error en los parámetros, solo se admite 1 a la vez" +
            "Estos pueden ser: dataframes, secuencias o informe")

}
