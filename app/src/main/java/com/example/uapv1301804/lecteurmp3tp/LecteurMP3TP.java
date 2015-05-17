package com.example.uapv1301804.lecteurmp3tp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import GestionMP3.GestionMP3;
import GestionMP3.FichierMP3;
import GestionMP3.EnvoyerMusique;
import GestionMP3.CommandeVocal;
import Ice.InitializationData;

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
    //Pour communiquer avec ICEStorm
    private Ice.Communicator communicator = null;
    //La boite de dialogue pour ajouter un mp3
    private final static int ID_AJOUTER_MP3_DIALOG = 0;
    //Commande vocale
    private CommandeVocal commandeVocal;

    private String titre;
    private String artiste;
    private String album;
    private String compo;



    /**
     * Méthode permettant la création d'une activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iceStorm();
        gestionMP3 = new GestionMP3(communicator,this);
        setContentView(R.layout.activity_lecteur_mp3_tp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        init();
        ImageButton ajouter = (ImageButton) findViewById(R.id.btAjouter);
        // add button listener
        ajouter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                dialogAjouter();
            }
        });
        //Commandes vocales
        commandeVocal = new CommandeVocal(this,gestionMP3);
    }

    private void iceStorm() {
        try {
            InitializationData initData = new InitializationData();
            initData.properties = Ice.Util.createProperties();
            initData.properties.setProperty("Ice.Default.Router", "Glacier2/router:tcp -h shoud.ovh -p 5036");
            initData.properties.setProperty("Ice.ACM.Client", "0");
            initData.properties.setProperty("Ice.RetryIntervals", "-1");
            initData.properties.setProperty("CallbackAdapter.Router", "Glacier2/router:tcp -h shoud.ovh -p 5036");
            communicator = Ice.Util.initialize(initData);
        } catch (Exception e) {
            Log.e("IceStorm erreur = ", e.toString());
        }
    }


    /**
     * Fenetre permettant de rajouter une musique
     */
    public void dialogAjouter()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);


        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alertDialogView = inflater.inflate(R.layout.ajouter_mp3_dialog, null);
        alertDialogBuilder.setTitle("Ajouter MP3");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setView(alertDialogView);
        alertDialogBuilder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //On récupère les EditTexts
                EditText etTitre = (EditText)alertDialogView.findViewById(R.id.etTitre);
                EditText etArtiste = (EditText)alertDialogView.findViewById(R.id.etArtiste);
                EditText etAlbum = (EditText)alertDialogView.findViewById(R.id.etAlbum);
                EditText etCompo = (EditText)alertDialogView.findViewById(R.id.etCompo);
                //On met à jour les informations de la musique
                titre = etTitre.getText().toString();
                artiste = etArtiste.getText().toString();
                album = etAlbum.getText().toString();
                compo = etCompo.getText().toString();
                ChoisirEnvoyer();
            }
        });
        //Création de la boite de dialogue
        AlertDialog alertDialog = alertDialogBuilder.create();
        //Afficher la boite de dialogue
        alertDialog.show();
    }

    public void init()
    {
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

        AutoCompleteTextView recherche = (AutoCompleteTextView) findViewById(R.id.tvRecherche);
        recherche.setThreshold(2);

        //On met la liste de musique
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listMusique);
        //On le rajoute à l'adaptateur
        recherche.setAdapter(adapter);
        //Permet de détecter quand on appui sur une touche quand on fait une recherche
        //pour rafraichir la recherche un live
        recherche.setOnKeyListener(this);
    }

    public void rafraichir()
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

        AutoCompleteTextView recherche = (AutoCompleteTextView) findViewById(R.id.tvRecherche);
        recherche.setThreshold(2);
        //On met la liste de musique
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listMusique);
        //On le rajoute à l'adaptateur
        recherche.setAdapter(adapter);
    }

    public void btrafraichir(View controlView)
    {
        rafraichir();
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
                intent.putExtra("FichierMP3", new FichierMP3(gestionMP3.getTitre(), gestionMP3.getArtiste(), gestionMP3.getAlbum(), gestionMP3.getCompo()));
                startActivity(intent);
            }
        });
    }

    /**
     * Méthode permettant de chercher un mp3 à envoyer
     */
    public void ChoisirEnvoyer()
    {
        if(!titre.isEmpty())
        {
            Intent myIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            myIntent.setType("audio/*");
            myIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(myIntent, 0);
        }
    }

    /**
     * Permet de récupérer le résultat de la fenetre permettant de rajouter une musique
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 0)
            {
                String chemin = data.getData().getPath();
                EnvoyerMusique envoyerMusique = new EnvoyerMusique(chemin, titre, artiste, album, compo, new GestionMP3.ProgressDialogPerso(this), gestionMP3);
                envoyerMusique.execute();
                titre = "";
                artiste = "";
                album= "";
                compo= "";
            }
        }
    }

    /**
     * Permet de supprimer une musique
     * @param controlView
     */
    public void btSupprimer(View controlView)
    {
        if(gestionMP3.supprimer())
            rafraichir();
    }

    public void btMicro(View controlView)
    {
        Button controlButton = (Button)findViewById(R.id.playStop);
        gestionMP3.pause();
        controlButton.setText("Play");
        commandeVocal.enregistrement();
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
    public ArrayList<String> getListMusique()
    {
        return listMusique;
    }
}
