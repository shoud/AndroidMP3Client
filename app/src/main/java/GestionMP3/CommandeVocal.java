package GestionMP3;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.uapv1301804.lecteurmp3tp.LecteurMP3TP;
import com.example.uapv1301804.lecteurmp3tp.R;


/**
 * Classe permettant d'enregistrer la voix de l'utilisateur pour
 * commander vocalement l'application
 */
public class CommandeVocal
{

    private final LecteurMP3TP lecteurMP3TP;
    private final GestionMP3 gestionMP3;
    private final ImageButton mic;
    private SpeechRecognizer asr = null;
    private Intent intent = null;

    /**
     * Constructeur de la classe CommandeVocale
     * @param lecteurMP3TP l'activité principale de l'application
     */
    public CommandeVocal(LecteurMP3TP lecteurMP3TP, GestionMP3 gestionMP3) {
        //On récupére l'activité principale
        this.lecteurMP3TP = lecteurMP3TP;
        //Permet de lancer les méthodes
        this.gestionMP3 = gestionMP3;
        //Récupération du bouton d'enregistrement
        mic = (ImageButton) lecteurMP3TP.findViewById(R.id.micro);
        //Création de l'objet pour gérer le micro
        asr = SpeechRecognizer.createSpeechRecognizer(lecteurMP3TP);
        //Permet de traiter la résultat
        asr.setRecognitionListener(new ResultProcessor());
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    }

    /**
     * Permet de lancer un enregistrement vocale
     */
    public void enregistrement() {
        //Lance un enregistrement
        asr.startListening(intent);
    }

    /**
     * Permet de stopper un enregistrement vocale
     */
    public void stopperEnregistrement() {
        //Permet d'arrêter l'enregistrement
        asr.stopListening();
    }
    private class ResultProcessor implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "Audio recording error.";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Other client side errors.";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "Insufficient permissions";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "Other network related errors.";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "Network operation timed out.";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "No recognition result matched.";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RecognitionService busy.";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "Server sends error status.";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "No speech input";
                    break;
                default:
                    message = "Error";
            }
            Log.e("SpeechRecognizer", message);
        }

        @Override
        public void onResults(Bundle results) {
            List<String> tmp = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.v("BuiltinText", tmp.get(0));
            String joue = "joue";
            ArrayList<String> listMusique = lecteurMP3TP.getListMusique();

            if(tmp.get(0).toLowerCase().contains(joue.toLowerCase()))
            {
                System.out.println("Dans joue");
                for(String titre : listMusique)
                {
                    System.out.println(titre);

                    if(tmp.get(0).contains(titre.toLowerCase().toLowerCase()))
                    {
                        System.out.println("Dans titre");
                        try
                        {
                            System.out.println("Titre de la musique = " + titre);
                            gestionMP3.jouer(titre);
                            Button controlButton = (Button)lecteurMP3TP.findViewById(R.id.playStop);
                            controlButton.setText("Stop");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
            asr.stopListening();
            //recordButton.setText(R.string.record);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    }

}
