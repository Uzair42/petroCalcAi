package com.mu42.petrolefficiency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import  android.text.InputType

// FuelRequiredFragment.kt
class FuelRequiredFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        val etDistance = EditText(requireContext()).apply {
            hint = "Distance (km)"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        val etMileage = EditText(requireContext()).apply {
            hint = "Mileage (km/l)"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        val etPricePerLiter = EditText(requireContext()).apply {
            hint = "Price per Liter (Rs)"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        val btnCalculate = Button(requireContext()).apply {
            text = "Calculate Fuel & Cost"
        }

        val btnBack = Button(requireContext()).apply {
            text = "🔙 Back to Main"
        }

        val tvResult = TextView(requireContext()).apply {
            textSize = 18f
            setPadding(0, 16, 0, 0)
        }

        layout.addView(etDistance)
        layout.addView(etMileage)
        layout.addView(etPricePerLiter)
        layout.addView(btnCalculate)
        layout.addView(btnBack)
        layout.addView(tvResult)

        btnCalculate.setOnClickListener {
            val km = etDistance.text.toString().toDoubleOrNull()
            val mileage = etMileage.text.toString().toDoubleOrNull()
            val price = etPricePerLiter.text.toString().toDoubleOrNull()

            if (km == null || mileage == null || price == null || mileage == 0.0) {
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val liters = km / mileage
            val cost = liters * price

            tvResult.text = "⛽ Fuel Required: %.2f L\n💰 Estimated Cost: Rs %.2f".format(liters, cost)
        }

        btnBack.setOnClickListener {
            (activity as? MainActivity)?.switchToMainFragment()
        }

        return layout
    }
}
