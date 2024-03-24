package com.example.xpendy

import android.annotation.SuppressLint
import android.content.Context

class DBOperations {
    fun addIncome(context: Context, month: String, income: String){
        // INPUT FORMAT: "[Food: 25000, Bills: 8000, Fuel: 7000]"
        val db = DBHandler(context, null)
        db.addIncome(month, income)
    }


    fun addExpense(context: Context, month: String, expense: String){
        // INPUT FORMAT: "[Food: 25000, Bills: 8000, Fuel: 7000]"

        val db = DBHandler(context, null)
        db.addExpense(month, expense)
    }
    @SuppressLint("Range")
    fun readIncome(context: Context, month: String): String{
        val dbHandler = DBHandler(context, null)
        val incomeCursor = dbHandler.readIncome(month)
        var income = ""

        if (incomeCursor != null) {
            while (incomeCursor.moveToNext()) {
                income = incomeCursor.getString(incomeCursor.getColumnIndex(DBHandler.INCOME_COL))
            }
        }
        incomeCursor?.close()
        return income
    }

    @SuppressLint("Range")
    fun readExpense(context: Context, month: String): String{
        val dbHandler = DBHandler(context, null)
        val expenseCursor = dbHandler.readExpense(month)
        var expense = ""

        if (expenseCursor != null) {
            while (expenseCursor.moveToNext()) {
                expense = expenseCursor.getString(expenseCursor.getColumnIndex(DBHandler.EXPENSE_COL))
            }
        }
        expenseCursor?.close()
        return expense
    }
}