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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AceptadosFragment : Fragment() {

    lateinit var binding: FragmentAceptadosBinding
    lateinit var listaAceptados:ArrayList<Lugar>
    lateinit var adapterLista: LugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listaAceptados = ArrayList()
        binding = FragmentAceptadosBinding.inflate(inflater, container, false)
        adapterLista = LugarAdapter(listaAceptados)
        binding.listaLugaresAceptados.adapter = adapterLista
        binding.listaLugaresAceptados.adapter = adapterLista
        binding.listaLugaresAceptados.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        listaAceptados.clear()
        Firebase.firestore
            .collection("Lugares")
            .whereEqualTo("estado", EstadoLugar.ACEPTADO)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    listaAceptados.add(lugar)
                }
                adapterLista.notifyDataSetChanged()
            }
    }
}