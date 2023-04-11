package adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import models.Resultado
import java.time.format.DateTimeFormatter

class ResultadoAdapter: TypeAdapter<Resultado>() {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    override fun write(out: JsonWriter, value: Resultado?) {
        if (value ==  null){
            out.nullValue()
            return
        }
        out.beginObject()
        out.name("id").value(value.id)
        out.name("sitio").value(value.sitio)
        out.name("ciudad").value(value.ciudad)
        out.name("tempMaxima").value(value.tempMaxima)
        out.name("tempMinima").value(value.tempMaxima)
        out.name("hora").value(value.hora.format(timeFormatter))
        out.name("precipitacion").value(value.precipitacion)
        out.name("date").value(value.date.format(dateFormatter))
        out.endObject()
    }

    override fun read(`in`: JsonReader?): Resultado {
        TODO("Not yet implemented")
    }

}