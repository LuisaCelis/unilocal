package co.edu.eam.unilocal.bd

import android.util.Log
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Horario
import co.edu.eam.unilocal.model.Lugar

object Lugares {

    private val lugares:ArrayList<Lugar> = ArrayList()
    var idLugar=7

    init {

        val horario1 = Horario(1, Horarios.obtenerTodos(), 12, 20)
        val horario2 = Horario(2, Horarios.obtenerEntreSemana(), 9, 12)
        val horario3 = Horario(3, Horarios.obtenerFinSemana(), 14, 23)

        val tels:ArrayList<String> = ArrayList()
        tels.add("7431952")
        tels.add("7481808")

        val lugar1 = Lugar("Lucerna", "Deliciosos platillos para disfrutar en familia", 1, EstadoLugar.SIN_REVISAR, 3, "Cl. 20 #14-41",73.3434f, -40.4345f, 1)
        lugar1.id = 3
        lugar1.horarios.add(horario2)

        /*val lugar2 = Lugar("Bar Zonica", "Bar en la ciudad de Armenia", 2, EstadoLugar.SIN_REVISAR, 5, "Cra. 14 # 22-41",73.3434f, -40.4345f, 1)
        lugar2.id = 2
        lugar2.horarios.add(horario1)*/

        val lugar3 = Lugar("Café Quindío", "Café delicioso y exclusivo", 3, EstadoLugar.RECHAZADO, 2, "Cra 20 #32-29",73.3434f, -40.4345f, 5)
        lugar3.id = 4
        lugar3.horarios.add(horario1)

        val lugar4 = Lugar("Luchos Bar", "Los mejores cocteles de la ciudad", 1, EstadoLugar.ACEPTADO, 5, "Cra. 109a #6511",73.3434f, -40.4345f, 3)
        lugar4.id = 5
        lugar4.horarios.add(horario3)

        val lugar5 = Lugar("Hotel Montes de la Castellana", "Hotel de tres estrellas", 1, EstadoLugar.SIN_REVISAR, 1, "Cl. 14 Nte. #10-34",73.3434f, -40.4345f, 1)
        lugar5.id = 6
        lugar5.horarios.add( horario1 )

        val lugar6 = Lugar("Ayenda Calypso 1142", "Ideal para visitantes", 2, EstadoLugar.ACEPTADO, 1, "Cra. 24 Bis #72-08",73.3434f, -40.4345f, 2)
        lugar6.id = 7
        lugar6.horarios.add( horario2 )


        lugares.add( lugar1 )
        //lugares.add( lugar2 )
        lugares.add( lugar3 )
        lugares.add( lugar4 )
        lugares.add( lugar5 )
        lugares.add( lugar6 )

    }

    fun listarPorEstado(estado:EstadoLugar):ArrayList<Lugar>{
        return lugares.filter { l -> l.estado == estado }.toCollection(ArrayList())

    }

    fun obtener(id:Int): Lugar?{
        return lugares.firstOrNull { l -> l.id == id }
    }

    fun buscarNombre(nombre:String): ArrayList<Lugar> {
        return lugares.filter { l -> l.name.lowercase().contains(nombre.lowercase()) && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun crear(lugar:Lugar){
        idLugar = idLugar + 1
        lugar.id = idLugar
        Log.e("ID", "Cont: " + idLugar)
        lugares.add( lugar )
    }

    fun buscarCiudad(codigoCiudad:Int): ArrayList<Lugar> {
        return lugares.filter { l -> l.idCiudad == codigoCiudad && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun buscarCategoria(codigoCategoria:Int): ArrayList<Lugar> {
        return lugares.filter { l -> l.idCategory == codigoCategoria && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun listarPorPropietario(codigo:Int):ArrayList<Lugar>{
        Log.e("ID2","posición 2: " + obtener(2))
        return lugares.filter { l ->l.idCreador == codigo &&  l.estado == EstadoLugar.ACEPTADO}.toCollection(ArrayList())
    }

    fun cambiarEstado(codigo:Int, nuevoEstado:EstadoLugar){

        val lugar = lugares.firstOrNull{ l -> l.id == codigo}

        if(lugar!=null){
            lugar.estado = nuevoEstado
        }

    }

}