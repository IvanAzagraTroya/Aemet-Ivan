package log

import adapters.InformeAdapter
import adapters.ResultadoAdapter
import com.google.gson.GsonBuilder
import models.Informe
import models.Resultado
import models.Resultados
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class JsonLogger(private val dir: Path) {
    fun resultsJson(resultados: Resultados){
        val file: Path
        if(!Files.exists(Path.of("$dir/resultados.json"))){
            file = Files.createFile(Path.of("$dir/resultados.json"))
            objectJson(resultados, file)
        }else {
            println("Desea crear un nuevo archivo: Escriba Y")
            val option = readln()
            if(option.contentEquals("Y", true)){
                Files.delete(Path.of("$dir/resultados.json"))
                println("Archivo anterior borrado")
                file = Files.createFile(Path.of("$dir/resultados.json"))
                objectJson(resultados, file)
            }else println("Manteniendo el archivo JSON")
        }
        println("Ya se puede consultar el archivo JSON")
    }
    private fun objectJson(resultados: Resultados, file: Path){
        val gson = GsonBuilder()
            .registerTypeAdapter(Resultado::class.java, ResultadoAdapter())
            .setPrettyPrinting()
            .create()
        val json = gson.toJson(resultados.resultados)
        File(file.toString()).writeText(json)
    }

    fun informeMadrid(informe: Informe){
        val file: Path
        if(!Files.exists(Path.of("$dir/informe.json"))){
            file = Files.createFile(Path.of("$dir/informe.json"))
            objectJson(informe, file)
        }else {
            println("Desea crear un nuevo archivo: Escriba Y")
            val option = readln()
            if(option.contentEquals("Y", true)){
                Files.delete(Path.of("$dir/informe.json"))
                println("Archivo anterior borrado")
                file = Files.createFile(Path.of("$dir/informe.json"))
                objectJson(informe, file)
            }else println("Manteniendo el informe JSON")
        }
        println("Ya se puede consultar el informe JSON")
    }
    private fun objectJson(informe: Informe, file: Path){
        val gson = GsonBuilder()
            .registerTypeAdapter(Informe::class.java, InformeAdapter())
            .setPrettyPrinting()
            .create()
        val json = gson.toJson(informe)
        File(file.toString()).writeText(json)
    }
}