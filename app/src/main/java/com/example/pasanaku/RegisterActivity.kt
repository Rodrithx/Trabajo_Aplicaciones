package com.example.pasanaku

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.pasanaku.db.DBHelper

class RegisterActivity : ComponentActivity() {

    lateinit var etNombre: EditText
    lateinit var etUsuario: EditText
    lateinit var etPassword: EditText
    lateinit var etMonto: EditText
    lateinit var tvError: TextView
    lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.etFullName)
        etUsuario = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etMonto = findViewById(R.id.etMontoAporte)
        tvError = findViewById(R.id.tvError)
        btnRegistrar = findViewById(R.id.btnRegister)

        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
    }

    fun registrarUsuario() {
        val nombre = etNombre.text.toString()
        val usuario = etUsuario.text.toString()
        val pass = etPassword.text.toString()
        val montoTxt = etMonto.text.toString()

        if (nombre == "" || usuario == "" || pass == "" || montoTxt == "") {
            tvError.text = "Todos los campos son obligatorios"
            return
        }

        val monto = montoTxt.toDoubleOrNull()
        if (monto == null) {
            tvError.text = "Ingresa un monto valido"
            return
        }

        val dbHelper = DBHelper(this)
        val existe = dbHelper.getUserByUsername(usuario)

        if (existe != null) {
            tvError.text = "Ese usuario ya existe"
        } else {
            dbHelper.insertUser(usuario, pass, nombre, monto)
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}