package co.edu.eam.unilocal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityMainBinding
import co.edu.eam.unilocal.fragmentos.FavoritosFragment
import co.edu.eam.unilocal.fragmentos.InicioFragment
import co.edu.eam.unilocal.fragmentos.MisLugaresFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private var MENU_INICIO = "inicio"
    private var MENU_MIS_LUGARES = "mis_lugares"
    private var MENU_FAVORITOS = "favoritos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reemplazarFragmento(1, MENU_INICIO)

        binding.barraInferior.setOnItemSelectedListener {
            when(it.itemId){

                R.id.menu_inicio -> reemplazarFragmento(1, MENU_INICIO)
                R.id.menu_mis_lugares -> reemplazarFragmento(2, MENU_MIS_LUGARES)
                R.id.menu_favoritos -> reemplazarFragmento(3, MENU_FAVORITOS)

            }
            true
        }
        binding.navView.setNavigationItemSelectedListener(this)
    }

    fun reemplazarFragmento(valor:Int, nombre:String){

        var fragmento: Fragment = when(valor) {
            1 -> InicioFragment()
            2 -> MisLugaresFragment()
            else -> FavoritosFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace( binding.contenidoPrincipal.id, fragmento )
            .addToBackStack(nombre)
            .commit()

    }

    fun mostrarMenu(){
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount

        if(count > 0) {
            val nombre = supportFragmentManager.getBackStackEntryAt(count - 1).name
            when (nombre) {
                MENU_INICIO -> binding.barraInferior.menu.getItem(0).isChecked = true
                MENU_MIS_LUGARES -> binding.barraInferior.menu.getItem(1).isChecked = true
                else -> binding.barraInferior.menu.getItem(2).isChecked = true
            }
        }

    }

    fun cerrarSesion(){
        val sh = getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sh.clear()
        sh.commit()
        finish()
    }


    fun irAPerfil(){
        //jsjsjsj
    }

    fun irAFAQ(){
        //dfddfd
    }

    fun irATYC(){
        //fgdfgd
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.perfil -> irAPerfil()
            R.id.faq -> irAFAQ()
            R.id.tyc -> irATYC()
            R.id.cerrar -> cerrarSesion()
        }
        item.isChecked=true
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}