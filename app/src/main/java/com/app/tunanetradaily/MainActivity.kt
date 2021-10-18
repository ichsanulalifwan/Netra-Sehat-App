package com.app.tunanetradaily

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.app.tunanetradaily.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity()/*, CoroutineScope, RecognitionListener*/ {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
//    private lateinit var speechRecognizer: SpeechRecognizer
//    private lateinit var sttIntent: Intent
//    private var textToSpeechEngine: TextToSpeech? = null
//    private val job = Job()
//    override val coroutineContext: CoroutineContext
//        get() = job + Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }

        setupNavigation()

        // Init speechRecognizer
//        setSpeech()
//
//        binding.cvGiziSeimbang.setOnClickListener {
//            textToSpeechEngine?.stop()
//            stopListening()
//            val giziSeimbangMenu = Intent(this, GiziSeimbangActivity::class.java)
//            startActivity(giziSeimbangMenu)
//        }
//        binding.cvCovid19.setOnClickListener {
//            textToSpeechEngine?.stop()
//            stopListening()
//            val covidMenu = Intent(this, Covid19Activity::class.java)
//            startActivity(covidMenu)
//        }
//        binding.cvPelayananKesehatan.setOnClickListener {
//            textToSpeechEngine?.stop()
//            stopListening()
//            val pelayananKesehatanMenu = Intent(this, PelayananKesehatanActivity::class.java)
//            startActivity(pelayananKesehatanMenu)
//        }
//        binding.cvContactPerson.setOnClickListener {
//            textToSpeechEngine?.stop()
//            stopListening()
//            val cpSahabatNetraMenu = Intent(this, ContactSahabatNetraActivity::class.java)
//            startActivity(cpSahabatNetraMenu)
//        }
    }

    //
