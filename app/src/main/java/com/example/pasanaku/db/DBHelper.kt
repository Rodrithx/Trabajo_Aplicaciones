package com.example.pasanaku.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "pasanaku.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, fullName TEXT, montoAporte REAL)")
        db.execSQL("CREATE TABLE notas (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, texto TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS notas")
        onCreate(db)
    }

    fun insertUser(username: String, password: String, fullName: String, monto: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        values.put("fullName", fullName)
        values.put("montoAporte", monto)
        db.insert("usuarios", null, values)
        db.close()
    }

    fun getUserByUsername(username: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ?", arrayOf(username))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("username")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getString(cursor.getColumnIndexOrThrow("fullName")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("montoAporte"))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun login(username: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ? AND password = ?", arrayOf(username, password))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("username")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getString(cursor.getColumnIndexOrThrow("fullName")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("montoAporte"))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun insertNote(userId: Int, texto: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("userId", userId)
        values.put("texto", texto)
        db.insert("notas", null, values)
        db.close()
    }

    fun getNotesForUser(userId: Int): List<Note> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM notas WHERE userId = ?", arrayOf(userId.toString()))
        val lista = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            lista.add(
                Note(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("userId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("texto"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

    fun deleteNote(id: Int) {
        val db = this.writableDatabase
        db.delete("notas", "id = ?", arrayOf(id.toString()))
        db.close()
    }
}