package co.edu.eam.unilocal.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityRegistroBinding
import co.edu.eam.unilocal.model.Rol
import co.edu.eam.unilocal.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_carga)
        dialog = builder.create()

        binding.btnRegistro.setOnClickListener{ registrarUsuario()}
    }

    fun registrarUsuario(){
        val name = binding.nameUsuario.text.toString()
        val nickname = binding.nicknameUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()
        val password = binding.passwordUsuario.text.toString()

        setDialog(true)

        if (name.isEmpty()){
            binding.nameLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.nameLayout.error = null
        }

        if (nickname.isEmpty()){
            binding.nicknameLayout.error = getString(R.string.es_obligatorio)
        }else{
            if(nickname.length > 10){
                binding.nicknameLayout.error = getString(R.string.maximo_caracteres)
            }else{
                binding.nicknameLayout.error = null
            }
        }

        if (email.isEmpty()){
            binding.emailLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.emailLayout.error = null
        }

        if (password.isEmpty()){
            binding.passwordLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passwordLayout.error = null
        }

        if (name.isNotEmpty() && nickname.isNotEmpty() && nickname.length <= 10 && email.isNotEmpty() && password.isNotEmpty()){
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val usuario = FirebaseAuth.getInstance().currentUser
                        if(usuario != null){
                            verificarEmail(usuario)
                            val usuarioNuevo = Usuario( name, nickname, email, password, Rol.CLIENTE)
                            Firebase.firestore
                                .collection("usuarios")
                                .document(usuario.uid)
                                .set(usuarioNuevo)
                                .addOnSuccessListener {
                                    Toast.makeText(this, getString(R.string.registro_correcto), Toast.LENGTH_LONG).show()
                                    setDialog(false)
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }.addOnFailureListener {u->
                                    Toast.makeText(this, u.message.toString(), Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                }.addOnFailureListener {
                    setDialog(false)
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
                }
        }

    }

    private fun setDialog(show: Boolean) {
        if (show) dialog.show() else dialog.dismiss()
    }

    private fun verificarEmail(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(baseContext, getString(R.string.email_enviado), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(baseContext, getString(R.string.email_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cambiarActividad(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}