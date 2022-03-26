package com.amir.sevenminutesworkout


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.amir.sevenminutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode


class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {

               metricVisibility()
            } else {

              usUnitVisibility()
            }
        }

        binding?.btnCalculateBMI?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun calculateMetricUnit() {

            //we want to do the things if validateMetricUnits is true
            if (validateMetricUnits()) {
                //dividing by 100, because we enter the height in cm
                val heightValue: Float =
                    binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIReult(bmi)

            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter the METRIC valid values.",
                    Toast.LENGTH_LONG
                ).show()
            }

    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            calculateMetricUnit()
        } else {
            calculateUsUnits()
        }
    }

    private fun calculateUsUnits() {

            if (validateUsUnits()) {
                val usUnitFeet = binding?.etUsUnitFeet?.text.toString()
                val usUnitInch = binding?.etUsUnitInch?.text.toString()
                val usUnitWeight = binding?.etUsUnitWeight?.text.toString().toFloat()

                val heightValue = usUnitInch.toFloat() + usUnitFeet.toFloat() * 12
                val bmi = 703 * (usUnitWeight / (heightValue * heightValue))
                displayBMIReult(bmi)

            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid US values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }

    //TODO METRICVISIBILITY
    private fun metricVisibility() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.llMetric?.visibility = View.VISIBLE
        binding?.llUsUnits?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE


    }

    //TODO USUNITVISIBILITY
    private fun usUnitVisibility() {
        currentVisibleView = US_UNITS_VIEW
        binding?.llUsUnits?.visibility = View.VISIBLE
        binding?.llMetric?.visibility = View.INVISIBLE

        binding?.etUsUnitFeet?.text!!.clear()
        binding?.etUsUnitInch?.text!!.clear()
        binding?.etUsUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE

    }

    //to check if the user enter sth or not
    //we have to check it everytime when the user click on the button
    //TODO IS METRIC VALID
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false

        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    //TODO IS VALID UNITS
    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsUnitInch?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsUnitFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }


    private fun displayBMIReult(bmi: Float) {
        val bmiLable: String
        val bmiDescription: String
        //compreTo get the value and compareTo
        //if BMI less or equal 15
        if (bmi.compareTo(15f) <= 0) {
            bmiLable = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLable = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLable = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLable = "Normal"
            bmiDescription = "Congratulations! You are in a good shape"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLable = "Overweight"
            bmiDescription = "Oops! You really need to take care of yourself! Workout!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLable = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of yourself! Workout!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLable = "Obese Class | (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLable = "Obese Class | (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLable
        binding?.tvBMIDescription?.text = bmiDescription
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}