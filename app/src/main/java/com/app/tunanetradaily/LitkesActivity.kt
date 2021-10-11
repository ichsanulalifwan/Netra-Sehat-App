package com.app.tunanetradaily

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.tunanetradaily.databinding.ActivityLitkesBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class LitkesActivity : AppCompatActivity(), CoroutineScope, RecognitionListener {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private lateinit var binding: ActivityLitkesBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private var textToSpeechEngine: TextToSpeech? = null

    /*private val textToSpeechEngine2: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(
            this
        ) { status ->
            // set our locale only if init was success.
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine2.language = Locale("id", "ID")
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLitkesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //AudioManager.getSystemService(Context.AUDIO_SERVICE).setParameters("noise_suppression=on");

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
        val textLitkes = getString(R.string.menu_litkes)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(textLitkes, TextToSpeech.QUEUE_FLUSH, null, "tts1")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i("TextToSpeech", "On Start")
                startListening()
            }

            // Get Input speech after TTS Done
            override fun onDone(utteranceId: String?) {
                Log.i("TextToSpeech", "On Done")

                // sementara aman
                /*if (utteranceId == "tts1") {
                    loop = +1
                    launch(Dispatchers.Main.immediate) {
                        speechRecognizer.startListening(sttIntent)
                    }
                } else if (utteranceId == "tts2") {
                    loop = +2
                    launch(Dispatchers.Main.immediate) {
                        speechRecognizer.startListening(sttIntent)
                    }
                } else if (utteranceId == "tts0") {
                    loop = +11
                    launch(Dispatchers.Main.immediate) {
                        speechRecognizer.startListening(sttIntent)
                    }
                }*/


                /*// Get the Intent action
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
                    startActivityForResult(sttIntent, MainActivity.REQUEST_CODE_STT1)
                } catch (e: ActivityNotFoundException) {
                    // Handling error when the service is not available.
                    e.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "Your device does not support SpeechRecognizer.",
                        Toast.LENGTH_LONG
                    ).show()
                }*/

                /*try {
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
                }*/
            }

            override fun onError(utteranceId: String?) {
                Log.i(TAG, "TTS On Error")
            }
        })
    }

    private fun setSpeech() {

        speechRecognizer.setRecognitionListener(this)

        // Language model defines the purpose, there are special models for other use cases, like search.
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Adding an extra language, you can use any language from the Locale class.
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
        //sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100)

        /*
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
            1000
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1500)*/

        // Text that shows up on the Speech input prompt.
        /*sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bicara Sekarang!")*/


        // keep
        /*speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Log.i(TAG, "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.i(TAG, "onBeginningOfSpeech")
                val text = "Mendengarkan . . ."
                binding.tvResult.text = text
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
                binding.tvResult.text = errorMessage

                // sementara lumayan aman
                *//*if (loop == 0 || loop == 1) {
                    val textLitkes2 = getString(R.string.menu_litkes2)
                    textToSpeechEngine?.speak(textLitkes2, TextToSpeech.QUEUE_FLUSH, null, "tts2")
                    //val message = "Silahkan katakan sekali lagi"
                    //textToSpeechEngine?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "tts2")
                } else if (loop == 2) {
                    val textLitkes0 = getString(R.string.menu_litkes0)
                    textToSpeechEngine?.speak(textLitkes0, TextToSpeech.QUEUE_FLUSH, null, "tts0")
                } else {
                    val message = "Silahkan katakan sekali lagi"
                    textToSpeechEngine?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "tts0")
                }*//*

                //val message = "Silahkan katakan sekali lagi"
                //textToSpeechEngine?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "tts2")
            }

            override fun onResults(results: Bundle?) {
                Log.i(TAG, "onResults")
                val matches =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                val recognizedText = matches?.get(0)

                binding.tvResult.text = recognizedText

                speechRecognizer.stopListening()

                if (recognizedText.equals("satu", true) || recognizedText == "1") {
                    textToSpeechEngine?.stop()
                    val giziSeimbangMenu =
                        Intent(this@LitkesActivity, GiziSeimbangActivity::class.java)
                    startActivity(giziSeimbangMenu)
                    //finish()
                } else if (recognizedText.equals("tujuh", true) || recognizedText == "7"
                ) {
                    *//*val backPreviousMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                    startActivity(backPreviousMenu)*//*
                    finish()
                } else if (recognizedText.equals(
                        "sembilan",
                        true
                    ) || recognizedText == "9"
                ) {
                    val backMainMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                    startActivity(backMainMenu)
                    finishAffinity()
                } else if (recognizedText.equals("nol", true) || recognizedText == "0") {
                    finishAffinity()
                    exitProcess(0)
                } else {
                    val messageNoMatch =
                        "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
                    textToSpeechEngine?.speak(
                        messageNoMatch,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts0"
                    )
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
        })*/
    }

    private fun startOver() {
        speechRecognizer.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)

        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
        sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100)

        /*sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1000)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
            1000
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1500)*/
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

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
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

                        if (recognizedText.equals("tujuh", true) || recognizedText == "7") {
                            finish()
                        } else if (recognizedText.equals(
                                "sembilan",
                                true
                            ) || recognizedText == "9"
                        ) {
                            val backIntent = Intent(this@LitkesActivity, MainActivity::class.java)
                            startActivity(backIntent)
                            finish()
                        } else if (recognizedText.equals("nol", true) || recognizedText == "0") {
                            finishAffinity()
                            exitProcess(0)
                        } else Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }*/

    override fun onReadyForSpeech(p0: Bundle?) {
        Log.i(TAG, "onReadyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech")
        val text = "Mendengarkan . . ."
        binding.tvResult.text = text
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
        binding.tvResult.text = errorMessage
        startOver()
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        binding.tvResult.text = recognizedText
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check7 = recognizedText.equals("tujuh", true) || recognizedText == "7"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        when {
            check1 -> {
                textToSpeechEngine?.stop()
                stopListening()
                val giziSeimbangMenu = Intent(this@LitkesActivity, GiziSeimbangActivity::class.java)
                startActivity(giziSeimbangMenu)
                //finish()
            }
            check7 -> {
                textToSpeechEngine?.stop()
                stopListening()
                val backPreviousMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                startActivity(backPreviousMenu)
                finish()
            }
            check9 -> {
                textToSpeechEngine?.stop()
                stopListening()
                val backMainMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                startActivity(backMainMenu)
                finishAffinity()
            }
            check0 -> {
                textToSpeechEngine?.stop()
                stopListening()
                finishAffinity()
                exitProcess(0)
            }
            else -> {
               startOver()
                /*val messageNoMatch =
                    "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
                textToSpeechEngine?.speak(messageNoMatch, TextToSpeech.QUEUE_FLUSH, null, "tts0")
                Toast.makeText(applicationContext, "Pilihan Salah", Toast.LENGTH_SHORT).show()*/
            }
        }
    }

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")
/*        val matches = parsialResult?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check7 = recognizedText.equals("tujuh", true) || recognizedText == "7"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        when {
            check1 -> {
                textToSpeechEngine?.stop()
                stopListening()
                val giziSeimbangMenu = Intent(this@LitkesActivity, GiziSeimbangActivity::class.java)
                startActivity(giziSeimbangMenu)
                //finish()
            }
            check7 -> {
                textToSpeechEngine?.stop()
                stopListening()
                val backPreviousMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                startActivity(backPreviousMenu)
                finish()
            }
            check9 -> {
                textToSpeechEngine?.stop()
                stopListening()
                val backMainMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                startActivity(backMainMenu)
                finishAffinity()
            }
            check0 -> {
                textToSpeechEngine?.stop()
                stopListening()
                finishAffinity()
                exitProcess(0)
            }
        }*/
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
        //textToSpeechEngine2.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine?.shutdown()
        speechRecognizer.destroy()
        //textToSpeechEngine2.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "LitkesActivity"
    }
}