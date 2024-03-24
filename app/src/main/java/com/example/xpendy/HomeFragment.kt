package com.example.xpendy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
        val currentMonth = getCurrentMonth()


    }

    private fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val monthIndex = calendar.get(Calendar.MONTH)
        val monthsArray = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        return monthsArray[monthIndex]
    }


}
