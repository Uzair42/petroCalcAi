package com.mu42.petrolefficiency

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.DropBoxManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


// ChartFragment.kt
class ChartFragment : DialogFragment() {
    companion object {
        fun newInstance(history: List<Pair<Double, Double>>): ChartFragment {
            val fragment = ChartFragment()
            val bundle = Bundle()
            val convertedList = history.mapIndexed { index, pair ->
                ChartEntry(index.toFloat(), pair.second.toFloat())
            }
            val json = Gson().toJson(convertedList)
            bundle.putString("entries", json)
            fragment.arguments = bundle
            return fragment
        }
    }

    data class ChartEntry(val x: Float, val y: Float)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val chart = LineChart(context)
        val json = arguments?.getString("entries") ?: return Dialog(context)
        val type = object : TypeToken<List<ChartEntry>>() {}.type
        val chartEntries: List<ChartEntry> = Gson().fromJson(json, type)

        val entries = chartEntries.map { Entry(it.x, it.y) }
        val dataSet = LineDataSet(entries, "Distance (km)").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setDrawValues(true)
        }

        chart.data = LineData(dataSet)
        chart.description.text = "Fuel History"
        chart.invalidate()

        return AlertDialog.Builder(context)
            .setView(chart)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
    }
}
