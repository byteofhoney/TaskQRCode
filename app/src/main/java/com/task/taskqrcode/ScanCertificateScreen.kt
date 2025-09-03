package com.task.taskqrcode


import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ScanCertificateScreen() {
    var resultText by remember { mutableStateOf("Scan a QR Code") }

    val launcher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val certId = result.contents
            FirebaseFirestore.getInstance().collection("certificates")
                .document(certId).get()
                .addOnSuccessListener { doc ->
                    resultText = if (doc.exists()) {
                        val name = doc.getString("studentName")
                        val course = doc.getString("course")
                        val date = doc.getString("date")
                        "Valid! \nName: $name\nCourse: $course\nDate: $date"
                    } else {
                        "Invalid!"
                    }
                }
                .addOnFailureListener { e -> resultText = "Error: ${e.message}" }
        }
    }

    Column(modifier = Modifier.padding(26.dp)) {
        // Text
        Text(resultText)
        Spacer(modifier = Modifier.height(20.dp))
        //button
        Button(onClick = {
            val options = ScanOptions().apply {
                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                setPrompt("Scan QR Code")
                setCameraId(0)
                setBeepEnabled(true)
                setOrientationLocked(true)
            }
            launcher.launch(options)
        }) {
            Text("Start Scan")
        }
    }}

