package adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import models.Informe

class InformeAdapter: TypeAdapter<Informe>() {
    override fun write(out: JsonWriter, value: Informe?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.beginObject()
        out.name("tempMedia").value(value.tempMedia)
        out.name("tempMax").value(value.tempMax)
        out.name("tempMin").value(value.tempMin)
        out.name("precipitation").value(value.precipitation)
        out.endObject()
    }

    override fun read(`in`: JsonReader?): Informe {
        TODO("Not yet implemented")
    }

}