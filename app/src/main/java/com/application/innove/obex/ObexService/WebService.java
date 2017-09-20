package com.application.innove.obex.ObexService;

import android.os.StrictMode;
import android.util.Log;

import com.application.innove.obex.ObexFragments.DocumentsFragment;
import com.application.innove.obex.Utilityclass.OBEXConstants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOError;
import java.io.IOException;
import java.net.ConnectException;

public class WebService  {
    private String TAG = WebService.class.getSimpleName();
    public static boolean fIsErrorInService = true;
    private static String NAMESPACE = OBEXConstants.namspace;
    private final String fLiabraryWebServiceUrl = OBEXConstants.localservice;


    public String callServices(String p_Json, String ftitle) {
        try {
            SoapObject soapRequeset = new SoapObject(NAMESPACE, ftitle);
            soapRequeset.addProperty(getPropertyInfo("Info", p_Json));
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(soapRequeset);
            return callLibWebService(ftitle, envelope);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public String callGetServices(String ftitle) {
        try {
            SoapObject soapRequeset = new SoapObject(NAMESPACE, ftitle);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(soapRequeset);
            return callLibWebService(ftitle, envelope);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private PropertyInfo getPropertyInfo(String p_ParamName, String p_ParamValue) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName(p_ParamName);
        propertyInfo.setType(String.class);
        propertyInfo.setValue(p_ParamValue);
        return propertyInfo;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private String callLibWebService(String p_ServiceName, SoapSerializationEnvelope p_envelope) {
        String wsResponce;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            fIsErrorInService = false;
            HttpTransportSE aht = new HttpTransportSE(fLiabraryWebServiceUrl, 1000 * 60 * 2);
            aht.debug = true;
            aht.call(NAMESPACE + p_ServiceName, p_envelope);
            Log.e(TAG, "Url : " + fLiabraryWebServiceUrl);
            Log.e(TAG, "Url Method : " + p_ServiceName);
            if (p_envelope.bodyIn instanceof SoapFault) {
                wsResponce = ((SoapFault) p_envelope.bodyIn).faultstring;
                Log.e(TAG, "WSresponce : " + wsResponce.toString());
            } else {
                // SoapObject resultsRequestSOAP = (SoapObject) p_envelope.bodyIn;
                Object response = p_envelope.getResponse();
                wsResponce = response.toString();
                Log.e(TAG, "WSresponce : " + wsResponce.toString());
            }
            
           /* AndroidHttpTransport httpTranspot = new AndroidHttpTransport(fLiabraryWebServiceUrl);
            httpTranspot.call(NAMESPACE + p_ServiceName, p_envelope);
            SoapPrimitive soapResponse = (SoapPrimitive) p_envelope.getResponse();
            return soapResponse.toString();*/
            return wsResponce;

        } catch (ConnectException ce) {
            fIsErrorInService = true;
        } catch (IOException ioEx) {
            fIsErrorInService = true;

        } catch (IOError db) {
            fIsErrorInService = true;
        } catch (XmlPullParserException xmlppEx) {
            fIsErrorInService = true;
        } catch (IllegalArgumentException lE) {
            fIsErrorInService = true;
        } catch (Exception e) {
            fIsErrorInService = true;
        }

        return null;
    }


}
