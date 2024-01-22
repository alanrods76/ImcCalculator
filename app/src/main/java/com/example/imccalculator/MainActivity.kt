package com.example.imccalculator

import android.content.Intent
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

/**
 * The main activity representing the IMC calculator.
 */
class MainActivity : AppCompatActivity() {

    // Colors to represent gender selection and non-selection
    private var isSelectedColor: Int = 0
    private var noSelectedColor: Int = 0
    private var currentHeight: Float = 1.00F
    private var currentWeight: Int = 30
    private var currentAge: Int = 10
    private var isBtnMotion = false

    // Gender views and other UI components
    private lateinit var handler: Handler
    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var textHeight: TextView
    private lateinit var rangeSlider: RangeSlider
    private lateinit var textWeight: TextView
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnAddWeight: FloatingActionButton
    private lateinit var textAge: TextView
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var btnCalculater: Button

    /**
     * Method called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize selected and non-selected colors
        isSelectedColor = ContextCompat.getColor(this, R.color.background_component_selected)
        noSelectedColor = ContextCompat.getColor(this, R.color.background_component)

        // Initialize UI components and set up event listeners
        initComponents()
        initListeners()
        handler = Handler()
    }

    /**
     * Initializes the gender views and other UI components.
     */
    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        textHeight = findViewById(R.id.textHeight)
        rangeSlider = findViewById(R.id.rangeSlider)
        textWeight = findViewById(R.id.textWeight)
        btnSubtractWeight = findViewById(R.id.btnSubstracWeight)
        btnAddWeight = findViewById(R.id.btnAddWeight)
        textAge = findViewById(R.id.textAge)
        btnSubtractAge = findViewById(R.id.btnSubstracAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        btnCalculater = findViewById(R.id.btnCalculater)
    }

    /**
     * Initializes click listeners for the gender views and other UI components.
     */
    private fun initListeners() {
        viewMale.setOnClickListener {
            setGenderColorMale()
        }
        viewFemale.setOnClickListener {
            setGenderColorFemale()
        }
        rangeSlider.addOnChangeListener { _, value, _ ->
            handleChangeRangeSlider(value)
        }
        btnSubtractWeight.setOnTouchListener { _, event ->
            handleTouch(btnSubtractWeight, event, ::subtractWeight)
            true
        }
        btnAddWeight.setOnTouchListener { _, event ->
            handleTouch(btnAddWeight, event, ::addWeight)
            true
        }
        btnSubtractAge.setOnTouchListener { _, event ->
            handleTouch(btnSubtractAge, event, ::subtractAge)
            true
        }
        btnAddAge.setOnTouchListener { _, event ->
            handleTouch(btnAddAge, event, ::addAge)
            true
        }
        btnCalculater.setOnClickListener{
            calculate()
            navigateResult()
        }
    }

    /**
     * Changes the background color to represent the selection of the male gender.
     */
    private fun setGenderColorMale() {
        viewMale.setCardBackgroundColor(isSelectedColor)
        viewFemale.setCardBackgroundColor(noSelectedColor)
    }

    /**
     * Changes the background color to represent the selection of the female gender.
     */
    private fun setGenderColorFemale() {
        viewMale.setCardBackgroundColor(noSelectedColor)
        viewFemale.setCardBackgroundColor(isSelectedColor)
    }

    /**
     * Handles the change event of the RangeSlider for height selection.
     */
    private fun handleChangeRangeSlider(value: Float) {
        currentHeight = value/100
        textHeight.text = "$currentHeight M"
    }

    /**
     * Subtracts weight and updates the corresponding TextView.
     */
    private fun subtractWeight() {
        if (currentWeight > 30) {
            currentWeight--
            textWeight.text = "$currentWeight KG"
        }
    }

    /**
     * Adds weight and updates the corresponding TextView.
     */
    private fun addWeight() {
        if (currentWeight < 200) {
            currentWeight++
            textWeight.text = "$currentWeight KG"
        }
    }

    /**
     * Subtracts age and updates the corresponding TextView.
     */
    private fun subtractAge() {
        if (currentAge > 10) {
            currentAge--
            textAge.text = "$currentAge AÑOS"
        }
    }

    /**
     * Adds age and updates the corresponding TextView.
     */
    private fun addAge() {
        if (currentAge < 100) {
            currentAge++
            textAge.text = "$currentAge AÑOS"
        }
    }

    private fun handleTouch(
        btnClick: FloatingActionButton,
        event: MotionEvent,
        funSelected: () -> Unit
    ) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // When the button is pressed
                isBtnMotion = true
                startRepeatingTask(funSelected)
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // When the button is released or the event is canceled
                isBtnMotion = false
                stopRepeatingTask()
            }
        }
    }

    /**
     * Starts a repeating task using a specified function.
     *
     * @param funSelected The function to be invoked repeatedly while the button motion is active.
     *                    This function should perform the desired action.
     */
    private fun startRepeatingTask(funSelected: () -> Unit) {
        handler.post(object : Runnable {
            override fun run() {
                if (isBtnMotion) {
                    funSelected.invoke()
                    handler.postDelayed(
                        this,
                        100
                    ) // Adjust the repetition time as needed
                }
            }
        })
    }

    /**
     * Stops the repeating task.
     */
    private fun stopRepeatingTask() {
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * Calculates the IMC and updates the corresponding TextView.
     */
    private  fun calculate(): Float {
        return (currentWeight / (currentHeight * currentHeight))
    }

    /**
     * Calculates the IMC and updates the corresponding TextView.
     */
    private fun navigateResult(){
        var imc = calculate()
        val df = DecimalFormat("#.##")
        println(df.format(imc).toDouble())
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("result", df.format(imc).toDouble())
        startActivity(intent)
    }
}
