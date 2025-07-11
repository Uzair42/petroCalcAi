package com.mu42.petrolefficiency

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.mu42.petrolefficiency.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val fuelHistory = mutableListOf<Pair<Double, Double>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("FuelPrefs", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadPreferences()
        binding.btnCalculate.setOnClickListener { calculateFuelAndDistance() }
        binding.btnSavePreferences.setOnClickListener { savePreferences() }
        binding.btnShowHistory.setOnClickListener { showHistoryChart() }
    }

    private fun calculateFuelAndDistance() {
        val pricePerLiter = binding.etPricePerLiter.text.toString().toDoubleOrNull()
        val mileage = binding.etMileage.text.toString().toDoubleOrNull()
        val amount = binding.etAmount.text.toString().toDoubleOrNull()

        if (pricePerLiter == null || mileage == null || amount == null) {
            Toast.makeText(requireContext(), "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            return
        }

        val liters = amount / pricePerLiter
        val kilometers = liters * mileage

        binding.tvResult.text = "⛽ Fuel: %.2f L\n🛣️ Distance: %.2f km".format(liters, kilometers)

        fuelHistory.add(Pair(liters, kilometers))
        saveHistory()
    }

    private fun savePreferences() {
        with(sharedPreferences.edit()) {
            putString("pricePerLiter", binding.etPricePerLiter.text.toString())
            putString("mileage", binding.etMileage.text.toString())
            putString("amount", binding.etAmount.text.toString())
            apply()
        }
        Toast.makeText(requireContext(), "Preferences saved!", Toast.LENGTH_SHORT).show()
    }

    private fun loadPreferences() {
        binding.etPricePerLiter.setText(sharedPreferences.getString("pricePerLiter", ""))
        binding.etMileage.setText(sharedPreferences.getString("mileage", ""))
        binding.etAmount.setText(sharedPreferences.getString("amount", ""))
        loadHistory()
    }

    private fun saveHistory() {
        val historyJson = Gson().toJson(fuelHistory)
        sharedPreferences.edit().putString("history", historyJson).apply()
    }

    private fun loadHistory() {
        val json = sharedPreferences.getString("history", null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Pair<Double, Double>>>() {}.type
            val list: MutableList<Pair<Double, Double>> = Gson().fromJson(json, type)
            fuelHistory.clear()
            fuelHistory.addAll(list)
        }
    }

    private fun showHistoryChart() {
        val chartFragment = ChartFragment.newInstance(fuelHistory)
        chartFragment.show(parentFragmentManager, "chart")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
