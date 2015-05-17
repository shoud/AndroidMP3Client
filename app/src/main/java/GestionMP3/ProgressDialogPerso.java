package GestionMP3;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Fenetre de dialogue notifiant l'envoi d'un fichier
 * Elle permet d'afficher un chargement
 */
public class ProgressDialogPerso extends ProgressDialog{

    /**
     * Constructeur de la classe ProgressDialogPerso
     * @param context le context de l'activity
     */
    public ProgressDialogPerso(Context context) {
        super(context);
    }

    /**
     * Affichage des informations sur l'envoi
     * @param size la taille du téléchargement
     * @param titre le titre de la musique en cour d'envoi
     */
    public void set(int size,String titre)
    {
        //Affichage du titre de la musique qui est entraint d'être ajouter
        setMessage("Envoi " + titre);
        //Permet d'afficher la taille en octet
        setMax(size / 1024);
        //On ne peut pas fermer la fenêtre pendant l'envoi
        setCancelable(false);
    }
}
