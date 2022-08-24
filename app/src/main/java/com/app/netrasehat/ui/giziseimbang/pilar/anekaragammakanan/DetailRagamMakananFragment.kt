package com.app.netrasehat.ui.giziseimbang.pilar.anekaragammakanan

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
import com.app.netrasehat.databinding.FragmentDetailRagamMakananBinding
import com.app.netrasehat.model.RagamMakanan
import com.bumptech.glide.Glide
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
class DetailRagamMakananFragment : Fragment(), CoroutineScope, RecognitionListener {

    @Inject
    lateinit var prefs: DataStore<Preferences>
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private lateinit var viewModel: DetailRagamMakananViewModel
    private lateinit var dataRagamMakanan: RagamMakanan
    private lateinit var audioManager: AudioManager
    private var textToSpeechEngine: TextToSpeech? = null
    private var _binding: FragmentDetailRagamMakananBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailRagamMakananFragmentArgs>()
    private var speechRate: Float? = 1.0f

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailRagamMakananBinding.inflate(inflater, container, false)

        // Init viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailRagamMakananViewModel::class.java]

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

            // Set Detail RagamMakanan
            val ragamMakananId = args.id
            viewModel.setRagamMakanan(ragamMakananId)

            if (ragamMakananId == 1) {
                binding.apply {
                    tvKandunganRagamMakanan.visibility = View.GONE
                    detailKandunganRagamMakanan.visibility = View.GONE
                    tvMasalahRagamMakanan.visibility = View.GONE
                    detailMasalahRagamMakanan.visibility = View.GONE
                }
            } else if (ragamMakananId == 5) {
                binding.btnJenisJenis.visibility = View.GONE
            }

            // Get RagamMakanan
            dataRagamMakanan = viewModel.getDetailRagamMakanan(requireActivity())

            // Populate RagamMakanan
            populateData(dataRagamMakanan)

            // Set button behavior
            setBtnJenisJenis(dataRagamMakanan)

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

    private fun populateData(data: RagamMakanan) {
        binding.apply {
            tvTitleRagamMakanan.text = data.judul
            detailPengertianRagamMakanan.text = data.pengertian
            detailManfaatRagamMakanan.text = data.manfaat
            detailJenisRagamMakanan.text = data.jenis
            detailPorsiRagamMakanan.text = data.porsi
            detailKandunganRagamMakanan.text = data.kandungan
            detailMasalahRagamMakanan.text = data.masalah
            Glide.with(requireActivity())
                .load(data.img)
                .centerCrop()
                .into(imgHeaderRagamMakanan)
        }
    }

