package co.edu.eam.unilocal.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentPendientesBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar

class PendientesFragment : Fragment() {

    lateinit var binding:FragmentPendientesBinding
    private var listaPendientes:ArrayList<Lugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("PendientesFragment", "Creando vista para fragmento de pendientes")
        binding = FragmentPendientesBinding.inflate(inflater, container, false)

        listaPendientes = Lugares.listarPorEstado(EstadoLugar.SIN_REVISAR)
        Log.d("PendientesFragment", "Lugares pendientes: ${listaPendientes.size}")

        val adapter = LugarAdapter(listaPendientes)
        binding.listaLugaresPendientes.adapter = adapter
        binding.listaLugaresPendientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}