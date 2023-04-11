package log

import models.Resultados
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import kotlin.jvm.Throws

class XmlLogger(private val dir: Path) {
    /*@Throws(JAXBException::class)
    fun unmarshal(resultado: Resultado){
        val context = JAXBContext.newInstance(Resultados::class.java)
        val unmarshaller = context.createUnmarshaller()
        if(Files.exists(file) && Files.size(file).toInt() != 0){
            val resultados = unmarshaller.unmarshal(File(file.toString())) as Resultados
            if(!resultados.resultados.contains(resultado)) resultados.resultados.add(resultado)
            //executionsXml(resultados)
        }else {
            println("No se ha encontrado el archivo donde realizar el guardado o este está vacío")
            exitProcess(1)
        }
    }*/

    fun resultsXml(resultados: Resultados){
        val file: Path
        if(!Files.exists(Path.of("$dir/resultados.xml"))){
            file = Files.createFile(Path.of("$dir/resultados.xml"))
            objectXml(resultados, file)
        }else{
            println("Ya existe el archivo XML, desea reemplazarlo?")
            val option = readln()
            if(option.contentEquals("Y", true)){
                Files.delete(Path.of("$dir/resultados.xml"))
                println("Archivo anterior borrado")
                file = Files.createFile(Path.of("$dir/resultados.xml"))
                objectXml(resultados, file)
            }else println("Manteniendo el archivo XML")
        }
        println("Ya se puede consultar el archivo XML")
    }

    @Throws(JAXBException::class)
    private fun objectXml(resultados: Resultados, file: Path){
        val context = JAXBContext.newInstance(Resultados::class.java)
        val marshaller = context.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        marshaller.marshal(resultados, File(file.toString()))
    }
}