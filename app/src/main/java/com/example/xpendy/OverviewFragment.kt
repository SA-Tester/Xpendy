package com.example.xpendy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.Calendar

class OverviewFragment : Fragment() {

    private lateinit var pieChart: PieChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call addTestData
        // addTestData(getCurrentMonth())
        var formats = Formats()

        // Initialize pie chart
        pieChart = view.findViewById(R.id.pieChart)

        // Set current month in TextView
        val currentMonthTextView = view.findViewById<TextView>(R.id.month)
        val currentMonth = formats.getCurrentMonth()
        val fullMonthName = getFullMonthName(currentMonth)
        currentMonthTextView.text = fullMonthName

        // Load expense data
        displayExpenseData()

        view.findViewById<Button>(R.id.expenseButton).setOnClickListener {
            displayExpenseData()
        }

        view.findViewById<Button>(R.id.incomeButton).setOnClickListener {
            displayIncomeData()
        }
    }

    private fun addTestData(month: String){
        val income = "[Sales: 40000, Rentals: 20000]"
        val expenses = "[Food: 5000, Travel: 6000, Clothing: 3000]"

        val dbCursor = DBOperations()
        dbCursor.addExpense(requireContext(), month, expenses)
        dbCursor.addIncome(requireContext(), month, income)
    }

    private fun displayExpenseData() {
        var formats = Formats()
        val currentMonth = formats.getCurrentMonth()
        val expenseData = DBOperations().readExpense(requireContext(), currentMonth)
        val formattedData = Formats().formatStringToArray(expenseData)
        displayPieChart(formattedData)
    }

    private fun displayIncomeData() {
        var formats = Formats()
        val currentMonth = formats.getCurrentMonth()
        val incomeData = DBOperations().readIncome(requireContext(), currentMonth)
        val formattedData = Formats().formatStringToArray(incomeData)
        displayPieChart(formattedData)
    }

    private fun displayPieChart(data: Map<String, Float>) {
        val entries = ArrayList<PieEntry>()
        for ((category, amount) in data) {
            entries.add(PieEntry(amount, category))
        }
        val dataSet = PieDataSet(entries, "Data")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()
    }

    private fun getFullMonthName(abbreviation: String): String {
        return when (abbreviation) {
            "Jan" -> "January"
            "Feb" -> "February"
            "Mar" -> "March"
            "Apr" -> "April"
            "May" -> "May"
            "Jun" -> "June"
            "Jul" -> "July"
            "Aug" -> "August"
            "Sep" -> "September"
            "Oct" -> "October"
            "Nov" -> "November"
            "Dec" -> "December"
            else -> "Invalid Month"
        }
    }


}
