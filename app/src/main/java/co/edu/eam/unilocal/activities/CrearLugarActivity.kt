package co.edu.eam.unilocal.activities

import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Ciudades
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityCrearLugarBinding
import co.edu.eam.unilocal.fragmentos.MisLugaresFragment
import co.edu.eam.unilocal.model.Category
import co.edu.eam.unilocal.model.Ciudad
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar

class CrearLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCrearLugarBinding
    var posCiudad:Int = -1
    var posCategoria:Int = -1
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        cargarCiudades()
        cargarCategorias()

        binding.btnCrear.setOnClickListener { crearNuevoLugar() }
    }

    fun cargarCiudades(){
        var lista = ciudades.map { c -> c.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCiudad = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    fun cargarCategorias(){
        var lista = categorias.map { c -> c.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.categoriaLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCategoria = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    fun crearNuevoLugar() {

        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val idCiudad = ciudades[posCiudad].id
        val idCategoria = categorias[posCategoria].id

        if (nombre.isEmpty()) {
            binding.nombreLayout.error = getString(R.string.es_obligatorio)
        } else {
            binding.nombreLayout.error = null
        }

        if (descripcion.isEmpty()) {
            binding.descripcionLayout.error = getString(R.string.es_obligatorio)
        } else {
            binding.descripcionLayout.error = null
        }

        if (direccion.isEmpty()) {
            binding.direccionLayout.error = getString(R.string.es_obligatorio)
        } else {
            binding.direccionLayout.error = null
        }

        if (telefono.isEmpty()) {
            binding.telefonoLayout.error = getString(R.string.es_obligatorio)
        } else {
            binding.telefonoLayout.error = null
        }

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != -1 && idCategoria != -1) {

            val nuevoLugar = Lugar(nombre, descripcion, 1, EstadoLugar.SIN_REVISAR, idCategoria,direccion,0f, 0f, idCiudad)

            val telefonos: ArrayList<String> = ArrayList()
            telefonos.add(telefono)

            nuevoLugar.telefonos = telefonos

            Lugares.crear(nuevoLugar)

            Toast.makeText(this, getString(R.string.lugar_creado), Toast.LENGTH_LONG).show()
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}