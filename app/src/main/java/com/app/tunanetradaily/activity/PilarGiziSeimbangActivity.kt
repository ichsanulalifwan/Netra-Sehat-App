package com.app.tunanetradaily.activity

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.tunanetradaily.MainActivity
import com.app.tunanetradaily.R
import com.app.tunanetradaily.databinding.ActivityPilarGiziSeimbangBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class PilarGiziSeimbangActivity : AppCompatActivity(), CoroutineScope, RecognitionListener {

    private lateinit var binding: ActivityPilarGiziSeimbangBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private var textToSpeechEngine: TextToSpeech? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilarGiziSeimbangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init speechRecognizer
        setSpeech()

        binding.cvAnekaRagam.setOnClickListener {
            textToSpeechEngine?.stop()
            stopListening()
            val giziSeimbangMenu = Intent(this, AnekaRagamMakananActivity::class.java)
            startActivity(giziSeimbangMenu)
        }
        binding.cvPhbs.setOnClickListener {
            textToSpeechEngine?.stop()
            stopListening()
            val covidMenu = Intent(this, PhbsActivity::class.java)
            startActivity(covidMenu)
        }
        binding.cvAktivitasFisik.setOnClickListener {
            textToSpeechEngine?.stop()
            stopListening()
            val covidMenu = Intent(this, AktivitasFisikActivity::class.java)
            startActivity(covidMenu)
        }
        binding.cvPantauBb.setOnClickListener {
            textToSpeechEngine?.stop()
            stopListening()
            val covidMenu = Intent(this, BeratBadanActivity::class.java)
            startActivity(covidMenu)
        }
    }

    override fun onStart() {
        super.onStart()
        // Init TTS
        textToSpeechEngine = TextToSpeech(this) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                // start speech litkes option
                textToSpeech()
            }
        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val pilarGizi = getString(R.string.menu_pilarGiziSeimbang)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(pilarGizi, TextToSpeech.QUEUE_FLUSH, null, "tts")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i("TextToSpeech", "On Start")
                startListening()
            }

            // Get Input speech after TTS Done
            override fun onDone(utteranceId: String?) {
                Log.i("TextToSpeech", "On Done")
            }

            override fun onError(utteranceId: String?) {
                Log.i(TAG, "TTS On Error")
            }
        })
    }

    private fun setSpeech() {

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)

        // Get the Intent action
        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        // Language model defines the purpose, there are special models for other use cases, like search.
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Adding an extra language, you can use any language from the Locale class.
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
    }

    private fun startOver() {
        stopListening()
        // start again
        startListening()
    }

    private fun startListening() {
        launch(Dispatchers.Main.immediate) {
            speechRecognizer.startListening(sttIntent)
        }
    }

    private fun stopListening() {
        speechRecognizer.stopListening()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        Log.i(TAG, "onReadyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech")
        val text = "Mendengarkan . . ."
        binding.tvSpeak.text = text
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i(TAG, "onRmsChanged: $rmsdB")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.i(TAG, "onBufferReceived: $buffer")
    }

    override fun onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech")
    }

    override fun onError(errorCode: Int) {
        val errorMessage: String = getErrorText(errorCode)
        Log.d(TAG, "FAILED $errorMessage")
        binding.tvSpeak.text = errorMessage
        startOver()
    }

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        binding.tvSpeak.text = recognizedText
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check2 = recognizedText.equals("dua", true) || recognizedText == "2"
        val check3 = recognizedText.equals("tiga", true) || recognizedText == "3"
        val check4 = recognizedText.equals("empat", true) || recognizedText == "4"
        val check7 = recognizedText.equals("tujuh", true) || recognizedText == "7"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        when {
            check1 -> {
                val anekaMenu = Intent(this, AnekaRagamMakananActivity::class.java)
                startActivity(anekaMenu)
            }
            check2 -> {
                val phbsMenu = Intent(this, PhbsActivity::class.java)
                startActivity(phbsMenu)
            }
            check3 -> {
                val aktivitasMenu = Intent(this, AktivitasFisikActivity::class.java)
                startActivity(aktivitasMenu)
            }
            check4 -> {
                val beratMenu = Intent(this, BeratBadanActivity::class.java)
                startActivity(beratMenu)
            }
            check7 -> {
                finish()
            }
            check9 -> {
                val backMainMenu = Intent(this, MainActivity::class.java)
                startActivity(backMainMenu)
                finishAffinity()
            }
            check0 -> {
                finishAffinity()
                exitProcess(0)
            }
            else -> {
                startOver()
            }
        }
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i(TAG, "onEvent")
    }

    private fun getErrorText(errorCode: Int): String {
        val message: String = when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "Tidak Cocok"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Tidak ada masukan"
            else -> "Didn't understand, please try again."
        }
        return message
    }

    override fun onPause() {
        super.onPause()
        textToSpeechEngine?.stop()
        stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeechEngine?.shutdown()
        speechRecognizer.destroy()
    }

    companion object {
        private const val TAG = "PilarGiziActivity"
    }
}