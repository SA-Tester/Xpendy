package com.example.xpendy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class CompareFragment : Fragment() {

    private lateinit var pieChart: PieChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compare, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieChart = view.findViewById(R.id.comparisionPieChart)

        // Sample data for the pie chart
        val entries = listOf(
            PieEntry(18.5f, "Green"),
            PieEntry(26.7f, "Yellow"),
            PieEntry(24.0f, "Red"),
            PieEntry(30.8f, "Blue")
        )

        val dataSet = PieDataSet(entries, "Sample Data")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate() // Refresh the chart


        val dropdown: Spinner = view.findViewById(R.id.spinner1)
        val items = arrayOf("Jan", "Feb", "Mar")

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter
        dropdown.setSelection(0)

        val selection = "Jan"
        val spinnerPosition: Int = adapter.getPosition(selection)
        dropdown.setSelection(spinnerPosition)
    }
}