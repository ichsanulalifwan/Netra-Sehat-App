package com.app.tunanetradaily

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.app.tunanetradaily.databinding.FragmentLiterasiKesehatanBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class LiterasiKesehatanFragment : Fragment(), CoroutineScope, RecognitionListener {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private var _binding: FragmentLiterasiKesehatanBinding? = null
    private val binding get() = _binding!!
    //private lateinit var speechRecognizer: SpeechRecognizer
    //private lateinit var sttIntent: Intent
    private var textToSpeechEngine: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        // Get the Intent action
        //sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        // init speechRecognizer
        //setSpeech()

        // Init Text to Speech
        textToSpeechEngine = TextToSpeech(context) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                // start TTS
                textToSpeech()
            }
        }

        // Inflate the layout for this fragment
        _binding = FragmentLiterasiKesehatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val welcomeText = getString(R.string.welcome_message)
        val litkesText = getString(R.string.menu_litkes)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        //textToSpeechEngine?.speak(welcomeText, TextToSpeech.QUEUE_FLUSH, null, "welcomeText")
        textToSpeechEngine?.speak(litkesText, TextToSpeech.QUEUE_FLUSH, null, "litkesText")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")
                //startListening()
            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                /*// start SpeechRecognizers
                if (utteranceId == "welcomeText") {
                    startListening()
                }*/

            }

            override fun onError(utteranceId: String?) {
                Log.i(TAG, "TTS On Error")
            }
        })
    }

    /*private fun litkesTTS() {
       // val textLitkes = getString(R.string.litkes_menu)
       // textToSpeechEngine2.speak(textLitkes, TextToSpeech.QUEUE_FLUSH, null, "tts")

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
                    startActivityForResult(sttIntent, MainActivity.REQUEST_CODE_STT2)
                } catch (e: ActivityNotFoundException) {
                    // Handling error when the service is not available.
                    e.printStackTrace()
                    Toast.makeText(
                        context,
                        "Your device does not support SpeechRecognizer.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onError(utteranceId: String?) {
                Log.i("TextToSpeech", "On Error")
            }
        })
    }*/

    /*private fun setSpeech() {

        //speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(this)

        // Get the Intent action
        //sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        // Language model defines the purpose, there are special models for other use cases, like search.
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Adding an extra language, you can use any language from the Locale class.
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))

    }*/

   /* private fun startOver() {
        //stopListening()
        speechRecognizer.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(this)

        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))

        // start again
        startListening()
    }*/

    /*private fun startListening() {
        launch(Dispatchers.Main.immediate) {
            speechRecognizer.startListening(sttIntent)
        }
    }

    private fun stopListening() {
        speechRecognizer.stopListening()
    }*/

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
       //startOver()
    }

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")

        val matches = parsialResult?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        binding.tvSpeak.text = recognizedText

        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check7 = recognizedText.equals("tujuh", true) || recognizedText == "7"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        when {
            check1 -> {
                textToSpeechEngine?.stop()
               // stopListening()
                val giziSeimbangMenu = Intent(context, GiziSeimbangActivity::class.java)
                startActivity(giziSeimbangMenu)
                //finish()
            }
            check7 -> {
                textToSpeechEngine?.stop()
                //stopListening()
                /*val backPreviousMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                startActivity(backPreviousMenu)*/
                parentFragmentManager.popBackStack()
                //finish()
            }
            check9 -> {
                textToSpeechEngine?.stop()
               // stopListening()
                val backMainMenu = Intent(context, MainActivity::class.java)
                startActivity(backMainMenu)
                activity?.let { ActivityCompat.finishAffinity(it) }
            }
            check0 -> {
                textToSpeechEngine?.stop()
                //stopListening()
                activity?.finishAffinity()
                exitProcess(0)
            }
            else -> {
               // startOver()
            }
        }
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        binding.tvSpeak.text = recognizedText
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check7 = recognizedText.equals("tujuh", true) || recognizedText == "7"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        when {
            check1 -> {
                textToSpeechEngine?.stop()
                //stopListening()
                val giziSeimbangMenu = Intent(context, GiziSeimbangActivity::class.java)
                startActivity(giziSeimbangMenu)
                //finish()
            }
            check7 -> {
                textToSpeechEngine?.stop()
                //stopListening()
                /*val backPreviousMenu = Intent(this@LitkesActivity, MainActivity::class.java)
                startActivity(backPreviousMenu)*/
                parentFragmentManager.popBackStack()
                //finish()
            }
            check9 -> {
                textToSpeechEngine?.stop()
                //stopListening()
                val backMainMenu = Intent(context, MainActivity::class.java)
                startActivity(backMainMenu)
                activity?.let { ActivityCompat.finishAffinity(it) }
            }
            check0 -> {
                textToSpeechEngine?.stop()
                //stopListening()
                activity?.finishAffinity()
                exitProcess(0)
            }
            else -> {
                //startOver()
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
        //stopListening()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine?.shutdown()
        //speechRecognizer.destroy()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "LitkesFragment"
    }
}