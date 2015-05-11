package GestionMP3;

import android.media.MediaPlayer;
import android.media.AudioManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe permettant de gérer les mp3
 * Ajouter
 * Supprimer
 * Rechercher
 * Lire
 */
public class GestionMP3 implements MediaPlayer.OnPreparedListener {

    private Ice.Communicator ic = null;
    private Ice.ObjectPrx base = null;
    private Serveur.mp3Prx mp3 = null;
    private MediaPlayer mp = null;
    private String titre = null;
    private String artiste = null;
    private String album = null;
    private String compo = null;
    private String token = "";

    public Serveur.mp3Prx getMp3()
    {
        return mp3;
    }

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
     * @param titre Le nom du mp3 à lancer
     */
    public void jouer(String titre) throws IOException
    {

        mp.reset();
        this.titre = titre;
        this.artiste = mp3.getArtiste(titre);
        this.album = mp3.getAlbum(titre);
        this.compo = mp3.getCompo(titre);
        mp3.jouerMP3(titre);
        this.token = mp3.getToken();
        mp3.play();
        String url = "http://shoud.ovh:8090/" + token + ".mp3"; // your URL here
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(url);
        mp.prepare(); // might take long! (for buffering, etc)
        mp.start();
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
     * @return true Si le mp3 a pu être supprimé
     * @return false si le mp3 n'a pas pu être supprimé.
     */
    public boolean supprimer()
    {
        return mp3.supprimerMP3(this.titre);
    }

    public boolean supprimer(String titre)
    {
        return mp3.supprimerMP3(titre);
    }

    public String getTitre()
    {
        return this.titre;
    }
    public String getAlbum()
    {
        return this.album;
    }
    public String getArtiste()
    {
        return this.artiste;
    }
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

