package com.app.netrasehat.giziseimbang.pilar.aktivitasfisik

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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.netrasehat.MainActivity
import com.app.netrasehat.R
import com.app.netrasehat.databinding.FragmentAktivitasFisikBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class AktivitasFisikFragment : Fragment(), CoroutineScope, RecognitionListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private var _binding: FragmentAktivitasFisikBinding? = null
    private val binding get() = _binding!!
    private var textToSpeechEngine: TextToSpeech? = null
    private var loopCode: Int? = 0

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAktivitasFisikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            // Init speechRecognizer
            setSpeech()

            // Init Toolbar
            val toolbar = binding.topAppBar
            val navHostFragment = NavHostFragment.findNavController(this)
            NavigationUI.setupWithNavController(toolbar, navHostFragment)

            // Set Navigate to previous page (backstack)
            toolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }

            with(binding) {
                btnAfRingan.setOnClickListener {
                    navigateToDetail(1)
                    loopCode = 0
                }
                btnAfSedang.setOnClickListener {
                    navigateToDetail(2)
                    loopCode = 0
                }
                btnAfBerat.setOnClickListener {
                    navigateToDetail(3)
                    loopCode = 0
                }
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

                // start speech
                textToSpeech()
            }
        }
    }

    private fun navigateToDetail(id: Int) {
        val actionToJenis =
            AktivitasFisikFragmentDirections.actionAktivitasFisikFragmentToJenisAktivitasFisikFragment(
                id
            )
        findNavController().navigate(actionToJenis)
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val menu = getString(R.string.menu_aktivitasFisik)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(menu, TextToSpeech.QUEUE_FLUSH, null, "menu")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")

            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                val textParam = utteranceId.equals("menu")
                        || utteranceId.equals("wrongTts")
                        || utteranceId.equals("jenis")
                if (textParam) {
                    startListening()
                }
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
        startOver()
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check2 = recognizedText.equals("dua", true) || recognizedText == "2"
        val check3 = recognizedText.equals("tiga", true) || recognizedText == "3"
        val check8 = recognizedText.equals("delapan", true) || recognizedText == "8"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        if (loopCode == 0) {
            when {
                check1 -> {
                    val pengertian = getString(R.string.pengertian_aktivitasfisik)
                    val menu = getString(R.string.menu_aktivitasFisik)
                    textToSpeechEngine?.speak(pengertian, TextToSpeech.QUEUE_FLUSH, null, "pengertian")
                    // back to previous menu
                    textToSpeechEngine?.speak(menu, TextToSpeech.QUEUE_ADD, null, "menu")
                }
                check2 -> {
                    val jenis = getString(R.string.menu_jenisAktivitasFisik)
                    textToSpeechEngine?.speak(jenis, TextToSpeech.QUEUE_FLUSH, null, "jenis")
                    loopCode = 1
                }
                check3 -> {
                    val masalah = getString(R.string.masalah_aktivitasfisik)
                    val menu = getString(R.string.menu_aktivitasFisik)
                    textToSpeechEngine?.speak(masalah, TextToSpeech.QUEUE_FLUSH, null, "masalah")
                    // back to previous menu
                    textToSpeechEngine?.speak(menu, TextToSpeech.QUEUE_ADD, null, "menu")
                }
                check8 -> findNavController().navigateUp()
                check9 -> backToMainMenu()
                check0 -> exitApp()
                else -> wrongOption()
            }
        } else if (loopCode == 1) {
            when {
                check1 -> {
                    navigateToDetail(1)
                    loopCode = 0
                }
                check2 -> {
                    navigateToDetail(2)
                    loopCode = 0
                }
                check3 -> {
                    navigateToDetail(3)
                    loopCode = 0
                }
                check8 -> findNavController().navigateUp()
                check9 -> backToMainMenu()
                check0 -> exitApp()
                else -> wrongOption()
            }
        }
    }

    private fun backToMainMenu() {
        val backMainMenu = Intent(context, MainActivity::class.java)
        startActivity(backMainMenu)
        activity?.let {
            ActivityCompat.finishAffinity(it)
        }
    }

    private fun exitApp() {
        activity?.finishAffinity()
        exitProcess(0)
    }

    private fun wrongOption() {
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

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i(TAG, "onEvent")
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

    override fun onDestroyView() {
        super.onDestroyView()
        textToSpeechEngine?.shutdown()
        speechRecognizer.destroy()
        _binding = null
    }

    companion object {
        private const val TAG = "AktivitasFisikFragment"
    }
}