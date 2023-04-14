package co.edu.eam.unilocal.model
import java.util.*

class Comentario (
    var id:Int,
    var texto:String,
    var idUser: Int,
    var idLugar: Int,
    var calificacion: Int
) {
    var fecha:Date = Date()

    override fun toString(): String {
        return "Comentario(id=$id, texto='$texto', idUsuario=$idUser, idLugar=$idLugar, calificacion=$calificacion, fecha=$fecha)"
    }

}