package com.example.xpendy

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

        override fun onCreate(db: SQLiteDatabase?) {
            val query1 = (
                "CREATE TABLE $TABLE_NAME_1 " +
                        "($ID_COL INTEGER PRIMARY KEY, " +
                        "$MONTH_COL TEXT, " +
                        "$INCOME_COL TEXT)"
            )

            val query2 = (
                "CREATE TABLE $TABLE_NAME_2 " +
                        "($ID_COL INTEGER PRIMARY KEY, " +
                        "$MONTH_COL TEXT, " +
                        "$EXPENSE_COL TEXT)"
                )

            db?.execSQL(query1)
            db?.execSQL(query2)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_1")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_2")
            onCreate(db)
        }

        fun addIncome(month: String, income: String){
            val values = ContentValues()

            values.put(MONTH_COL, month)
            values.put(INCOME_COL, income)

            val db = this.writableDatabase
            db.insert(TABLE_NAME_1, null, values)
            db.close()
        }

        fun addExpense(month: String, expense: String){
            val values = ContentValues()

            values.put(MONTH_COL, month)
            values.put(EXPENSE_COL, expense)

            val db = this.writableDatabase
            db.insert(TABLE_NAME_2, null, values)
            db.close()
        }

        fun removeIncome(month: String){
            val db = this.writableDatabase
            db.delete(TABLE_NAME_1, "$MONTH_COL = ?", arrayOf(month))
            db.close()
        }

        fun removeExpense(month: String){
            val db = this.writableDatabase
            db.delete(TABLE_NAME_2, "$MONTH_COL = ?", arrayOf(month))
            db.close()
        }

        fun readIncome(month: String): Cursor? {
            val db = this.readableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME_1 WHERE $MONTH_COL = ?", arrayOf(month))
        }

        fun readExpense(month: String): Cursor? {
            val db = this.readableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME_2 WHERE $MONTH_COL = ?", arrayOf(month))
        }

        companion object{
            private const val DATABASE_NAME = "xpendy"
            private const val DATABASE_VERSION = 1
            const val TABLE_NAME_1 = "income"
            const val TABLE_NAME_2 = "expense"
            const val ID_COL = "id"
            const val MONTH_COL = "month"
            const val INCOME_COL = "incomes"
            const val EXPENSE_COL = "expenses"
        }
    }