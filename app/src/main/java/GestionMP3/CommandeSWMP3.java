package GestionMP3;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 */
public class CommandeSWMP3
{
    public static String getAction(String phrase)
    {
        String namespace = "http://webservice/";
        String method = "getAction";
        SoapObject request = new SoapObject(namespace, method);
        request.addProperty("phrase", phrase);
        SoapSerializationEnvelope envelope = getEnvelope(request);

        String soapAction = "\"" + namespace + method + "\"";
        HttpTransportSE ht = getHttp();
        try
        {
            ht.call(soapAction, envelope);
            SoapPrimitive resultString = (SoapPrimitive)envelope.getResponse();
            return resultString.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTitre(String phrase)
    {
        String namespace = "http://webservice/";
        String method = "getTitre";
        SoapObject request = new SoapObject(namespace, method);
        request.addProperty("phrase", phrase);

        SoapSerializationEnvelope envelope = getEnvelope(request);

        String soapAction = "\"" + namespace + method + "\"";
        HttpTransportSE ht = getHttp();
        try
        {
            ht.call(soapAction, envelope);
            SoapPrimitive resultString = (SoapPrimitive)envelope.getResponse();
            return resultString.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private final static SoapSerializationEnvelope getEnvelope(SoapObject request)
    {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        return envelope;
    }

    private final static HttpTransportSE getHttp()
    {
        String requestURL = "http://shoud.ovh:8080/CommandeMP3/CommandeMP3WS";
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,requestURL,60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

}