    // Set Button jenis-jenis text and navigation
    private fun setBtnJenisJenis(data: RagamMakanan) {
        val text = getString(R.string.title_jenis_jenis_makanan) + " " + data.judul
        val jenisId = data.id

        binding.apply {
            btnJenisJenis.text = text

            btnJenisJenis.setOnClickListener {
                val actionToListJenis =
                    DetailRagamMakananFragmentDirections
                        .actionDetailRagamMakananFragmentToJenisRagamMakananFragment(jenisId)
                findNavController().navigate(actionToListJenis)
            }
        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val menuDetailRagamMakanan = getString(R.string.menu_detail_anekaRagamMakanan)
        val menuDetailRagamMakana2 = getString(R.string.menu_detail_anekaRagamMakanan2)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        if (args.id == 1) {
            textToSpeechEngine?.speak(
                menuDetailRagamMakana2,
                TextToSpeech.QUEUE_FLUSH,
                null, "menuDetailRagamMakanan"
            )
        } else {
            textToSpeechEngine?.speak(
                menuDetailRagamMakanan,
                TextToSpeech.QUEUE_FLUSH,
                null, "menuDetailRagamMakanan"
            )
        }

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")
            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                val textParam = utteranceId.equals("menuDetailRagamMakanan")
                        || utteranceId.equals("wrongTts")
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
        val check1 = recognizedText?.contains("satu", true) == true || recognizedText?.contains("1") == true
        val check2 = recognizedText?.contains("dua", true) == true || recognizedText?.contains("2") == true
        val check3 = recognizedText?.contains("tiga", true) == true || recognizedText?.contains("3") == true
        val check4 = recognizedText?.contains("empat", true) == true || recognizedText?.contains("4") == true
        val check5 = recognizedText?.contains("lima", true) == true || recognizedText?.contains("5") == true
        val check6 = recognizedText?.contains("enam", true) == true || recognizedText?.contains("6") == true
        val check8 = recognizedText?.contains("delapan", true) == true || recognizedText?.contains("8") == true
        val check9 = recognizedText?.contains("sembilan", true) == true || recognizedText?.contains("9") == true
        val check0 = recognizedText?.contains("nol", true) == true || recognizedText?.contains("0") == true
        val ragamMakananId = args.id
        val menuDetailRagamMakanan = getString(R.string.menu_detail_anekaRagamMakanan)
        val menuDetailRagamMakana2 = getString(R.string.menu_detail_anekaRagamMakanan2)

        if (ragamMakananId == 1) {
            when {
                check1 -> check1(menuDetailRagamMakana2)
                check2 -> check2(menuDetailRagamMakana2)
                check3 -> check3()
                check4 -> check4(menuDetailRagamMakana2)
                check8 -> findNavController().navigateUp()
                check9 -> backToMainMenu()
                check0 -> exitApp()
                else -> wrongOption()
            }
        } else {
            when {
                check1 -> check1(menuDetailRagamMakanan)
                check2 -> check2(menuDetailRagamMakanan)
                check3 -> {
                    if (ragamMakananId == 5) {
                        val option = dataRagamMakanan.jenis
                        textToSpeechEngine?.speak(
                            option,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "option"
                        )
                        textToSpeechEngine?.speak(
                            menuDetailRagamMakanan,
                            TextToSpeech.QUEUE_ADD,
                            null,
                            "menuDetailRagamMakanan"
                        )
                    } else check3()
                }
                check4 -> check4(menuDetailRagamMakanan)
                check5 -> {
                    val option = dataRagamMakanan.kandungan
                    textToSpeechEngine?.speak(
                        option,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "option"
                    )
                    textToSpeechEngine?.speak(
                        menuDetailRagamMakanan,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        "menuDetailRagamMakanan"
                    )
                }
                check6 -> {
                    val option = dataRagamMakanan.masalah
                    textToSpeechEngine?.speak(
                        option,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "option"
                    )
                    textToSpeechEngine?.speak(
                        menuDetailRagamMakanan,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        "menuDetailRagamMakanan"
                    )
                }
                check8 -> findNavController().navigateUp()
                check9 -> backToMainMenu()
                check0 -> exitApp()
                else -> wrongOption()
            }
        }
    }

    private fun check1(menuDetailMakanan: String) {
        val option = dataRagamMakanan.pengertian
        textToSpeechEngine?.speak(
            option,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "option"
        )
        textToSpeechEngine?.speak(
            menuDetailMakanan,
            TextToSpeech.QUEUE_ADD,
            null,
            "menuDetailRagamMakanan"
        )
    }

    private fun check2(menuDetailMakanan: String) {
        val option = dataRagamMakanan.manfaat
        textToSpeechEngine?.speak(
            option,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "option"
        )
        textToSpeechEngine?.speak(
            menuDetailMakanan,
            TextToSpeech.QUEUE_ADD,
            null,
            "menuDetailRagamMakanan"
        )
    }

    private fun check3() {
        val actionToListJenis =
            DetailRagamMakananFragmentDirections
                .actionDetailRagamMakananFragmentToJenisRagamMakananFragment(
                    dataRagamMakanan.id
                )
        findNavController().navigate(actionToListJenis)
    }

    private fun check4(menuDetailMakanan: String) {
        val option = dataRagamMakanan.porsi
        textToSpeechEngine?.speak(
            option,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "option"
        )
        textToSpeechEngine?.speak(
            menuDetailMakanan,
            TextToSpeech.QUEUE_ADD,
            null,
            "menuDetailRagamMakanan"
        )
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
        private const val TAG = "DetailRagamMakanan"
    }
}