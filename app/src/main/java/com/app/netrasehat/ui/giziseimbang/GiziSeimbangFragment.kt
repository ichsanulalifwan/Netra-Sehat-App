package com.app.netrasehat.ui.giziseimbang

import android.content.Context
import android.content.Intent
import android.media.AudioManager
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
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.netrasehat.MainActivity
import com.app.netrasehat.R
import com.app.netrasehat.databinding.FragmentGiziSeimbangBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

@AndroidEntryPoint
class GiziSeimbangFragment : Fragment(), CoroutineScope, RecognitionListener {

    @Inject
    lateinit var prefs: DataStore<Preferences>
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private lateinit var audioManager: AudioManager
    private var _binding: FragmentGiziSeimbangBinding? = null
    private val binding get() = _binding!!
    private var textToSpeechEngine: TextToSpeech? = null
    private var speechRate: Float? = 1.0f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGiziSeimbangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.setParameters("noise_suppression=on")

            // get Text to Speech speed rate
            getSpeechRate()

            // Init speechRecognizer
            setSpeech()

            // Init Toolbar
            val toolbar = binding.toolbar
            val navHostFragment = NavHostFragment.findNavController(this)
            NavigationUI.setupWithNavController(toolbar, navHostFragment)

            /*setHasOptionsMenu(true)
            (activity as AppCompatActivity).setSupportActionBar(toolbar)*/

            // Set Navigate to previous page (backstack)
            toolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }

            with(binding) {
                cvPilarGizi.setOnClickListener {
                    val actionToPilarGizi =
                        GiziSeimbangFragmentDirections.actionNavigationGiziSeimbangToPilarGiziSeimbangFragment()
                    findNavController().navigate(actionToPilarGizi)
                }
                cvPesanGizi.setOnClickListener {
                    val actionToPesanGizi =
                        GiziSeimbangFragmentDirections.actionNavigationGiziSeimbangToPesanGiziSeimbangFragment()
                    findNavController().navigate(actionToPesanGizi)
                }
            }
        }
    }

    private fun getSpeechRate() {
        lifecycleScope.launch {
            prefs.data.catch { e ->
                e.printStackTrace()
            }.collectLatest {
                speechRate = it[floatPreferencesKey("speechRate")]
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Init TTS
        textToSpeechEngine = TextToSpeech(context) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                speechRate?.let { textToSpeechEngine?.setSpeechRate(it) }

                // start speech
                textToSpeech()
            }
        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val giziSeimbang = getString(R.string.menu_giziSeimbang)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(giziSeimbang, TextToSpeech.QUEUE_FLUSH, null, "giziSeimbang")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")

            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                startListening()
            }

            override fun onError(utteranceId: String?) {
                Log.i(TAG, "TTS On Error")
            }
        })
    }

    private fun setSpeech() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
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
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context?.packageName)
    }

    private fun startListening() {
        launch(Dispatchers.Main.immediate) {
            speechRecognizer.startListening(sttIntent)
        }
    }

    private fun stopListening() {
        speechRecognizer.stopListening()
    }

    private fun startOver() {
        // Destroy and rebuild again
        stopListening()
        speechRecognizer.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(this)

        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("id", "ID"))
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context?.packageName)

        /*sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1000)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
            1000
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1500)*/
        //sttIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        // start again
        startListening()
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
//        binding.tvSpeak.text = errorMessage
        startOver()
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)

        when {
            recognizedText?.contains(
                "satu",
                true
            ) == true || recognizedText?.contains("1") == true -> {
                val actionToPilarGizi =
                    GiziSeimbangFragmentDirections.actionNavigationGiziSeimbangToPilarGiziSeimbangFragment()
                findNavController().navigate(actionToPilarGizi)
            }
            recognizedText?.contains(
                "dua",
                true
            ) == true || recognizedText?.contains("2") == true -> {
                val actionToPesanGizi =
                    GiziSeimbangFragmentDirections.actionNavigationGiziSeimbangToPesanGiziSeimbangFragment()
                findNavController().navigate(actionToPesanGizi)
            }
            recognizedText?.contains(
                "delapan",
                true
            ) == true || recognizedText?.contains("8") == true -> {
                findNavController().navigateUp()
            }
            recognizedText?.contains(
                "sembilan",
                true
            ) == true || recognizedText?.contains("9") == true -> {
                val backMainMenu = Intent(context, MainActivity::class.java)
                startActivity(backMainMenu)
                activity?.let { finishAffinity(it) }
            }
            recognizedText?.contains(
                "nol",
                true
            ) == true || recognizedText?.contains("0") == true -> {
                activity?.finishAffinity()
                exitProcess(0)
            }
            else -> {
                val messageNoMatch =
                    "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
                textToSpeechEngine?.speak(
                    messageNoMatch,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "wrongTts"
                )
                Toast.makeText(context, "Pilihan tidak ada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")
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
        super.onPause()
        textToSpeechEngine?.stop()
        stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textToSpeechEngine?.shutdown()
        speechRecognizer.destroy()
        _binding = null
    }

    companion object {
        private const val TAG = "GiziSeimbangFragment"
    }
}