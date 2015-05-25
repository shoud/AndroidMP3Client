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

    //L'activité principale de l'application
    private final LecteurMP3TP lecteurMP3TP;
    //Le gestionnaire de mp3
    private final GestionMP3 gestionMP3;
    //L'objet permettant d'utiliser l'api google speech
    private SpeechRecognizer asr = null;
    //Pour utiliser l'api goole
    private Intent intent = null;

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
        //Le bouton jouer/pause
        controlButton = (Button)lecteurMP3TP.findViewById(R.id.playStop);
        //Création de l'objet pour gérer le micro
        asr = SpeechRecognizer.createSpeechRecognizer(lecteurMP3TP);
        //Permet de traiter la résultat
        asr.setRecognitionListener(new ResultaSpeech());
        //Pour dire qu'on veut reconnaitre la parole
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

    /**
     * Classe ResultaSpeech, elle permet de gérer les action une fois
     * qu'on a reçu la chaine de caractère par l'api google
     */
    private class ResultaSpeech implements RecognitionListener
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
        /**
         * Méthode peremettant de signigier une érreure dans les logs
         * si il y a eu un problème avec google speech
         */
        public void onError(int erreur)
        {
            //Le message a ecrire dans le log
            String message;
            switch (erreur)
            {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "Audio enregistrement erreur.";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Erreur client.";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "Permissions insuffisantes";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "Erreur de réseau.";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "Temps réseau dépassé.";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "Pas de résulta.";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "Le service est surchargé.";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "Erreur du serveur";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "Pas de son enregistré";
                    break;
                default:
                    message = "Erreur inconnu";
            }
            Log.e("SpeechRecognizer", message);
        }

        @Override
        /**
         * La méthode qui reçoit le résulat de speech
         */
        public void onResults(Bundle results) {
            //la liste des résulats possible, en 0 le plus possible vers le moins possible
            List<String> tmp = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            //On garde la possibilité la plus sûr
            String phrase = tmp.get(0);
            //Le titre de la musique récupéré dans la commande vocale
            String titre;
            try
            {
                //Récupération de l'action à executer sur le service web
                String action = CommandeSWMP3.getAction(phrase);
                //Si l'action est jouer
                if(action.equals("jouer"))
                {
                    //on récupére le titre de la musique à jouer
                    titre = CommandeSWMP3.getTitre(phrase);
                    //Si le titre de la musique est différent de null
                    if (titre != null)
                    {
                            try
                            {
                                //On met à jour le titre avec celui dans le téléphone
                                titre = titrePresent(titre);
                                //On jout le titre sur le téléphone
                                gestionMP3.jouer(titre);
                                //On met le bouton sur stop, pour pouvoir stopper la musique
                                controlButton.setText("Stop");
                            } catch (IOException e)
                            {
                                //Si il y a une erreur
                                e.printStackTrace();
                            }
                    }
                    else
                        //On affiche une fenêtre pour dire que le titre est inconnu
                        errTitre();
                }
                else
                {
                    //Si l'action est supprimer
                    if(action.equals("supprimer"))
                    {
                        //On récupère le titre grâce au service web
                        titre = CommandeSWMP3.getTitre(phrase);
                        //Si le titre est différent de null
                        if(titre != null)
                        {
                            //On met à jour le titre avec celui sur le téléphone
                            titre = titrePresent(titre);
                            //On supprime la musique
                            gestionMP3.supprimer(titre);
                            //On rafraichie la liste de musique
                            lecteurMP3TP.rafraichir();
                        }
                    }
                    else
                    {
                        //Si l'action est rechercher
                        if(action.equals("rechercher"))
                        {

                        }
                        else
                        {
                            //Si l'action est ajouter
                            if(action.equals("ajouter"))
                            {
                                //Ouverture de la fenetre pour rajouter une musique
                                lecteurMP3TP.dialogAjouter();
                            }
                            else
                            {
                                //Si aucune action est trouvé, on affiche la phrase entendu
                                errCommande(phrase);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        /**
         * Méthode permettant de savoir si le titre est présent dans le téléphone
         * @param titre le titre de la musique à chercher
         * @return la version du titre présent dans le téléphone
         */
        public String titrePresent(String titre)
        {
            //La liste de musique présente sur le téléphone
            ArrayList<String> listMusique = new ArrayList<String>(lecteurMP3TP.getListMusique());
            //On test sur tout les titre présent
            for(String titreListe : listMusique)
            {
                //Si le titre existe
                if(titre.toLowerCase().contains(titreListe.toLowerCase()))
                    //On retourne le titre présent sur le téléphone
                    return titreListe;

            }
            //Sinon on retourne null
            return null;
        }

        /**
         * Méthode permettant de notifier à l'utilisateur que le titre n'est pas trouvé
         */
        public void errTitre()
        {
            //Création d'un constructeur de dialogue
            AlertDialog.Builder builder = new AlertDialog.Builder(lecteurMP3TP);
            //Afficher que le titre n'a pas pu être trouvé
            builder.setMessage("Titre non trouvé")
                    //Pour redire la commande
                    .setPositiveButton("Redire commande", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Lance l'enregistrement d'une phrase
                            enregistrement();
                        }
                    })
                    //Si l'utilisateur veut annuler
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            //Création de la boite de dialogue
            builder.create();
            //Affichage de la boite de dialogue
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
