package GestionMP3;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerResultsIntent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;

import com.example.uapv1301804.lecteurmp3tp.LecteurMP3TP;
import com.example.uapv1301804.lecteurmp3tp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe permettant d'enregistrer la voix de l'utilisateur pour
 * commander vocalement l'application
 */
public class CommandeVocal
{

    private final LecteurMP3TP lecteurMP3TP;
    private final Button mic;
    private SpeechRecognizer asr = null;
    private Intent intent = null;

    /**
     * Constructeur de la classe CommandeVocale
     * @param lecteurMP3TP l'activité principale de l'application
     */
    public CommandeVocal(LecteurMP3TP lecteurMP3TP) {
        //On récupére l'activité principale
        this.lecteurMP3TP = lecteurMP3TP;
        //Récupération du bouton d'enregistrement
        mic = (Button) lecteurMP3TP.findViewById(R.id.micro);
        asr = SpeechRecognizer.createSpeechRecognizer(lecteurMP3TP);
        //asr.setRecognitionListener(new ResultProcessor());
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    }

    /**
     * Permet de lancer un enregistrement vocale
     */
    public void enregistrement() {
        mic.setText("stop");
        asr.startListening(intent);
    }

    /**
     * Permet de stopper un enregistrement vocale
     */
    public void stopperEnregistrement() {
        mic.setText("REC");
        asr.stopListening();
    }

}
