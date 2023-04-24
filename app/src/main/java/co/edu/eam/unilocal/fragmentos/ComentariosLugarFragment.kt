package co.edu.eam.unilocal.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.ComentarioAdapter
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.model.Comentario

class ComentariosLugarFragment : Fragment() {
    lateinit var binding: FragmentComentariosLugarBinding
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

        //colorPorDefecto = binding.estrellas.textColors.defaultColor
        lista = Comentarios.listar(codigoLugar)
        adapter = ComentarioAdapter(lista)
        binding.listaComentarios.adapter = adapter
        binding.listaComentarios.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //binding.comentarLugar.setOnClickListener { comentar() }

        for (i in 0 until listOf(binding.estrellas.estrellas).size){
            //(binding.estrellas.estrellas[i] as TextView).setOnClickListener { presionarEstrella(i) }
        }

        return binding.root
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
