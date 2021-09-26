package com.app.tunanetradaily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import com.app.tunanetradaily.databinding.ActivityGiziSeimbangBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class GiziSeimbangActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private lateinit var binding: ActivityGiziSeimbangBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private var textToSpeechEngine: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGiziSeimbangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        // Get the Intent action
        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        setSpeech()

        textToSpeechEngine = TextToSpeech(this) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                // start speech litkes option
                litkesTTS()
            }
        }
    }

    private fun litkesTTS() {
        // Get the text from local string resource
        val textLitkes = getString(R.string.menu_giziSeimbang)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(textLitkes, TextToSpeech.QUEUE_FLUSH, null, "tts")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i("TextToSpeech", "On Start")
            }

            // Get Input speech after TTS Done
            override fun onDone(utteranceId: String?) {
                Log.i("TextToSpeech", "On Done")
                launch(Dispatchers.Main.immediate) {
                    speechRecognizer.startListening(sttIntent)
                }
            }

            override fun onError(utteranceId: String?) {
                Log.i(TAG, "TTS On Error")
            }
        })
    }

    private fun setSpeech() {

        // Language model defines the purpose, there are special models for other use cases, like search.
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Adding an extra language, you can use any language from the Locale class.
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
        // Text that shows up on the Speech input prompt.
        sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bicara Sekarang!")

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Log.i(TAG, "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.i(TAG, "onBeginningOfSpeech")
                /*val text = "Mendengarkan . . ."
                binding.tvResult.text = text*/
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

                val message = "Silahkan katakan sekali lagi"
                textToSpeechEngine?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "tts2")
            }

            override fun onResults(results: Bundle?) {
                Log.i(TAG, "onResults")
                val matches =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                val recognizedText = matches?.get(0)

                speechRecognizer.stopListening()

                if (recognizedText.equals("tujuh", true) || recognizedText == "7") {
                    val backPreviousMenu = Intent(this@GiziSeimbangActivity, LitkesActivity::class.java)
                    startActivity(backPreviousMenu)
                    finish()
                    speechRecognizer.stopListening()
                } else if (recognizedText.equals(
                        "sembilan",
                        true
                    ) || recognizedText == "9"
                ) {
                    val backMainMenu = Intent(this@GiziSeimbangActivity, MainActivity::class.java)
                    startActivity(backMainMenu)
                    finish()
                    speechRecognizer.stopListening()
                } else if (recognizedText.equals("nol", true) || recognizedText == "0") {
                    finishAffinity()
                    speechRecognizer.stopListening()
                    exitProcess(0)
                } else {
                    val messageNoMatch = "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
                    textToSpeechEngine?.speak(messageNoMatch, TextToSpeech.QUEUE_FLUSH, null, "tts2")
                    Toast.makeText(applicationContext, "Pilihan Salah", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onPartialResults(p0: Bundle?) {
                Log.i(TAG, "onPartialResults")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                Log.i(TAG, "onEvent")
            }
        })
    }

    fun getErrorText(errorCode: Int): String {
        val message: String = when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
        return message
    }

    override fun onPause() {
        textToSpeechEngine?.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine?.shutdown()
        speechRecognizer.destroy()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "GiziSeimbangActivity"
    }
}