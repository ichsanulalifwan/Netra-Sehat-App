package com.app.netrasehat.ui.giziseimbang.pilar.beratbadan

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
import androidx.core.app.ActivityCompat
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
import com.app.netrasehat.databinding.FragmentMenghitungBbBinding
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
class MenghitungBbFragment : Fragment(), CoroutineScope, RecognitionListener {

    @Inject
    lateinit var prefs: DataStore<Preferences>
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private lateinit var audioManager: AudioManager
    private var _binding: FragmentMenghitungBbBinding? = null
    private val binding get() = _binding!!
    private var textToSpeechEngine: TextToSpeech? = null
    private var loopCode: Int? = 0
    private var speechRate: Float? = 1.0f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenghitungBbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            // enhanced audio input
            audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.setParameters("noise_suppression=on")

            // get Text to Speech speed rate
            getSpeechRate()

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
                btnHitung.setOnClickListener {
                    countImt()
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

    private fun countImt() {
        binding.apply {
            val beratBadan = addBb.text.toString().toInt()
            val tinggiBadan = addTb.text.toString().toInt()
            // Coount Imt
            val convertTb = tinggiBadan * 0.01
            val result = beratBadan / (convertTb * convertTb)

            // binding result
            resultImt.text = String.format("%.1f", result)

            when {
                result < 18.5 -> {
                    txtSummaryImt.text = getString(R.string.result_imt_1)
                    txtSummaryImt.setTextColor(resources.getColor(R.color.red_summary))
                }
                result in 18.5..22.9 -> {
                    txtSummaryImt.text = getString(R.string.result_imt_2)
                    txtSummaryImt.setTextColor(resources.getColor(R.color.green_summary))
                }
                result in 23.0..24.9 -> {
                    txtSummaryImt.text = getString(R.string.result_imt_3)
                    txtSummaryImt.setTextColor(resources.getColor(R.color.red_summary))
                }
                result >= 25 -> {
                    txtSummaryImt.text = getString(R.string.result_imt_4)
                    txtSummaryImt.setTextColor(resources.getColor(R.color.red_summary))
                }
            }
            cvResultImt.visibility = View.VISIBLE
        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val inputBb = getString(R.string.menu_inputBbImt)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(inputBb, TextToSpeech.QUEUE_FLUSH, null, "inputBb")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")

            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                val textParam = utteranceId.equals("inputBb")
                        || utteranceId.equals("wrongInput")
                        || utteranceId.equals("menu")
                        || utteranceId.equals("inputTb")
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
        val check8 = recognizedText.equals("delapan", true) || recognizedText == "8"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"
        if (loopCode == 0) {
            if (recognizedText != null) {
                if (recognizedText.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                    binding.addBb.setText(recognizedText)
                    val mendapatkanBb =
                        "Berat badan yang anda masukkan ialah $recognizedText kilogram, selanjutnya silahkan masukkan tinggi badan anda dalam satuan sentimeter."
                    textToSpeechEngine?.speak(
                        mendapatkanBb,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "inputTb"
                    )
                    loopCode = 1
                } else wrongInput()
            }
            when {
                check8 -> findNavController().navigateUp()
                check9 -> {
                    val backMainMenu = Intent(context, MainActivity::class.java)
                    startActivity(backMainMenu)
                    activity?.let { ActivityCompat.finishAffinity(it) }
                }
                check0 -> {
                    activity?.finishAffinity()
                    exitProcess(0)
                }
            }
        } else if (loopCode == 1) {
            if (recognizedText != null) {
                if (recognizedText.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                    binding.addTb.setText(recognizedText)
                    countImt()
                    val resultImt = binding.resultImt.text.toString()
                    val summary = binding.txtSummaryImt.text.toString()

                    val mendapatkanTb =
                        "Tinggi badan yang anda masukkan ialah $recognizedText sentimeter. Hasil Indeks Massa Tubuh Anda ialah $resultImt. $summary"
                    textToSpeechEngine?.speak(
                        mendapatkanTb,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "result"
                    )
                    // back to previous menu
                    val menu = getString(R.string.menu_kembali)
                    textToSpeechEngine?.speak(menu, TextToSpeech.QUEUE_ADD, null, "menu")
                } else wrongInput()
            }
            when {
                check8 -> findNavController().navigateUp()
                check9 -> {
                    val backMainMenu = Intent(context, MainActivity::class.java)
                    startActivity(backMainMenu)
                    activity?.let { ActivityCompat.finishAffinity(it) }
                }
                check0 -> {
                    activity?.finishAffinity()
                    exitProcess(0)
                }
            }
        }
    }

    private fun wrongInput() {
        val messageWrongInput =
            "Anda Salah Memasukkan Angka, silahkan coba lagi"
        textToSpeechEngine?.speak(
            messageWrongInput,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "wrongInput"
        )
        Toast.makeText(context, "Harap Memasukkan Angka Yang Benar", Toast.LENGTH_SHORT)
            .show()
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
        private const val TAG = "HitungBeratBadan"
    }
}