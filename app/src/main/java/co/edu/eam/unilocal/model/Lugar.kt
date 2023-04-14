package co.edu.eam.unilocal.model
import java.util.*

class Lugar (
    var name: String,
    var description: String,
    var idCreador: Int,
    var estado: EstadoLugar,
    var idCategory: Int,
    var direccion: String,
    var latitud: Float, var longitud: Float,
    var idCiudad: Int
    )

{
    var id:Int = 0
    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()

    fun estaAbierto():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)
        //val minuto = fecha.get(Calendar.MINUTE)

        var mensaje = "Cerrado"

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) && hora < horario.horaCierre && hora > horario.horaInicio  ){
                mensaje = "Abierto"
                break
            }
        }

        return mensaje
    }

    fun obtenerHoraCierre():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) ){
                mensaje = horario.horaCierre.toString()
                break
            }
        }

        return mensaje
    }

    fun obtenerCalificacionPromedio(comentarios:ArrayList<Comentario>):Int{
        var promedio = 0

        if(comentarios.size > 0) {
            val suma = comentarios.stream().map { c -> c.calificacion }
                .reduce { suma, valor -> suma + valor }.get()

            promedio = suma/comentarios.size
        }

        return promedio
    }


    override fun toString(): String {
        return "Lugar(id=$id, nombre='$name', descripcion='$description', idCreador=$idCreador, estado=$estado, idCategoria=$idCategory, latitud=$latitud, longitud=$longitud, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }

}