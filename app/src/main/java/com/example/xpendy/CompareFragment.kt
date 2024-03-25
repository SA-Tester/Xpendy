package com.example.xpendy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.math.roundToInt


class CompareFragment : Fragment() {

    private lateinit var pieChart1: PieChart
    private lateinit var pieChart2: PieChart
    private lateinit var incomeTable: TableLayout
    private lateinit var expenseTable: TableLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compare, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adding Test Data
        addTestData("Jan", "[Sales: 10000, Rentals: 30000]", "[Food: 5000, Bills: 4500, Clothing: 7000]")
        addTestData("Feb", "[Salary: 50000, Sales: 4000]", "[Food: 5000, Bills: 4500, Fuel: 5000]")

        initDropdown(view)
        val dropdown: Spinner = view.findViewById(R.id.spinner1)
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view1: View, position: Int, id: Long) {
                val selectedMonth = dropdown.selectedItem.toString()
                initPieCharts(view, selectedMonth)
                initIncomeTable(view, selectedMonth)
                initExpenseTable(view, selectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun initDropdown(view: View){
        val dropdown: Spinner = view.findViewById(R.id.spinner1)
        val items = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter

        val selection = "Jan"
        val spinnerPosition: Int = adapter.getPosition(selection)
        dropdown.setSelection(spinnerPosition)
    }

    private fun initPieCharts(view: View, selectedMonth: String){
        // Creating an object from the class
        val dbCursor = DBOperations()
        val formats = Formats()

        val entries1 = ArrayList<PieEntry>()
        val entries2 = ArrayList<PieEntry>()

        pieChart1 = view.findViewById(R.id.incomePieChart)
        pieChart2 = view.findViewById(R.id.expensePieChart)

        val incomeData = dbCursor.readIncome(requireContext(), selectedMonth)
        val expenseData = dbCursor.readExpense(requireContext(), selectedMonth)

        val resultMap1 = formats.formatStringToArray(incomeData)
        val resultMap2 = formats.formatStringToArray(expenseData)

        for ((key, value) in resultMap1) {
            entries1.add(PieEntry(value, key))
        }

        for ((key, value) in resultMap2) {
            entries2.add(PieEntry(value, key))
        }

        val dataSet1 = PieDataSet(entries1, "Income Sources")
        val dataSet2 = PieDataSet(entries2, "Expenses")
        dataSet1.colors = ColorTemplate.COLORFUL_COLORS.toList()
        dataSet2.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val data1 = PieData(dataSet1)
        pieChart1.data = data1
        pieChart1.invalidate() // Refresh the income chart

        val data2 = PieData(dataSet2)
        pieChart2.data = data2
        pieChart2.invalidate() // Refresh the expense chart
    }

    private fun initIncomeTable(view: View, selectedMonth: String){
        val dbCursor = DBOperations()
        val formats = Formats()

        // Criteria, %, status
        incomeTable = view.findViewById(R.id.incomeTableLayout)
        formats.cleanTable(incomeTable)
        val data = formats.formatStringToArray(dbCursor.readIncome(requireContext(), selectedMonth))

        var total = 0f
        for (row in data) {
            for ((key, value) in listOf(row)) {
                total += value
            }
        }

        for (row in data) {
            val tableRow = TableRow(requireContext())

            for ((key, value) in listOf(row)) {
                val itemText = TextView(requireContext())
                itemText.width = 320
                itemText.text = key
                itemText.setPadding(20, 20, 20, 20)
                itemText.textAlignment = View.TEXT_ALIGNMENT_CENTER
                tableRow.addView(itemText)

                val amountText = TextView(requireContext())
                amountText.text = ((value/total) *100).roundToInt().toString() + "%"
                amountText.width = 320
                amountText.setPadding(20,20,20,20)
                amountText.textAlignment = View.TEXT_ALIGNMENT_CENTER
                tableRow.addView(amountText)
            }
            incomeTable.addView(tableRow)
        }
    }

    private fun initExpenseTable(view: View, selectedMonth: String){
        val dbCursor = DBOperations()
        val formats = Formats()

        // Criteria, %, status
        expenseTable = view.findViewById(R.id.expenseTableLayout)
        formats.cleanTable(expenseTable)

        val data = formats.formatStringToArray(dbCursor.readExpense(requireContext(), selectedMonth))

        var total = 0f
        for (row in data) {
            for ((key, value) in listOf(row)) {
                total += value
            }
        }

        for (row in data) {
            val tableRow = TableRow(requireContext())

            for ((key, value) in listOf(row)) {
                val itemText = TextView(requireContext())
                itemText.width = 320
                itemText.text = key
                itemText.setPadding(20, 20, 20, 20)
                itemText.textAlignment = View.TEXT_ALIGNMENT_CENTER
                tableRow.addView(itemText)

                val amountText = TextView(requireContext())
                amountText.text = ((value/total) *100).roundToInt().toString() + "%"
                amountText.width = 320
                amountText.setPadding(20,20,20,20)
                amountText.textAlignment = View.TEXT_ALIGNMENT_CENTER
                tableRow.addView(amountText)
            }
            expenseTable.addView(tableRow)
        }
    }

    private fun addTestData(month: String, income: String, expenses: String){
        val dbCursor = DBOperations()
        dbCursor.addExpense(requireContext(), month, expenses)
        dbCursor.addIncome(requireContext(), month, income )
    }
}