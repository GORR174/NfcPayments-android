package net.catstack.nfcpay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nfc.*
import net.catstack.nfcpay.R

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
            intent.putExtra("cardNumber", 4276160040462427L)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}