//    override fun onStart() {
//        super.onStart()
//
//        // Init TTS
//        textToSpeechEngine = TextToSpeech(this) { arg0 ->
//            if (arg0 == TextToSpeech.SUCCESS) {
//                // Set language
//                textToSpeechEngine?.language = Locale("id", "ID")
//
//                // start speech welcome message
//                textToSpeech()
//            }
//        }
//    }
//
    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the ActionBar with navController and 3 top level destinations
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    //
//    private fun textToSpeech() {
//        // Get the text from local string resource
//        val welcomeText = getString(R.string.welcome_message)
//        val litkesText = getString(R.string.menu_litkes)
//
//        // Lollipop and above requires an additional ID to be passed.
//        // Call Lollipop+ function
//        textToSpeechEngine?.speak(welcomeText, TextToSpeech.QUEUE_FLUSH, null, "welcomeText")
//        textToSpeechEngine?.speak(litkesText, TextToSpeech.QUEUE_ADD, null, "litkesText")
//
//        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
//            override fun onStart(utteranceId: String?) {
//                Log.i(TAG, "TTS On Start")
//            }
//
//            override fun onDone(utteranceId: String?) {
//                Log.i(TAG, "TTS On Done")
//                if (utteranceId == "welcomeText") {
//                    startListening()
//                }
//            }
//
//            override fun onError(utteranceId: String?) {
//                Log.i(TAG, "TTS On Error")
//            }
//        })
//    }
//
//    private fun setSpeech() {
//
//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
//        speechRecognizer.setRecognitionListener(this)
//
//        // Get the Intent action
//        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        // Language model defines the purpose, there are special models for other use cases, like search.
//        sttIntent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        // Adding an extra language, you can use any language from the Locale class.
//        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
//        //sttIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
//        //sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1)
//    }
//
//    private fun startOver() {
//        stopListening()
//        /*speechRecognizer.destroy()
//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
//        speechRecognizer.setRecognitionListener(this)
//
//        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        sttIntent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))*/
//
//        // start again
//        startListening()
//    }
//
//    private fun startListening() {
//        launch(Dispatchers.Main.immediate) {
//            speechRecognizer.startListening(sttIntent)
//        }
//    }
//
//    private fun stopListening() {
//        speechRecognizer.stopListening()
//    }
//
//    override fun onReadyForSpeech(p0: Bundle?) {
//        Log.i(TAG, "onReadyForSpeech")
//    }
//
//    override fun onBeginningOfSpeech() {
//        Log.i(TAG, "onBeginningOfSpeech")
//        val text = "Mendengarkan . . ."
//        binding.tvSpeak.text = text
//    }
//
//    override fun onRmsChanged(rmsdB: Float) {
//        Log.i(TAG, "onRmsChanged: $rmsdB")
//    }
//
//    override fun onBufferReceived(buffer: ByteArray?) {
//        Log.i(TAG, "onBufferReceived: $buffer")
//    }
//
//    override fun onEndOfSpeech() {
//        Log.i(TAG, "onEndOfSpeech")
//    }
//
//    override fun onError(errorCode: Int) {
//        val errorMessage: String = getErrorText(errorCode)
//        Log.d(TAG, "FAILED $errorMessage")
//        binding.tvSpeak.text = errorMessage
//        startOver()
//    }
//
//    override fun onPartialResults(parsialResult: Bundle?) {
//        Log.i(TAG, "onPartialResults")
//
////        val matches = parsialResult?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
////        val recognizedText = matches?.get(0)
////        binding.tvSpeak.text = recognizedText
////        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
////        val check2 = recognizedText.equals("dua", true) || recognizedText == "2"
////        val check3 = recognizedText.equals("tiga", true) || recognizedText == "3"
////        val check4 = recognizedText.equals("empat", true) || recognizedText == "4"
////        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"
////
////        when {
////            check1 -> {
////                textToSpeechEngine?.stop()
////                stopListening()
////                val giziSeimbangMenu = Intent(this, GiziSeimbangActivity::class.java)
////                startActivity(giziSeimbangMenu)
////                finish()
////            }
////            check2 -> {
////                textToSpeechEngine?.stop()
////                stopListening()
////                val covidMenu = Intent(this, Covid19Activity::class.java)
////                startActivity(covidMenu)
////                finish()
////            }
////            check3 -> {
////                textToSpeechEngine?.stop()
////                stopListening()
////                val pelayananKesehatanMenu = Intent(this, PelayananKesehatanActivity::class.java)
////                startActivity(pelayananKesehatanMenu)
////                finish()
////            }
////            check4 -> {
////                textToSpeechEngine?.stop()
////                stopListening()
////                val cpSahabatNetraMenu = Intent(this, ContactSahabatNetraActivity::class.java)
////                startActivity(cpSahabatNetraMenu)
////                finish()
////            }
////            check0 -> {
////                textToSpeechEngine?.stop()
////                stopListening()
////                finishAffinity()
////                exitProcess(0)
////            }
////            else -> {
////                startOver()
////            }
////        }
//    }
//
//    override fun onResults(results: Bundle?) {
//        Log.i(TAG, "onResults")
//
//        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//        val recognizedText = matches?.get(0)
//        binding.tvSpeak.text = recognizedText
//        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
//        val check2 = recognizedText.equals("dua", true) || recognizedText == "2"
//        val check3 = recognizedText.equals("tiga", true) || recognizedText == "3"
//        val check4 = recognizedText.equals("empat", true) || recognizedText == "4"
//        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"
//
//        when {
//            check1 -> {
//                //textToSpeechEngine?.stop()
//                //stopListening()
//                val giziSeimbangMenu = Intent(this, GiziSeimbangActivity::class.java)
//                startActivity(giziSeimbangMenu)
//                //finish()
//            }
//            check2 -> {
//                //textToSpeechEngine?.stop()
//                //stopListening()
//                val covidMenu = Intent(this, Covid19Activity::class.java)
//                startActivity(covidMenu)
//                //finish()
//            }
//            check3 -> {
//                //textToSpeechEngine?.stop()
//                //stopListening()
//                val pelayananKesehatanMenu = Intent(this, PelayananKesehatanActivity::class.java)
//                startActivity(pelayananKesehatanMenu)
//                //finish()
//            }
//            check4 -> {
//                //textToSpeechEngine?.stop()
//                //stopListening()
//                val cpSahabatNetraMenu = Intent(this, ContactSahabatNetraActivity::class.java)
//                startActivity(cpSahabatNetraMenu)
//                //finish()
//            }
//            check0 -> {
//                //textToSpeechEngine?.stop()
//                //stopListening()
//                finishAffinity()
//                exitProcess(0)
//            }
//            else -> {
//                startOver()
//            }
//        }
//    }
//
//    override fun onEvent(p0: Int, p1: Bundle?) {
//        Log.i(TAG, "onEvent")
//    }
//
//    private fun getErrorText(errorCode: Int): String {
//        val message: String = when (errorCode) {
//            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
//            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
//            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
//            SpeechRecognizer.ERROR_NETWORK -> "Network error"
//            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
//            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
//            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
//            SpeechRecognizer.ERROR_SERVER -> "error from server"
//            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
//            else -> "Didn't understand, please try again."
//        }
//        return message
//    }
//
    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RecordAudioRequestCode
            )
        }
    }

    //
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty()) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onPause() {
//        textToSpeechEngine?.stop()
//        stopListening()
//        super.onPause()
//    }
//
//    override fun onDestroy() {
//        textToSpeechEngine?.shutdown()
//        speechRecognizer.destroy()
//        super.onDestroy()
//    }
//
    companion object {
        private const val RecordAudioRequestCode = 1
        private const val TAG = "MainActivity"
    }
}