package models

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name ="resultado")
@XmlAccessorType(XmlAccessType.FIELD)
class Resultados(){
    @XmlElementWrapper(name = "resultados_list")
    val resultados: MutableList<Resultado> = mutableListOf()
}
