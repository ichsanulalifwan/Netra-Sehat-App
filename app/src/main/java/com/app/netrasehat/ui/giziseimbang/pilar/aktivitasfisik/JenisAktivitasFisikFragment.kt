package com.app.netrasehat.ui.giziseimbang.pilar.aktivitasfisik

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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.app.netrasehat.MainActivity
import com.app.netrasehat.R
import com.app.netrasehat.databinding.FragmentJenisAktivitasFisikBinding
import com.app.netrasehat.model.AktivitasFisik
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
class JenisAktivitasFisikFragment : Fragment(), CoroutineScope, RecognitionListener {

    @Inject
    lateinit var prefs: DataStore<Preferences>
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private lateinit var viewModel: JenisAktivitasFisikViewModel
    private lateinit var dataAktivitas: AktivitasFisik
    private lateinit var audioManager: AudioManager
    private var _binding: FragmentJenisAktivitasFisikBinding? = null
    private val binding get() = _binding!!
    private var textToSpeechEngine: TextToSpeech? = null
    private val args by navArgs<JenisAktivitasFisikFragmentArgs>()
    private var speechRate: Float? = 1.0f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJenisAktivitasFisikBinding.inflate(inflater, container, false)

        // Init viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[JenisAktivitasFisikViewModel::class.java]

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

            // Set Detail Aktivitas Fisik
            val typeAktivitas = args.typeAktivitas
            viewModel.setJenisAktivitas(typeAktivitas)

            // Get Aktivitas Fisik
            dataAktivitas = viewModel.getDetailAktivitas(requireActivity())

            // Populate Aktivitas Fisik
            populateData(dataAktivitas)
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
                dataAktivitas.apply {
                    textToSpeech(pengertian, manfaat, contoh)
                }
            }
        }
    }

    private fun populateData(jenisAktivitas: AktivitasFisik) {
        binding.apply {
            txtJenisName.text = jenisAktivitas.title
            txtPengertianAf.text = jenisAktivitas.pengertian
            txtManfaatAf.text = jenisAktivitas.manfaat
            txtContohAf.text = jenisAktivitas.contoh
        }
    }

    private fun textToSpeech(pengertian: String, manfaat: String, contoh: String) {
        // Get the text from local string resource
        val menuPilihan = getString(R.string.menu_kembali)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(pengertian, TextToSpeech.QUEUE_FLUSH, null, "pengertian")
        textToSpeechEngine?.speak(manfaat, TextToSpeech.QUEUE_ADD, null, "manfaat")
        textToSpeechEngine?.speak(contoh, TextToSpeech.QUEUE_ADD, null, "contoh")
        textToSpeechEngine?.speak(menuPilihan, TextToSpeech.QUEUE_ADD, null, "menuPilihan")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")

            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                val textParam = utteranceId.equals("menuPilihan") || utteranceId.equals("wrongTts")
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
        val check8 = recognizedText?.contains("delapan", true) == true || recognizedText?.contains("8") == true
        val check9 = recognizedText?.contains("sembilan", true) == true || recognizedText?.contains("9") == true
        val check0 = recognizedText?.contains("nol", true) == true || recognizedText?.contains("0") == true

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
        private const val TAG = "JenisAktivitasFisik"
    }
}