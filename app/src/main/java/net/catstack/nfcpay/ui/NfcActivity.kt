package net.catstack.nfcpay.ui

import android.content.Intent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.pro100svitlo.creditCardNfcReader.CardNfcAsyncTask
import com.pro100svitlo.creditCardNfcReader.utils.CardNfcUtils
import io.github.tapcard.android.NFCCardReader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_nfc.*
import net.catstack.nfcpay.R

class NfcActivity : AppCompatActivity(), CardNfcAsyncTask.CardNfcInterface {

    var mNfcAdapter: NfcAdapter? = null
    var mCardNfcUtils: CardNfcUtils? = null
    var mCardNfcAsyncTask: CardNfcAsyncTask? = null
    var mIntentFromCreate: Boolean = false
    var nfcCardReader: NFCCardReader? = null

    var needToDispose: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        sumValueText.text = intent.getIntExtra("sum", 0).toString() + " ₽"

        cancelButton.setOnClickListener {
            finish()
        }

        nfcPayImage.setOnTouchListener(object : View.OnTouchListener {
            val gestureDetector = GestureDetector(this@NfcActivity,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        returnCardNumber(4276160040462427L)
                        return super.onDoubleTap(e)
                    }
                })

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gestureDetector.onTouchEvent(event)
                return true
            }
        })

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter != null) {
            nfcCardReader = NFCCardReader(this)
            mCardNfcUtils = CardNfcUtils(this)
            mIntentFromCreate = false
            onNewIntent(intent)
        }
    }

    private fun returnCardNumber(cardNumber: Long) {
        val intent = Intent()
        intent.putExtra("isSuccessful", true)
        intent.putExtra("cardNumber", cardNumber)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        mIntentFromCreate = false;
        if (mNfcAdapter != null && !mNfcAdapter?.isEnabled!!) {
            //show some turn on nfc dialog here
        } else if (mNfcAdapter != null) {
            mCardNfcUtils?.enableDispatch()
            nfcCardReader?.enableDispatch()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mNfcAdapter != null) {
            mCardNfcUtils?.disableDispatch()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        needToDispose = nfcCardReader
            ?.readCardRx2(intent)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    try {
                        val cardNumber = it.cardNumber.replace(" ", "").toLongOrNull()
                        if (cardNumber == null) {
                            Toast.makeText(
                                this,
                                "Произошла ошибка. Попробуйте снова или используйте другую карту",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@subscribe
                        }

                        returnCardNumber(cardNumber)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                        Toast.makeText(
                            this,
                            "Произошла ошибка. Попробуйте снова или используйте другую карту",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) { throwable: Throwable? -> throwable?.printStackTrace() }
    }

    override fun startNfcReadCard() {
        println("Start NFC card reading...")
    }

    override fun cardIsReadyToRead() {
        mCardNfcAsyncTask?.let {
            val card = it.cardNumber
            val expiredDate = it.cardExpireDate
            val cardType = it.cardType

            println("READY {$card} {$expiredDate} {$cardType}")
        }
    }

    override fun doNotMoveCardSoFast() {

    }

    override fun unknownEmvCard() {
        println("Unknown card")
    }

    override fun cardWithLockedNfc() {
        println("Card NFC is locked!")
    }

    override fun finishNfcReadCard() {
        mCardNfcAsyncTask = null
        println("NFC card reading has been finished")
    }
}