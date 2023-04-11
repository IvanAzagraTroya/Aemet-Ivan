package lector

import models.Resultado
import utils.LocalTimeParser
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.appendText
import kotlin.io.path.name
import kotlin.system.exitProcess

object Lector {
    fun escribirCsv(dirInicial: Path, dirDestino: Path){
        val cabecera = "Sitio; Ciudad; Máximo; Hora-Máximo; Mínimo; Hora-Mínimo; Precipitación; Fecha\n"
        val newArchivo = mutableListOf<String>()
        val finalFile: Path
        if(!Files.exists(Path.of(dirDestino.toString()+File.separator+"Aemet.csv"))) {
            readContents(dirInicial, newArchivo)
            finalFile = Files.createFile(Path.of(dirDestino.toString()+ File.separator+"Aemet.csv"))
            for (item in newArchivo) {
                finalFile.appendText(item)
            }
        } else {
            println("Ya existe el archivo Aemet.csv, desea borrarlo?")
            val eleccion = readln()
            if(eleccion.contentEquals("Y", true)){
                println("Creando nuevo archivo...")
                Files.delete(Path.of(dirDestino.toString()+File.separator+"Aemet.csv"))
                readContents(dirInicial, newArchivo)
                finalFile = Files.createFile(Path.of(dirDestino.toString()+ File.separator+"Aemet.csv"))
                finalFile.appendText(cabecera)
                for (item in newArchivo) {
                    finalFile.appendText(item)
                }
            }else {
                println("Se mantiene el archivo ya existente")
            }
        }
        println("Archivo Aemet.csv accesible")
    }

    fun leerCsv(dir: Path, delimiter: String): List<Resultado> {
        println("Leyendo csv Aemet")
        val resultados = mutableListOf<Resultado>()
        val lines = File(dir.toString()+File.separator+"Aemet.csv")
            .readLines().drop(1)

        if(lines.isEmpty()){
            println("El archivo Aemet.csv está vacío")
            exitProcess(0)
        }
        lines.forEach{
            val arguments = it.split(delimiter)
            val resultado = Resultado(
                sitio = arguments[0],
                ciudad = arguments[1],
                tempMaxima = arguments[2].toDouble(),
                tempMinima = arguments[4].toDouble(),
                hora = LocalTimeParser(arguments[5], "H:m"),
                precipitacion = arguments[6].toDouble(),
                date = LocalDate.parse(arguments[7])
            )
            resultados.add(resultado)
        }
        println("Aemet.csv leído y guardado en memoria")
        return resultados
    }


    private fun readContents(dirInicial: Path, newArchivo: MutableList<String>){
        Files.walk(dirInicial).skip(1)
            .forEach {
                val name = it.name.substringBeforeLast(".").substringAfter("Aemet").trim()
                println("Leyendo Aemet de la fecha: $name")
                val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                dirInicial.resolve(it).toFile().inputStream()
                    .bufferedReader(Charsets.UTF_8).use {
                        val text = it.readLines()
                        for(t in text){
                            newArchivo.add("$t;${LocalDate.parse(name, formatter)}\n")
                        }
                    }
            }
    }
}