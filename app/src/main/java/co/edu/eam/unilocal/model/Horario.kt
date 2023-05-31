package co.edu.eam.unilocal.model

class Horario()
{
    constructor(diaSemana:ArrayList<DiaSemana>,  horaInicio: Int, horaCierre: Int):this(){
        this.diaSemana = diaSemana
        this.horaInicio = horaInicio
        this.horaCierre = horaCierre
    }

    var id : Int = 0
    var diaSemana: ArrayList<DiaSemana> = ArrayList()
    var horaInicio: Int = 0
    var horaCierre:Int = 0

    override fun toString(): String {
        return "Horario(diaSemana=$diaSemana, horaInicio=$horaInicio, horaCierre=$horaCierre)"
    }
}