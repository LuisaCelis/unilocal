package co.edu.eam.unilocal.bd

import android.util.Log
import co.edu.eam.unilocal.model.DiaSemana
import co.edu.eam.unilocal.model.Horario

object Horarios {
    val horarios:ArrayList<Horario> = ArrayList()

    fun obtenerTodos():ArrayList<DiaSemana>{
        val todosDias:ArrayList<DiaSemana> = ArrayList()
        todosDias.addAll( DiaSemana.values() )
        return todosDias
    }

    fun obtenerFinSemana():ArrayList<DiaSemana>{
        val todosDias:ArrayList<DiaSemana> = ArrayList()
        todosDias.add(DiaSemana.VIERNES)
        todosDias.add(DiaSemana.SABADO)

        return todosDias
    }

    fun obtenerEntreSemana():ArrayList<DiaSemana>{
        val todosDias:ArrayList<DiaSemana> = ArrayList()
        todosDias.add( DiaSemana.LUNES )
        todosDias.add( DiaSemana.MARTES )
        todosDias.add( DiaSemana.MIERCOLES )
        todosDias.add( DiaSemana.JUEVES )
        todosDias.add( DiaSemana.VIERNES )

        return todosDias
    }

    fun agregarHorario(horario:Horario):Horario{
        Log.e("Horarios",horarios.size.toString())
        horario.id = horarios.size +1
        horarios.add(horario)
        return horario
    }
}