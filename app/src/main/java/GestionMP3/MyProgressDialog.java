package GestionMP3;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Fenetre de dialogue notifiant l'envoi d'un fichier
 */
public class MyProgressDialog extends ProgressDialog{

    /**
     * Constructeur de classe MyProgressDialog
     * @param context
     */
    public MyProgressDialog(Context context) {
        super(context);
    }

    /**
     * Affichage des informations
     * @param size la taille du téléchargement
     * @param titre le titre de la musique en cour d'envoi
     */
    public void set(int size,String titre) {
        setMessage("Envoi " + titre);
        setMax(size / 1024);
        setCancelable(false);
    }
}
