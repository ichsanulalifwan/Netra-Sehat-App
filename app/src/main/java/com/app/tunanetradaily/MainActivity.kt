package com.app.tunanetradaily

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.tunanetradaily.databinding.ActivityMainBinding
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var textToSpeechEngine: TextToSpeech? = null

    private val textToSpeechEngine2: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(
            this
        ) { status ->
            // set our locale only if init was success.
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine2.language = Locale("id", "ID")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Text to Speech
        /*tts = TextToSpeech(this, this)*/

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }

        textToSpeechEngine = TextToSpeech(this) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                // start speech welcome message
                welcomeTTS()
            }
        }

        litkesTTS()

        binding.btnSpeak.setOnClickListener {
            Toast.makeText(this, "btnSpeak Clicked", Toast.LENGTH_LONG).show()
        }

        binding.btnListen.setOnClickListener {
            litkesTTS()
            /*// Get the text to be converted to speech from our EditText.
            val text = binding.tvSpeak.text.toString()
            // Check if user hasn't input any text.
            if (text.isNotEmpty()) {
                // Lollipop and above requires an additional ID to be passed.
                // Call Lollipop+ function
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts")
            } else {
                Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show()
            }*/
        }
    }

    private fun welcomeTTS() {
        // Get the text from local string resource
        val text = getString(R.string.welcome_message)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i("TextToSpeech", "On Start")
            }

            // Get Input speech after TTS Done
            override fun onDone(utteranceId: String?) {
                Log.i("TextToSpeech", "On Done")

                // Get the Intent action
                val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                // Language model defines the purpose, there are special models for other use cases, like search.
                sttIntent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                // Adding an extra language, you can use any language from the Locale class.
                sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
                // Text that shows up on the Speech input prompt.
                sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bicara Sekarang!")
                try {
                    // Start the intent for a result, and pass in our request code.
                    startActivityForResult(sttIntent, REQUEST_CODE_STT1)
                } catch (e: ActivityNotFoundException) {
                    // Handling error when the service is not available.
                    e.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "Your device does not support SpeechRecognizer.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onError(utteranceId: String?) {
                Log.i("TextToSpeech", "On Error")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT1 -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        val choice = "satu"
                        // Do what you want with the recognized text.
                        binding.tvSpeak.text = recognizedText

                        if (recognizedText.equals(choice, true) || recognizedText == "1")
                            litkesTTS()
                        else
                            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            REQUEST_CODE_STT2 -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        binding.tvSpeak.text = recognizedText

                        if (recognizedText.equals("nol", true) || recognizedText == "0") {
                            finish()
                            exitProcess(0)
                        } else {
                            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun litkesTTS() {
        val textLitkes = getString(R.string.litkes_menu)
        textToSpeechEngine2.speak(textLitkes, TextToSpeech.QUEUE_FLUSH, null, "tts")

        textToSpeechEngine2.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i("TextToSpeech", "On Start")
            }

            // Get Input speech after TTS Done
            override fun onDone(utteranceId: String?) {
                Log.i("TextToSpeech", "On Done")

                // Get the Intent action
                val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                // Language model defines the purpose, there are special models for other use cases, like search.
                sttIntent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                // Adding an extra language, you can use any language from the Locale class.
                sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
                // Text that shows up on the Speech input prompt.
                sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bicara Sekarang!")
                try {
                    // Start the intent for a result, and pass in our request code.
                    startActivityForResult(sttIntent, REQUEST_CODE_STT2)
                } catch (e: ActivityNotFoundException) {
                    // Handling error when the service is not available.
                    e.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "Your device does not support SpeechRecognizer.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onError(utteranceId: String?) {
                Log.i("TextToSpeech", "On Error")
            }
        })
    }

    private fun checkPermission() {

    }

    override fun onPause() {
        textToSpeechEngine?.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine?.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val REQUEST_CODE_STT1 = 1
        private const val REQUEST_CODE_STT2 = 2
    }
}