package com.example.xpendy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.Format
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var availableBalanceLabel: TextView
    private lateinit var incomeAmountLabel: TextView
    private lateinit var expensesAmountLabel: TextView
    private lateinit var dbOperations: DBHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        dbOperations = DBHandler(requireContext(), null)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        availableBalanceLabel = view.findViewById(R.id.availablebalanceLabel)
        incomeAmountLabel = view.findViewById(R.id.incomeamountLabel)
        expensesAmountLabel = view.findViewById(R.id.expensesamountLabel)

        // Retrieve current month
        val formats = Formats()
        val currentMonth = formats.getCurrentMonth()
        val totIncome = getTotalIncome(currentMonth)
        val totExpense = getTotalExpenses(currentMonth)

        incomeAmountLabel.text = totIncome.toString()
        expensesAmountLabel.text = totExpense.toString()
        availableBalanceLabel.text = (totIncome - totExpense).toString()

    }

    private fun getTotalIncome(currentMonth: String): Float{
        val dbCursor = DBOperations()
        val formats = Formats()

        val data = formats.formatStringToArray(dbCursor.readIncome(requireContext(), currentMonth))
        var tot = 0f
        for((key, value) in data){
            tot += value
        }
        return tot
    }

    private fun getTotalExpenses(currentMonth: String): Float{
        val dbCursor = DBOperations()
        val formats = Formats()

        val data = formats.formatStringToArray(dbCursor.readExpense(requireContext(), currentMonth))
        var tot = 0f
        for((key, value) in data){
            tot += value
        }
        return tot
    }
}
