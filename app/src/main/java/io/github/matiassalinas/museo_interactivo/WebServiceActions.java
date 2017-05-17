package io.github.matiassalinas.museo_interactivo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by matias on 02-05-17.
 */

public class WebServiceActions {
    private static String link = "http://museointeractivo.msalinas.cl/consultas.php";
    private static int cantObjetivos = 0;

    public static ArrayList<Zona> getZonas(int idMuseo) throws IOException {
        String opcion = "getZonas";
        String urlParameters = "Opcion="+opcion+"&idMuseo="+idMuseo;
        JSONArray json = getArrayFromServer(link,urlParameters);
        ArrayList<Zona> zonas = new ArrayList<>();
        for(int i = 0; i<json.length();i++){
            try {
                JSONObject z = json.getJSONObject(i);
                cantObjetivos += Integer.parseInt((String) z.get("count"));
                Zona zona = new Zona(Integer.parseInt((String) z.get("idZona")),
                                    idMuseo,
                                    (String) z.get("nombre"),
                                    (String) z.get("descripcion"),
                                    (String) z.get("imagen"),
                                    Integer.parseInt((String) z.get("count")));
                zonas.add(zona);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return zonas;
    }

    public static ArrayList<Objetivo> getObjetivos(int idZona) throws IOException {
        String opcion = "getObjetivos";
        String urlParameters = "Opcion="+opcion+"&idZona="+idZona;
        JSONArray json = getArrayFromServer(link,urlParameters);
        ArrayList<Objetivo> objetivos = new ArrayList<>();
        for(int i = 0; i<json.length(); i++){
            try{
                JSONObject obj = json.getJSONObject(i);
                Objetivo objetivo = new Objetivo(Integer.parseInt((String) obj.get("idObjetivo")),
                                                idZona,
                                                (String) obj.get("titulo"),
                                                (String) obj.get("texto"),
                                                (String) obj.get("imagen"));
                objetivos.add(objetivo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return objetivos;
    }

    public static ArrayList<Historial> getHistorial(String idEntrada) throws IOException{
        String opcion = "getHistorial";
        String urlParameters = "Opcion="+opcion+"&idEntrada="+idEntrada;
        JSONArray json = getArrayFromServer(link,urlParameters);
        ArrayList<Historial> historial = new ArrayList<>();
        for(int i = 0; i<json.length();i++){
            try {
                JSONObject z = json.getJSONObject(i);
                Historial historia = new Historial(Integer.parseInt((String) z.get("idObjetivo")),
                                    Integer.parseInt((String) z.get("puntaje")),
                                    Integer.parseInt((String) z.get("idZona")));
                historial.add(historia);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return historial;
    }

    public static void objetivoCompletado(String idEntrada, int idObjetivo, int puntaje){
        String opcion = "objetivoCompletado";
        String urlParameters = "Opcion="+opcion+"&idEntrada="+idEntrada+"&idObjetivo="+idObjetivo+"&puntaje="+String.valueOf(puntaje);
        getArrayFromServer(link,urlParameters);
    }

    public static ArrayList<String> iniciarSesion(String idEntrada) throws JSONException {
        String opcion = "iniciarSesion";
        String urlParameters = "Opcion="+opcion+"&idEntrada="+idEntrada;
        JSONArray json = getArrayFromServer(link,urlParameters);
        ArrayList<String> array = new ArrayList<>();
        Log.d("JSON", String.valueOf(json));
        if(json.length() == 0) return null;
        JSONObject z = json.getJSONObject(0);
        array.add(0, (String) z.get("idUsuario"));
        array.add(1, (String) z.get("idMuseo"));
        array.add(2, (String) z.get("nombre"));
        array.add(3, (String) z.get("correo"));
        array.add(4, (String) z.get("direccion"));
        array.add(5, (String) z.get("telefono"));
        return array;
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

    public static int getCantObjetivos(){
        return cantObjetivos;
    }
}
