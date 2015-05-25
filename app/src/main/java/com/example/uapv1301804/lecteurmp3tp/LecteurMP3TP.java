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
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import GestionMP3.GestionMP3;
import GestionMP3.EnvoyerMusique;
import GestionMP3.CommandeVocal;
import GestionMP3.ProgressDialogPerso;

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
    //titre de la musique
    private String titre;
    //l'artiste de la musique
    private String artiste;
    //l'album de la musique
    private String album;
    //le compositeur de la musique
    private String compo;
    //L'adresse du serveur
    private String serveurAdresse = null;

    /**
     * Méthode permettant la création d'une activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Layoute de l'activité
        setContentView(R.layout.activity_lecteur_mp3_tp);
        //Pour pouvoir utiliser le service web
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Récupération du bouton ajouter
        ImageButton ajouter = (ImageButton) findViewById(R.id.btAjouter);
        //Ajoute le bouton ajouter au listener pour qu'il lance une boite de dialogue
        ajouter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                dialogAjouter();
            }
        });
        //récupération du bouton supprimer
        ImageButton supprimer = (ImageButton) findViewById(R.id.btSupprimer);
        //Ajout du bouton supprimer dans le listener pour qu'il lance une boite de dialogue
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogSupprimer();
            }
        });
        //Récupération du bouton information
        ImageButton information = (ImageButton) findViewById(R.id.btInformation);
        //Ajout du bouton supprimer dans le listener pour qu'il lance une boite de dialogue
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogInformation();
            }
        });

        //Création d'un créateur de dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //On lui dit qu'il doit utiliser le xml connexion_mp3_dialog
        final View alertDialogView = inflater.inflate(R.layout.connexion_mp3_dialog, null);
        //Le titre de la fenêtre sera ajouter mp3
        alertDialogBuilder.setTitle("IP Serveur");
        //On peut sortir de la fenêtre
        alertDialogBuilder.setCancelable(false);
        //On rajoute la vue au dialogue
        alertDialogBuilder.setView(alertDialogView);
        //Le bouton positife s'appellra Connexion
        alertDialogBuilder.setPositiveButton("Connexion", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Récupération EditText  serveur
                EditText serveur = (EditText)alertDialogView.findViewById(R.id.etConnexion);
                //On met à jour les informations de la musique
                String serveurAdresse = serveur.getText().toString();
                connexion(serveurAdresse);
            }
        });
        //Création de la boite de dialogue
        AlertDialog alertDialog = alertDialogBuilder.create();
        //Afficher la boite de dialogue
        alertDialog.show();
    }

    /**
     * Méthode permettant de se connecter au routeur Glacier
     */
    private void iceStorm() {
        try {
            InitializationData initializationData = new InitializationData();
            initializationData.properties = Ice.Util.createProperties();
            initializationData.properties.setProperty("Ice.Default.Router", "Glacier2/router:tcp -h "+serveurAdresse+" -p 5036");
            initializationData.properties.setProperty("Ice.ACM.Client", "0");
            initializationData.properties.setProperty("Ice.RetryIntervals", "-1");
            initializationData.properties.setProperty("CallbackAdapter.Router", "Glacier2/router:tcp -h "+serveurAdresse+" -p 5036");
            //Création du comminucator
            communicator = Ice.Util.initialize(initializationData);
        } catch (Exception e) {
            Log.e("IceStorm erreur = ", e.toString());
        }
    }

    /**
     * Méthode permettant de se connecter au serveur
     * @param serveurAdresse
     */
    private void connexion(String serveurAdresse)
    {
        //Récupération de l'adresse du serveur
        this.serveurAdresse = serveurAdresse;
        //initialisation de ICEStorm
        iceStorm();
        //Création du gestionnaire de mp3
        gestionMP3 = new GestionMP3(communicator,this,serveurAdresse);
        //Permet de récupérer la liste de musique
        init();
        //Initialisation des commandes vocales
        commandeVocal = new CommandeVocal(this,gestionMP3);
    }


    /**
     * Fenetre permettant de rajouter une musique
     */
    public void dialogAjouter()
    {
        //Création d'un créateur de dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //On lui dit qu'il doit utiliser le xml ajouter_mp3_dialog
        final View alertDialogView = inflater.inflate(R.layout.ajouter_mp3_dialog, null);
        //Le titre de la fenêtre sera ajouter mp3
        alertDialogBuilder.setTitle("Ajouter MP3");
        //On peut sortir de la fenêtre
        alertDialogBuilder.setCancelable(false);
        //On rajoute la vue au dialogue
        alertDialogBuilder.setView(alertDialogView);
        //Le bouton positife s'appellra Envoyer
        alertDialogBuilder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Récupération EditText  titre
                EditText etTitre = (EditText)alertDialogView.findViewById(R.id.etTitre);
                //Récupération EditText artiste
                EditText etArtiste = (EditText)alertDialogView.findViewById(R.id.etArtiste);
                //Récupération EditText album
                EditText etAlbum = (EditText)alertDialogView.findViewById(R.id.etAlbum);
                //Récupération EditText compositeur
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

    /**
     * Fenetre permettant de supprimer une musique
     */
    public void dialogSupprimer()
    {
        //Création d'un créateur de dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //On dit quel vu il faut utiliser
        final View alertDialogView = inflater.inflate(R.layout.supprimer_mp3_dialog, null);
        //Le titre de le fenêtre
        alertDialogBuilder.setTitle("Supprimer MP3");
        //Peu être fermé
        alertDialogBuilder.setCancelable(false);
        //On rajoute la vue choisir
        alertDialogBuilder.setView(alertDialogView);
        //Récupération de la barre de recherche
        final AutoCompleteTextView rechercheSupprimer = (AutoCompleteTextView) alertDialogView.findViewById(R.id.rechercheSupprimer);
        rechercheSupprimer.setThreshold(2);
        //On met la liste de musique
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listMusique);
        //On le rajoute à l'adaptateur
        rechercheSupprimer.setAdapter(adapter);
        //Création d'un bouton pour supprimer la musique choisie
        alertDialogBuilder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //On récupère le titre de la musique a supprimer
                String titre = rechercheSupprimer.getText().toString();
                //Si le titre est pas = à null et présent sur le serveur
                if(gestionMP3.rechercher(titre))
                {
                    //On supprime la musique du serveur
                    gestionMP3.supprimer(titre);
                    //On rafraichie la liste de musique
                    rafraichir();
                }

            }
        });
        //Création de la boite de dialogue
        AlertDialog alertDialog = alertDialogBuilder.create();
        //Afficher la boite de dialogue
        alertDialog.show();
    }

    /**
     * Fenetre permettant de voir des information sur une musique
     */
    public void dialogInformation()
    {
        //Création d'un créateur de dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //La vue que l'on souhaite utiliser
        final View alertDialogView = inflater.inflate(R.layout.information_mp3_dialog, null);
        //Le titre de la fenêtre
        alertDialogBuilder.setTitle("Information MP3");
        //On peut fermer la fenêtre
        alertDialogBuilder.setCancelable(false);
        //On rajoute la vue choisir
        alertDialogBuilder.setView(alertDialogView);

        //Récupération du TextVieux titre
        TextView tvtitre = (TextView) alertDialogView.findViewById(R.id.tvTitre);
        //Récupération du TextVieux artiste
        TextView tvartiste = (TextView) alertDialogView.findViewById(R.id.tvArtiste);
        //Récupération du TextVieux album
        TextView tvalbum = (TextView) alertDialogView.findViewById(R.id.tvAlbum);
        //Récupération du TextVieux compositeur
        TextView tvcompo = (TextView) alertDialogView.findViewById(R.id.tvCompositeur);
        //Récupération de la musique en cour de lecture
        String titre = gestionMP3.getTitre();
        //Mise à jour du champ titre
        tvtitre.setText("Titre : " + titre);
        //Mise à jour du champ album
        tvalbum.setText("Album : " + gestionMP3.getMp3().getAlbum(titre));
        //Mise à jour du champ artiste
        tvartiste.setText("Artiste : " + gestionMP3.getMp3().getArtiste(titre));
        //Mise à jour du champ compositeur
        tvcompo.setText("Compositeur : " + gestionMP3.getMp3().getCompo(titre));
        //Fermeture de la fenêtre quand on clique sur quitter
        alertDialogBuilder.setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}});
        //Création de la boite de dialogue
        AlertDialog alertDialog = alertDialogBuilder.create();
        //Afficher la boite de dialogue
        alertDialog.show();
    }

    /**
     * Méthode permettant d'initialiser l'interface, dont la liste de musique
     */
    public void init()
    {
        //Récupération de la listeview de musique
        final ListView lv = (ListView)findViewById(R.id.lvMusique);
        context = this;
        //On récupère la liste de musique
        for(String morceau : gestionMP3.lister())
        {
            //On met à jour la liste de musique
            if(!listMusique.contains(morceau))
                listMusique.add(morceau);
        }
        //On rajoute la liste de musique nouvellement créé
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listMusique);
        lv.setAdapter(adapter);
        //Pour que quand on appui sur une musique elle se lance
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long id)
            {
                try
                {
                    //Lancer la musique séléctionné
                    gestionMP3.jouer((String) lv.getItemAtPosition(position));
                    //Récupération du bouton play / stop
                    Button controlButton = (Button)findViewById(R.id.playStop);
                    //Mettre à jour le bouton sur stop
                    controlButton.setText("Stop");
                } catch (IOException e)
                {
                    //Message d'erreur
                    e.printStackTrace();
                }
            }
        });
        //Récupération du champ recherche
        AutoCompleteTextView recherche = (AutoCompleteTextView) findViewById(R.id.tvRecherche);
        //Afficher les possibilité après avoir rentré 2 lettres
        recherche.setThreshold(2);
        //On met la liste de musique
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listMusique);
        //On le rajoute à l'adaptateur
        recherche.setAdapter(adapter);
        //Permet de détecter quand on appui sur une touche quand on fait une recherche
        //pour rafraichir la recherche un live
        recherche.setOnKeyListener(this);
    }

    /**
     * Méthode permettant de rafraichir la liste de musique
     * quand il y a un ajout ou une suppression
     */
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

    /**
     * Méthode permettant de rafraichir manuellement la liste de musique
     * @param controlView
     */
    public void btrafraichir(View controlView)
    {
        rafraichir();
    }

    /**
     * Pour android
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lecteur_mp3_t, menu);
        return true;
    }

    /**
     * Pour android
     * @param item
     * @return
     */
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
        //Récupération du bouton play/stop
        Button controlButton = (Button)findViewById(R.id.playStop);
        //On regard si le bouton == play
        if(controlButton.getText().equals("Play")) {
            //On lance la méthode jouer
            gestionMP3.play();
            //On change le bouton par stop
            controlButton.setText("Stop");
        } else
        {
            //Sinon on fait pause
            gestionMP3.pause();
            //On met le bouton à play
            controlButton.setText("Play");
        }

    }

    /**
     * Méthode permettant de chercher un mp3 à envoyer
     */
    public void ChoisirEnvoyer()
    {
        //Vérification si un titre a bien été rentré
        if(!titre.isEmpty())
        {
            //Pour récupéré un fichier dans android
            Intent myIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            myIntent.setType("audio/*");
            myIntent.setAction(Intent.ACTION_GET_CONTENT);
            //Pour récupré le résultat
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
        //Si un fichier a été selectionné
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 0)
            {
                //Récupération du chemin du fichier dans le téléphone
                String chemin = data.getData().getPath();
                //Création de l'objet pour envoyer une musique
                EnvoyerMusique envoyerMusique = new EnvoyerMusique(chemin, titre, artiste, album, compo, new ProgressDialogPerso(this), gestionMP3);
                //On envoi la musique
                envoyerMusique.execute();
                //On remet les variables à null
                titre = "";
                artiste = "";
                album= "";
                compo= "";
            }
        }
    }

    /**
     * Méthode permettant d'activer la reconaissance vocale
     * @param controlView
     */
    public void btMicro(View controlView)
    {
        //Récupération du bouton play/stop
        Button controlButton = (Button)findViewById(R.id.playStop);
        //On arrête la musique
        gestionMP3.pause();
        //On met le bouton sur play
        controlButton.setText("Play");
        //Lancement de l'enregistrement
        commandeVocal.enregistrement();
    }

    /**
     * Méthode permettant de renvoyer la liste de musique
     * @return listMusique La liste musique actuel
     */
    public ArrayList<String> getListMusique()
    {
        return listMusique;
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
        //Récupération de la liste
        ListView lv = (ListView)findViewById(R.id.lvMusique);
        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //Si un touche est appuyé
            if (!event.isShiftPressed()) {
                switch (view.getId()) {
                    //Si on veut rajouter d'autre objets
                    case R.id.tvRecherche :
                    {
                        //Récupération du text de la recherche
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
