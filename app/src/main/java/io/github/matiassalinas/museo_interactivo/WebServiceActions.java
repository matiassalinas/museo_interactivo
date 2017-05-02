package io.github.matiassalinas.museo_interactivo;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by matias on 02-05-17.
 */

public class WebServiceActions {
    private static String link = "http://museointeractivo.msalinas.cl/consultas.php";

    public static JSONArray getZonas(int idMuseo) throws IOException {
        String opcion = "getZonas";
        String urlParameters = "Opcion="+opcion+"&idMuseo="+idMuseo;
        return getArrayFromServer(link,urlParameters);
    }

    private static JSONArray getArrayFromServer(String link, String urlParameters){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //longitud de datos que son enviados por el POST
            connection.setRequestProperty("Content-Length",""+Integer.toString(urlParameters.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            //  Here you read any answer from server.
            BufferedReader serverAnswer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = serverAnswer.readLine()) != null) {
                sb.append(line);
            }
            wr.close();
            return (new JSONArray(sb.toString()));

        }catch(Exception e){

        }
        return null;
    }
}
