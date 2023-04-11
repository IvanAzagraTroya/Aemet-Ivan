package utils

import java.io.FileInputStream
import java.util.*

class ObtenerProperties(private val file: String) {
    fun getProperties(): Properties {
        val properties = Properties()
        val archivo = FileInputStream(file)
        properties.load(archivo)
        return properties
    }
}