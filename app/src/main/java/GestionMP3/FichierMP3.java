package GestionMP3;

import java.io.Serializable;

/**
 * Classe représentant un fichier mp3
 */
public class FichierMP3 implements Serializable
{
    //le titre de la musique
    private String titre = null;
    //L'artiste de la musique
    private String artiste = null;
    //L'album de la musique
    private String album = null;
    //Le compositeur de la musique
    private String compo = null;

    /**
     * Constructeur de FichierMP3 permet de créer une représentation d'un mp3
     * @param titre le titre du mp3
     * @param artiste l'artiste du mp3
     * @param album l'album du mp3
     * @param compo le compositeur du mp3
     */
    public FichierMP3(String titre, String artiste, String album, String compo)
    {
        //Récupération du titre de la musique
        this.titre = titre;
        //Récupération de l'artiste de la musique
        this.artiste =artiste;
        //Récupération de l'album de la musique
        this.album = album;
        //Récupération du compositeur de la musique
        this.compo = compo;
    }

    /**
     * Renvoi le titre de la musique
     * @return titre le titre de la musique
     */
    public String getTitre()
    {
        return this.titre;
    }

    /**
     * Renvoi l'artiste de la musique
     * @return artiste L'artiste de la musique
     */
    public String getArtiste()
    {
        return this.artiste;
    }

    /**
     * Renvoi l'album de la musique
     * @return album l'album de la musique
     */
    public String getAlbum()
    {
        return this.album;
    }

    /**
     * Renvoi le compositeur de la musique
     * @return compo Le compositeur de la musique
     */
    public String getCompo()
    {
        return this.compo;
    }
}
