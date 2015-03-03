package GestionMP3;

/**
 * Created by uapv1301804 on 03/03/15.
 */
public class GestionMP3 {

    private Ice.Communicator ic = null;
    private Ice.ObjectPrx base = null;
    private Serveur.mp3Prx mp3 = null;

    /**
     * Constructeur de la classe GestionMP3
     * Il permet de créer la connexion avec le serveur.
     */
    public GestionMP3()
    {
        ic = Ice.Util.initialize();
        base = ic.stringToProxy("SimpleServeurMP3:default -h shoud.ovh -p 10000");
        mp3 = Serveur.mp3PrxHelper.checkedCast(base);
    }

    /**
     * Permet de lancer la lecture d'un MP3
     * @param nom Le nom du mp3 à lancer
     */
    public void jouer(String nom)
    {
        mp3.jouerMP3("test");
    }

    /**
     * Permet de rajouter un MP3
     */
    public void ajouter()
    {
        mp3.ajouterMP3("Toupou","test");
    }

    /**
     * Permet de rechercher un MP3
     * @param nom Le MP3 à rechercher
     * @return true Si le MP3 existe
     * @return false Si le MP3 existe pas
     */
    public boolean rechercher(String nom)
    {
        return mp3.rechercherMP3("Poutou");
    }

    /**
     * Permet de supprimer un MP3 sur le serveur
     * @param nom Le nom de l'MP3 à supprimer
     * @return true Si le mp3 a pu être supprimé
     * @return false si le mp3 n'a pas pu être supprimé.
     */
    public boolean supprimer(String nom)
    {
        return mp3.supprimerMP3("Poutou");
    }

    /**
     * Permet de récupérer la liste des MP3
     * @return listMp3 La liste en string des mp3
     */
    public String lister()
    {
        String listMp3 = "";
        for(String buf : mp3.listerMP3())
            listMp3 = listMp3 + buf;
        return listMp3;
    }
    protected void finalize ()
    {
        if(ic != null)
        {
            try
            {
                ic.destroy();
            }catch (Exception e)
            {
                System.err.println(e.getMessage());
                //System.out.println("=========================>>>" + e);
                return;
            }
        }
    }
}

