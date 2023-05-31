package co.edu.eam.unilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentFavoritosBinding
import co.edu.eam.unilocal.model.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoritosFragment : Fragment() {

    lateinit var binding: FragmentFavoritosBinding
    lateinit var lugaresFavoritos: ArrayList<Lugar>
    lateinit var adapter: LugarAdapter
    var usuario: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        lugaresFavoritos = ArrayList()

        usuario = FirebaseAuth.getInstance().currentUser
        if(usuario!= null){
            adapter = LugarAdapter(lugaresFavoritos)
            binding.listaFavoritos.adapter = adapter
            binding.listaFavoritos.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        lugaresFavoritos.clear()
        Firebase.firestore
            .collection("usuarios")
            .document(usuario!!.uid)
            .collection("favoritos")
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    Firebase.firestore
                        .collection("Lugares")
                        .document(doc.id)
                        .get()
                        .addOnSuccessListener {l->
                            val lugar = l.toObject(Lugar::class.java)
                            lugar!!.key = l.id
                            lugaresFavoritos.add(lugar)
                            adapter.notifyDataSetChanged()
                        }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        lugaresFavoritos.clear()
        Firebase.firestore
            .collection("usuarios")
            .document(usuario!!.uid)
            .collection("favoritos")
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    Firebase.firestore
                        .collection("Lugares")
                        .document(doc.id)
                        .get()
                        .addOnSuccessListener {l->
                            val lugar = l.toObject(Lugar::class.java)
                            lugar!!.key = l.id
                            lugaresFavoritos.add(lugar)
                            adapter.notifyDataSetChanged()
                        }
                }
            }
    }
}