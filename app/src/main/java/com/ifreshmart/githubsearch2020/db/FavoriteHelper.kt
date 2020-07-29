package com.ifreshmart.githubsearch2020.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteUserColumns.Companion.LOGIN
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteUserColumns.Companion.TABLE_NAME


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

    @Throws(android.database.SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${BaseColumns._ID} ASC",
            null)
    }

    fun queryByLogin(login: String): Cursor {
        return database.query(
            DATABASE_TABLE,                              // The table to query
            null,                              // The array of columns to return (pass null to get all)
            "$LOGIN = ?",                     // The columns for the WHERE clause
            arrayOf(login),                          // The values for the WHERE clause
            null,                           // don't group the rows
            null,                           // don't filter by row groups
            null,                         // don't orderby
            null                           // dont't limit
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByLogin(login: String): Int {
        return database.delete(DATABASE_TABLE, "$LOGIN = '$login'", null)
    }

}