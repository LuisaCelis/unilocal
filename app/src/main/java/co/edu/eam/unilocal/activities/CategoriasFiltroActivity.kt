package co.edu.eam.unilocal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.CategoriaAdapter
import co.edu.eam.unilocal.databinding.ActivityCategoriasFiltroBinding
import co.edu.eam.unilocal.fragmentos.LugaresCategoriasFragment
import co.edu.eam.unilocal.model.Category
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoriasFiltroActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoriasFiltroBinding
    lateinit var categorias:ArrayList<Category>
    private var LUAGRES_CATEGORIA = "Lugares_por_categoria"
    var codigoCategoria:String? = ""
    lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasFiltroBinding.inflate(layoutInflater)
        categorias = ArrayList()

        codigoCategoria = intent.extras!!.getString("idCategory")

        if(codigoCategoria != ""){
            reemplazarFragmento(1, LUAGRES_CATEGORIA)
        }

        categoriaAdapter = CategoriaAdapter(categorias, this)
        binding.listaCategorias.adapter = categoriaAdapter
        binding.listaCategorias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setContentView(binding.root)
    }

    fun reemplazarFragmento(valor: Int,nombre:String) {

        var fragmento: Fragment = when (valor) {
            1 -> LugaresCategoriasFragment.newInstance(codigoCategoria!!)
            else -> Fragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.contenidoPrincipal.id, fragmento)
            .addToBackStack(nombre)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        categorias.clear()
        Firebase.firestore
            .collection("categorias")
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    val categoria = doc.toObject(Category::class.java)
                    categoria.key = doc.id
                    categorias.add(categoria)
                }
                categoriaAdapter.notifyDataSetChanged()
            }
    }

    fun terminar(){

    }

    companion object{

        fun newInstance(){
        }

    }
}