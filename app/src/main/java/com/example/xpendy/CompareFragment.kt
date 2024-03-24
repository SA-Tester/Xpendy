package com.example.xpendy

import android.annotation.SuppressLint
import android.content.Context
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class CompareFragment : Fragment() {

    private lateinit var pieChart1: PieChart
    private lateinit var pieChart2: PieChart
    private lateinit var incomeTable: TableLayout
    private lateinit var expenseTable: TableLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compare, container, false)
    }

    override fun onViewCreated(view1: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view1, savedInstanceState)

        initDropdown(view1)
        val dropdown: Spinner = view1.findViewById(R.id.spinner1)
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedMonth = dropdown.selectedItem.toString()
                initPieCharts(view1, selectedMonth)
                initIncomeTable(view1, selectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
//        addTestDataIncome(view)
//        addTestDataExpense(view)
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
        val entries1 = ArrayList<PieEntry>()
        val entries2 = ArrayList<PieEntry>()

        pieChart1 = view.findViewById(R.id.incomePieChart)
        pieChart2 = view.findViewById(R.id.expensePieChart)

        val incomeData = readIncome(requireContext(), selectedMonth)
        val expenseData = readExpense(requireContext(), selectedMonth)

        val resultMap1 = formatStringToArray(incomeData)
        val resultMap2 = formatStringToArray(expenseData)

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
        // Criteria, %, status
        incomeTable = view.findViewById(R.id.incomeTableLayout)
        val data = listOf(formatStringToArray(readIncome(requireContext(), selectedMonth)))

        for (row in data) {
            val tableRow = TableRow(requireContext())

            for (item in row) {
                val textView = TextView(requireContext())
                textView.text = item.toString()
                textView.setPadding(10, 10, 10, 10)
                tableRow.addView(textView)
            }
            incomeTable.addView(tableRow)
        }
    }

    private fun initExpenseTable(){}

    private fun formatStringToArray(input: String): Map<String, Float> {
        val regex = Regex("""(\w+)\s*:\s*(\d+)""")
        val matchResults = regex.findAll(input)
        val resultMap = mutableMapOf<String, Float>()
        for (matchResult in matchResults) {
            val (key, value) = matchResult.destructured
            resultMap[key] = value.toFloat()
        }
        return resultMap
    }

    @SuppressLint("Range")
    private fun readIncome(context: Context, month: String): String{
        val dbHandler = DBHandler(context, null)
        val incomeCursor = dbHandler.readIncome(month)
        var income = ""

        if (incomeCursor != null) {
            while (incomeCursor.moveToNext()) {
                income = incomeCursor.getString(incomeCursor.getColumnIndex(DBHandler.INCOME_COL))
            }
        }
        return income
        incomeCursor?.close()
    }

    @SuppressLint("Range")
    private fun readExpense(context: Context, month: String): String{
        val dbHandler = DBHandler(context, null)
        val expenseCursor = dbHandler.readExpense(month)
        var expense = ""

        if (expenseCursor != null) {
            while (expenseCursor.moveToNext()) {
                expense = expenseCursor.getString(expenseCursor.getColumnIndex(DBHandler.EXPENSE_COL))
            }
        }
        return expense
        expenseCursor?.close()
    }


    // TEST DATA ===================================================================================
    private fun addTestDataIncome(view: View){
        val db = DBHandler(requireContext(), null)

        val dropdown: Spinner = view.findViewById(R.id.spinner1)

        val month = dropdown.selectedItem.toString()
        val income = "[Salary: 50000]"

        db.addIncome(month, income)
    }

    private fun addTestDataExpense(view: View){
        val db = DBHandler(requireContext(), null)

        val dropdown: Spinner = view.findViewById(R.id.spinner1)

        val month = dropdown.selectedItem.toString()
        val expense = "[Food: 25000, Bills: 8000, Fuel: 7000]"

        db.addExpense(month, expense)
    }

    //==============================================================================================
}