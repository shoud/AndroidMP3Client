package com.example.uapv1301804.lecteurmp3tp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import GestionMP3.GestionMP3;
import GestionMP3.FichierMP3;
import GestionMP3.EnvoyerMusique;
import GestionMP3.MyProgressDialog;

/**
 * Activité principale de l'application, elle permet :
 * De rechercher un mp3
 * D'afficher la liste de mp3
 * De lire un mp3
 * De mettre sur pause
 * De relancer un mp3
 * D'afficher les informations du mp3 en cour
 * D'ajouter un mp3
 * De faire une comman,de vocale
 */
public class LecteurMP3TP extends Activity implements View.OnKeyListener {

    //Objet permettant de gérer les mp3, lien avec le serveur distant
    private GestionMP3 gestionMP3 = null;
    //La liste des musiques sur le serveur
    private ArrayList<String> listMusique = new ArrayList<String>();
    //La liste des fichiers corespondant à une recherche
    private ArrayList<String> listRecherche = new ArrayList<String>();
    //L'adaptateur
    private ArrayAdapter<String> adapter;
    //Le context
    private Context context;

    /**
     * Méthode permettant la création d'une activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestionMP3 = new GestionMP3();
       setContentView(R.layout.activity_lecteur_mp3_tp);

        //Récupération de la listeview de musique
        final ListView lv = (ListView)findViewById(R.id.lvMusique);
        context = this;
        //On récupère la liste de musique
        for(String morceau : gestionMP3.lister())
        {
            if(!listMusique.contains(morceau))
                listMusique.add(morceau);
        }
        //On met à jour la liste de musique
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listMusique);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long id)
            {
                try {
                    gestionMP3.jouer((String) lv.getItemAtPosition(position));
                    Button controlButton = (Button)findViewById(R.id.playStop);
                    controlButton.setText("Stop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //EditText permettant de faire une recherche
        EditText editText1 = (EditText) findViewById(R.id.tvRecherche);
        //Permet de détecter quand on appui sur une touche quand on fait une recherche
        //pour rafraichir la recherche un live
        editText1.setOnKeyListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lecteur_mp3_t, menu);
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
     * Gestion du bouton play/pause pour pouvoir arrêter la musique
     * @param controlView
     * @throws IOException
     */
    public void btPlayStop(View controlView) throws IOException {
        Button controlButton = (Button)findViewById(R.id.playStop);

        if(controlButton.getText().equals("Play")) {
            gestionMP3.play();
            controlButton.setText("Stop");
        } else {
            gestionMP3.pause();
            controlButton.setText("Play");
        }

    }

    /**
     * Gestion du bouton permettant d'afficher les informations
     * de la musique en cour de lecture
     * @param controlView
     */
    public void btInformation(View controlView)
    {
        //Récupération du bouton
        final Button loginButton = (Button) findViewById(R.id.btInfo);
        //Lancement d'une nouvelle activité permettant d'afficher les informations
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LecteurMP3TP.this, ActivityMusique.class);
                intent.putExtra("FichierMP3", new FichierMP3(gestionMP3.getNom()));
                startActivity(intent);
            }
        });
    }

    /**
     * Gestion du bouton permettant de rajouter un musique
     * Lance une fenetre permettant de sélectionner une musique
     * @param controlView
     */
    public void btAjouter(View controlView)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        startActivityForResult(intent,0);
    }

    /**
     * Permet de récupérer le résultat de la fenetre permettant de rajouter une musique
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                String songPath = data.getData().getPath();
                String titre = "JeTest";
                EnvoyerMusique envoyerMusique = new EnvoyerMusique(songPath, titre, new MyProgressDialog(this), gestionMP3);
                envoyerMusique.execute();
            }
        }
    }

    /**
     * Permet de supprimer une musique
     * @param controlView
     */
    public void btSupprimer(View controlView)
    {
        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageBtSupprimer);
        if(gestionMP3.supprimer())
        {
            //Récupération de la listeview de musique
            ListView lv = (ListView)findViewById(R.id.lvMusique);
            context = this;
            listMusique.clear();
            //On récupère la liste de musique
            for(String morceau : gestionMP3.lister())
            {
                if(!listMusique.contains(morceau))
                    listMusique.add(morceau);
            }
            adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listMusique);
            lv.setAdapter(adapter);
        }
    }

    /**
     * Méthode permettant de detecter l'appui sur une touche
     * Utilisé pour réfraichir un live la liste des solutions en fonction d'une recherche
     * @param view
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        //On récupère le editText qui a déclenché l'évenement
        EditText myEditText = (EditText) view;
        ListView lv = (ListView)findViewById(R.id.lvMusique);

        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity", "Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.tvRecherche :
                    {
                        String recherche = myEditText.getText().toString();

                        if(listMusique.contains(recherche))
                        {
                            listRecherche.clear();
                            listRecherche.add(recherche);
                            adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listRecherche);
                            lv.setAdapter(adapter);
                        }
                        else
                        {
                            adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listMusique);
                            lv.setAdapter(adapter);
                        }
                        break;
                    }
                }
                return true;
            }

        }
        return false;
    }
}
