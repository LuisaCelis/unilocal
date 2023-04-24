package co.edu.eam.unilocal.fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.ComentarioAdapter
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.model.Comentario
import co.edu.eam.unilocal.model.Lugar
import com.google.android.material.snackbar.Snackbar

class ComentariosLugarFragment : Fragment() {
    lateinit var binding: FragmentComentariosLugarBinding
    private var lugar: Lugar? = null
    var lista:ArrayList<Comentario> = ArrayList()
    private var codigoLugar:Int = 0
    private lateinit var adapter: ComentarioAdapter
    var codigoUsuario: Int = 0
    private var colorPorDefecto: Int =  0
    private var estrellas= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        codigoUsuario = sp.getInt("codigo_usuario", -1)

        if(arguments != null){
            codigoLugar = requireArguments().getInt("id_lugar")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)
        lugar = Lugares.obtener(codigoLugar)
        colorPorDefecto = binding.e1.textColors.defaultColor

        if(lugar != null) {
            Log.d("id", "id: ${codigoLugar}")
            lista = Comentarios.listar(lugar!!.id)
            Log.d("Comentarios", "com: ${lista}")
            adapter = ComentarioAdapter(lista)
            binding.listaComentarios.adapter = adapter
            binding.listaComentarios.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            for (i in 0 until binding.estrellasComentario.childCount){
                (binding.estrellasComentario[i] as TextView).setOnClickListener { presionarEstrella(i)}
            }
        }

        binding.comentarLugar.setOnClickListener { hacerComentario() }

        return binding.root
    }

    fun hacerComentario(){
        val texto = binding.contenidoComentario.text.toString()
        var id = lista.size+1

        if (texto.isNotEmpty() && estrellas > 0){
            val comentario = Comentario(id, texto, codigoUsuario, codigoLugar, estrellas)
            Comentarios.crear(comentario)

            limpiarFormulario()
            Snackbar.make(binding.root,getString(R.string.datos_correctos),Snackbar.LENGTH_LONG).show()

            lista.add(comentario)
            adapter.notifyItemInserted(lista.size-1)
        } else{
            Snackbar.make(binding.root,getString(R.string.datos_incorrectos),Snackbar.LENGTH_LONG).show()
        }
    }

    private fun limpiarFormulario(){
        binding.contenidoComentario.setText("")
        borrarSeleccion()
        estrellas = 0
    }

    private fun presionarEstrella(pos: Int){
        estrellas = pos+1
        borrarSeleccion()
        for (i in 0 .. pos){
            (binding.estrellasComentario[i] as TextView).setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
        }
    }

    private fun borrarSeleccion(){
        for(i in 0 until binding.estrellasComentario.childCount){
            (binding.estrellasComentario[i] as TextView).setTextColor(colorPorDefecto)
        }
    }

    companion object{

        fun newInstance(codigoLugar:Int):ComentariosLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

    }

}
