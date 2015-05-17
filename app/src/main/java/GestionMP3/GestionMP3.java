package GestionMP3;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import com.example.uapv1301804.lecteurmp3tp.LecteurMP3TP;

import java.io.IOException;
import java.util.*;

import Ice.Current;
import Ice.Identity;
import Serveur.Moniteur;
import Serveur._MoniteurDisp;

/**
 * Classe permettant de gérer le stream de mp3
 * Ajouter
 * Supprimer
 * Rechercher
 * Lire
 */
public class GestionMP3 implements MediaPlayer.OnPreparedListener {

    //Pour se connecter au serveur graĉe à ICE
    private Ice.Communicator ic = null;
    private Ice.ObjectPrx base = null;
    //Permet de lancer les fonction disponible sur le serveur
    private Serveur.mp3Prx mp3 = null;
    //Permet de lire des musique sur android
    private MediaPlayer mp = null;
    //Le titre de la musique qui est entraint d'être lue
    private String titre = null;
    //Le nom de l'artiste de la musique en cour
    private String artiste = null;
    //Le nom de l'album de la musique en cour
    private String album = null;
    //Le nom du compositeur de la musique en cour
    private String compo = null;
    //Le token pour avoir l'url qui permet de lire la musique
    private String token = "";
    //Le comminicateur pour utiliser ICEStorm
    private Ice.Communicator communicator = null;
    //Permet de savoir si on est connecté à la méssagerie ICEStorm
    private boolean connecte = false;
    //La connexion au routeur ICEStorm
    private Glacier2.RouterPrx routerPrx = null;
    //La connexion au moniteur ICEStorm
    private Ice.ObjectPrx moniteurPrx = null;
    //Le topique de ICEStorm
    private IceStorm.TopicPrx topicPrx = null;
    //L'activity principale du programme
    private LecteurMP3TP lecteurMP3TP = null;

    /**
     * Permet de renvoyer l'objet permettant d'executer les fonction du serveur
     * @return mp3 L'objet permettant d'appeler les fonctions du serveur
     */
    public Serveur.mp3Prx getMp3()
    {
        return mp3;
    }

    /**
     * Constructeur de la classe GestionMP3
     * Il permet de créer la connexion avec le serveur ICE et Storm.
     */
    public GestionMP3(Ice.Communicator communicator, LecteurMP3TP lecteurMP3TP)
    {
        //On récupère le communicator pour Storm
        this.communicator = communicator;
        //On récupère l'activity principale
        this.lecteurMP3TP = lecteurMP3TP;
        //Initialisation du routeur
        initRouteur();
        //Initialisation de ICE
        ic = Ice.Util.initialize();
        //Le lien pour se connecter au serveur ICE
        base = ic.stringToProxy("SimpleServeurMP3:default -h shoud.ovh -p 10000");
        //Récupération de l'objet permettant d'executer les fonctions du serveur
        mp3 = Serveur.mp3PrxHelper.checkedCast(base);
        //Création de lobjet permettant de lire les mp3
        mp = new MediaPlayer();
        //Initialisation de ICEStorm
        initStorm();
    }

    /**
     * Méthode permettant d'initialiser le routeur Glacier
     */
    private void initRouteur()
    {
        try
        {
            //On récupère le routeur
            Ice.RouterPrx defaultRouter = communicator.getDefaultRouter();
            //On récupère l'objet permettant de créer une connexion
            routerPrx = Glacier2.RouterPrxHelper.checkedCast(defaultRouter);
            try
            {
                //On créer une session sur le routeur grâce aux identifiants
                routerPrx.createSession("shoud", "f8ka7wer9");
                //On met à jour connecte pour dire qu'on est bien connecté au routeur
                connecte = true;
            } catch (Exception e)
            {
                //On affiche un message dans les logs si il y a un problme pour se connecter au routeur
                Log.e("Erreur session = ", e.toString());
            }
        } catch (Exception e)
        {
            //Si on peut pas se connecter au routeur
            Log.e("Erreur Glacier = ", e.toString());
        }
    }

    /**
     * Méthode permettant d'initialiser Storm
     */
    private void initStorm() {
        //Si la connexion au routeur a échoué on ne va pas plus loin
        if (!connecte)
            //On sort de la méthode
            return;
        //Sinon
        try
        {
            //On se connecte à ICEStorm
            Ice.ObjectPrx obj = communicator.stringToProxy("IceStorm/TopicManager:tcp -h shoud.ovh -p 5038");
            //Récupération du topique manageur
            IceStorm.TopicManagerPrx topicManager = IceStorm.TopicManagerPrxHelper.checkedCast(obj);
            //Récupération de l'adaptateur
            Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithRouter("MonitorAdapter", routerPrx);
            //Création du moniteur qui permet d'envoyer des méssages
            Moniteur monitor = new MoniteurI();
            //Récupération du moniteur proxy
            moniteurPrx = adapter.add(monitor, new Identity("default", routerPrx.getCategoryForClient())).ice_twoway();
            //Activation de storm
            adapter.activate();
            try
            {
                //On dit qu'on veut envoyer des messages dans ce topique
                topicPrx = topicManager.retrieve("NotificationAjouterSupprimer");
                try
                {
                    //Création d'une map
                    Map<String, String> qos = new HashMap<>();
                    qos.put("reliability", "ordered");
                    //On lie le moniteur au topique
                    topicPrx.subscribeAndGetPublisher(qos, moniteurPrx);
                } catch (Exception e)
                {
                    //Si on ne peut pas soubscrire au topique
                    Log.e("Erreur soubscritpion : ", e.toString());
                }
            } catch (Exception e)
            {
                //Si on ne peut pas récupérer le topique
                Log.e("Erreur recup topique : ", e.toString());
            }
        } catch (Exception e)
        {
            //Si on peut pas se connecter à ICEStorm
            Log.e("Erreur ICEStorm", e.toString());
        }
    }

