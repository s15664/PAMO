package com.example.bmi2

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

import java.util.ArrayList
import java.util.Collections


class MainActivity : AppCompatActivity() {
    private var bmiResult: TextView? = null
    private var bmiClass: TextView? = null
    private var miffinResult: TextView? = null
    private var height: Double = 0.toDouble()
    private var height2: Double = 0.toDouble()
    private var heightView: TextView? = null
    private var heightView2: TextView? = null
    private var massView: TextView? = null
    private var massView2: TextView? = null
    private var mass: Double = 0.toDouble()
    private var mass2: Double = 0.toDouble()
    private var ageView: TextView? = null
    private var age: Int = 0
    private var male_button: RadioButton? = null
    private var result: Double = 0.toDouble()
    private var result_miffin: Double = 0.toDouble()
    private var bmiClassString: String? = null
    private var miffinGender: Int = 0
    private var foodImg: ImageView? = null
    private var question: TextView? = null
    private var result_counter: Int = 0
    private var question_counter: Int = 0
    private var answers: List<String>? = null
    private var result_quiz: TextView? = null


    private var answer_1: Button? = null
    private var answer_2: Button? = null
    private var answer_3: Button? = null
    private var answer_4: Button? = null
    private var restart: Button? = null

    private var home_layout: ConstraintLayout? = null
    private var bmi_layout: ConstraintLayout? = null
    private var miffin_layout: ConstraintLayout? = null
    private var chart_layout: ConstraintLayout? = null
    private var quiz_layout: ConstraintLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                home_layout!!.visibility = View.VISIBLE
                bmi_layout!!.visibility = View.INVISIBLE
                miffin_layout!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                quiz_layout!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bmi -> {
                home_layout!!.visibility = View.INVISIBLE
                bmi_layout!!.visibility = View.VISIBLE
                miffin_layout!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                quiz_layout!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_miffin -> {
                home_layout!!.visibility = View.INVISIBLE
                bmi_layout!!.visibility = View.INVISIBLE
                miffin_layout!!.visibility = View.VISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                quiz_layout!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_quiz -> {
                home_layout!!.visibility = View.INVISIBLE
                bmi_layout!!.visibility = View.INVISIBLE
                miffin_layout!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                quiz_layout!!.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chart -> {
                home_layout!!.visibility = View.INVISIBLE
                bmi_layout!!.visibility = View.INVISIBLE
                miffin_layout!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.VISIBLE
                quiz_layout!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private val buttonListenerBmi = object : View.OnClickListener {

        override fun onClick(v: View) {
            height = java.lang.Double.parseDouble(heightView!!.text.toString())
            mass = java.lang.Double.parseDouble(massView!!.text.toString())
            val result = calculate(mass, height)
            bmiResult!!.text = java.lang.Double.toString(result)
            bmiClass!!.text = bmiClassString
            foodImg!!.visibility = View.VISIBLE
            hideKeyboard()

        }
    }

    private val buttonListenerMiffin = object : View.OnClickListener {

        override fun onClick(v: View) {
            mass2 = java.lang.Double.parseDouble(massView2!!.text.toString())
            height2 = java.lang.Double.parseDouble(heightView2!!.text.toString())
            age = Integer.parseInt(ageView!!.text.toString())
            calculateMiffin(mass2, height2, age)
            miffinResult!!.text = java.lang.Double.toString(result_miffin)
            hideKeyboard()

        }
    }


    private val buttonRestart = object : View.OnClickListener {
        override fun onClick(v: View) {
            nextQuestion(1)
            result_counter = 0
            question_counter = 1
            restart!!.visibility = View.INVISIBLE
            result_quiz!!.visibility = View.INVISIBLE
            answer_1!!.visibility = View.VISIBLE
            answer_2!!.visibility = View.VISIBLE
            answer_3!!.visibility = View.VISIBLE
            answer_4!!.visibility = View.VISIBLE
            question!!.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        question_counter = 1
        result_counter = 0
        bmi_layout = findViewById(R.id.bmi_layout)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        home_layout = findViewById<View>(R.id.home_layout) as ConstraintLayout
        bmi_layout = findViewById<View>(R.id.bmi_layout) as ConstraintLayout
        miffin_layout = findViewById<View>(R.id.miffin_layout) as ConstraintLayout
        chart_layout = findViewById<View>(R.id.chart_layout) as ConstraintLayout
        quiz_layout = findViewById<View>(R.id.quiz_layout) as ConstraintLayout
        bmiResult = findViewById<View>(R.id.bmiresult) as TextView
        miffinResult = findViewById<View>(R.id.miffin_result) as TextView
        ageView = findViewById<View>(R.id.age) as TextView
        bmiClass = findViewById<View>(R.id.bmiclass) as TextView
        heightView = findViewById<View>(R.id.height) as TextView
        heightView2 = findViewById<View>(R.id.height_2) as TextView
        massView = findViewById<View>(R.id.mass) as TextView
        massView2 = findViewById<View>(R.id.mass2) as TextView
        male_button = findViewById<View>(R.id.radio_male) as RadioButton
        foodImg = findViewById<View>(R.id.imageView) as ImageView
        question = findViewById<View>(R.id.question) as TextView
        val buttonBmi = findViewById<Button>(R.id.calculate)
        val buttonMiffin = findViewById<Button>(R.id.miffin)
        answer_1 = findViewById(R.id.answer_1)
        answer_2 = findViewById(R.id.answer_2)
        answer_3 = findViewById(R.id.answer_3)
        answer_4 = findViewById(R.id.answer_4)
        answers = ArrayList()
        restart = findViewById(R.id.button_restart)
        result_quiz = findViewById(R.id.quiz_result)
        buttonBmi.setOnClickListener(buttonListenerBmi)
        buttonMiffin.setOnClickListener(buttonListenerMiffin)
        setOnClick(answer_1!!)
        setOnClick(answer_2!!)
        setOnClick(answer_3!!)
        setOnClick(answer_4!!)

        restart!!.setOnClickListener(buttonRestart)
        home_layout!!.visibility = View.VISIBLE
        bmi_layout!!.visibility = View.INVISIBLE
        miffin_layout!!.visibility = View.INVISIBLE
        chart_layout!!.visibility = View.INVISIBLE
        quiz_layout!!.visibility = View.INVISIBLE
        foodImg!!.visibility = View.INVISIBLE
        restart!!.visibility = View.INVISIBLE
        result_quiz!!.visibility = View.INVISIBLE
        val myWebView = findViewById<View>(R.id.webview) as WebView

        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true

        //Example based on the
        //https://developers-dot-devsite-v2-prod.appspot.com/chart/interactive/docs/gallery/piechart.html
        val htmlData = ("<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
                + "    <script type=\"text/javascript\">"
                + "      google.charts.load('current', {'packages':['corechart']});"
                + "      google.charts.setOnLoadCallback(drawChart);"

                + "      function drawChart() {"

                + "        var data = google.visualization.arrayToDataTable(["
                + "          ['Year', 'Weight(kgs)'],"
                + "          ['2005',  120],"
                + "          ['2006',  115],"
                + "          ['2007',  113],"
                + "          ['2008', 100],"
                + "          ['2009',  97]"
                + "        ]);"

                + "        var options = {"
                + "          title: 'Weight loss chart',"
                + "          curveType: 'function',"
                + "          legend: { position: 'bottom' }"
                + "        };"

                + "        var chart = new google.visualization.LineChart(document.getElementById('chart'));"

                + "        chart.draw(data, options);"
                + "      }"
                + "    </script>"
                + "  </head>"
                + "  <body>"
                + "    <div id=\"chart\" style=\"width: 500px; height: 500px;\"></div>"
                + "  </body>"
                + "</html>")
        myWebView.loadData(htmlData, "text/html", "UTF-8")
        nextQuestion(1)

    }


    private fun setOnClick(btn: Button) {
        btn.setOnClickListener {
            when (question_counter) {
                1 -> if (btn.text === resources.getString(R.string.answer_1_good)) {
                    result_counter++
                }
                2 -> if (btn.text === resources.getString(R.string.answer_2_good)) {
                    result_counter++
                }
                3 -> if (btn.text === resources.getString(R.string.answer_3_good)) {
                    result_counter++
                }
                4 -> if (btn.text === resources.getString(R.string.answer_4_good)) {
                    result_counter++
                }
                5 -> {
                    if (btn.text === resources.getString(R.string.answer_5_good)) {
                        result_counter++
                    }
                    result_quiz!!.text = resources.getString(R.string.result) + " " + result_counter + " " + resources.getString(R.string.result_2)
                    result_quiz!!.visibility = View.VISIBLE
                    restart!!.visibility = View.VISIBLE
                    answer_1!!.visibility = View.INVISIBLE
                    answer_2!!.visibility = View.INVISIBLE
                    answer_3!!.visibility = View.INVISIBLE
                    answer_4!!.visibility = View.INVISIBLE
                    question!!.visibility = View.INVISIBLE
                }
            }
            question_counter++
            if (question_counter <= 5) {
                nextQuestion(question_counter)
            }
        }
    }

    private fun calculate(mass: Double, height: Double): Double {
        result = mass / (height * height)
        if (result < 18.5) {
            bmiClassString = resources.getString(R.string.bmi_1)
            foodImg!!.setImageResource(R.drawable.steak)
        } else if (result >= 18.5 && result < 24.9) {
            bmiClassString = resources.getString(R.string.bmi_2)
            foodImg!!.setImageResource(R.drawable.rice)
        } else if (result >= 24.9 && result < 29.9) {
            bmiClassString = resources.getString(R.string.bmi_3)
            foodImg!!.setImageResource(R.drawable.saladch)
        } else if (result >= 30) {
            bmiClassString = resources.getString(R.string.bmi_4)
            foodImg!!.setImageResource(R.drawable.salad)
        }
        return result
    }

    private fun calculateMiffin(mass: Double, height: Double, age: Int): Double {

        if (male_button!!.isChecked) {
            miffinGender = 5
        } else
            miffinGender = -161
        result_miffin = mass * 10 + height * 100 * 6.25 - age * 5 + miffinGender
        return result_miffin
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun nextQuestion(nr: Int) {
        answers = getAnswers(nr)
        answer_1!!.text = answers!![0]
        answer_2!!.text = answers!![1]
        answer_3!!.text = answers!![2]
        answer_4!!.text = answers!![3]
        val questionString = getQuestion(nr)
        question!!.text = questionString
    }

    private fun getAnswers(nr: Int): List<String> {
        val answers = ArrayList<String>()
        when (nr) {
            1 -> {
                answers.add(resources.getString(R.string.answer_1_bad_1))
                answers.add(resources.getString(R.string.answer_1_bad_2))
                answers.add(resources.getString(R.string.answer_1_good))
                answers.add(resources.getString(R.string.answer_1_bad_3))
                answers.add(resources.getString(R.string.answer_2_bad_1))
                answers.add(resources.getString(R.string.answer_2_bad_2))
                answers.add(resources.getString(R.string.answer_2_bad_3))
                answers.add(resources.getString(R.string.answer_2_good))
                answers.add(resources.getString(R.string.answer_3_bad_1))
                answers.add(resources.getString(R.string.answer_3_good))
                answers.add(resources.getString(R.string.answer_3_bad_2))
                answers.add(resources.getString(R.string.answer_3_bad_3))
                answers.add(resources.getString(R.string.answer_4_good))
                answers.add(resources.getString(R.string.answer_4_bad_1))
                answers.add(resources.getString(R.string.answer_4_bad_2))
                answers.add(resources.getString(R.string.answer_4_bad_3))
                answers.add(resources.getString(R.string.answer_5_bad_1))
                answers.add(resources.getString(R.string.answer_5_bad_2))
                answers.add(resources.getString(R.string.answer_5_good))
                answers.add(resources.getString(R.string.answer_5_bad_3))
            }
            2 -> {
                answers.add(resources.getString(R.string.answer_2_bad_1))
                answers.add(resources.getString(R.string.answer_2_bad_2))
                answers.add(resources.getString(R.string.answer_2_bad_3))
                answers.add(resources.getString(R.string.answer_2_good))
                answers.add(resources.getString(R.string.answer_3_bad_1))
                answers.add(resources.getString(R.string.answer_3_good))
                answers.add(resources.getString(R.string.answer_3_bad_2))
                answers.add(resources.getString(R.string.answer_3_bad_3))
                answers.add(resources.getString(R.string.answer_4_good))
                answers.add(resources.getString(R.string.answer_4_bad_1))
                answers.add(resources.getString(R.string.answer_4_bad_2))
                answers.add(resources.getString(R.string.answer_4_bad_3))
                answers.add(resources.getString(R.string.answer_5_bad_1))
                answers.add(resources.getString(R.string.answer_5_bad_2))
                answers.add(resources.getString(R.string.answer_5_good))
                answers.add(resources.getString(R.string.answer_5_bad_3))
            }
            3 -> {
                answers.add(resources.getString(R.string.answer_3_bad_1))
                answers.add(resources.getString(R.string.answer_3_good))
                answers.add(resources.getString(R.string.answer_3_bad_2))
                answers.add(resources.getString(R.string.answer_3_bad_3))
                answers.add(resources.getString(R.string.answer_4_good))
                answers.add(resources.getString(R.string.answer_4_bad_1))
                answers.add(resources.getString(R.string.answer_4_bad_2))
                answers.add(resources.getString(R.string.answer_4_bad_3))
                answers.add(resources.getString(R.string.answer_5_bad_1))
                answers.add(resources.getString(R.string.answer_5_bad_2))
                answers.add(resources.getString(R.string.answer_5_good))
                answers.add(resources.getString(R.string.answer_5_bad_3))
            }
            4 -> {
                answers.add(resources.getString(R.string.answer_4_good))
                answers.add(resources.getString(R.string.answer_4_bad_1))
                answers.add(resources.getString(R.string.answer_4_bad_2))
                answers.add(resources.getString(R.string.answer_4_bad_3))
                answers.add(resources.getString(R.string.answer_5_bad_1))
                answers.add(resources.getString(R.string.answer_5_bad_2))
                answers.add(resources.getString(R.string.answer_5_good))
                answers.add(resources.getString(R.string.answer_5_bad_3))
            }
            5 -> {
                answers.add(resources.getString(R.string.answer_5_bad_1))
                answers.add(resources.getString(R.string.answer_5_bad_2))
                answers.add(resources.getString(R.string.answer_5_good))
                answers.add(resources.getString(R.string.answer_5_bad_3))
            }
        }
        return answers
    }

    private fun getQuestion(nr: Int): String {
        when (nr) {
            1 -> return resources.getString(R.string.question_1)
            2 -> return resources.getString(R.string.question_2)
            3 -> return resources.getString(R.string.question_3)
            4 -> return resources.getString(R.string.question_4)
            5 -> return resources.getString(R.string.question_5)
        }
        return ""
    }
}
