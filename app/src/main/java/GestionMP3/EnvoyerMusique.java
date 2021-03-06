package GestionMP3;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Classe permettant d'envoyer un fichier mp3 sur le serveur
 */
public class EnvoyerMusique extends AsyncTask<Void, Integer, Void>
{
    //Le chemin du MP3
    private String chemin = null;
    //Le titre du MP3
    private String titre = null;
    //l'artiste
    private String artiste = null;
    //Album
    private String album = null;
    //Compositeur
    private String compo = null;
    //La boite de dialogue pour montrer l'envoi du MP3
    private ProgressDialogPerso dialog = null;
    //Récupération de l'objet permettant de lancer des requettes sur le serveur
    private Serveur.mp3Prx mp3 = null;
    //Initialisation de la taille à 0.
    private int size = 0;
    //L'objet permettant de gérer les mp3
    private GestionMP3 gestionMP3 = null;

    /**
     * Constructeur de la classe EnvoyerMusique
     * Elle est initialisé à chaque envoie de mp3
     * @param chemin Le chemin du mp3 à envoyer
     * @param titre Le titre du mp3 à envoyer
     * @param dialog La boite de dialogue à utiliser lors de l'envoi
     * @param gestionMP3 L'objet permettant de gérer les mp3
     */
    public EnvoyerMusique(String chemin, String titre, String artiste, String album, String compo, ProgressDialogPerso dialog, GestionMP3 gestionMP3) {
        //le chemin du mp3 sur le téléphone
        this.chemin = chemin;
        //le titre de la musique
        this.titre = titre;
        //Le nom de l'artiste de la musique
        this.artiste = artiste;
        //Le nom de l'album de la musique
        this.album = album;
        //Le nom du compositeur
        this.compo = compo;
        //La boite de dialogue pour afficher l'envoi
        this.dialog = dialog;
        //Permet de gérer les mp3 sur le téléphone
        this.gestionMP3 = gestionMP3;
        //Permet de gérer les mp3 sur le serveur
        this.mp3 = gestionMP3.getMp3();
    }

    /**
     * Méthode permettant d'envoyer un fichier
     * @param params
     */
    @Override
    protected Void doInBackground(Void... params) {
        try
        {
            int offset = 0;
            //Pour définir le fichier à envoyer
            File file = new File(chemin);
            //Ouverture du stream pour envoyer le fichier
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            int max = 512 * 1024;
            //La taille du fichier en bit
            size = (int) file.length();
            byte[] bytes = new byte[size];
            int i = in.read(bytes);
            if (i == 0)
                return null;
            dialog.set(size, titre);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgressNumberFormat("%1d/%2dKB");
            while (offset < size)
            {
                int end = offset + max;
                if (end > size)
                    end = size;
                byte[] temp = Arrays.copyOfRange(bytes, offset, end);
                try
                {
                    publishProgress(offset);
                    mp3.envoyerMusique(titre, artiste, album, compo, temp);
                } catch (Exception e)
                {
                    Log.e("Erreur envoi", e.toString());
                }
                offset += end;
            }
            dialog.dismiss();
        } catch (Exception e)
        {
            Log.e("Erreur envoi", e.toString());
        }
        return null;
    }

    /**
     * Méthode permettant de montrer l'avancement de l'envoi
     * @param values
     */
    @Override
    public void onProgressUpdate(Integer... values)
    {
        if (values[0] == 0) {
            dialog.setMessage("Envoi de " + titre );
            dialog.setMax(size);
            dialog.setCancelable(false);
            dialog.show();
        }
        dialog.setProgress(values[0]);
    }

    /**
     * Méthode permettant de fermer la boite de dialogue
     * @param result
     */
    @Override
    public void onPostExecute(Void result)
    {
        if (this.dialog != null)
            this.dialog.dismiss();
    }
}
