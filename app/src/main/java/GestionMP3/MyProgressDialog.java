package GestionMP3;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog{

    public MyProgressDialog(Context context) {
        super(context);
    }

    public void set(int size,String titre) {
        setMessage("Envoi " + titre);
        setMax(size / 1024);
    }
}
