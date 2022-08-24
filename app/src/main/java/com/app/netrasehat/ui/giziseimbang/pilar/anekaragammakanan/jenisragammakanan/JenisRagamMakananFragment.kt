package com.app.netrasehat.ui.giziseimbang.pilar.anekaragammakanan.jenisragammakanan

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
import androidx.recyclerview.widget.GridLayoutManager
import com.app.netrasehat.MainActivity
import com.app.netrasehat.R
import com.app.netrasehat.databinding.FragmentAnekaRagamMakananBinding
import com.app.netrasehat.model.JenisJenisMakanan
import com.app.netrasehat.adapters.JenisMakananAdapter
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
class JenisRagamMakananFragment : Fragment(), CoroutineScope, RecognitionListener {

    @Inject
    lateinit var prefs: DataStore<Preferences>
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private lateinit var adapterJenisMakanan: JenisMakananAdapter
    private lateinit var viewModel: JenisRagamMakananViewModel
    private lateinit var dataJenisMakanan: List<JenisJenisMakanan>
    private lateinit var audioManager: AudioManager
    private var textToSpeechEngine: TextToSpeech? = null
    private var _binding: FragmentAnekaRagamMakananBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<JenisRagamMakananFragmentArgs>()
    private var loopCode: Int? = 0
    private var speechRate: Float? = 1.0f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnekaRagamMakananBinding.inflate(inflater, container, false)

        // Init viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[JenisRagamMakananViewModel::class.java]

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

