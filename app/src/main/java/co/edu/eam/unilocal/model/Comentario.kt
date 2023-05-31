package co.edu.eam.unilocal.model
import java.util.*

class Comentario () {

    constructor(texto: String, idUser: String, calificacion: Int):this(){
        this.texto = texto
        this.idUser = idUser
        //this.idLugar = idLugar
        this.calificacion = calificacion
    }

    var key:String = ""
    var texto:String = ""
    var idUser: String = ""
    //var idLugar: Int = 0
    var calificacion: Int = 0
    var fecha:Date = Date()

    override fun toString(): String {
        return "Comentario(texto='$texto', idUsuario=$idUser, calificacion=$calificacion, fecha=$fecha)"
    }

}