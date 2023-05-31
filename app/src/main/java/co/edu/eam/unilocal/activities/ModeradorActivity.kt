package co.edu.eam.unilocal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityModeradorBinding
import co.edu.eam.unilocal.fragmentos.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ModeradorActivity : AppCompatActivity() {

    lateinit var binding:ActivityModeradorBinding
    private var MENU_PENDIENTE = "pendiente"
    private var MENU_ACEPTADO = "aceptado"
    private var MENU_RECHAZADO = "rechazado"
    private var usuario: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityModeradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reemplazarFragmento(1, MENU_PENDIENTE)

        binding.barraInferiorMod.setOnItemSelectedListener {
            when(it.itemId){

                R.id.lista_pendiente -> reemplazarFragmento(1, MENU_PENDIENTE)
                R.id.lista_aceptados -> reemplazarFragmento(2, MENU_ACEPTADO)
                R.id.lista_rechazados -> reemplazarFragmento(3, MENU_RECHAZADO)
            }
            true
        }

        usuario = FirebaseAuth.getInstance().currentUser
        if(usuario!=null) {
            binding.btnCerrar.setOnClickListener { cerrarSesion() }
        }
    }

    fun reemplazarFragmento(valor:Int, nombre:String){

        var fragmento: Fragment = when(valor) {
            1 -> PendientesFragment()
            2 -> AceptadosFragment()
            else -> RechazadosFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace( binding.contenidoPrincipal.id, fragmento )
            .addToBackStack(nombre)
            .commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount

        if(count > 0) {
            val nombre = supportFragmentManager.getBackStackEntryAt(count - 1).name
            when (nombre) {
                MENU_PENDIENTE -> binding.barraInferiorMod.menu.getItem(0).isChecked = true
                MENU_ACEPTADO -> binding.barraInferiorMod.menu.getItem(1).isChecked = true
                else -> binding.barraInferiorMod.menu.getItem(2).isChecked = true
            }
        }

    }

    fun cerrarSesion(){
        FirebaseAuth.getInstance().signOut()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }


}
