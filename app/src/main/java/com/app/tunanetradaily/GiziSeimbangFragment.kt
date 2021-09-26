package com.app.tunanetradaily

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.tunanetradaily.databinding.FragmentGiziSeimbangBinding
import java.util.*

class GiziSeimbangFragment : Fragment() {

    private var _binding: FragmentGiziSeimbangBinding? = null
    private val binding get() = _binding!!

    private var textToSpeechEngine: TextToSpeech? = null

    private val textToSpeechEngine2: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(
            context
        ) { status ->
            // set our locale only if init was success.
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine2.language = Locale("id", "ID")
            }
        }
    }

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

        if (activity != null)  {

            // Init Text to Speech
            /*tts = TextToSpeech(this, this)*/



        }

        textToSpeechEngine = TextToSpeech(context) { arg0 ->
            if (arg0 == TextToSpeech.SUCCESS) {
                // Set language
                textToSpeechEngine?.language = Locale("id", "ID")

                // start speech welcome message
                //welcomeTTS()
            }
        }

        //litkesTTS()

        binding.btnSpeak.setOnClickListener {
            Toast.makeText(context, "btnSpeak Clicked", Toast.LENGTH_LONG).show()
        }

        binding.btnListen.setOnClickListener {
            //litkesTTS()
            /*// Get the text to be converted to speech from our EditText.
            val text = binding.tvSpeak.text.toString()
            // Check if user hasn't input any text.
            if (text.isNotEmpty()) {
                // Lollipop and above requires an additional ID to be passed.
                // Call Lollipop+ function
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts")
            } else {
                Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show()
            }*/
        }
    }

    /*private fun welcomeTTS() {
        // Get the text from local string resource
        val text = getString(R.string.welcome_message)

        // Lollipop and above requires an additional ID to be passed.
        // Call Lollipop+ function
        textToSpeechEngine?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")

        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
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
                    startActivityForResult(sttIntent, MainActivity.REQUEST_CODE_STT1)
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
    }

    private fun litkesTTS() {
        val textLitkes = getString(R.string.litkes_menu)
        textToSpeechEngine2.speak(textLitkes, TextToSpeech.QUEUE_FLUSH, null, "tts")

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



    override fun onPause() {
        super.onPause()
        textToSpeechEngine?.stop()
        textToSpeechEngine2.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textToSpeechEngine?.shutdown()
        textToSpeechEngine2.shutdown()
        _binding = null
    }

    /*override fun onDestroy() {
        textToSpeechEngine?.shutdown()
        textToSpeechEngine2.shutdown()
        super.onDestroy()
    }*/

    companion object {
        private const val REQUEST_CODE_STT1 = 1
        private const val REQUEST_CODE_STT2 = 2
    }
}