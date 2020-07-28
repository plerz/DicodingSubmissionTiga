package com.ifreshmart.githubsearch2020.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.LOGIN
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion._ID
import java.sql.SQLException

class FavoriteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase


    companion object {

        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavoriteHelper(context)
        }
    }

    // Open & Close Connection to dB
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun isOpen(): Boolean {
        return try {
            database.isOpen
        } catch (e: Exception) {
            false
        }
    }

    // CRUD Querry All
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    // Querry by Login
    fun queryByLogin(login: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$LOGIN = ?",
            arrayOf(login),
            null,
            null,
            null,
            null
        )
    }

    // Save data to dB
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // Delete data to dB
    fun deleteByLogin(login: String): Int {
        return database.delete(DATABASE_TABLE, "$LOGIN = '$login'", null)
    }
}