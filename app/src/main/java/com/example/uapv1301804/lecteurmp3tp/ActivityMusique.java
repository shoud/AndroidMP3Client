package com.example.uapv1301804.lecteurmp3tp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import GestionMP3.FichierMP3;

/**
 * Activité permettant d'afficher toutes les informations d'un mp3
 */
public class ActivityMusique extends Activity {

    private FichierMP3 fichierMP3 = null;
    private TextView tvTitre = null;
    private TextView tvAlbum = null;
    private TextView tvCompo = null;
    private TextView tvChanteur = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_musique);

        fichierMP3 = (FichierMP3) getIntent().getSerializableExtra("FichierMP3");
        System.out.println(fichierMP3.getNom());


        tvTitre = (TextView) findViewById(R.id.tTitre);
        tvAlbum = (TextView) findViewById(R.id.tAlbum);
        tvCompo = (TextView) findViewById(R.id.tCompo);
        tvChanteur = (TextView) findViewById(R.id.tChanteur);

        if(fichierMP3 != null)
        {
           tvTitre.setText(fichierMP3.getNom());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_musique, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Permet de revenir sur la fenêtre principale
     * @param controlView
     */
    public void btRetourInfo(View controlView)
    {
        final Button btRetour = (Button) findViewById(R.id.btRetourInfo);
        btRetour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMusique.this, LecteurMP3TP.class);
                startActivity(intent);
            }
        });
    }

    public void btSupprimer(View controlView)
    {
        final Button btSupprimer = (Button) findViewById(R.id.btSupprimer);
        btSupprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMusique.this, LecteurMP3TP.class);
                startActivity(intent);
            }
        });
    }
}
