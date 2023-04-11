package models

import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name= "resultado")
@XmlAccessorType(XmlAccessType.FIELD)
@DataSchema
class Resultado(){
    @XmlAttribute(name = "id")
    val id: String = UUID.randomUUID().toString()
    lateinit var sitio: String
    lateinit var ciudad: String
    var tempMaxima:Double? = null
    var tempMinima:Double? = null
    lateinit var hora: LocalTime
    var precipitacion: Double? = null
    lateinit var date: LocalDate

    constructor(sitio: String, ciudad: String, tempMaxima: Double,
                tempMinima:Double, hora: LocalTime, precipitacion: Double, date: LocalDate): this(){
        this.sitio = sitio
        this.ciudad = ciudad
        this.tempMaxima = tempMaxima
        this.tempMinima = tempMinima
        this.hora = hora
        this.precipitacion = precipitacion
        this.date = date
                }

    override fun toString(): String {
        return "Resultado(id=$id" +
                "sitio='$sitio'" +
                "ciudad='$ciudad'" +
                "tempMaxima=$tempMaxima" +
                "tempMinima=$tempMinima" +
                "hora=$hora" +
                "precipitacion='$precipitacion'" +
                "date=$date)"
    }


}
