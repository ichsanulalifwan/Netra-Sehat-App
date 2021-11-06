package com.app.netrasehat.ui.giziseimbang.pilar.phbs

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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.netrasehat.MainActivity
import com.app.netrasehat.R
import com.app.netrasehat.databinding.FragmentPhbsBinding
import com.app.netrasehat.model.Phbs
import com.app.netrasehat.adapters.PhbsAdapter
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
class PhbsFragment : Fragment(), CoroutineScope, RecognitionListener {

    @Inject
    lateinit var prefs: DataStore<Preferences>
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private lateinit var phbsAdapter: PhbsAdapter
    private lateinit var viewModel: PhbsViewModel
    private var textToSpeechEngine: TextToSpeech? = null
    private var _binding: FragmentPhbsBinding? = null
    private val binding get() = _binding!!
    private var loopCode: Int? = 0
    private var speechRate: Float? = 1.0f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhbsBinding.inflate(inflater, container, false)

        // Init viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[PhbsViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            // get Text to Speech speed rate
            getSpeechRate()

            // Init Toolbar
            val toolbar = binding.topAppBar
            val navHostFragment = NavHostFragment.findNavController(this)
            NavigationUI.setupWithNavController(toolbar, navHostFragment)
            toolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }

            // getListPesan
            val dataPhbs = viewModel.getPhbsData(requireActivity())

            // Init Adapter and rv
            phbsAdapter = PhbsAdapter()
            phbsAdapter.setDataPhbs(dataPhbs)
            setupRecyclerView()
            // Navigate to detail pesan
            onItemSelected()

            // Init speechRecognizer
            setSpeech()
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

    private fun setupRecyclerView() {
        with(binding.rvPhbs) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = phbsAdapter
        }
    }

    private fun onItemSelected() {
        phbsAdapter.setOnItemClickListener(object : PhbsAdapter.OnItemClickListener {
            override fun onItemClicked(phbs: Phbs) {
                navigateToDetail(phbs.id)
            }
        })
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val phbs1 = getString(R.string.menu_phbs1)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(phbs1, TextToSpeech.QUEUE_FLUSH, null, "phbs1")

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
        val check4 = recognizedText.equals("empat", true) || recognizedText == "4"
        val check5 = recognizedText.equals("lima", true) || recognizedText == "5"
        val check6 = recognizedText.equals("enam", true) || recognizedText == "6"
        val check7 = recognizedText.equals("tujuh", true) || recognizedText == "7"
        val check8 = recognizedText.equals("delapan", true) || recognizedText == "8"
        val check9 = recognizedText.equals("sembilan", true) || recognizedText == "9"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        if (loopCode == 0) {
            when {
                check1 -> {
                    navigateToDetail(1)
                }
                check2 -> {
                    navigateToDetail(2)
                }
                check3 -> {
                    navigateToDetail(3)
                }
                check4 -> {
                    navigateToDetail(4)
                }
                check5 -> {
                    navigateToDetail(5)
                }
                check6 -> {
                    navigateToDetail(6)
                }
                check7 -> {
                    val phbs2 = getString(R.string.menu_phbs2)
                    textToSpeechEngine?.speak(phbs2, TextToSpeech.QUEUE_FLUSH, null, "phbs2")
                    loopCode = 1
                }
                check8 -> {
                    findNavController().navigateUp()
                }
                check9 -> {
                    backToMainMenu()
                }
                check0 -> {
                    exitApp()
                }
                else -> {
                    wrongOption()
                }
            }
        } else if (loopCode == 1) {
            when {
                check1 -> navigateToDetail(7)
                check2 -> navigateToDetail(8)
                check8 -> findNavController().navigateUp()
                check9 -> backToMainMenu()
                check0 -> exitApp()
                else -> wrongOption()
            }
        }
    }

    private fun navigateToDetail(id: Int) {
        val actionToDetailPhbs = PhbsFragmentDirections.actionPhbsFragmentToDetailPhbsFragment(id)
        findNavController().navigate(actionToDetailPhbs)
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
        private const val TAG = "PhbsFragment"
    }
}