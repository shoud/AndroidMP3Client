package GestionMP3;

import java.io.Serializable;

public class FichierMP3 implements Serializable
{
    private String nom = null;

    public FichierMP3(String nom)
    {
        this.nom = nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }
    public String getNom()
    {
        return this.nom;
    }

}
