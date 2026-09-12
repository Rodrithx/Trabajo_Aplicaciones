package com.example.pasanaku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.pasanaku.db.DBHelper

class MainActivity : ComponentActivity() {

    lateinit var etUsuario: EditText
    lateinit var etPassword: EditText
    lateinit var tvError: TextView
    lateinit var btnLogin: Button
    lateinit var tvRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsuario = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        tvError = findViewById(R.id.tvError)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegistro = findViewById(R.id.tvGoRegister)

        btnLogin.setOnClickListener {
            validarLogin()
        }

        tvRegistro.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    fun validarLogin() {
        val usuario = etUsuario.text.toString()
        val pass = etPassword.text.toString()

        if (usuario == "" || pass == "") {
            tvError.text = "Todos los campos son obligatorios"
            return
        }

        val dbHelper = DBHelper(this)
        val user = dbHelper.login(usuario, pass)

        if (user != null) {
            val i = Intent(this, DashboardActivity::class.java)
            i.putExtra("userId", user.id)
            i.putExtra("fullName", user.fullName)
            i.putExtra("monto", user.montoAporte)
            startActivity(i)
            finish()
        } else {
            tvError.text = "Usuario o contraseña incorrectos"
        }
    }
}