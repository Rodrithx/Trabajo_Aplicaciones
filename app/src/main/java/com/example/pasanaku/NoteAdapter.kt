package com.example.pasanaku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pasanaku.db.Note

class NoteAdapter(
    private val notas: MutableList<Note>,
    private val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTexto: TextView = view.findViewById(R.id.tvNoteText)
        val btnEliminar: Button = view.findViewById(R.id.btnDeleteNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val nota = notas[position]
        holder.tvTexto.text = nota.text
        holder.btnEliminar.setOnClickListener { onDelete(nota) }
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    fun actualizarLista(nuevaLista: List<Note>) {
        notas.clear()
        notas.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}