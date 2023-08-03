package com.example.tiptime


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.calculateButton.setOnClickListener { calculateTip() }
  }

  private fun calculateTip() {
    val cost = binding.costOfService.text.toString().toDoubleOrNull()
    if (cost == null) {
      displayTip(0.0)
      return
    }

    val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
      R.id.tip_awesome -> 0.20
      R.id.tip_good -> 0.18
      else -> 0.15
    }

    var tip = tipPercentage * cost
    if (binding.roundUpSwitch.isChecked) {
      tip = kotlin.math.ceil(tip)
    }

    displayTip(tip)
  }

  private fun displayTip(tip : Double) {
    val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
    binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
  }

}