            // Init Toolbar
            val toolbar = binding.topAppBar
            val navHostFragment = NavHostFragment.findNavController(this)
            NavigationUI.setupWithNavController(toolbar, navHostFragment)
            toolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }

            // get type Jenis Ragam Makanan
            val typeMakanan = args.typeMakanan
            // check JenisRagamMakanan and get List JenisRagamMakanan and set title page
            binding.apply {
                when (typeMakanan) {
                    1 -> {
                        dataJenisMakanan = viewModel.getJenisMakananPokok(requireActivity())
                        toolbarTitle.text = getString(R.string.toolbar_title1)
                    }
                    2 -> {
                        dataJenisMakanan = viewModel.getJenisLaukPauk(requireActivity())
                        toolbarTitle.text = getString(R.string.toolbar_title2)
                    }
                    3 -> {
                        dataJenisMakanan = viewModel.getJenisSayuran(requireActivity())
                        toolbarTitle.text = getString(R.string.toolbar_title3)
                    }
                    4 -> {
                        dataJenisMakanan = viewModel.getJenisBuah(requireActivity())
                        toolbarTitle.text = getString(R.string.toolbar_title4)
                    }
                }
            }

            // Init Adapter and rv
            adapterJenisMakanan = JenisMakananAdapter()
            adapterJenisMakanan.setData(dataJenisMakanan)
            setupRecyclerView()

            // Navigate to detail JenisRagamMakanan
            onItemSelected(typeMakanan)

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
        with(binding.rvRagamMakanan) {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = adapterJenisMakanan
        }
    }

    private fun onItemSelected(typeMakanan: Int) {
        adapterJenisMakanan.setOnItemClickListener(object :
            JenisMakananAdapter.OnItemClickListener {
            override fun onItemClicked(data: JenisJenisMakanan) {
                navigateToDetail(data.id, typeMakanan)
            }
        })
    }

    private fun navigateToDetail(id: Int, type: Int) {
        val actionToDetail =
            JenisRagamMakananFragmentDirections.actionJenisRagamMakananFragmentToDetailJenisMakananFragment(
                id, type
            )
        findNavController().navigate(actionToDetail)
    }

    private fun textToSpeech() {
        // Get the text from local string resource by typeMakanan
        val menuJenisMakanan: String

        when (args.typeMakanan) {
            1 -> {
                menuJenisMakanan = getString(R.string.menu_jenisMakananPokok)
                textToSpeechEngine?.speak(
                    menuJenisMakanan,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "menuUtama"
                )
            }
            2 -> {
                menuJenisMakanan = getString(R.string.menu_jenisLaukPauk1)
                textToSpeechEngine?.speak(
                    menuJenisMakanan,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "menuUtama"
                )
            }
            3 -> {
                menuJenisMakanan = getString(R.string.menu_jenisSayuran1)
                textToSpeechEngine?.speak(
                    menuJenisMakanan,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "menuUtama"
                )
            }
            4 -> {
                menuJenisMakanan = getString(R.string.menu_jenisBuah1)
                textToSpeechEngine?.speak(
                    menuJenisMakanan,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "menuUtama"
                )
            }
        }

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
        val check1 = recognizedText?.contains("satu", true) == true || recognizedText?.contains("1") == true
        val check2 = recognizedText?.contains("dua", true) == true || recognizedText?.contains("2") == true
        val check3 = recognizedText?.contains("tiga", true) == true || recognizedText?.contains("3") == true
        val check4 = recognizedText?.contains("empat", true) == true || recognizedText?.contains("4") == true
        val check5 = recognizedText?.contains("lima", true) == true || recognizedText?.contains("5") == true
        val check6 = recognizedText?.contains("enam", true) == true || recognizedText?.contains("6") == true
        val check7 = recognizedText?.contains("tujuh", true) == true || recognizedText?.contains("7") == true
        val check8 = recognizedText?.contains("delapan", true) == true || recognizedText?.contains("8") == true
        val check9 = recognizedText?.contains("sembilan", true) == true || recognizedText?.contains("9") == true
        val check0 = recognizedText?.contains("nol", true) == true || recognizedText?.contains("0") == true

        when (val typeMakanan = args.typeMakanan) {
            1 -> {
                when {
                    check1 -> navigateToDetail(1, typeMakanan)
                    check2 -> navigateToDetail(2, typeMakanan)
                    check3 -> navigateToDetail(3, typeMakanan)
                    check4 -> navigateToDetail(4, typeMakanan)
                    check5 -> navigateToDetail(5, typeMakanan)
                    check6 -> navigateToDetail(6, typeMakanan)
                    check7 -> navigateToDetail(7, typeMakanan)
                    check8 -> findNavController().navigateUp()
                    check9 -> backToMainMenu()
                    check0 -> exitApp()
                    else -> wrongOption()
                }
            }
            2 -> {
                if (loopCode == 0) {
                    when {
                        check1 -> navigateToDetail(1, typeMakanan)
                        check2 -> navigateToDetail(2, typeMakanan)
                        check3 -> navigateToDetail(3, typeMakanan)
                        check4 -> navigateToDetail(4, typeMakanan)
                        check5 -> navigateToDetail(5, typeMakanan)
                        check6 -> navigateToDetail(6, typeMakanan)
                        check7 -> {
                            val menuJenisLaukPauk2 = getString(R.string.menu_jenisLaukPauk2)
                            textToSpeechEngine?.speak(
                                menuJenisLaukPauk2,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "menuUtama"
                            )
                            loopCode = 1
                        }
                        check8 -> findNavController().navigateUp()
                        check9 -> backToMainMenu()
                        check0 -> exitApp()
                        else -> wrongOption()
                    }
                } else if (loopCode == 1) {
                    when {
                        check1 -> navigateToDetail(7, typeMakanan)
                        check2 -> navigateToDetail(8, typeMakanan)
                        check3 -> navigateToDetail(9, typeMakanan)
                        check8 -> findNavController().navigateUp()
                        check9 -> backToMainMenu()
                        check0 -> exitApp()
                        else -> wrongOption()
                    }
                }
            }
            3 -> {
                when (loopCode) {
                    0 -> {
                        when {
                            check1 -> navigateToDetail(1, typeMakanan)
                            check2 -> navigateToDetail(2, typeMakanan)
                            check3 -> navigateToDetail(3, typeMakanan)
                            check4 -> navigateToDetail(4, typeMakanan)
                            check5 -> navigateToDetail(5, typeMakanan)
                            check6 -> navigateToDetail(6, typeMakanan)
                            check7 -> {
                                val menuJenisSayuran2 = getString(R.string.menu_jenisSayuran2)
                                textToSpeechEngine?.speak(
                                    menuJenisSayuran2,
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "menuUtama"
                                )
                                loopCode = 1
                            }
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                    1 -> {
                        when {
                            check1 -> navigateToDetail(7, typeMakanan)
                            check2 -> navigateToDetail(8, typeMakanan)
                            check3 -> navigateToDetail(9, typeMakanan)
                            check4 -> navigateToDetail(10, typeMakanan)
                            check5 -> navigateToDetail(11, typeMakanan)
                            check6 -> navigateToDetail(12, typeMakanan)
                            check7 -> {
                                val menuJenisSayuran3 = getString(R.string.menu_jenisSayuran3)
                                textToSpeechEngine?.speak(
                                    menuJenisSayuran3,
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "menuUtama"
                                )
                                loopCode = 2
                            }
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                    2 -> {
                        when {
                            check1 -> navigateToDetail(13, typeMakanan)
                            check2 -> navigateToDetail(14, typeMakanan)
                            check3 -> navigateToDetail(15, typeMakanan)
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                }
            }
            4 -> {
                when (loopCode) {
                    0 -> {
                        when {
                            check1 -> navigateToDetail(1, typeMakanan)
                            check2 -> navigateToDetail(2, typeMakanan)
                            check3 -> navigateToDetail(3, typeMakanan)
                            check4 -> navigateToDetail(4, typeMakanan)
                            check5 -> navigateToDetail(5, typeMakanan)
                            check6 -> navigateToDetail(6, typeMakanan)
                            check7 -> {
                                val menuJenisBuah2 = getString(R.string.menu_jenisBuah2)
                                textToSpeechEngine?.speak(
                                    menuJenisBuah2,
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "menuUtama"
                                )
                                loopCode = 1
                            }
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                    1 -> {
                        when {
                            check1 -> navigateToDetail(7, typeMakanan)
                            check2 -> navigateToDetail(8, typeMakanan)
                            check3 -> navigateToDetail(9, typeMakanan)
                            check4 -> navigateToDetail(10, typeMakanan)
                            check5 -> navigateToDetail(11, typeMakanan)
                            check6 -> navigateToDetail(12, typeMakanan)
                            check7 -> {
                                val menuJenisBuah3 = getString(R.string.menu_jenisBuah3)
                                textToSpeechEngine?.speak(
                                    menuJenisBuah3,
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "menuUtama"
                                )
                                loopCode = 2
                            }
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                    2 -> {
                        when {
                            check1 -> navigateToDetail(13, typeMakanan)
                            check2 -> navigateToDetail(14, typeMakanan)
                            check3 -> navigateToDetail(15, typeMakanan)
                            check4 -> navigateToDetail(16, typeMakanan)
                            check5 -> navigateToDetail(17, typeMakanan)
                            check6 -> navigateToDetail(18, typeMakanan)
                            check7 -> {
                                val menuJenisBuah4 = getString(R.string.menu_jenisBuah4)
                                textToSpeechEngine?.speak(
                                    menuJenisBuah4,
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    "menuUtama"
                                )
                                loopCode = 3
                            }
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                    3 -> {
                        when {
                            check1 -> navigateToDetail(19, typeMakanan)
                            check2 -> navigateToDetail(20, typeMakanan)
                            check3 -> navigateToDetail(21, typeMakanan)
                            check4 -> navigateToDetail(22, typeMakanan)
                            check5 -> navigateToDetail(23, typeMakanan)
                            check8 -> findNavController().navigateUp()
                            check9 -> backToMainMenu()
                            check0 -> exitApp()
                            else -> wrongOption()
                        }
                    }
                }
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
        private const val TAG = "JenisRagamMakanan"
    }
}