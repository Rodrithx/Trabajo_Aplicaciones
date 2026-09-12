package com.example.pasanaku

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pasanaku.db.DBHelper
import com.example.pasanaku.db.Note

class DashboardActivity : ComponentActivity() {

    lateinit var tvBienvenida: TextView
    lateinit var tvMonto: TextView
    lateinit var etNota: EditText
    lateinit var btnAgregar: Button
    lateinit var rvNotas: RecyclerView
    lateinit var adapter: NoteAdapter
    lateinit var dbHelper: DBHelper

    var userId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvBienvenida = findViewById(R.id.tvWelcome)
        tvMonto = findViewById(R.id.tvMontoAporte)
        etNota = findViewById(R.id.etNote)
        btnAgregar = findViewById(R.id.btnAddNote)
        rvNotas = findViewById(R.id.rvNotes)

        dbHelper = DBHelper(this)

        userId = intent.getIntExtra("userId", -1)
        val nombre = intent.getStringExtra("fullName")
        val monto = intent.getDoubleExtra("monto", 0.0)

        tvBienvenida.text = "Bienvenido, " + nombre
        tvMonto.text = "Tu aporte mensual: Bs. " + monto

        adapter = NoteAdapter(mutableListOf()) { nota -> eliminarNota(nota) }
        rvNotas.layoutManager = LinearLayoutManager(this)
        rvNotas.adapter = adapter

        btnAgregar.setOnClickListener {
            agregarNota()
        }

        cargarNotas()
    }

    fun cargarNotas() {
        val lista = dbHelper.getNotesForUser(userId)
        adapter.actualizarLista(lista)
    }

    fun agregarNota() {
        val texto = etNota.text.toString()
        if (texto == "") return

        dbHelper.insertNote(userId, texto)
        etNota.setText("")
        cargarNotas()
    }

    fun eliminarNota(nota: Note) {
        dbHelper.deleteNote(nota.id)
        cargarNotas()
    }
}