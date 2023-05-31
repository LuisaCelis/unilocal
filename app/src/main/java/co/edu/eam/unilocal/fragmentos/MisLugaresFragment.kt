package co.edu.eam.unilocal.fragmentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.activities.CrearLugarActivity
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MisLugaresFragment : Fragment() {

    lateinit var binding: FragmentMisLugaresBinding
    var lista:ArrayList<Lugar> = ArrayList()
    lateinit var adapter:LugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMisLugaresBinding.inflate(inflater, container, false)

        binding.btnNuevoLugar.setOnClickListener { irACrearLugar() }
        lista = ArrayList()

        adapter = LugarAdapter(lista)
        binding.listaMisLugares.adapter = adapter
        binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    private fun irACrearLugar(){
        startActivity( Intent(activity, CrearLugarActivity::class.java) )
    }

    override fun onResume() {
        super.onResume()
        lista.clear()
        val usuario = FirebaseAuth.getInstance().currentUser
        Firebase.firestore
            .collection("Lugares")
            .whereEqualTo("idCreador",usuario!!.uid)
            .whereEqualTo("estado", EstadoLugar.ACEPTADO)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    lista.add(lugar)
                }
                Log.e("Lugares", lista.toString())
                adapter.notifyDataSetChanged()
            }

    }
}