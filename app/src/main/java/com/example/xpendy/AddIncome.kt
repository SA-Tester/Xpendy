package com.example.xpendy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class AddIncome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_add_income)
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

        val addIncomeBtn = findViewById<Button>(R.id.submitIncome)
        addIncomeBtn.setOnClickListener{
            val dbCursor = DBOperations()
            val formats = Formats()

            val note = getSource()
            val amount = getAmount().toFloat()

            var newData = "["
            val incomesOfTheMonth = dbCursor.readIncome(this, formats.getCurrentMonth())
            val dataList = formats.formatStringToArray(incomesOfTheMonth)
            for ((key, value ) in dataList){
                newData += "$key: $value,"
            }
            newData += "$note: $amount]"

            dbCursor.removeIncome(this, formats.getCurrentMonth())
            dbCursor.addIncome(this, formats.getCurrentMonth(), newData)
        }
    }

    private fun getSource(): String {
        val noteInput = findViewById<EditText>(R.id.note_income)
        return noteInput.text.toString()
    }

    private fun getAmount(): String{
        val amountInput = findViewById<EditText>(R.id.income_amount)
        return amountInput.text.toString()
    }
}
