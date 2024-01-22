package com.example.imccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * The MainActivity2 activity represents the result of the Body Mass Index (BMI) calculation.
 */
class MainActivity2 : AppCompatActivity() {

    private var result: Double = 0.0

    private lateinit var textImc: TextView
    private lateinit var textDefinition: TextView
    private lateinit var textDescription: TextView
    private lateinit var btnRecalculate: Button

    /**
     * Method called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Retrieve the BMI result from the intent's extras; set to -1.0 if not provided
        result = intent.extras?.getDouble("result") ?: -1.0

        // Initialize UI components and set up event listeners
        initComponents()
        showImcResult()
        showDescriptions(result)
        initListeners()
    }

    /**
     * Initializes the UI components.
     */
    private fun initComponents() {
        textImc = findViewById(R.id.textImc)
        textDefinition = findViewById(R.id.textDefinition)
        textDescription = findViewById(R.id.textDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)
    }

    /**
     * Initializes click listeners for the UI components.
     */
    private fun initListeners() {
        btnRecalculate.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * Displays the BMI result on the UI.
     */
    private fun showImcResult() {
        textImc.text = result.toString()
    }

    /**
     * Shows descriptions based on BMI range on the UI.
     */
    private fun showDescriptions(result: Double) {
        textImc.text = result.toString()
        when (result) {
            in 0.00..18.50 -> {
                textImc.setTextColor(ContextCompat.getColor(this, R.color.underweight))
                textDefinition.setTextColor(ContextCompat.getColor(this, R.color.underweight))
                textDefinition.text = getText(R.string.title_underweight)
                textDescription.text = getText(R.string.description_underweight)
            }
            in 18.51..24.99 -> {
                textImc.setTextColor(ContextCompat.getColor(this, R.color.normal_weight))
                textDefinition.setTextColor(ContextCompat.getColor(this, R.color.normal_weight))
                textDefinition.text = getText(R.string.title_normal_weight)
                textDescription.text = getText(R.string.description_normal_weight)
            }
            in 25.00..29.99 -> {
                textImc.setTextColor(ContextCompat.getColor(this, R.color.overweight))
                textDefinition.setTextColor(ContextCompat.getColor(this, R.color.overweight))
                textDefinition.text = getText(R.string.title_overweight)
                textDescription.text = getText(R.string.description_overweight)
            }
            in 30.00..70.00 -> {
                textImc.setTextColor(ContextCompat.getColor(this, R.color.obesity))
                textDefinition.setTextColor(ContextCompat.getColor(this, R.color.obesity))
                textDefinition.text = getText(R.string.title_obesity)
                textDescription.text = getText(R.string.description_obesity)
            }
            else -> {
                textImc.setTextColor(ContextCompat.getColor(this, R.color.obesity))
                textDefinition.setTextColor(ContextCompat.getColor(this, R.color.obesity))
                textDefinition.text = getText(R.string.error)
                textDescription.text = getText(R.string.error)
            }
        }
    }
}
