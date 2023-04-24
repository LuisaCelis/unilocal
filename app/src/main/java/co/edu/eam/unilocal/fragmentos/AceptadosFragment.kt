package co.edu.eam.unilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentAceptadosBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar

class AceptadosFragment : Fragment() {

    lateinit var binding: FragmentAceptadosBinding
    private var listaAceptados:ArrayList<Lugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAceptadosBinding.inflate(inflater, container, false)

        listaAceptados = Lugares.listarPorEstado(EstadoLugar.ACEPTADO)

        val adapter = LugarAdapter(listaAceptados)
        binding.listaLugaresAceptados.adapter = adapter
        binding.listaLugaresAceptados.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}