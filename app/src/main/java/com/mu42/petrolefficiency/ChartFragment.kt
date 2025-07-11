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


class ChartFragment : DialogFragment() {

    companion object {
        fun newInstance(history: List<Pair<Double, Double>>): ChartFragment {
            val fragment = ChartFragment()
            val bundle = Bundle()
            val json = Gson().toJson(history)
            bundle.putString("history", json)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val chart = LineChart(context)
        val historyJson = arguments?.getString("history") ?: return Dialog(context)
        val type = object : TypeToken<List<Pair<Double, Double>>>() {}.type
        val history: List<Pair<Double, Double>> = Gson().fromJson(historyJson, type)

        val entries = history.mapIndexed { index, pair ->
            DropBoxManager.Entry(index.toFloat().toString(), pair.second.toFloat().toLong())
        }
        val dataSet = LineDataSet(entries as List<Entry?>?, "Distance (km)")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        chart.data = LineData(dataSet)
        chart.description.text = "Fuel History"
        chart.invalidate()

        val builder = AlertDialog.Builder(context)
        builder.setView(chart)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        return builder.create()
    }
}
