package GestionMP3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;
import com.example.uapv1301804.lecteurmp3tp.LecteurMP3TP;
import com.example.uapv1301804.lecteurmp3tp.R;

import org.json.JSONException;


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

    //Pour traduire une commande
    private enum commande {jouer,supprimer,rechercher,ajouter,erreur; }
    private ArrayList<String> listJouer = new ArrayList<String>();
    private ArrayList<String> listSupprimer = new ArrayList<String>();
    private ArrayList<String> listRechercher = new ArrayList<String>();
    private ArrayList<String> listAjouter = new ArrayList<String>();
    Button controlButton;

    /**
     * Constructeur de la classe CommandeVocale
     * @param lecteurMP3TP l'activité principale de l'application
     */
    public CommandeVocal(LecteurMP3TP lecteurMP3TP, GestionMP3 gestionMP3) {
        //On récupére l'activité principale
        this.lecteurMP3TP = lecteurMP3TP;
        //Permet de lancer les méthodes
        this.gestionMP3 = gestionMP3;
        //Initialisation des commandes
        initCommande();
        //Récupération du bouton d'enregistrement
        mic = (ImageButton) lecteurMP3TP.findViewById(R.id.micro);
        controlButton = (Button)lecteurMP3TP.findViewById(R.id.playStop);
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

    public void initCommande()
    {
        //Pour jouer
        listJouer.add("jouer");
        listJouer.add("joue");
        listJouer.add("lance");
        listJouer.add("lire");

        //Pour supprimer
        listSupprimer.add("supprime");
        listSupprimer.add("supprimer");
        listSupprimer.add("enleve");

        //Pour rechercher
        listRechercher.add("recherche");
        listRechercher.add("rechercher");

        //Pour ajouter
        listAjouter.add("ajoute");
        listAjouter.add("ajouter");
        listAjouter.add("rajoute");
    }

    private class ResultProcessor implements RecognitionListener
    {

        @Override
        public void onReadyForSpeech(Bundle params){}

        @Override
        public void onBeginningOfSpeech(){}

        @Override
        public void onRmsChanged(float rmsdB){}

        @Override
        public void onBufferReceived(byte[] buffer){}

        @Override
        public void onEndOfSpeech(){}

        @Override
        public void onError(int error)
        {
            String message;
            switch (error)
            {
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
            //Log.v("BuiltinText", tmp.get(0));
            System.out.println("Avant le try");
            try
            {

                //service.MethodToInvoke(...);
                //System.out.println(">>>>>>>>>>La commande voulue : " + service.getAction(tmp.get(0)));
                //System.out.println(">>>>>>>>>>>>>>>Le titre voulu" + service.getTitre(tmp.get(0)));

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            System.out.println("apres le try");
            stopperEnregistrement();
        }


        /**
         * Méthode permettant de notifier à l'utilisateur que le titre n'est pas trouvé
         */
        public void errTitre()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(lecteurMP3TP);
            builder.setMessage("Titre non trouvé")
                    .setPositiveButton("Redire commande", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            enregistrement();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }

        /**
         * Notifie à l'utilisateur que la commande n'est pas compris
         * @param cmd
         */
        public void errCommande(String cmd)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(lecteurMP3TP);
            builder.setMessage("Commande erroné : " + cmd)
                    .setPositiveButton("Redire commande", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            enregistrement();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    }

}
