package co.edu.eam.unilocal.fragmentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.DetalleLugarActivity
import co.edu.eam.unilocal.activities.MainActivity
import co.edu.eam.unilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.unilocal.model.Ciudad
import co.edu.eam.unilocal.model.Lugar
import co.edu.eam.unilocal.model.Rol
import co.edu.eam.unilocal.model.Usuario
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class InfoLugarFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    lateinit var binding: FragmentInfoLugarBinding
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    private var lugar: Lugar? = null
    private var codigoLugar:String = ""
    var usuario: FirebaseUser? = null
    lateinit var favoritos: ArrayList<String>
    lateinit var gMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getString("id_lugar").toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)
        usuario = FirebaseAuth.getInstance().currentUser
        cargarInformacionPerfil(usuario!!.uid)
        if(codigoLugar!= ""){
            Firebase.firestore
                .collection("Lugares")
                .document(codigoLugar!!)
                .get()
                .addOnSuccessListener {
                    val usuario = FirebaseAuth.getInstance().currentUser
                    lugar = it.toObject(Lugar::class.java)
                    cargarInformacion(lugar!!)
                    favoritos = ArrayList()
                    Firebase.firestore
                        .collection("usuarios")
                        .document(usuario!!.uid)
                        .collection("favoritos")
                        .get()
                        .addOnSuccessListener {f->
                            for(doc in f){
                                favoritos.add(doc.id)
                            }
                            val favorito = favoritos.firstOrNull{fa -> fa == lugar!!.key }
                            if(favorito != null){
                                binding.corazonFavorito.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_favorito))
                                binding.corazonFavorito.setOnClickListener{elimarFavoritos(lugar!!.key,usuario!!.uid)}
                            }else{
                                binding.corazonFavorito.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_no_favorito))
                                binding.corazonFavorito.setOnClickListener{agregarFavoritos(lugar!!.key,usuario!!.uid)}
                            }
                        }
                }
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapa_ubicacion_lugar) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root

    }

    fun eliminarLugar(){
        Firebase.firestore
            .collection("Lugares")
            .document(codigoLugar!!)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),getString(R.string.lugar_eliminado), Toast.LENGTH_LONG)
                    .show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
    }

    fun cargarInformacion(lugar:Lugar){
        Firebase.firestore
            .collection("ciudades")
            .document(lugar.idCiudad)
            .get()
            .addOnSuccessListener {
                val ciudad = it.toObject(Ciudad::class.java)
                binding.ubicacionLugar.text = ciudad!!.name
            }
        binding.nombreLugar.text = lugar.name
        binding.descripcionLugar.text = lugar.description
        binding.direccionLugar.text = lugar.direccion

        if(usuario!!.uid == lugar.idCreador){
            binding.eliminarLugar.visibility = View.VISIBLE
            binding.eliminarLugar.setOnClickListener { eliminarLugar() }
        }

        var telefonos = ""

        if(lugar.telefonos.isNotEmpty()) {
            for (tel in lugar.telefonos) {
                telefonos += "$tel, "
            }
            telefonos = telefonos.substring(0, telefonos.length - 2)
        }

        binding.telefonoLugar.text = telefonos

        var horarios = ""

        Log.e("Horario", lugar.horarios.toString())

        for( horario in lugar.horarios ){
            for(dia in horario.diaSemana){
                horarios += "$dia: ${horario.horaInicio}:00 - ${horario.horaCierre}:00\n"
            }
        }
        gMap.addMarker(
            MarkerOptions().position(LatLng(lugar!!.posicion.lat, lugar!!.posicion.lng))
            .title(lugar.name)
            .visible(true))!!
            .tag = lugar.key

        binding.horariosLugar.text = horarios
    }

    fun agregarFavoritos(codigoLugar:String, codigoUsuario:String){
        val fecha = HashMap<String, Date>()
        fecha.put("fecha", Date())
        Firebase.firestore
            .collection("usuarios")
            .document(codigoUsuario)
            .collection("favoritos")
            .document(codigoLugar)
            .set(fecha)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), getString(R.string.lugar_fav), Toast.LENGTH_LONG)
                    .show()
                binding.corazonFavorito.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_favorito))
                binding.corazonFavorito.setOnClickListener{elimarFavoritos(codigoLugar, codigoUsuario)}
            }
    }

    fun elimarFavoritos(codigoLugar:String, codigoUsuario:String){
        Firebase.firestore
            .collection("usuarios")
            .document(codigoUsuario)
            .collection("favoritos")
            .document(codigoLugar)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), getString(R.string.lugar_no_fav), Toast.LENGTH_LONG)
                    .show()
                binding.corazonFavorito.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_no_favorito))
                binding.corazonFavorito.setOnClickListener{agregarFavoritos(codigoLugar, codigoUsuario)}
            }
    }

    companion object{

        fun newInstance(codigoLugar: String):InfoLugarFragment{
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        gMap = p0
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.setOnInfoWindowClickListener (this)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15F))
    }

    override fun onInfoWindowClick(p0: Marker) {
        val intent = Intent(requireContext(), DetalleLugarActivity::class.java)
        intent.putExtra("codigo",p0.tag.toString())
        startActivity(intent)
    }

    fun cargarInformacionPerfil(user: String){
        Firebase.firestore
            .collection("usuarios")
            .document(user)
            .get()
            .addOnSuccessListener {
                val usuario = it.toObject(Usuario::class.java)
                usuario!!.key = it.id
                if(usuario.rol == Rol.MODERADOR){
                    binding.corazonFavorito.visibility = View.GONE
                }
            }
    }
}