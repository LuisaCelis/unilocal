package co.edu.eam.unilocal.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityLoginBinding
import co.edu.eam.unilocal.model.Rol
import co.edu.eam.unilocal.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_carga)
        dialog = builder.create()

        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null){
            makeRedirection(usuario)
        }else{
            binding.recuperarContrasena.setOnClickListener { recuperarContrasena() }
            binding.btnLogin.setOnClickListener { login() }
            binding.btnRegistro.setOnClickListener { irRegistro() }
            setContentView(binding.root)
        }
    }

    fun irRegistro(){
        startActivity( Intent(this, RegistroActivity::class.java) )
    }

    private fun setDialog(show: Boolean) {
        if (show) dialog.show() else dialog.dismiss()
    }

    fun recuperarContrasena() {
        startActivity(Intent(this, OlvidarContrasenaActivity::class.java))
    }

    fun login(){
        val email = binding.emailUsuario.text.toString()
        var password = binding.passwordUsuario.text.toString()

        if (email.isEmpty()){
            binding.correoLayout.isErrorEnabled = true
            binding.correoLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.correoLayout.error = null
        }

        if (password.isEmpty()){
            binding.passwordLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passwordLayout.error = null
        }

        if(email.isNotEmpty() && password.isNotEmpty()){
            setDialog(true)
            var usuario: Usuario? = null
            Firebase.firestore
                .collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener {
                    for (doc in it) {
                        usuario = doc.toObject(Usuario::class.java)
                        usuario!!.key = doc.id
                        break
                    }

                    try {
                        if (usuario != null) {
                            FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { u ->
                                    Log.e("USUARIO", u.toString())
                                    if (u.isSuccessful) {
                                        val userLogin = FirebaseAuth.getInstance().currentUser
                                        if (userLogin != null) {
                                            Firebase.firestore
                                                .collection("usuarios")
                                                .document(userLogin.uid)
                                                .get()
                                                .addOnSuccessListener { u ->
                                                    makeRedirection(userLogin)
                                                }
                                        }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            getString(R.string.datos_incorrectos),
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        setDialog(true)
                                    }
                                }.addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        getString(R.string.datos_incorrectos),
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    setDialog(true)
                                }
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.datos_incorrectos),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            this,
                            getString(R.string.datos_incorrectos),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        setDialog(true)
                    }
                }
        }
    }

    fun makeRedirection(user: FirebaseUser) {
        Firebase.firestore
            .collection("usuarios")
            .document(user.uid)
            .get()
            .addOnSuccessListener { u ->
                val rol = u.toObject(Usuario::class.java)!!.rol
                val intent = when (rol) {
                    Rol.ADMINISTRADOR -> Intent(this, AdministradorActivity::class.java)
                    Rol.CLIENTE -> Intent(this, MainActivity::class.java)
                    Rol.MODERADOR -> Intent(this, ModeradorActivity::class.java)
                }
                setDialog(false)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Log.e("USUARIO", it.message.toString())
            }
    }
}