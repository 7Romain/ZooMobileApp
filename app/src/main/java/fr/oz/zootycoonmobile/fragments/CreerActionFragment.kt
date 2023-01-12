package fr.oz.zootycoonmobile.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import fr.oz.zootycoonmobile.ActionRepository
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import fr.oz.zootycoonmobile.viewModel.MainViewModel
import fr.oz.zootycoonmobile.viewModelFactory.MainViewModelFactory
import java.util.*


class CreerActionFragment(private val context: MainActivity) : Fragment() {
    private lateinit var viewModel: MainViewModel
    lateinit var btnDatePicker: Button
    lateinit var btnTimePicker: Button
    lateinit var btnValider: Button
    lateinit var txtDate: EditText
    lateinit var txtTime: EditText
    lateinit var enclos: EditText
    lateinit var animal: EditText
    lateinit var espece: EditText
    lateinit var observations: EditText
    lateinit var mYear: Number
    lateinit var mMonth: Number
    lateinit var mDay: Number
    lateinit var mHour: Number
    lateinit var mMinute: Number
    var jour: String = "*"
    var heure: String = "*"
    lateinit var qrscanner: CodeScannerView
    lateinit var codeScanner: CodeScanner
    lateinit var btnEnclos: Button
    lateinit var btnAnimal: Button
    lateinit var btnEspece: Button
    val MY_CAMERA_PERMISSION_REQUEST = 1111


    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_creer_action, container, false)
        btnDatePicker = view.findViewById(R.id.btn_date)
        btnTimePicker = view.findViewById(R.id.btn_time)
        txtDate = view.findViewById(R.id.in_date)
        txtTime = view.findViewById(R.id.in_time)
        btnValider = view.findViewById(R.id.btnValider)
        enclos = view.findViewById(R.id.editTextEnclos)
        animal = view.findViewById(R.id.editTextAnimal)
        espece = view.findViewById(R.id.editTextEspece)
        observations = view.findViewById(R.id.editTextObservations)
        qrscanner = view.findViewById(R.id.scannerView)
        btnEnclos = view.findViewById(R.id.enclosLabel)
        btnAnimal = view.findViewById(R.id.animalLabel)
        btnEspece = view.findViewById(R.id.especeLabel)

        codeScanner = CodeScanner(context, qrscanner)

        qrscanner.visibility = View.INVISIBLE

        btnEnclos.setOnClickListener {
            qrscanner.visibility = View.VISIBLE
            it.hideKeyboard()
            openScanner("enclos")
        }
        btnAnimal.setOnClickListener {
            qrscanner.visibility = View.VISIBLE
            it.hideKeyboard()
            openScanner("animal")
        }

        btnEspece.setOnClickListener {
            qrscanner.visibility = View.VISIBLE
            it.hideKeyboard()
            openScanner("espece")
        }

        txtTime.setOnClickListener {

            showTimePicker()
        }
        txtDate.setOnClickListener {

            showDatePicker()
        }
        btnDatePicker.setOnClickListener {
            showDatePicker()

        }

        btnTimePicker.setOnClickListener {
            showTimePicker()

        }

        btnValider.setOnClickListener {
            if (jour == "*" || heure == "*") {
                codeScanner.stopPreview()
                codeScanner.releaseResources()
                qrscanner.visibility = View.INVISIBLE
                val myToast = Toast.makeText(
                    context,
                    "Veuillez choisir une date et une heure s'il vous plait",
                    Toast.LENGTH_LONG
                )
                myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                myToast.show()
            } else if (observations.text.isBlank()) {
                codeScanner.stopPreview()
                codeScanner.releaseResources()
                qrscanner.visibility = View.INVISIBLE
                val myToast = Toast.makeText(
                    context,
                    "Veuillez indiquer une tache à exectuer",
                    Toast.LENGTH_LONG
                )
                myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                myToast.show()
            } else {
                var reponseTexte = ""
                val dateTime = "$jour $heure"
                println(dateTime)
                val enclosid: String = enclos.text.toString()
                val animalId: String = animal.text.toString()
                val especeId: String = espece.text.toString()
                val obs: String = observations.text.toString()


                val repository = ActionRepository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel =
                    ViewModelProvider(context, viewModelFactory).get(MainViewModel::class.java)
                viewModel.postAction(
                    8, enclosid, especeId,
                    animalId, dateTime, obs
                )
                viewModel.myPushResponse.observe(context, androidx.lifecycle.Observer { response ->
                    println(response)
                    lateinit var myToast: Toast
                    if (response.code() == 201) {
                        reponseTexte =
                            response.body()?.observations.toString() + " prévue pour le " + response.body()?.datePrevue.toString()
                        myToast =
                            Toast.makeText(
                                context,
                                "L'action a bien été ajouté : $reponseTexte ",
                                Toast.LENGTH_LONG
                            )
                    } else {
                        myToast =
                            Toast.makeText(
                                context,
                                "L'enregistrement de l'action a échoué ",
                                Toast.LENGTH_LONG
                            )
                    }
                    myToast.setGravity(Gravity.CENTER, 0, 0)
                    myToast.show()
                    codeScanner.stopPreview()
                    codeScanner.releaseResources()
                    qrscanner.visibility = View.INVISIBLE
                    context.supportFragmentManager.popBackStack()
                })
            }
        }
        return view
    }

    private fun showDatePicker() {
        fun miseEnPage(nombre: Int): String =
            if (nombre <= 9) "0" + nombre.toString() else nombre.toString()

        val c: Calendar = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            context,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                jour =
                    miseEnPage(dayOfMonth) + "/" + miseEnPage(monthOfYear + 1) + "/" + year
                txtDate.setText(
                    jour
                )
            }, mYear as Int, mMonth as Int, mDay as Int
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        fun miseEnPage(nombre: Int): String =
            if (nombre <= 9) "0" + nombre.toString() else nombre.toString()

        // Get Current Time
        // Get Current Time
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]

        // Launch Time Picker Dialog

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            context,
            OnTimeSetListener { view, hourOfDay, minute ->
                heure = miseEnPage(hourOfDay) + ":" + miseEnPage(
                    minute
                )
                txtTime.setText(
                    heure
                )
            },
            mHour as Int,
            mMinute as Int,
            true
        )
        timePickerDialog.show()
    }

    private fun openScanner(champ: String) {

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            val texte: String = it.text
            when (champ) {
                "enclos" -> enclos.setText(texte)
                "espece" -> espece.setText(texte)
                "animal" -> animal.setText(texte)
            }
            codeScanner.stopPreview()
            codeScanner.releaseResources()
            qrscanner.visibility = View.INVISIBLE

        }
        codeScanner.errorCallback = ErrorCallback {
            Toast.makeText(
                context,
                "Erreur lors de la tentative de scan",
                Toast.LENGTH_LONG
            ).show()
            codeScanner.stopPreview()
            codeScanner.releaseResources()
            qrscanner.visibility = View.INVISIBLE
        }

        checkPermission()
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.CAMERA),
                MY_CAMERA_PERMISSION_REQUEST
            )
        } else {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_CAMERA_PERMISSION_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            codeScanner.startPreview()
        } else {
            Toast.makeText(
                context,
                "Impossible de scanner sans l'autorisation d'utiliser la caméra",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}