    /**
     * Permet de lancer la lecture d'un MP3
     * @param titre Le nom du mp3 à lancer
     */
    public void jouer(String titre) throws IOException
    {
        //Vérification si le titre n'est pas vide
        if(titre != null)
        {
            //On remet à 0 le lecteur multimedia
            mp.reset();
            //On met à jour le titre courant
            this.titre = titre;
            //On met à jour l'artiste courant
            this.artiste = mp3.getArtiste(titre);
            //On met à jour l'album courant
            this.album = mp3.getAlbum(titre);
            //On met à jour le compositeur courant
            this.compo = mp3.getCompo(titre);
            //Mise en place du stream sur le serveur
            mp3.jouerMP3(titre);
            //Récupération du token pour le lien unique
            this.token = mp3.getToken();
            //Lancement du stream sur le serveur
            mp3.play();
            //L'url pour lire la musique sur le serveur
            String url = "http://shoud.ovh:8090/" + token + ".mp3"; // your URL here
            //On dit au lecteur multimedia qu'on lit un stream
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //On met le lien de la musique à lire
            mp.setDataSource(url);
            //Charge la musique, rapidité en fonction de la connexion
            mp.prepare();
            //Permet de lire la musique sur le téléphone
            mp.start();
        }
    }

    /**
     * Permet de mettre en pause la musique
     */
    public void pause()
    {
        mp.pause();
    }

    /**
     * Permet de lancer la musique
     */
    public void play()
    {
        mp.start();
    }

    /**
     * Permet de rechercher un MP3
     * @param titre Le MP3 à rechercher
     * @return true Si le MP3 existe
     * @return false Si le MP3 existe pas
     */
    public boolean rechercher(String titre)
    {
        return mp3.rechercherMP3(titre);
    }

    /**
     * Permet de supprimer un MP3 sur le serveur
     * @return true Si le mp3 a pu être supprimé
     * @return false si le mp3 n'a pas pu être supprimé.
     */
    public boolean supprimer()
    {
        //Vérification si le titre n'est pas equal à null et si il existe
        if(titre != null && mp3.rechercherMP3(titre))
            //Suppresion de la musique ou pas en fonction de la réponse du serveur
            return mp3.supprimerMP3(this.titre);
        //On ne supprime rien
        return false;
    }

    /**
     * Permet de supprimer un MP3 sur le serveur
     * @param titre le titre de la musique à supprimer
     * @return true Si le mp3 a pu être supprimé
     * @return false si le mp3 n'a pas pu être supprimé.
     */
    public boolean supprimer(String titre)
    {
        //Vérification si le titre n'est pas equal à null et si il existe
        if(titre != null && mp3.rechercherMP3(titre))
            //Suppresion de la musique ou pas en fonction de la réponse du serveur
            return mp3.supprimerMP3(titre);
        //On ne supprime rien
        return false;
    }

    /**
     * Méthode renvoyant le titre de la musique courante
     * @return titre le titre de la musique courante
     */
    public String getTitre()
    {
        return this.titre;
    }

    /**
     * Méthode renvoyant l'album de la musique courante
     * @return album l'album de la musique courante
     */
    public String getAlbum()
    {
        return this.album;
    }
    /**
     * Méthode renvoyant l'artiste de la musique courante
     * @return artiste le nom de l'artiste de la musique courante
     */
    public String getArtiste()
    {
        return this.artiste;
    }
    /**
     * Méthode renvoyant le compositeur de la musique courante
     * @return compo le compositeur de la musique courante
     */
    public String getCompo()
    {
        return this.compo;
    }
    /**
     * Permet de récupérer la liste des MP3
     * @return listMp3 La liste en string des mp3
     */
    public String[] lister()
    {
        return mp3.listerMP3();
    }

    /**
     * Permet de lancer le lecteur multimedia
     * @param mp le lecteur multimedia
     */
    public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }

    /**
     * Permet de détruire la connexion au serveur ICE
     */
    protected void finalize ()
    {
        //Si la connexion au serveur est différente de null
        if(ic != null)
        {
            try
            {
                //Destruction de la connexion
                ic.destroy();
            }catch (Exception e)
            {
                System.err.println(e.getMessage());
                return;
            }
        }
    }

    /**
     * la classe MoniteurI permet d'envoyer un message avec ICEStorm
     */
    private class MoniteurI extends _MoniteurDisp {
        @Override
        /**
         * Cette méthode permet d'envoyer un rapport à l'utilisateur en fonction
         * de l'action executé
         */
        public void rapport(String action, String titre, Current __current)
        {
            //Le message qui sera envoyé à tout les utilisateurs
            final String message;
            //Si l'action reçu est ajouter
            if (action.equals("ajouter"))
            {
                //On affiche à l'utilisateur le titre qui vient d'être ajouté
                message = "Le mp3 " + titre + " a été rajouté";
                //On rafraichie la liste
                lecteurMP3TP.rafraichir();
            }

            //Si l'action est supprimer
            else if (action.equals("supprimer"))
                //On affiche à l'utilisateur le titre qui vient d'être supprimé
                message = "Le mp3 " + titre + " a été supprimé";
                else
                    //On affiche qu'il y a quelque chose d' étrange
                    message = "L'action faite n'existe pas Oo";
            //Permet d'afficher un message sur l'écran de l'utilisateur
            lecteurMP3TP.runOnUiThread(new Runnable()
            {
                    @Override
                    /**
                     * Pour afficher le message sur l'écran
                     */
                    public void run()
                    {
                        //Permet d'afficher un message court en bas de l'acran de l'utilisateur
                        Toast.makeText(lecteurMP3TP.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}

