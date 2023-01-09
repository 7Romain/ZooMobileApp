package fr.oz.zootycoonmobile.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import java.util.*


class CreerActionFragment(private val context: MainActivity) : Fragment() {
    lateinit var btnDatePicker: Button
    lateinit var btnTimePicker: Button
    lateinit var txtDate: EditText
    lateinit var txtTime: EditText
    lateinit var mYear: Number
    lateinit var mMonth: Number
    lateinit var mDay: Number
    lateinit var mHour: Number
    lateinit var mMinute: Number


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fun miseEnPage(nombre: Int): String =
            if (nombre <= 9) "0" + nombre.toString() else nombre.toString()

        val supportFragmentManager = parentFragmentManager
        val view = inflater.inflate(R.layout.fragment_creer_action, container, false)
        btnDatePicker = view.findViewById(R.id.btn_date)
        btnTimePicker = view.findViewById(R.id.btn_time)
        txtDate = view.findViewById(R.id.in_date)
        txtTime = view.findViewById(R.id.in_time)

        btnDatePicker.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                context,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    txtDate.setText(
                        miseEnPage(dayOfMonth) + "/" + miseEnPage(monthOfYear + 1) + "/" + year
                    )
                }, mYear as Int, mMonth as Int, mDay as Int
            )
            datePickerDialog.show()

        }

        btnTimePicker.setOnClickListener {
            // Get Current Time
            // Get Current Time
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            // Launch Time Picker Dialog

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                context,
                OnTimeSetListener { view, hourOfDay, minute -> txtTime.setText("$hourOfDay h $minute") },
                mHour as Int,
                mMinute as Int,
                false
            )
            timePickerDialog.show()


        }

//        val actionsList = view?.findViewById<CardView>(R.id.actionsList)
//        val actionsCreate = view?.findViewById<CardView>(R.id.actionsCreate)
//        actionsList?.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//
//            transaction.replace(R.id.fragment_container, HomeFragment(context))
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
//        actionsCreate?.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//
//            transaction.replace(R.id.fragment_container, HomeFragment(context))
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
        return view


    }
}