package GestionMP3;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.net.Proxy;

/**
 * Classe permettant de communiquer avec le service web
 * qui permet de traduire une phrase en une action et un titre
 */
public class CommandeSWMP3
{
    //Le namespace du service web
    static String namespace = "http://webservice/";


    /**
     * Méthode permettant d'appeller la méthode getAction du service web
     * @param phrase La phrase a faire traduire par le service web
     * @return l'action que le service web a trouvé par rapport à la phrase
     */
    public static String getAction(String phrase)
    {
        //La méthode a utiliser sur le service web
        String methode = "getAction";
        //Création de la requet
        SoapObject soapObject = new SoapObject(namespace, methode);
        //Le paramètre à envoyer avec la méthode
        soapObject.addProperty("phrase", phrase);
        //Création de l'enveloppe pour la requete
        SoapSerializationEnvelope soapSerializationEnvelope = getSoapSerializationEnvelope(soapObject);
        String soapAction = "\"" + namespace + methode + "\"";
        HttpTransportSE httpTransportSE = getHttpTransportSE();
        try
        {
            //Envoi de la requette au service web
            httpTransportSE.call(soapAction, soapSerializationEnvelope);
            //Récupération du résulta du servcie web
            SoapPrimitive soapPrimitive = (SoapPrimitive)soapSerializationEnvelope.getResponse();
            //Retourne l'action trouvé par le service web
            return soapPrimitive.toString();
        }
        catch (Exception e)
        {
            //Si il y a une erreur
            e.printStackTrace();
        }
        return null;
    }

    public static String getTitre(String phrase)
    {
        //La méthode a utiliser sur le service web
        String methode = "getTitre";
        //Création de la requet
        SoapObject soapObject = new SoapObject(namespace, methode);
        //Le paramètre à envoyer avec la méthode
        soapObject.addProperty("phrase", phrase);
        //Création de l'enveloppe pour la requete
        SoapSerializationEnvelope soapSerializationEnvelope = getSoapSerializationEnvelope(soapObject);
        String soapAction = "\"" + namespace + methode + "\"";
        HttpTransportSE httpTransportSE = getHttpTransportSE();
        try
        {
            //Envoi de la requette au service web
            httpTransportSE.call(soapAction, soapSerializationEnvelope);
            //Récupération du résulta du servcie web
            SoapPrimitive soapPrimitive = (SoapPrimitive)soapSerializationEnvelope.getResponse();
            //Retourne l'action trouvé par le service web
            return soapPrimitive.toString();
        }
        catch (Exception e)
        {
            //Si il y a une erreur
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Création de l'enveloppe pour le service web
     * @param soapObject la requette
     * @return l'enveloppe
     */
    private final static SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject soapObject)
    {
        //création de l'enveloppe
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapSerializationEnvelope.implicitTypes = true;
        soapSerializationEnvelope.setAddAdornments(false);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        //Retourne l'enveloppe
        return soapSerializationEnvelope;
    }

    /**
     * Permet de construire un requette http
     * @return la requette http
     */
    private final static HttpTransportSE getHttpTransportSE()
    {
        //L'url du service web
        String urlWS = "http://shoud.ovh:8080/CommandeMP3/CommandeMP3WS";
        //Création de la requette http
        HttpTransportSE httpTransportSE = new HttpTransportSE(Proxy.NO_PROXY,urlWS,60000);
        //Activation du debug
        httpTransportSE.debug = true;
        //La version xml
        httpTransportSE.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        //On retourne l'objet permettant de faire la requette http
        return httpTransportSE;
    }
}
