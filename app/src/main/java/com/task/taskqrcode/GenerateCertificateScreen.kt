package com.task.taskqrcode

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.BarcodeEncoder

@Composable
fun GenerateCertificateScreen() {
    var name by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var status by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Student Name") })
        OutlinedTextField(value = program, onValueChange = { program = it }, label = { Text("Program Name") })
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Issue Date") })
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            if (name.isNotBlank() && program.isNotBlank() && date.isNotBlank()) {
                val certId = "CERT_${System.currentTimeMillis()}"
                val certData = mapOf(
                    "studentName" to name,
                    "program" to program,
                    "date" to date,
                    "valid" to true
                )
                FirebaseFirestore.getInstance().collection("certificates")
                    .document(certId).set(certData)

                val qrText = certId
                val encoder = BarcodeEncoder()
                qrBitmap = encoder.encodeBitmap(qrText, com.google.zxing.BarcodeFormat.QR_CODE, 300, 300)
                status = "Certificate QR Saved!"
            } else {
                status = "Fill all fields"
            }
        }) {
            Text("Generate QR")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(status)
        qrBitmap?.let { Image(bitmap = it.asImageBitmap(), contentDescription = null) }
    }
}

