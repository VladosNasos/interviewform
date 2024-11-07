package com.example.interviewform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var editFullName: EditText
    private lateinit var editAge: EditText
    private lateinit var seekBarSalary: SeekBar
    private lateinit var textSalaryLabel: TextView

    private lateinit var radioGroupQuestion1: RadioGroup
    private lateinit var radioGroupQuestion2: RadioGroup
    private lateinit var radioGroupQuestion3: RadioGroup
    private lateinit var radioGroupQuestion4: RadioGroup
    private lateinit var radioGroupQuestion5: RadioGroup

    private lateinit var checkboxExperience: CheckBox
    private lateinit var checkboxTeamwork: CheckBox
    private lateinit var checkboxTravel: CheckBox

    private lateinit var buttonSubmit: Button
    private lateinit var textResult: TextView

    private var salaryMin = 1000     // Минимальная зарплата в USD
    private var salaryMax = 3000     // Максимальная зарплата в USD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация виджетов
        editFullName = findViewById(R.id.edit_full_name)
        editAge = findViewById(R.id.edit_age)
        seekBarSalary = findViewById(R.id.seekbar_salary)
        textSalaryLabel = findViewById(R.id.text_salary_label)

        radioGroupQuestion1 = findViewById(R.id.radio_group_question1)
        radioGroupQuestion2 = findViewById(R.id.radio_group_question2)
        radioGroupQuestion3 = findViewById(R.id.radio_group_question3)
        radioGroupQuestion4 = findViewById(R.id.radio_group_question4)
        radioGroupQuestion5 = findViewById(R.id.radio_group_question5)

        checkboxExperience = findViewById(R.id.checkbox_experience)
        checkboxTeamwork = findViewById(R.id.checkbox_teamwork)
        checkboxTravel = findViewById(R.id.checkbox_travel)

        buttonSubmit = findViewById(R.id.button_submit)
        textResult = findViewById(R.id.text_result)

        // Настройка SeekBar
        seekBarSalary.max = salaryMax - salaryMin
        seekBarSalary.progress = 0
        updateSalaryLabel()

        seekBarSalary.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateSalaryLabel()
                validateInput()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Не используется
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Не используется
            }
        })

        // Добавляем TextWatcher для EditText
        editFullName.addTextChangedListener(inputWatcher)
        editAge.addTextChangedListener(inputWatcher)

        // Обработчик кнопки "Здати тест"
        buttonSubmit.setOnClickListener {
            val result = calculateResult()
            textResult.visibility = View.VISIBLE
            textResult.text = result
        }
    }

    private fun updateSalaryLabel() {
        val salary = salaryMin + seekBarSalary.progress
        textSalaryLabel.text = "Бажана зарплатня: $salary USD"
    }

    // Функция для проверки валидности введенных данных
    private fun validateInput() {
        val fullName = editFullName.text.toString().trim()
        val ageStr = editAge.text.toString().trim()

        buttonSubmit.isEnabled = fullName.isNotEmpty() && ageStr.isNotEmpty()
    }

    private val inputWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            validateInput()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Не используется
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Не используется
        }
    }

    // Функция для подсчета результатов и генерации ответа
    private fun calculateResult(): String {
        val ageStr = editAge.text.toString()
        val age = if (ageStr.isNotEmpty()) ageStr.toInt() else 0
        val salary = salaryMin + seekBarSalary.progress

        val errors = mutableListOf<String>()

        // Проверка возраста
        if (age < 21 || age > 40) {
            errors.add("Вік не відповідає вимогам компанії (від 21 до 40 років).")
        }

        // Проверка зарплаты
        if (salary < salaryMin || salary > salaryMax) {
            errors.add("Заробітна платня не відповідає пропозиції компанії ($salaryMin - $salaryMax USD).")
        }

        // Если есть ошибки в данных
        if (errors.isNotEmpty()) {
            return errors.joinToString("\n")
        }

        var totalPoints = 0

        // Проверка ответов на вопросы

        // Вопрос 1
        val selectedOptionQ1 = radioGroupQuestion1.checkedRadioButtonId
        if (selectedOptionQ1 != -1) {
            val radioButtonQ1 = findViewById<RadioButton>(selectedOptionQ1)
            if (radioButtonQ1.text == getString(R.string.q1_option3)) {
                totalPoints += 2
            }
        }

        // Вопрос 2
        val selectedOptionQ2 = radioGroupQuestion2.checkedRadioButtonId
        if (selectedOptionQ2 != -1) {
            val radioButtonQ2 = findViewById<RadioButton>(selectedOptionQ2)
            if (radioButtonQ2.text == getString(R.string.q2_option1)) {
                totalPoints += 2
            }
        }

        // Вопрос 3
        val selectedOptionQ3 = radioGroupQuestion3.checkedRadioButtonId
        if (selectedOptionQ3 != -1) {
            val radioButtonQ3 = findViewById<RadioButton>(selectedOptionQ3)
            if (radioButtonQ3.text == getString(R.string.q3_option2)) {
                totalPoints += 2
            }
        }

        // Вопрос 4
        val selectedOptionQ4 = radioGroupQuestion4.checkedRadioButtonId
        if (selectedOptionQ4 != -1) {
            val radioButtonQ4 = findViewById<RadioButton>(selectedOptionQ4)
            if (radioButtonQ4.text == getString(R.string.q4_option1)) {
                totalPoints += 2
            }
        }

        // Вопрос 5
        val selectedOptionQ5 = radioGroupQuestion5.checkedRadioButtonId
        if (selectedOptionQ5 != -1) {
            val radioButtonQ5 = findViewById<RadioButton>(selectedOptionQ5)
            if (radioButtonQ5.text == getString(R.string.q5_option1)) {
                totalPoints += 2
            }
        }

        // Дополнительные навыки
        if (checkboxExperience.isChecked) {
            totalPoints += 2
        }
        if (checkboxTeamwork.isChecked) {
            totalPoints += 1
        }
        if (checkboxTravel.isChecked) {
            totalPoints += 1
        }

        // Проверка набранных баллов
        return if (totalPoints >= 10) {
            getString(R.string.result_positive)
        } else {
            getString(R.string.result_negative)
        }
    }
}