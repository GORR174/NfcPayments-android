package net.catstack.nfcpay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nfc.*

class NfcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        sumValueText.text = intent.getIntExtra("sum", 0).toString() + " ₽"

        cancelButton.setOnClickListener {
            finish()
        }

        nfcPayImage.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isSuccessful", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}