package com.mu42.petrolefficiency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mu42.petrolefficiency.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCalculate.setOnClickListener {
            calculateFuelAndDistance()
        }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
