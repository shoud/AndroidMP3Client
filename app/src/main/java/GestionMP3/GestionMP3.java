package GestionMP3;

import android.media.MediaPlayer;
import android.media.AudioManager;

import java.io.IOException;

/**
 * Created by uapv1301804 on 03/03/15.
 */
public class GestionMP3 implements MediaPlayer.OnPreparedListener {

    private Ice.Communicator ic = null;
    private Ice.ObjectPrx base = null;
    private Serveur.mp3Prx mp3 = null;
    private MediaPlayer mp = null;
    private String nom = null;

    /**
     * Constructeur de la classe GestionMP3
     * Il permet de créer la connexion avec le serveur.
     */
    public GestionMP3()
    {
        ic = Ice.Util.initialize();
        base = ic.stringToProxy("SimpleServeurMP3:default -h shoud.ovh -p 10000");
        mp3 = Serveur.mp3PrxHelper.checkedCast(base);
        mp = new MediaPlayer();
    }

    /**
     * Permet de lancer la lecture d'un MP3
     * @param nom Le nom du mp3 à lancer
     */
    public void jouer(String nom)
    {
        this.nom = nom;
        mp3.begin_jouerMP3(nom);
    }
    public void start() throws IOException {
        String url = "http://shoud.ovh:8090/" + nom + ".mp3"; // your URL here
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(url);
        mp.prepare(); // might take long! (for buffering, etc)
        mp.start();
    }
    public void pause()
    {
        mp.pause();
    }
    public void play()
    {
        mp.start();
    }
    public Boolean isPlaying() {
        return mp != null && mp.isPlaying();
    }
    /**
     * Permet de rajouter un MP3
     */
    public void ajouter(String nom)
    {
        mp3.ajouterMP3(nom,"test");
    }

    /**
     * Permet de rechercher un MP3
     * @param nom Le MP3 à rechercher
     * @return true Si le MP3 existe
     * @return false Si le MP3 existe pas
     */
    public boolean rechercher(String nom)
    {
        return mp3.rechercherMP3("Poutou");
    }

    /**
     * Permet de supprimer un MP3 sur le serveur
     * @param nom Le nom de l'MP3 à supprimer
     * @return true Si le mp3 a pu être supprimé
     * @return false si le mp3 n'a pas pu être supprimé.
     */
    public boolean supprimer(String nom)
    {
        return mp3.supprimerMP3("Poutou");
    }

    /**
     * Permet de récupérer la liste des MP3
     * @return listMp3 La liste en string des mp3
     */
    public String lister()
    {
        String listMp3 = "";
        for(String buf : mp3.listerMP3())
            listMp3 = listMp3 + buf;
        return listMp3;
    }
    public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }
    protected void finalize ()
    {
        if(ic != null)
        {
            try
            {
                ic.destroy();
            }catch (Exception e)
            {
                System.err.println(e.getMessage());
                return;
            }
        }
    }
}

