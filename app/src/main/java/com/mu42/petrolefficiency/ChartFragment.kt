package com.mu42.petrolefficiency

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.DropBoxManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


// ChartFragment.kt (converted to full-screen centered Fragment with expanded chart)
class ChartFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootLayout = FrameLayout(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#80000000")) // semi-transparent background
            setPadding(24, 24, 24, 24)
        }

        val chartCard = CardView(requireContext()).apply {
            radius = 32f
            setCardBackgroundColor(Color.WHITE)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
                marginStart = 32
                marginEnd = 32
            }
        }

        val chart = LineChart(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                600 // increased height for better visibility
            )
        }

        val json = arguments?.getString("entries") ?: return rootLayout
        val type = object : TypeToken<List<ChartEntry>>() {}.type
        val chartEntries: List<ChartEntry> = Gson().fromJson(json, type)

        val entries = chartEntries.map { Entry(it.x, it.y) }
        val dataSet = LineDataSet(entries, "Distance (km)").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
            setDrawFilled(true)
            fillColor = Color.parseColor("#A3D8F4")
        }

        chart.data = LineData(dataSet)
        chart.description.text = "Fuel History"
        chart.setPadding(32, 32, 32, 32)
        chart.invalidate()

        val closeBtn = Button(requireContext()).apply {
            text = "Close"
            setPadding(0, 32, 0, 0)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        val linear = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            addView(chart)
            addView(closeBtn)
        }

        chartCard.addView(linear)
        rootLayout.addView(chartCard)
        return rootLayout
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}
