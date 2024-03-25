package com.example.xpendy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Code for back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.bottomNavigationView, MoneyFragment())
                .commit()
        }

        val addExpenseBtn = findViewById<Button>(R.id.submitExpense)
        addExpenseBtn.setOnClickListener{
            val dbCursor = DBOperations()
            val formats = Formats()

            val note = getSource()
            val amount = getAmount().toFloat()

            var newData = "["
            val expensesOfTheMonth = dbCursor.readExpense(this, formats.getCurrentMonth())
            val dataList = formats.formatStringToArray(expensesOfTheMonth)
            for ((key, value ) in dataList){
                newData += "$key: $value,"
            }
            newData += "$note: $amount]"

            dbCursor.removeExpense(this, formats.getCurrentMonth())
            dbCursor.addExpense(this, formats.getCurrentMonth(), newData)
        }
    }

    private fun getSource(): String {
        val noteInput = findViewById<EditText>(R.id.note_expense)
        return noteInput.text.toString()
    }

    private fun getAmount(): String{
        val amountInput = findViewById<EditText>(R.id.expense_amount)
        return  amountInput.text.toString()
    }
}