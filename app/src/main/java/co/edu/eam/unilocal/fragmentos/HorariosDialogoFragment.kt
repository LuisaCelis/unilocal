package co.edu.eam.unilocal.fragmentos

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.bd.Horarios
import co.edu.eam.unilocal.databinding.FragmentHorariosDialogoBinding
import co.edu.eam.unilocal.model.DiaSemana
import co.edu.eam.unilocal.model.Horario


class HorariosDialogoFragment :  DialogFragment(){
    lateinit var binding: FragmentHorariosDialogoBinding
    lateinit var listener: HorariosDialogoFragment.onHorarioCreadoListener
    var diaSeleccionado: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHorariosDialogoBinding.inflate(inflater,container, false)
        cargarDiasSemana()
        binding.agregarHorario.setOnClickListener { agregarHorario()}
        return binding.root
    }


    fun agregarHorario(){
        val dia = diaSeleccionado
        val horaInicio = binding.horaInicio.text.toString()
        val horaCierre = binding.horaFin.text.toString()

        if(dia!= -1 && horaInicio.isNotEmpty() && horaCierre.isNotEmpty() ){
            val horarios: ArrayList<DiaSemana> = ArrayList()
            horarios.add(DiaSemana.values()[dia])
            val horario = Horarios.agregarHorario(Horario(horarios, horaInicio.toInt(),horaCierre.toInt()))
            listener.elegirHorario(horario)
            dismiss()
        }
    }

    fun cargarDiasSemana(){
        var lista = DiaSemana.values()
        var adapter= ArrayAdapter(requireContext(), R.layout.simple_spinner_item,lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.diaSemana.adapter= adapter
        binding.diaSemana.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                diaSeleccionado = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    interface onHorarioCreadoListener{
        fun elegirHorario(horario: Horario)
    }

}
