package info.androidhive.slidingmenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import info.androidhive.slidingmenu.model.*;
import android.util.Log;

public class WebServiceCall {

	private static final String URL = "http://smartfridgeapp.com/Service1.asmx";
	private static final String NAMESPACE = "http://smartfridgeapp.com/webservices/";
	//String URL="http://fridgecloudservice.cloudapp.net/FridgeService.svc?wsdl";
	
	public List<String> CallGetWebservice(String MethodName , List<WebServiceParameterItem> Params)
	{
		 String SOAP_ACTION = "http://smartfridgeapp.com/webservices/"+ MethodName;
	     String METHOD_NAME = MethodName;
	     List<String> MYproperties = new ArrayList<String>();
	     
         List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();
         WebServiceParameterItem property = new WebServiceParameterItem("","");
	     
         SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
         for (WebServiceParameterItem param : Params) { //methodun parametreleri ekleniyor..
        	 
        	 request.addProperty(param.getName(),param.getValue());
		}
         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
         envelope.dotNet = true;
         envelope.setOutputSoapObject(request);
         AndroidHttpTransport httpTransport = new AndroidHttpTransport(URL);
         SoapPrimitive result = null;
       
         try {
             httpTransport.call(SOAP_ACTION, envelope);     
             SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
             
             SoapObject DataSet = (SoapObject) resultsRequestSOAP.getProperty(0);
             SoapObject Diffgram = (SoapObject) DataSet.getProperty(1);
             SoapObject NewDataSet=new SoapObject("", "");
             if(Diffgram.getPropertyCount()>0)
             {
              NewDataSet= (SoapObject) Diffgram.getProperty(0); 
              
              int count =0;
              for (int i = 0; i < NewDataSet.getPropertyCount(); i++) {
 	             SoapObject table1 = (SoapObject) NewDataSet.getProperty(i); 
 	             for (int j = 0; j < table1.getPropertyCount(); j++) {
 	            	 property.setName(" ");//(5*i)+j olmalý ve MYproperties sonucu dogru ona göre yapýlmalý.
 	            	 property.setValue(table1.getProperty(j).toString().trim());// member tablosundaki ilk kayýt, property(1) ikinci kayýt
 	            	 MYproperties.add(table1.getProperty(j).toString().trim());
 	            	 count++;
 	             }
              }
             }
             /*table dan birsey dönmez ise patlýyor.*/

             
             
   
         } catch (IOException e) {

         Log.i("TAG", e.getMessage());
         } catch (XmlPullParserException e) {
        	 Log.i("TAG", e.getMessage());
         } 
         return MYproperties;
	}
	
	public void CallInsertUpdateWebservice(String MethodName , List<WebServiceParameterItem> Params)
	{
		 String SOAP_ACTION = "http://smartfridgeapp.com/webservices/"+ MethodName;
	     String METHOD_NAME = MethodName;

         List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();
         WebServiceParameterItem property = new WebServiceParameterItem("","");
	     
         SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
         for (WebServiceParameterItem param : Params) { //methodun parametreleri ekleniyor..
        	 
        	 request.addProperty(param.getName(),param.getValue());
		}
         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
         envelope.dotNet = true;
         envelope.setOutputSoapObject(request);
         AndroidHttpTransport httpTransport = new AndroidHttpTransport(URL);
         SoapPrimitive result = null;
         try {
             httpTransport.call(SOAP_ACTION, envelope);     
                
         } catch (IOException e) {

         Log.i("TAG", e.getMessage());
         } catch (XmlPullParserException e) {
        	 Log.i("TAG", e.getMessage());
         } 
	}
}
