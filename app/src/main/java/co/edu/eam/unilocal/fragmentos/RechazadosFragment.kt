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
import co.edu.eam.unilocal.databinding.FragmentRechazadosBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar

class RechazadosFragment : Fragment() {

    lateinit var binding: FragmentRechazadosBinding
    private var listaRechazados:ArrayList<Lugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRechazadosBinding.inflate(inflater, container, false)

        listaRechazados = Lugares.listarPorEstado(EstadoLugar.RECHAZADO)

        val adapter = LugarAdapter(listaRechazados)
        binding.listaLugaresRechazados.adapter = adapter
        binding.listaLugaresRechazados.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}