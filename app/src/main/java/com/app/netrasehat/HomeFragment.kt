package com.app.netrasehat

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.netrasehat.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class HomeFragment : Fragment(), CoroutineScope, RecognitionListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sttIntent: Intent
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var textToSpeechEngine: TextToSpeech? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            // Init speechRecognizer
            setSpeech()

            with(binding) {
                cvGiziSeimbang.setOnClickListener {
                    val actionToGiziSeumbang =
                        HomeFragmentDirections.actionHomeFragmentToNavigationGiziSeimbang()
                    findNavController().navigate(actionToGiziSeumbang)
                }

                cvCovid19.setOnClickListener {
                    val actionToCovid19 =
                        HomeFragmentDirections.actionNavigationHomeToCovid19Fragment()
                    findNavController().navigate(actionToCovid19)
                }

                cvPelayananKesehatan.setOnClickListener {
                    val actionToPelayanan =
                        HomeFragmentDirections.actionNavigationHomeToPelayananKesehatanFragment()
                    findNavController().navigate(actionToPelayanan)
                }

                cvContactPerson.setOnClickListener {
                    openWhatsApp(6282193593522, "Hi, Sahabat Netra")
//                    val actionToContact =
//                        HomeFragmentDirections.actionNavigationHomeToContactSahabatNetraFragment()
//                    findNavController().navigate(actionToContact)
                }
            }
        }
    }

    private fun openWhatsApp(phoneNumber: Long, message: String) {
        if (isWhatappInstalled()) {
            val i = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://api.whatsapp.com/send?phone=" + phoneNumber.toString() +
                            "&text=" + message
                )
            )
            startActivity(i)
        } else {
            Toast.makeText(context, "Whatsapp is not installed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun isWhatappInstalled(): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val whatsappInstalled: Boolean = try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return whatsappInstalled
    }

    override fun onStart() {
        super.onStart()

        // Init TTS
        textToSpeechEngine = TextToSpeech(context) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                // start speech welcome message
                //textToSpeech()
            }
        }
    }

    private fun textToSpeech() {
        // Get the text from local string resource
        val welcomeText = getString(R.string.welcome_message)
        val litkesText = getString(R.string.menu_litkes)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(welcomeText, TextToSpeech.QUEUE_FLUSH, null, "welcomeText")
        textToSpeechEngine?.speak(litkesText, TextToSpeech.QUEUE_ADD, null, "litkesText")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.i(TAG, "TTS On Start")
            }

            override fun onDone(utteranceId: String?) {
                Log.i(TAG, "TTS On Done")
                val ttsLoop = utteranceId.equals("tts0") ||
                        utteranceId.equals("tts1") ||
                        utteranceId.equals("litkesText")
//                if (ttsLoop) {
//                    startListening()
//                }

                if (ttsLoop) {
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
        sttIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        //sttIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1)
    }

    private fun startListening() {
        launch(Dispatchers.Main.immediate) {
            speechRecognizer.startListening(sttIntent)
        }
    }

    private fun stopListening() {
        speechRecognizer.stopListening()
    }

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
        //textToSpeechEngine?.stop()
        //stopListening()
        // val messageNoMatch = "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
//        textToSpeechEngine?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, "tts0")
        startOver()
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val recognizedText = matches?.get(0)
        binding.tvSpeak.text = recognizedText
        val check1 = recognizedText.equals("satu", true) || recognizedText == "1"
        val check2 = recognizedText.equals("dua", true) || recognizedText == "2"
        val check3 = recognizedText.equals("tiga", true) || recognizedText == "3"
        val check4 = recognizedText.equals("empat", true) || recognizedText == "4"
        val check0 = recognizedText.equals("nol", true) || recognizedText == "0"

        when {
            check1 -> {
                //textToSpeechEngine?.stop()
                //stopListening()
                //val giziSeimbangMenu = Intent(context, GiziSeimbangActivity::class.java)
                //startActivity(giziSeimbangMenu)
                //finish()
                val actionToGiziSeumbang =
                    HomeFragmentDirections.actionHomeFragmentToNavigationGiziSeimbang()
                findNavController().navigate(actionToGiziSeumbang)
            }
            check2 -> {
                //textToSpeechEngine?.stop()
                //stopListening()
                //finish()
            }
            check3 -> {
                //textToSpeechEngine?.stop()
                //stopListening()
//                val pelayananKesehatanMenu = Intent(context, PelayananKesehatanActivity::class.java)
//                startActivity(pelayananKesehatanMenu)
                //finish()
            }
            check4 -> {
//                val cpSahabatNetraMenu = Intent(context, ContactSahabatNetraActivity::class.java)
//                startActivity(cpSahabatNetraMenu)
                openWhatsApp(6282193593522, "Hi, Sahabat Netra")
            }
            check0 -> {
                //textToSpeechEngine?.stop()
                //stopListening()
                activity?.finishAffinity()
                exitProcess(0)
            }
            else -> {
                startOver()
                /*val messageNoMatch =
                    "Pilihan yang anda katakan tidak ada, silahkan katakan sekali lagi"
                textToSpeechEngine?.speak(messageNoMatch, TextToSpeech.QUEUE_FLUSH, null, "tts1")
                Toast.makeText(context, "Pilihan Salah", Toast.LENGTH_SHORT).show()*/
            }
        }
    }

    override fun onPartialResults(parsialResult: Bundle?) {
        Log.i(TAG, "onPartialResults")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i(TAG, "onEvent")
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
        sttIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        // start again
        startListening()
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
        private const val TAG = "HomeFragment"
    }
}