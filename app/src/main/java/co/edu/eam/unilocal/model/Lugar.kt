package co.edu.eam.unilocal.model
import java.util.*

class Lugar () {

    constructor(name: String,description: String,idCreador: String,estado: EstadoLugar,idCategory: String,direccion: String,posicion: Posicion,idCiudad: String):this(){
        this.name = name
        this.description = description
        this.idCreador = idCreador
        this.estado = estado
        this.idCategory = idCategory
        this.direccion = direccion
        this.posicion = posicion
        this.idCiudad = idCiudad
    }

    constructor(nombre:String, descripcion:String, idCreador:String, estadoLugar: EstadoLugar, idCategoria: String, direccion:String,idCiudad: String ):this(){
        this.name = nombre
        this.description = descripcion
        this.idCreador = idCreador
        this.estado = estadoLugar
        this.idCategory = idCategoria
        this.direccion = direccion
        this.idCiudad = idCiudad
    }

    var key:String = ""
    var name:String = ""
    var description:String = ""
    var idCreador:String = ""
    var estado:EstadoLugar = EstadoLugar.SIN_REVISAR
    var idCategory:String = ""
    var idModerador:String = ""
    var direccion:String = ""
    var posicion:Posicion = Posicion()
    var idCiudad:String = ""
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
    fun obtenerHoraApertura():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""
        var pos = 0

        for(horario in horarios){
            pos = horario.diaSemana.indexOf(DiaSemana.values()[dia-1])
            mensaje = if(pos != -1){"${horario.diaSemana[pos+1].toString().lowercase()} a las ${horario.horaInicio}:00"
            }else{
                "${horario.diaSemana[0].toString().lowercase()} a las ${horario.horaInicio}:00"
            }
            break
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
        return "Lugar(nombre='$name', descripcion='$description', idCreador=$idCreador, estado=$estado, idCategoria=$idCategory, posicion=$posicion, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }

}