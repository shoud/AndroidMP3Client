package GestionMP3;

import java.io.Serializable;

public class FichierMP3 implements Serializable
{
    private String titre = null;
    private String artiste = null;
    private String album = null;
    private String compo = null;

    public FichierMP3(String titre, String artiste, String album, String compo)
    {
        this.titre = titre;
        this.artiste =artiste;
        this.album = album;
        this.compo = compo;
    }

    public String getTitre()
    {
        return this.titre;
    }
    public String getArtiste()
    {
        return this.artiste;
    }
    public String getAlbum()
    {
        return this.album;
    }
    public String getCompo()
    {
        return this.compo;
    }
}
