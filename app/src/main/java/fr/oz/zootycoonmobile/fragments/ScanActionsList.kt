package fr.oz.zootycoonmobile.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R


class ScanActionsList(private val context: MainActivity, filtre: String) : Fragment() {
    private lateinit var qrscanner: CodeScannerView
    lateinit var codeScanner: CodeScanner
    lateinit var labelFiltre: TextView


    var destination = "tous"
    val filtrerPar = filtre
    val MY_CAMERA_PERMISSION_REQUEST = 1111


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan_actions_list, container, false)
        var affichage = "Veuillez scanner le QR Code de "
        if (filtrerPar == "zone") affichage = affichage + "la $filtrerPar." else affichage =
            affichage + "l'$filtrerPar."



        labelFiltre = view.findViewById(R.id.filtrerParLabel)
        labelFiltre.setText(affichage)
        qrscanner = view.findViewById(R.id.scannerView)
        codeScanner = CodeScanner(context, qrscanner)
        openScanner()
        return view
    }


    private fun openScanner() {

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            destination = it.text

            codeScanner.stopPreview()
            codeScanner.releaseResources()
            qrscanner.visibility = View.INVISIBLE
            loadFragment(ActionsListFragment(context, filtrerPar, destination))

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

    private fun loadFragment(fragment: Fragment) {
        val transaction = context.supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

}