package GestionMP3;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class EnvoyerMusique extends AsyncTask<Void, Integer, Void>
{
    private String chemin = null;
    private String titre = null;
    private MyProgressDialog dialog = null;
    private Serveur.mp3Prx mp3 = null;
    private int size = 0;
    private GestionMP3 gestionMP3 = null;

    public EnvoyerMusique(String chemin, String titre, MyProgressDialog dialog, GestionMP3 gestionMP3) {
        this.chemin = chemin;
        this.titre = titre;
        this.dialog = dialog;
        this.gestionMP3 = gestionMP3;
        this.mp3 = gestionMP3.getMp3();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            int offset = 0;
            File file = new File(chemin);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            int max = 512 * 1024;
            size = (int) file.length();
            byte[] bytes = new byte[size];
            int i = in.read(bytes);
            if (i == 0)
                return null;
            dialog.set(size, titre);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgressNumberFormat("%1d/%2dKB");
            while (offset < size) {
                int end = offset + max;
                if (end > size)
                    end = size;
                byte[] temp = Arrays.copyOfRange(bytes, offset, end);
                try {
                    publishProgress(offset);
                    mp3.envoyerMusique(titre, temp);
                } catch (Exception e) {
                    Log.e("upload", e.toString());
                }
                offset += end;
            }
            dialog.dismiss();
            gestionMP3.ajouter(titre);
        } catch (Exception e) {
            Log.e("upload", e.toString());
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values)
    {
        if (values[0] == 0) {
            dialog.setMessage("Uploading " + titre );
            dialog.setMax(size);
            dialog.show();
        }
        dialog.setProgress(values[0]);
    }

    @Override
    public void onPostExecute(Void result)
    {
        if (this.dialog != null)
            this.dialog.dismiss();
    }
}
