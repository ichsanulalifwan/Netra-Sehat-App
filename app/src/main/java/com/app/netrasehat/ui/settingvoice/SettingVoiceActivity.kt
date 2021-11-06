package com.app.netrasehat.ui.settingvoice

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.app.netrasehat.MainActivity
import com.app.netrasehat.R
import com.app.netrasehat.databinding.ActivitySettingVoiceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class SettingVoiceActivity : AppCompatActivity(), CoroutineScope, RecognitionListener {

    private lateinit var binding: ActivitySettingVoiceBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private var textToSpeechEngine: TextToSpeech? = null
    private var speedRate = 1.0f
    private val increment = 0.2f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = ActivitySettingVoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            btnPlus.setOnClickListener {
                increaseSpeech()
            }

            btnMinus.setOnClickListener {
                decreaseSpeech()
            }

            btnNext.setOnClickListener {
                startActivity()
            }
        }

        setSpeech()
    }

    override fun onStart() {
        super.onStart()

        // Init TTS
        textToSpeechEngine = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                textToSpeechEngine?.setSpeechRate(speedRate)

                val result: Int? = textToSpeechEngine?.setLanguage(Locale("id", "ID"))
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    val changeEngine = Intent()
                    changeEngine.action = "com.android.settings.TTS_SETTINGS"
                    changeEngine.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(changeEngine)
                    Log.e("error", "This Language is not supported")
                } else {
                    // start speech
                    textToSpeech()
                }

//                if (!context?.let { isPackageInstalled(it.packageManager, googleTtsPackage) }!!) {
//                    val installVoice = Intent(ACTION_INSTALL_TTS_DATA)
//                    startActivity(installVoice)
//                } else textToSpeechEngine?.setEngineByPackageName(googleTtsPackage)
            }
        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val speechTts = getString(R.string.speech_speed_message)
        val optionTts = getString(R.string.opsi_speed)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(speechTts, TextToSpeech.QUEUE_FLUSH, null, "speechTts")
        textToSpeechEngine?.speak(optionTts, TextToSpeech.QUEUE_ADD, null, "optionTts")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
            }

            override fun onDone(utteranceId: String?) {
                val ttsLoop = utteranceId.equals("optionTts") ||
                        utteranceId.equals("wrongTts")
                if (ttsLoop) {
                    startListening()
                }
            }

            override fun onError(utteranceId: String?) {
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
        // Adding an extra package for fix bug in different phone and API level
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        //sttIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        //sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1)
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
        startOver()
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check2 = recognizedText.equals("dua", true) || recognizedText == "2"
        val check3 = recognizedText.equals("tiga", true) || recognizedText == "3"

        when {
            check1 -> increaseSpeech()
            check2 -> decreaseSpeech()
            check3 -> startActivity()
            else -> {
                val messageNoMatch =
                    "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
                textToSpeechEngine?.speak(
                    messageNoMatch,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "wrongTts"
                )
                Toast.makeText(this, "Pilihan tidak ada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun increaseSpeech() {
        speedRate += increment
        textToSpeechEngine?.setSpeechRate(speedRate)

        val result = speechSpeed(speedRate)

        val speechSpeed = "Ini adalah contoh laju suara $result%"
        textToSpeechEngine?.speak(speechSpeed, TextToSpeech.QUEUE_FLUSH, null, "speechRate")

        val optionTts = getString(R.string.opsi_speed)
        textToSpeechEngine?.speak(optionTts, TextToSpeech.QUEUE_ADD, null, "optionTts")

        Toast.makeText(this, speechSpeed, Toast.LENGTH_SHORT).show()
    }

    private fun decreaseSpeech() {
        speedRate -= increment
        textToSpeechEngine?.setSpeechRate(speedRate)

        val result = speechSpeed(speedRate)

        val speechSpeed = "Ini adalah contoh laju suara $result%"
        textToSpeechEngine?.speak(speechSpeed, TextToSpeech.QUEUE_FLUSH, null, "speechRate")

        val optionTts = getString(R.string.opsi_speed)
        textToSpeechEngine?.speak(optionTts, TextToSpeech.QUEUE_ADD, null, "optionTts")

        Toast.makeText(this, speechSpeed, Toast.LENGTH_SHORT).show()
    }

    private fun speechSpeed(currentSpeed: Float): String {
        val speed = (currentSpeed - 1.0) * 100
        return String.format("%.0f", speed)
    }

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i(TAG, "onEvent")
    }

    private fun startActivity() {
        val main = Intent(this@SettingVoiceActivity, MainActivity::class.java)
        startActivity(main)
        finish()
    }

    private fun startOver() {
        // Destroy and rebuild again
        stopListening()
        speechRecognizer.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)

        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)

        // start again
        startListening()
    }

    private fun getErrorText(errorCode: Int): String {
        val message: String = when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Kesalahan perekaman audio, silakan coba lagi."
            SpeechRecognizer.ERROR_CLIENT -> "Kesalahan sisi klien, silakan coba lagi."
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "izin tidak mencukupi, silakan coba lagi."
            SpeechRecognizer.ERROR_NETWORK -> "Kesalahan jaringan, silakan coba lagi."
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "batas waktu jaringan, silakan coba lagi."
            SpeechRecognizer.ERROR_NO_MATCH -> "Tidak ada kecocokan, silakan coba lagi."
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Servis Pengenalan suara sibuk, silakan coba lagi."
            SpeechRecognizer.ERROR_SERVER -> "kesalahan dari server, silakan coba lagi."
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Tidak ada masukan ucapan, silakan coba lagi."
            else -> "Tidak dapat dipahami, silakan coba lagi."
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
        private const val TAG = "Setting Voice"
    }
}