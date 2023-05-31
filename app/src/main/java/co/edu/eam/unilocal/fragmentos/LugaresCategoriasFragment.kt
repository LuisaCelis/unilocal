package co.edu.eam.unilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentLugaresCategoriasBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LugaresCategoriasFragment : Fragment() {
    lateinit var binding: FragmentLugaresCategoriasBinding
    lateinit var lugares:ArrayList<Lugar>
    lateinit var lugarAdapter: LugarAdapter
    var codigoCategoria:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            codigoCategoria = requireArguments().getString("codigoCategoria")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLugaresCategoriasBinding.inflate(inflater, container, false)
        lugares = ArrayList()

        lugarAdapter = LugarAdapter(lugares)
        binding.listaLugaresPorCategorias.adapter = lugarAdapter
        binding.listaLugaresPorCategorias.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lugares.clear()
        Firebase.firestore
            .collection("Lugares")
            .whereEqualTo("idCategory", codigoCategoria)
            .whereEqualTo("estado" , EstadoLugar.ACEPTADO)
            .get()
            .addOnSuccessListener {
                for(doc in it ){
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    lugares.add(lugar)
                }
                lugarAdapter.notifyDataSetChanged()
            }
    }

    companion object{

        fun newInstance(codigoCategoria:String):LugaresCategoriasFragment{
            val args = Bundle()
            args.putString("codigoCategoria", codigoCategoria)
            val fragmento = LugaresCategoriasFragment()
            fragmento.arguments = args
            return fragmento
        }

    }
}